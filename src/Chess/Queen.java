package Chess;

public class Queen extends ChessPiece {

    public Queen(String color) {
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
        boolean isGoingVertical   = Math.abs(line - toLine) > 0;
        boolean isGoingHorizontal = Math.abs(column - toColumn) > 0;
        if ((isGoingVertical && !isGoingHorizontal) || (!isGoingVertical && isGoingHorizontal)) {
            return true;
        } else {
            return Math.abs(line - toLine) == Math.abs(column - toColumn);
        }
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}
