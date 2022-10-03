package Chess;

public class ChessBoard {

    public ChessPiece[][] board = new ChessPiece[8][8];
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) {
                return false;
            }
            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn,
                                                                endLine, endColumn)) {

                if (board[startLine][startColumn].getClass() == King.class ||  // check position for castling
                        board[startLine][startColumn].getClass() == Rook.class) {
                    board[startLine][startColumn].isMotionHappen();
                }

                board[endLine][endColumn] = board[startLine][startColumn];  // if piece can move, we moved a piece
                board[startLine][startColumn] = null;                       // set null to previous cell
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void printBoard() {  //print board in console
        System.out.println("\tPlayer 2(Black)\n");
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i >= 0; i--) {
            System.out.printf("%d\t", i);
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("..\t");
                } else {
                    System.out.printf("%s%s\t", board[i][j].getSymbol(),
                                                board[i][j].getColor().substring(0, 1).toLowerCase());
                }
            }
            System.out.print("\n\n");
        }
        System.out.println("\tPlayer 1(White)");
        System.out.printf("%n-> Turn %s -> ", nowPlayer);
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        int line;
        String color;
        if (nowPlayer.equals("White")) {
            color = "White";
            line = 0;
        } else {
            color = "Black";
            line = 7;
        }
        //if (nowPlayer.equals("White")) {
        if (board[line][0] == null || board[line][4] == null) {
            return false;
        }
        if (board[line][0].getClass() == Rook.class && board[line][4].getClass() == King.class &&  // check that King and Rook
                board[line][1] == null && board[line][2] == null && board[line][3] == null) {      // never moved
            if (board[line][0].getColor().equals(color) && board[line][4].getColor().equals(color) &&
                    !board[line][0].isMoved() && !board[line][4].isMoved() &&
                    !new King(color).isUnderAttack(this, 0, 2)) {  // check that position not in under attack
                board[line][4] = null;
                board[line][2] = new King(color);   // move King
                board[line][2].isMotionHappen();
                board[line][0] = null;
                board[line][3] = new Rook(color);   // move Rook
                board[line][3].isMotionHappen();
                nowPlayer = color.equals("White") ? "Black" : "White";  // next turn
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        /*} else {
            if (board[7][0] == null || board[7][4] == null) {
                return false;
            }
            if (board[7][0].getClass() == Rook.class && board[7][4].getClass() == King.class &&  // check that King and Rook
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {         // never moved
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].getCheck() && board[7][4].getCheck() &&
                        !new King("Black").isUnderAttack(this, 7, 2)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][2] = new King("Black");   // move King
                    board[7][2].setCheck();
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");   // move Rook
                    board[7][3].setCheck();
                    nowPlayer = "White";  // next turn
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }*/
    }

    public boolean castling7() {
        int line;
        String color;
        if (nowPlayer.equals("White")) {
            line = 0;
            color = "White";
        } else {
            line = 7;
            color = "Black";
        }
        if (board[line][7] == null || board[line][4] == null) {
            return false;
        }
        if (board[line][7].getClass() == Rook.class && board[line][4].getClass() == King.class &&  // check that King and Rook
                board[line][5] == null && board[line][6] == null) {                                // never moved
            if (board[line][7].getColor().equals(color) && board[line][4].getColor().equals(color) &&
                    !board[line][7].isMoved() && !board[line][4].isMoved() &&
                    !new King(color).isUnderAttack(this, 0, 6)) {  // check that position not in under attack
                board[line][4] = null;
                board[line][6] = new King(color);   // move King
                board[line][6].isMotionHappen();
                board[line][7] = null;
                board[line][5] = new Rook(color);   // move Rook
                board[line][5].isMotionHappen();
                nowPlayer = color.equals("White") ? "Black" : "White";  // next turn
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
