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
        if (chessBoard.board[toLine][toColumn] != null &&
                chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
            return false;
        }
        boolean isGoingVertical   = Math.abs(line - toLine) > 0;
        boolean isGoingHorizontal = Math.abs(column - toColumn) > 0;
        if ((isGoingVertical && !isGoingHorizontal) || (!isGoingVertical && isGoingHorizontal)) {
            if (isGoingVertical) {
                for (int i = Math.min(line, toLine) + 1; i < Math.max(line, toLine); i++) {
                    if (chessBoard.board[i][column] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = Math.min(column, toColumn) + 1; i < Math.max(column, toColumn); i++) {
                    if (chessBoard.board[line][i] != null) {
                        return false;
                    }
                }
            }
            return true;
        } else if (Math.abs(line - toLine) == Math.abs(column - toColumn)) {
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
        return "Q";
    }
}
