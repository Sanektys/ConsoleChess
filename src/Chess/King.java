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
        if (chessBoard.board[toLine][toColumn] != null &&
                chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
            return false;
        }
        return Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {

        int ways = 8;  //ways 5-8 - diagonal search, 1-4 vertical-horizontal search
        while (ways > 0) {
            int searchLine = line;
            int searchColumn = column;
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
                distance++;

                inBoardArea = chessBoard.checkPos(searchLine) && chessBoard.checkPos(searchColumn);
                if (!inBoardArea || chessBoard.board[searchLine][searchColumn] == null) {
                    continue;
                }
                if (chessBoard.board[searchLine][searchColumn].getColor().equals(this.color)) {
                    break;
                }

                Class<?> chessPiece = chessBoard.board[searchLine][searchColumn].getClass();
                if (ways > 4) {
                    if (distance == 1) {
                        if (chessPiece == King.class) {
                            return true;
                        } else if (chessPiece == Pawn.class) {
                            if (this.color.equals("White")) {
                                return searchLine - line == 1;
                            } else {
                                return searchLine - line == -1;
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
                    if (line == i || column == j) {  //King and Horse on the same line
                        continue;
                    } else {
                        int horseWays = 8;
                        while (horseWays > 0) {
                            int possibleLine = switch (horseWays) {
                                case 1, 2 -> i + 2;
                                case 3, 4 -> i - 2;
                                case 5, 6 -> i + 1;
                                case 7, 8 -> i - 1;
                                default -> 0;
                            };
                            int possibleColumn = switch (horseWays) {
                                case 5, 7 -> j + 2;
                                case 6, 8 -> j - 2;
                                case 1, 3 -> j + 1;
                                case 2, 4 -> j - 1;
                                default -> 0;
                            };
                            if (chessBoard.checkPos(possibleLine) && chessBoard.checkPos(possibleColumn)) {
                                if (possibleLine == line && possibleColumn == column) {
                                    return true;
                                }
                            }
                            horseWays--;
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
