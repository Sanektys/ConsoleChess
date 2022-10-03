package Chess;

public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column,
                                     int toLine, int toColumn) {
        if (line == toLine && column == toColumn) {
            return false;
        }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) {
            return false;
        }
        if (chessBoard.board[toLine][toColumn] != null &&
                chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
            return false;
        }
        boolean isGoingVertical   = Math.abs(line - toLine)     == 2;
        boolean isGoingHorizontal = Math.abs(column - toColumn) == 2;
        if (isGoingVertical || isGoingHorizontal) {
            if (isGoingVertical) {
                return Math.abs(column - toColumn) == 1;
            } else {
                return Math.abs(line - toLine) == 1;
            }
        } else {
            return false;
        }
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
