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
                } else if ((endLine == 7 || endLine == 0) && board[startLine][startColumn].getClass() == Pawn.class) {
                    board[startLine][startColumn] = new Queen(nowPlayer);   //Replace Pawn to Queen
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
        System.out.println("\tPlayer 1(White)\n");
        String kingIsUnderAttack = kingIsUnderAttack();
        if (!kingIsUnderAttack.isEmpty()) {
            System.out.println(kingIsUnderAttack);
        }
        System.out.printf("-> Turn %s -> ", nowPlayer);
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        int line;
        if (nowPlayer.equals("White")) {
            line = 0;
        } else {
            line = 7;
        }
        if (board[line][0] == null || board[line][4] == null) {
            return false;
        }
        if (board[line][0].getClass() == Rook.class && board[line][4].getClass() == King.class &&  // check that King and Rook
                board[line][1] == null && board[line][2] == null && board[line][3] == null) {      // never moved
            if (board[line][0].getColor().equals(nowPlayer) && board[line][4].getColor().equals(nowPlayer) &&
                    !board[line][0].isMoved() && !board[line][4].isMoved()) {
                if (!((King) board[line][4]).isUnderAttack(this, line, 4) &&
                        !((King) board[line][4]).isUnderAttack(this, line, 3) &&
                        !((King) board[line][4]).isUnderAttack(this, line, 2)) {  // check that position not in under attack
                    board[line][4] = null;
                    board[line][2] = new King(nowPlayer);   // move King
                    board[line][2].isMotionHappen();
                    board[line][0] = null;
                    board[line][3] = new Rook(nowPlayer);   // move Rook
                    board[line][3].isMotionHappen();
                    nowPlayer = nowPlayer.equals("White") ? "Black" : "White";  // next turn
                    return true;
                }
            }
        }
        return false;
    }

    public boolean castling7() {
        int line;
        if (nowPlayer.equals("White")) {
            line = 0;
        } else {
            line = 7;
        }
        if (board[line][7] == null || board[line][4] == null) {
            return false;
        }
        if (board[line][7].getClass() == Rook.class && board[line][4].getClass() == King.class &&  // check that King and Rook
                board[line][5] == null && board[line][6] == null) {                                // never moved
            if (board[line][7].getColor().equals(nowPlayer) && board[line][4].getColor().equals(nowPlayer) &&
                    !board[line][7].isMoved() && !board[line][4].isMoved()) {
                if (!((King) board[line][4]).isUnderAttack(this, line, 4) &&
                        !((King) board[line][4]).isUnderAttack(this, line, 5) &&
                        !((King) board[line][4]).isUnderAttack(this, line, 6)) {  // check that position not in under attack
                    board[line][4] = null;
                    board[line][6] = new King(nowPlayer);   // move King
                    board[line][6].isMotionHappen();
                    board[line][7] = null;
                    board[line][5] = new Rook(nowPlayer);   // move Rook
                    board[line][5].isMotionHappen();
                    nowPlayer = nowPlayer.equals("White") ? "Black" : "White";  // next turn
                    return true;
                }
            }
        }
        return false;
    }

    public String kingIsUnderAttack() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getClass() == King.class) {
                    if (((King) board[i][j]).isUnderAttack(this, i, j)) {
                        if (message.isEmpty()) {
                            message.append(board[i][j].getColor()).append(" king ");
                        } else {
                            message.append("and ").append(board[i][j].getColor()).append(" king ");
                        }
                    }
                }
            }
        }
        if (!message.isEmpty()) {
            message.append("is under check");
        }
        return message.toString();
    }

    public boolean kingsAlive() {
        King[] kings = new King[2];
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getClass() == King.class) {
                    kings[count++] = (King) board[i][j];
                }
            }
        }
        if (count < 2) {
            System.out.printf("%s wins!", kings[0].getColor());
            return false;
        } else {
            return true;
        }
    }
}
