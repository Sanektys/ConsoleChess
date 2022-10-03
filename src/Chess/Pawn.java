package Chess;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
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
        if (color.equals("White")) {
            int step = toLine - line;
            if (column != toColumn) {
                int attackStep = Math.abs(column - toColumn);
                return step == 1 && attackStep == 1 && chessBoard.board[toLine][toColumn] != null &&
                        !chessBoard.board[toLine][toColumn].getColor().equals(this.color);
            }
            if (line == 1) {
                return (step == 1 || step == 2) && chessBoard.board[toLine][column] == null;
            } else {
                return step == 1 && chessBoard.board[toLine][column] == null;
            }
        } else if (color.equals("Black")) {
            int step = line - toLine;
            if (column != toColumn) {
                int attackStep = Math.abs(column - toColumn);
                return step == 1 && attackStep == 1 && chessBoard.board[toLine][toColumn] != null &&
                        !chessBoard.board[toLine][toColumn].getColor().equals(this.color);
            }
            if (line == 6) {
                return (step == 1 || step == 2) && chessBoard.board[toLine][column] == null;
            } else {
                return step == 1 && chessBoard.board[toLine][column] == null;
            }
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
