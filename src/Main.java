import java.util.Random;

public class Main {
    private final char BLACK_BLOCK = '\u2588';
    private final char WHITE_BLOCK = '\u2591';

    public static void main(String[] args) {
        new Main().startBlockGame();
    }

    /**
     * Start the block game.
     */
    private void startBlockGame() {
        //Init new table
        int width = 16;
        int height = 16;
        char[][] table = initTable(width, height);
        printTable(table);

        //Start block changing
        for (int i = 0; i < 20; i++) {
            System.out.println("Round " + (i + 1) + "-----------------------------------");
            table = changeBlocks(table);
            printTable(table);
            System.out.println("**********************************************");
        }
    }

    /**
     * Initialize a empty table with full of white block and a black block.
     *
     * @param width  Width of table.
     * @param height Height of table.
     * @return A block table for game.
     */
    private char[][] initTable(int width, int height) {
        char[][] t = new char[width][height];
        //Make it all black
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                t[i][j] = BLACK_BLOCK;
            }
        }

        //Change three block to white
        int whiteBlocksNumber = 0;
        while (whiteBlocksNumber < 3) {
            int posX = new Random().nextInt(width);
            int posY = new Random().nextInt(height);
            if (t[posX][posY] != WHITE_BLOCK) {
                t[posX][posY] = WHITE_BLOCK;
                whiteBlocksNumber += 1;
            } else {
                continue;
            }
        }

        return t;
    }

    /**
     * The mainly function for block game, changing blocks color on table.
     *
     * @param table A initialized table for game.
     * @return The table after change.
     */
    private char[][] changeBlocks(char[][] table) {
        //Clone current table
        char[][] finalTable = cloneArrary(table, new char[table.length][table[0].length]);
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                int whiteBlockNumber = countWhiteBlocksAround(table, i, j);
                if (whiteBlockNumber < 2) {
                    finalTable[i][j] = BLACK_BLOCK;
                } else if (whiteBlockNumber == 2) {
                    //Do nothing
                } else if (whiteBlockNumber == 3) {
                    finalTable[i][j] = WHITE_BLOCK;
                } else {
                    finalTable[i][j] = BLACK_BLOCK;
                }
            }
        }
        return finalTable;
    }

    /**
     * Clone character array to a new array.
     *
     * @param src Source array.
     * @return New array cloned from source array.
     */
    private char[][] cloneArrary(char[][] src, char[][] dest) {
        for (int i = 0; i < src.length; i++) {
            if (src[i].length >= 0) {
                System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
            }
        }
        return dest;
    }

    /**
     * Count how many white blocks around current block.
     *
     * @param table Table for game.
     * @param posX  Current block's X position.
     * @param posY  Current block's Y position.
     * @return White blocks number.
     */
    private int countWhiteBlocksAround(char[][] table, int posX, int posY) {
        char leftTop = getBlock(table, posX - 1, posY - 1);
        char top = getBlock(table, posX - 1, posY);
        char rightTop = getBlock(table, posX - 1, posY + 1);
        char left = getBlock(table, posX, posY - 1);
        char right = getBlock(table, posX, posY + 1);
        char leftBottom = getBlock(table, posX + 1, posY - 1);
        char bottom = getBlock(table, posX + 1, posY);
        char rightBottom = getBlock(table, posX + 1, posY + 1);

        char[] blocks = new char[]{leftTop, top, rightTop, left, right, leftBottom, bottom, rightBottom};

        return getWhiteBlocksNum(blocks);
    }

    /**
     * Get block with position.
     *
     * @param table Table for game.
     * @param posX  Block's X position.
     * @param posY  Block's Y position.
     * @return Block or blank character.
     */
    private char getBlock(char[][] table, int posX, int posY) {
        char block = ' ';
        try {
            block = table[posX][posY];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
        return block;
    }

    /**
     * Count how many white blocks.
     *
     * @param blocks White blocks with blank character.
     * @return White blocks number.
     */
    private int getWhiteBlocksNum(char[] blocks) {
        int whiteBlocksNum = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == WHITE_BLOCK) {
                whiteBlocksNum += 1;
            }
        }
        return whiteBlocksNum;
    }

    /**
     * Print current table to stdout.
     *
     * @param table Table which needs to print.
     */
    private void printTable(char[][] table) {

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j]);
            }
            System.out.print("\n");
        }
    }
}
