package Chess;

public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) {
            return false;
        }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) {
            return false;
        }
        return Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        int kingLine = -1, kingColumn = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.board[i][j] == null) {
                    continue;
                }
                if (chessBoard.board[i][j].getClass() == King.class &&
                        chessBoard.board[i][j].getColor().equals(this.color)) {
                    kingLine = i;
                    kingColumn = j;
                    break;
                }
            }
            if (kingLine != -1) {
                break;
            }
        }
        if (kingLine == -1) {
            return false;
        }

        int ways = 8;  //ways 5-8 - diagonal search, 1-4 vertical-horizontal search
        while (ways > 0) {
            int searchLine = kingLine;
            int searchColumn = kingColumn;
            int distance = 0;
            boolean inBoardArea = true;
            do {
                searchLine += switch (ways) {
                    case 4, 7, 8 ->  1;
                    case 3, 5, 6 -> -1;
                    default -> 0;
                };
                searchColumn += switch (ways) {
                    case 2, 5, 8 ->  1;
                    case 1, 6, 7 -> -1;
                    default -> 0;
                };
                inBoardArea = chessBoard.checkPos(searchLine) && chessBoard.checkPos(searchColumn);
                if (!inBoardArea) {
                    continue;
                }
                distance++;
                if (chessBoard.board[searchLine][searchColumn] == null ||
                        chessBoard.board[searchLine][searchColumn].getColor().equals(this.color)) {
                    continue;
                }
                Class<?> chessPiece = chessBoard.board[searchLine][searchColumn].getClass();
                if (ways > 4) {
                    if (distance == 1) {
                        if (chessPiece == King.class) {
                            return true;
                        } else if (chessPiece == Pawn.class) {
                            if (this.color.equals("White")) {
                                return searchLine - kingLine == 1;
                            } else {
                                return searchLine - kingLine == -1;
                            }
                        }
                    }
                    if (chessPiece == Queen.class || chessPiece == Bishop.class) {
                        return true;
                    }
                } else {
                    if (distance == 1) {
                        if (chessPiece == King.class) {
                            return true;
                        }
                    }
                    if (chessPiece == Queen.class || chessPiece == Rook.class) {
                        return true;
                    }
                }
            } while (inBoardArea);
            ways--;
        }

        int totalEnemyHorses = 2;
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.board[i][j] == null) {
                    continue;
                }
                if (chessBoard.board[i][j].getClass() == Horse.class &&
                        !chessBoard.board[i][j].getColor().equals(this.color)) {
                    totalEnemyHorses--;
                    if (kingLine == i || kingColumn == j) {  //King and Horse on the same line
                        continue;
                    } else {
                        boolean inFirstWay  = chessBoard.board[i + 1][j + 2] == chessBoard.board[kingLine][kingColumn];
                        boolean inSecondWay = chessBoard.board[i + 1][j - 2] == chessBoard.board[kingLine][kingColumn];
                        boolean inThirdWay  = chessBoard.board[i - 1][j + 2] == chessBoard.board[kingLine][kingColumn];
                        boolean inFourthWay = chessBoard.board[i - 1][j - 2] == chessBoard.board[kingLine][kingColumn];
                        boolean inFifthWay  = chessBoard.board[i + 2][j + 1] == chessBoard.board[kingLine][kingColumn];
                        boolean inSixthWay  = chessBoard.board[i + 2][j - 1] == chessBoard.board[kingLine][kingColumn];
                        boolean inSeventhWay= chessBoard.board[i - 2][j + 1] == chessBoard.board[kingLine][kingColumn];
                        boolean inEighthWay = chessBoard.board[i - 2][j - 1] == chessBoard.board[kingLine][kingColumn];
                        if (inFirstWay || inSecondWay || inThirdWay || inFourthWay ||
                                inFifthWay || inSixthWay || inSeventhWay || inEighthWay) {
                            return true;
                        }
                    }
                }
                if (totalEnemyHorses == 0) {
                    break;
                }
            }
            if (totalEnemyHorses == 0) {
                break;
            }
        }
        return false;
    }
}
