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
        return Math.abs(line - toLine) == Math.abs(column - toColumn);
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
