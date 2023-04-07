package assignment3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Part1Test {   // =======  12 points =======
    @Test
    @Tag("score:1")
    @DisplayName("Block constructor test1")
    void BlockConstructorTest1() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(50);
        Block b = new Block(0, 2);

        Field childrenField = Block.class.getDeclaredField("children");
        childrenField.setAccessible(true);

        Block[] children = (Block[]) childrenField.get(b);

        assertEquals(4, children.length);

        Block[] urChildren = (Block[]) childrenField.get(children[0]);
        Block[] ulChildren = (Block[]) childrenField.get(children[1]);
        Block[] llChildren = (Block[]) childrenField.get(children[2]);
        Block[] lrChildren = (Block[]) childrenField.get(children[3]);

        assertEquals(4, urChildren.length);
        assertEquals(0, ulChildren.length);
        assertEquals(0, llChildren.length);
        assertEquals(4, lrChildren.length);

    }

    @Test
    @Tag("score:3")
    @DisplayName("Block constructor test2")
    void BlockConstructorTest2() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(4);
        Block b = new Block(0, 2);

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] children = (Block[]) childrenField.get(b);

        ArrayList<Color> expectedColor = new ArrayList<>();
        expectedColor.add(GameColors.YELLOW);
        expectedColor.add(null);
        expectedColor.add(GameColors.YELLOW);
        expectedColor.add(GameColors.RED);

        ArrayList<Color> actualColor = new ArrayList<>();

        for (Block child : children) {
            actualColor.add((Color) colorField.get(child));
        }

        assertEquals(expectedColor, actualColor);  // checking if the colors at level 1 are correct

        Block[] ulChildren = (Block[]) childrenField.get(children[1]);

        assertEquals(4, ulChildren.length);

        List<Color> expectedColorUL = List.of(GameColors.GREEN, GameColors.RED, GameColors.GREEN, GameColors.RED);
        List<Color> actualColorUL = new ArrayList<>();

        for (Block child : ulChildren) {
            actualColorUL.add((Color) colorField.get(child));
        }

        assertEquals(expectedColorUL, actualColorUL);  // checking if the colors at level 2 are correct for the upper left child
    }


    // Testing for the case were lvl > maxDepth, as per the TA's answer on Ed
    // https://edstem.org/us/courses/32649/discussion/2880926
    @Test
    @DisplayName("Block constructor test3")
    void BlockConstructorTest3() {
        assertThrows(IllegalArgumentException.class, () -> new Block(3, 1));
    }

    @Test
    @Tag("score:3")
    @DisplayName("Block updateSizeAndPosition() test1")
    void UpdateSizeAndPositionTest1() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(0, 0, 0, 1, 2, GameColors.YELLOW, new Block[0]);
        children[1] = new Block(0, 0, 0, 1, 2, GameColors.RED, new Block[0]);
        children[2] = new Block(0, 0, 0, 1, 2, GameColors.GREEN, new Block[0]);
        children[3] = new Block(0, 0, 0, 1, 2, GameColors.RED, new Block[0]);
        Block b = new Block(0, 0, 0, 0, 2, null, children);

        b.updateSizeAndPosition(16, 0, 0);

        Field childrenField = Block.class.getDeclaredField("children");
        Field sizeField = Block.class.getDeclaredField("size");
        Field xcoordField = Block.class.getDeclaredField("xCoord");
        Field ycoordField = Block.class.getDeclaredField("yCoord");

        childrenField.setAccessible(true);
        sizeField.setAccessible(true);
        xcoordField.setAccessible(true);
        ycoordField.setAccessible(true);

        assertEquals(16, (int) sizeField.get(b));
        assertEquals(0, (int) xcoordField.get(b));
        assertEquals(0, (int) ycoordField.get(b));


        ArrayList<Integer> actualSize = new ArrayList<>();
        ArrayList<Integer> Coords = new ArrayList<>();

        for (Block child : (Block[]) childrenField.get(b)) {
            actualSize.add((int) sizeField.get(child));
            Coords.add((int) xcoordField.get(child));
            Coords.add((int) ycoordField.get(child));
        }

        List<Integer> expectedSize = List.of(8, 8, 8, 8);
        List<Integer> expectedCoords = List.of(8, 0, 0, 0, 0, 8, 8, 8);  // UL x, UL y, UR x, UR y, LL x, LL y, LR x, LR y

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedCoords, Coords);
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block updateSizeAndPosition() test2")
    void UpdateSizeAndPositionTest2() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.updateSizeAndPosition(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> b.updateSizeAndPosition(3, 0, 0));
    }

    @Test
    @DisplayName("Block updateSizeAndPosition() test3")
    void UpdateSizeAndPositionTest3() {
        Block b = new Block(0, 0, 0, 0, 0, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.updateSizeAndPosition(0, 0, 0));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getBlocksToDraw() test1")
    void GetBlocksToDrawTest1() {
        Block.gen = new Random(4);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        ArrayList<BlockToDraw> blocksToDraw = b.getBlocksToDraw();

        assertEquals(14, blocksToDraw.size());

        int frameCount = 0;
        int blockCount = 0;

        for (BlockToDraw btd : blocksToDraw) {
            if (btd.getColor() == GameColors.FRAME_COLOR) {
                frameCount++;
            } else if (btd.getStrokeThickness() == 0) {
                blockCount++;
            }
        }

        assertEquals(7, frameCount);
        assertEquals(7, blockCount);
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getBlocksToDraw() test2")
    void GetBlocksToDrawTest2() {
        Block[] children = new Block[0];
        Block b = new Block(0, 0, 16, 0, 2, GameColors.YELLOW, children);

        ArrayList<BlockToDraw> blocksToDraw = b.getBlocksToDraw();
        assertEquals(2, blocksToDraw.size());

        for (BlockToDraw btd : blocksToDraw) {
            boolean frame = btd.getStrokeThickness() == 0 && btd.getColor() == GameColors.YELLOW;
            boolean block = btd.getStrokeThickness() == 3 && btd.getColor() == GameColors.FRAME_COLOR;
            assertTrue(frame || block);
        }
    }
}

class Part2Test {  // ========= 12 points =========
    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test1")
    void getSelectedBlock1() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.getSelectedBlock(2, 15, 4));
        assertThrows(IllegalArgumentException.class, () -> b.getSelectedBlock(15, 2, -1));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test2")
    void getSelectedBlock2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.RED, new Block[0]);
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]);
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);

        Block b = new Block(0, 0, 2, 0, 1, null, children);

        Field xCoordField = Block.class.getDeclaredField("xCoord");
        Field yCoordField = Block.class.getDeclaredField("yCoord");
        Field colorField = Block.class.getDeclaredField("color");

        xCoordField.setAccessible(true);
        yCoordField.setAccessible(true);
        colorField.setAccessible(true);

        Block res = b.getSelectedBlock(0, 0, 1);

        assertEquals(0, (int) xCoordField.get(res));
        assertEquals(0, (int) yCoordField.get(res));
        assertEquals(GameColors.RED, colorField.get(res));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test3")
    void getSelectedBlock3() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(4);
        Block b = new Block(0, 3);
        b.updateSizeAndPosition(16, 0, 0);

        Block res = b.getSelectedBlock(9, 10, 2);

        Field xCoordField = Block.class.getDeclaredField("xCoord");
        Field yCoordField = Block.class.getDeclaredField("yCoord");
        Field colorField = Block.class.getDeclaredField("color");
        Field childrenField = Block.class.getDeclaredField("children");

        xCoordField.setAccessible(true);
        yCoordField.setAccessible(true);
        colorField.setAccessible(true);
        childrenField.setAccessible(true);

        assertEquals(8, (int) xCoordField.get(res));
        assertEquals(8, (int) yCoordField.get(res));
        assertNull(colorField.get(res));
        assertEquals(4, ((Block[]) childrenField.get(res)).length);

        List<Color> colors = List.of(GameColors.BLUE, GameColors.YELLOW, GameColors.GREEN, GameColors.YELLOW);

        Block[] children = (Block[]) childrenField.get(res);

        for (int i = 0; i < 4; i++) {
            Block child = children[i];
            assertEquals(colors.get(i), colorField.get(child));
        }
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test4")
    void getSelectedBlock4() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(4);
        Block b = new Block(0, 3);
        b.updateSizeAndPosition(16, 0, 0);

        Block res = b.getSelectedBlock(9, 1, 2);

        Field xCoordField = Block.class.getDeclaredField("xCoord");
        Field yCoordField = Block.class.getDeclaredField("yCoord");
        Field colorField = Block.class.getDeclaredField("color");
        Field childrenField = Block.class.getDeclaredField("children");

        xCoordField.setAccessible(true);
        yCoordField.setAccessible(true);
        colorField.setAccessible(true);
        childrenField.setAccessible(true);

        assertEquals(8, (int) xCoordField.get(res));
        assertEquals(0, (int) yCoordField.get(res));
        assertEquals(GameColors.YELLOW, colorField.get(res));
        assertEquals(0, ((Block[]) childrenField.get(res)).length);
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block reflect() test1")
    void reflect1() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.reflect(2));
        assertThrows(IllegalArgumentException.class, () -> b.reflect(-1));
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block reflect() test2")
    void reflect2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.RED, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR
        Block b = new Block(0, 0, 2, 0, 1, null, children);

        b.reflect(0);  // reflect horizontally

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.GREEN, GameColors.RED, GameColors.YELLOW);
        List<Color> actual = new ArrayList<>();

        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);

    }

    @Test
    @Tag("score:1")
    @DisplayName("Block rotate() test1")
    void rotate1() {
        Block b = new Block();
        assertThrows(IllegalArgumentException.class, () -> b.rotate(2));
        assertThrows(IllegalArgumentException.class, () -> b.rotate(-1));
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block rotate() test2")
    void rotate2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.GREEN, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.BLUE, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.RED, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR

        Block b = new Block(0, 0, 2, 0, 1, null, children);

        b.rotate(1); // rotate counter-clockwise

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.RED, GameColors.BLUE, GameColors.GREEN);

        List<Color> actual = new ArrayList<>();
        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);

    }

    @Test
    @Tag("score:1")
    @DisplayName("Block smash() test1")
    void smash1() {
        Block b = new Block();

        assertFalse(b.smash());

        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.BLUE, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR

        b = new Block(0, 0, 2, 1, 2, null, children);

        assertTrue(b.smash());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block smash() test2")
    void smash2() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(0);
        Block b = new Block(1, 2);
        b.updateSizeAndPosition(4, 0, 0);

        b.smash();

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.RED, GameColors.BLUE, GameColors.YELLOW);
        List<Color> actual = new ArrayList<>();

        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);
    }

    // smash root level
    @Test
    @DisplayName("Block smash() test3")
    void smash3() {
        Block.gen = new Random(0);
        Block b = new Block(0, 4);
        b.updateSizeAndPosition(16, 0, 0);
        assertFalse(b.smash());
    }

    // smash leaf
    @Test
    @DisplayName("Block smash() test4")
    void smash4() {
        Block.gen = new Random(0);
        Block b = new Block(4, 4);
        b.updateSizeAndPosition(16, 0, 0);
        assertFalse(b.smash());
    }

}

