package Chess;

public class Bishop extends ChessPiece {

    public Bishop(String color) {
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
        if (Math.abs(line - toLine) == Math.abs(column - toColumn)) {
            int deltaLine   = (toLine > line) ? 1 : -1;
            int deltaColumn = (toColumn > column) ? 1 : -1;
            for (int i = line + deltaLine, j = column + deltaColumn; i != toLine;
                 i += deltaLine, j += deltaColumn) {
                if (chessBoard.board[i][j] != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