class Part3Test {  // ======== 16 points ========

    @Test // same as the one in the pdf
    @Tag("score:2")
    @DisplayName("Block flatten() test1")
    void Blockflatten1() {
        Block.gen = new Random(2);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.RED, GameColors.RED, GameColors.GREEN, GameColors.GREEN},
                {GameColors.RED, GameColors.RED, GameColors.GREEN, GameColors.GREEN},
                {GameColors.YELLOW, GameColors.YELLOW, GameColors.RED, GameColors.BLUE},
                {GameColors.YELLOW, GameColors.YELLOW, GameColors.YELLOW, GameColors.BLUE}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block flatten() test2")
    void Blockflatten2() {
        Block[] children = new Block[4];
        children[0] = new Block(8, 0, 8, 1, 1, GameColors.BLUE, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 1, GameColors.YELLOW, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 1, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 1, GameColors.GREEN, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 1, null, children);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.YELLOW, GameColors.BLUE},
                {GameColors.RED, GameColors.GREEN}
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }

    @Test
    @Tag("score:3")
    @DisplayName("Block flatten() test3")
    void Blockflatten3() {
        Block[] children = new Block[4];
        Block[] llChildren = new Block[4];

        llChildren[0] = new Block(4, 8, 4, 2, 2, GameColors.RED, new Block[0]);
        llChildren[1] = new Block(0, 8, 4, 2, 2, GameColors.GREEN, new Block[0]);
        llChildren[2] = new Block(0, 12, 4, 2, 2, GameColors.GREEN, new Block[0]);
        llChildren[3] = new Block(4, 12, 4, 2, 2, GameColors.YELLOW, new Block[0]);

        children[0] = new Block(8, 0, 8, 1, 2, GameColors.RED, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, null, llChildren);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.BLUE, GameColors.BLUE, GameColors.RED, GameColors.RED},
                {GameColors.BLUE, GameColors.BLUE, GameColors.RED, GameColors.RED},
                {GameColors.GREEN, GameColors.RED, GameColors.YELLOW, GameColors.YELLOW},
                {GameColors.GREEN, GameColors.YELLOW, GameColors.YELLOW, GameColors.YELLOW}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }

    // test for if flattened board is square
    @Test
    @DisplayName("Block flatten() test4")
    void Blockflatten4() {
        Block b = new Block(0, 10);
        b.updateSizeAndPosition(1024, 0, 0);
        Color[][] board = b.flatten();
        assertEquals(board.length, board[0].length);
        assertEquals(1024, board.length);
    }

    // test for if flattened board is minimized to unit-cells
    @Test
    @DisplayName("Block flatten() test5")
    void Blockflatten5() {
        Block b = new Block(0, 10);
        b.updateSizeAndPosition(2048, 0, 0);
        Color[][] board = b.flatten();
        assertEquals(board.length, board[0].length);
        assertEquals(1024, board.length);
    }

    @Test
    @DisplayName("Block flatten() test6")
    void Blockflatten6() {
        Block b = new Block(0, 0, 1, 0, 0, GameColors.RED, new Block[0]);
        b.updateSizeAndPosition(1, 0, 0);
        Color[][] board = b.flatten();
        Color[][] expected = {{GameColors.RED}};

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                assertEquals(expected[i][j], board[i][j]);
            }
        }

    }

    @Test
    @Tag("score:1")
    @DisplayName("PerimeterGoal score() test1")
    void PGscore1() {
        Block[] children = new Block[4];

        children[0] = new Block(8, 0, 8, 1, 2, GameColors.GREEN, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        PerimeterGoal p = new PerimeterGoal(GameColors.RED);
        assertEquals(4, p.score(b));
    }

    @Test
    @Tag("score:1")
    @DisplayName("PerimeterGoal score() test2")
    void PGscore2() {
        Block.gen = new Random(8);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        PerimeterGoal p = new PerimeterGoal(GameColors.YELLOW);
        assertEquals(3, p.score(b));
    }

    @Test
    @DisplayName("PerimeterGoal score() test3")
    void PGscore3() {
        Block.gen = new Random(10);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(16, 0, 0);

        PerimeterGoal p = new PerimeterGoal(GameColors.GREEN);
        assertEquals(4, p.score(b));
    }

    @Test
    @DisplayName("PerimeterGoal score() test4")
    void PGscore4() {
        Block.gen = new Random(20);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(48, 0, 0);

        PerimeterGoal p = new PerimeterGoal(GameColors.YELLOW);
        assertEquals(4, p.score(b));
    }

    @Test
    @DisplayName("PerimeterGoal score() test4")
    void PGscore5() {
        Block.gen = new Random(20);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(48, 0, 0);

        PerimeterGoal p = new PerimeterGoal(GameColors.GREEN);
        assertEquals(0, p.score(b));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test1")
    void BGBlobSize1() {
        BlobGoal g = new BlobGoal(GameColors.BLUE);

        Color[][] c = new Color[][]{
                {GameColors.YELLOW, GameColors.BLUE},
                {GameColors.RED, GameColors.RED}
        };

        assertEquals(0, g.undiscoveredBlobSize(0, 0, c, new boolean[2][2]));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test2")
    void BGBlobSize2() {
        BlobGoal g = new BlobGoal(GameColors.RED);

        Color[][] c = new Color[][]{
                {GameColors.BLUE, GameColors.RED, GameColors.GREEN},
                {GameColors.RED, GameColors.YELLOW, GameColors.RED},
                {GameColors.RED, GameColors.YELLOW, GameColors.GREEN},
                {GameColors.RED, GameColors.RED, GameColors.YELLOW}
        };

        assertEquals(1, g.undiscoveredBlobSize(0, 1, c, new boolean[4][3]));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test3")
    void BGBlobSize3() {
        Block.gen = new Random(8);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal g = new BlobGoal(GameColors.YELLOW);
        assertEquals(2, g.undiscoveredBlobSize(1, 1, b.flatten(), new boolean[4][4]));
    }

    @Test
    @Tag("score:1")
    @DisplayName("BlobGoal score() test1")
    void BGscore1() {
        Block[] children = new Block[4];
        Block[] urChildren = new Block[4];

        urChildren[0] = new Block(12, 0, 4, 2, 2, GameColors.GREEN, new Block[0]);
        urChildren[1] = new Block(8, 0, 4, 2, 2, GameColors.BLUE, new Block[0]);
        urChildren[2] = new Block(8, 4, 4, 2, 2, GameColors.RED, new Block[0]);
        urChildren[3] = new Block(12, 4, 4, 2, 2, GameColors.YELLOW, new Block[0]);

        children[0] = new Block(8, 0, 8, 1, 2, null, urChildren);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        BlobGoal g = new BlobGoal(GameColors.BLUE);
        assertEquals(5, g.score(b));
    }

    @Test
    @Tag("score:1")
    @DisplayName("BlobGoal score() test2")
    void BGscore2() {
        Block.gen = new Random(500);
        Block b = new Block(0, 3);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal g = new BlobGoal(GameColors.RED);
        assertEquals(18, g.score(b));
    }

    @Test
    @DisplayName("BlobGoal score() test3")
    void BGscore3() {
        Block.gen = new Random(10);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal bg = new BlobGoal(GameColors.GREEN);
        assertEquals(2, bg.score(b));
    }

    @Test
    @DisplayName("BlobGoal score() test4")
    void BGscore4() {
        Block.gen = new Random(20);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal bg = new BlobGoal(GameColors.RED);
        assertEquals(1, bg.score(b));
    }

    @Test
    @DisplayName("BlobGoal score() test5")
    void BGscore5() {
        Block.gen = new Random(20);
        Block b = new Block(0, 1);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal bg = new BlobGoal(GameColors.YELLOW);
        assertEquals(2, bg.score(b));
    }
}
