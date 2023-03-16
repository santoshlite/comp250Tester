package assignment2;

import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class Minitester {}

class SyntaxTest {

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for MyList")
    void syntaxTestMyList() throws NoSuchMethodException {

        Class<?> mList = MyList.class;
        //Check if MyList is an Interface
        assertTrue(mList.isInterface());

        // Check the methods by signature
        // Return NoSuchMethodException if the input type or method name is not matched
        assertEquals("getSize", mList.getMethod("getSize").getName());
        assertEquals("isEmpty", mList.getMethod("isEmpty").getName());
        assertEquals("add", mList.getMethod("add", Object.class).getName());
        assertEquals("clear", mList.getMethod("clear").getName());
        assertEquals("remove", mList.getMethod("remove").getName());

        // Check the return type of each method
        assertEquals("int", mList.getMethod("getSize").getReturnType().getName());
        assertEquals("boolean", mList.getMethod("isEmpty").getReturnType().getName());
        assertEquals("void", mList.getMethod("add", Object.class).getReturnType().getName());
        assertEquals("void", mList.getMethod("clear").getReturnType().getName());
        assertEquals(Object.class.getName(), mList.getMethod("remove").getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for MyLinkedList")
    void syntaxTestMyLinkedList() throws NoSuchMethodException, NoSuchFieldException {
        Class<?> mLinkedList = MyLinkedList.class;

        // Check if we have a field named size and its type is int
        assertEquals("int", mLinkedList.getDeclaredField("size").getType().getName());

        // Check isEmpty()
        assertEquals("isEmpty", mLinkedList.getMethod("isEmpty").getName());
        assertEquals("boolean", mLinkedList.getMethod("isEmpty").getReturnType().getName());

        // Check getSize()
        assertEquals("getSize", mLinkedList.getMethod("getSize").getName());
        assertEquals("int", mLinkedList.getMethod("getSize").getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for MyDoublyLinkedList")
    void syntaxTestMyDoublyLinkedList() throws NoSuchMethodException, NoSuchFieldException {

        Class<?> dList = MyDoublyLinkedList.class;

        // Check if MyDoublyLinkedList extends MyLinkedList
        assertEquals(MyLinkedList.class, dList.getSuperclass());

        // Check add()
        assertEquals("add", dList.getMethod("add", Object.class).getName());
        assertEquals("void", dList.getMethod("add", Object.class).getReturnType().getName());

        // Check remove()
        assertEquals("remove", dList.getMethod("remove").getName());
        assertEquals(Object.class.getName(), dList.getMethod("remove").getReturnType().getName());

        // Check addFirst and addLast
        assertEquals("addFirst", dList.getMethod("addFirst", Object.class).getName());
        assertEquals("void", dList.getMethod("addFirst", Object.class).getReturnType().getName());
        assertEquals("addLast", dList.getMethod("addLast", Object.class).getName());
        assertEquals("void", dList.getMethod("addLast", Object.class).getReturnType().getName());

        // Check removeFirst and removeLast
        assertEquals("removeFirst", dList.getMethod("removeFirst").getName());
        assertEquals(Object.class.getName(), dList.getMethod("removeFirst").getReturnType().getName());
        assertEquals("removeLast", dList.getMethod("removeLast").getName());
        assertEquals(Object.class.getName(), dList.getMethod("removeLast").getReturnType().getName());

        // Check peekFirst and peekLast
        assertEquals("peekFirst", dList.getMethod("peekFirst").getName());
        assertEquals(Object.class.getName(), dList.getMethod("peekFirst").getReturnType().getName());
        assertEquals("peekLast", dList.getMethod("peekLast").getName());
        assertEquals(Object.class.getName(), dList.getMethod("peekLast").getReturnType().getName());

        // Check clear
        assertEquals("clear", dList.getMethod("clear").getName());
        assertEquals("void", dList.getMethod("clear").getReturnType().getName());

        // Check equals
        assertEquals("equals", dList.getMethod("equals", Object.class).getName());
        assertEquals("boolean", dList.getMethod("equals", Object.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for MyStack")
    void syntaxTestMyStack() throws NoSuchMethodException, NoSuchFieldException {
        Class<?> stack = MyStack.class;

        // the name of the private field is not given
        // a constructor with no inputs shall be created by default

        // check push
        assertEquals("push", stack.getMethod("push", Object.class).getName());
        assertEquals("void", stack.getMethod("push", Object.class).getReturnType().getName());

        // check pop
        assertEquals("pop", stack.getMethod("pop").getName());
        assertEquals(Object.class.getName(), stack.getMethod("pop").getReturnType().getName());

        // check peek
        assertEquals("peek", stack.getMethod("peek").getName());
        assertEquals(Object.class.getName(), stack.getMethod("peek").getReturnType().getName());

        // check isEmpty
        assertEquals("isEmpty", stack.getMethod("isEmpty").getName());
        assertEquals("boolean", stack.getMethod("isEmpty").getReturnType().getName());

        // Check clear
        assertEquals("clear", stack.getMethod("clear").getName());
        assertEquals("void", stack.getMethod("clear").getReturnType().getName());

        // Check getSize
        assertEquals("getSize", stack.getMethod("getSize").getName());
        assertEquals("int", stack.getMethod("getSize").getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for MyQueue")
    void syntaxTestMyQueue() throws NoSuchMethodException, NoSuchFieldException {
        Class<?> queue = MyQueue.class;

        // check field
        boolean found = false;
        for (Field f : queue.getDeclaredFields()) {
            if (f.getType().equals(MyDoublyLinkedList.class)) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        // check enqueue
        assertEquals("enqueue", queue.getMethod("enqueue", Object.class).getName());
        assertEquals("void", queue.getMethod("enqueue", Object.class).getReturnType().getName());

        // check dequeue
        assertEquals("dequeue", queue.getMethod("dequeue").getName());
        assertEquals(Object.class.getName(), queue.getMethod("dequeue").getReturnType().getName());

        // check isEmpty
        assertEquals("isEmpty", queue.getMethod("isEmpty").getName());
        assertEquals("boolean", queue.getMethod("isEmpty").getReturnType().getName());

        // Check clear
        assertEquals("clear", queue.getMethod("clear").getName());
        assertEquals("void", queue.getMethod("clear").getReturnType().getName());

        // Check equals
        assertEquals("equals", queue.getMethod("equals", Object.class).getName());
        assertEquals("boolean", queue.getMethod("equals", Object.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for Position")
    void syntaxTestPosition() throws NoSuchMethodException, NoSuchFieldException {
        Class<Position> position = Position.class;
        // check constructors
        assertEquals(Position.class.getName(), position.getConstructor(int.class, int.class).getName());
        assertEquals(Position.class.getName(), position.getConstructor(Position.class).getName());

        // Check reset(int,int)
        assertEquals("reset", position.getMethod("reset", int.class, int.class).getName());
        assertEquals("void", position.getMethod("reset", int.class, int.class).getReturnType().getName());

        // Check reset(Position)
        assertEquals("reset", position.getMethod("reset", Position.class).getName());
        assertEquals("void", position.getMethod("reset", Position.class).getReturnType().getName());

        // Check getDistance
        assertEquals("getDistance", position.getMethod("getDistance", Position.class, Position.class).getName());
        assertEquals("int", position.getMethod("getDistance", Position.class, Position.class).getReturnType().getName());

        // Check getX and getY
        assertEquals("getX", position.getMethod("getX").getName());
        assertEquals("int", position.getMethod("getX").getReturnType().getName());
        assertEquals("getY", position.getMethod("getY").getName());
        assertEquals("int", position.getMethod("getY").getReturnType().getName());

        // Check moveWest
        assertEquals("moveWest", position.getMethod("moveWest").getName());
        assertEquals("void", position.getMethod("moveWest").getReturnType().getName());

        // Check moveEast
        assertEquals("moveEast", position.getMethod("moveEast").getName());
        assertEquals("void", position.getMethod("moveEast").getReturnType().getName());

        // Check moveWest
        assertEquals("moveNorth", position.getMethod("moveNorth").getName());
        assertEquals("void", position.getMethod("moveNorth").getReturnType().getName());

        // Check moveWest
        assertEquals("moveSouth", position.getMethod("moveSouth").getName());
        assertEquals("void", position.getMethod("moveSouth").getReturnType().getName());

        // Check equals
        assertEquals("equals", position.getMethod("equals", Object.class).getName());
        assertEquals("boolean", position.getMethod("equals", Object.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for TargetQueue")
    void syntaxTestTargetQueue() throws NoSuchMethodException, NoSuchFieldException {

        Class<TargetQueue> tq = TargetQueue.class;

        // Check if TargetQueue extends MyQueue
        assertEquals(MyQueue.class, tq.getSuperclass());

        // check field
        boolean found = false;
        for (Field f : tq.getDeclaredFields()) {
            if (f.getType().equals(MyStack.class)) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        // Check clear
        assertEquals("clear", tq.getMethod("clear").getName());
        assertEquals("void", tq.getMethod("clear").getReturnType().getName());

        // Check addTargets
        assertEquals("addTargets", tq.getMethod("addTargets", String.class).getName());
        assertEquals("void", tq.getMethod("addTargets", String.class).getReturnType().getName());
    }


    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for Direction")
    void syntaxTestDirection() throws NoSuchMethodException, NoSuchFieldException {
        assertEquals("NORTH", Direction.NORTH.toString());
        assertEquals("SOUTH", Direction.SOUTH.toString());
        assertEquals("WEST", Direction.WEST.toString());
        assertEquals("EAST", Direction.EAST.toString());
    }


    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for ActionQueue")
    void syntaxTestActionQueue() throws NoSuchMethodException, NoSuchFieldException {

        Class<ActionQueue> aq = ActionQueue.class;

        // Check if ActionQueue extends MyQueue
        assertEquals(MyQueue.class, aq.getSuperclass());

        // Check clear
        assertEquals("clear", aq.getMethod("clear").getName());
        assertEquals("void", aq.getMethod("clear").getReturnType().getName());

        // Check loadFromEncodedString
        assertEquals("loadFromEncodedString", aq.getMethod("loadFromEncodedString", String.class).getName());
        assertEquals("void", aq.getMethod("loadFromEncodedString", String.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for Region")
    void syntaxTestRegion() throws NoSuchMethodException, NoSuchFieldException{
        Class<Region> region = Region.class;

        // check constructor for region
        assertEquals(Region.class.getName(), region.getConstructor(int.class,int.class,int.class,int.class).getName());

        // Check contains
        assertEquals("contains", region.getMethod("contains", Position.class).getName());
        assertEquals("boolean", region.getMethod("contains", Position.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for Caterpillar")
    void syntaxTestCaterpillar() throws NoSuchMethodException, NoSuchFieldException{
        Class<Caterpillar> caterpillar = Caterpillar.class;

        // Check if Caterpillar extends MyDoublyLinkedList
        assertEquals(MyDoublyLinkedList.class,caterpillar.getSuperclass());

        // Check getHead
        assertEquals("getHead", caterpillar.getMethod("getHead").getName());
        assertEquals(Position.class.getName(), caterpillar.getMethod("getHead").getReturnType().getName());

        // Check eat
        assertEquals("eat", caterpillar.getMethod("eat", Position.class).getName());
        assertEquals("void", caterpillar.getMethod("eat", Position.class).getReturnType().getName());

        // Check move
        assertEquals("move", caterpillar.getMethod("move", Position.class).getName());
        assertEquals("void", caterpillar.getMethod("move", Position.class).getReturnType().getName());

        // Check selfCollision
        assertEquals("selfCollision", caterpillar.getMethod("selfCollision", Position.class).getName());
        assertEquals("boolean", caterpillar.getMethod("selfCollision", Position.class).getReturnType().getName());

    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for GameState")
    void syntaxTestGameState() throws NoSuchMethodException, NoSuchFieldException {
        assertEquals("WALL_COLLISION", GameState.WALL_COLLISION.toString());
        assertEquals("SELF_COLLISION", GameState.SELF_COLLISION.toString());
        assertEquals("NO_MORE_ACTION", GameState.NO_MORE_ACTION.toString());
        assertEquals("EAT", GameState.EAT.toString());
        assertEquals("MOVE", GameState.MOVE.toString());
        assertEquals("DONE", GameState.DONE.toString());
    }

    @Test
    @Tag("score:0")
    @DisplayName("Syntax Test for World")
    void syntaxTestWorld() throws NoSuchMethodException, NoSuchFieldException {
        Class<World> world = World.class;


        // check all 6 fields are present
        boolean foundCaterpillar = false;
        boolean foundPosition = false;
        boolean foundActionQueue = false;
        boolean foundTargetQueue = false;
        boolean foundRegion = false;
        boolean foundGameState = false;

        for (Field f : world.getDeclaredFields()) {
            if (f.getType().equals(Caterpillar.class)) {
                foundCaterpillar = true;
            } else if (f.getType().equals(Position.class)) {
                foundPosition = true;
            } else if (f.getType().equals(ActionQueue.class)) {
                foundActionQueue = true;
            } else if (f.getType().equals(TargetQueue.class)) {
                foundTargetQueue = true;
            } else if (f.getType().equals(Region.class)) {
                foundRegion = true;
            } else if (f.getType().equals(GameState.class)) {
                foundGameState = true;
            } else {
                continue;
            }
        }
        assertTrue(foundCaterpillar && foundPosition && foundActionQueue && foundTargetQueue && foundRegion && foundGameState);

        // check constructor for world
        assertEquals(World.class.getName(), world.getConstructor(TargetQueue.class, ActionQueue.class).getName());

        // Check step
        assertEquals("step", world.getMethod("step").getName());
        assertEquals("void", world.getMethod("step").getReturnType().getName());

        // Check getState
        assertEquals("getState", world.getMethod("getState").getName());
        assertEquals(GameState.class.getName(), world.getMethod("getState").getReturnType().getName());

        // Check getCaterpillar
        assertEquals("getCaterpillar", world.getMethod("getCaterpillar").getName());
        assertEquals(Caterpillar.class.getName(), world.getMethod("getCaterpillar").getReturnType().getName());

        // Check getFood
        assertEquals("getFood", world.getMethod("getFood").getName());
        assertEquals(Position.class.getName(), world.getMethod("getFood").getReturnType().getName());

        // Check isRunning
        assertEquals("isRunning", world.getMethod("isRunning").getName());
        assertEquals("boolean", world.getMethod("isRunning").getReturnType().getName());

    }
}

class Part1Test {

    // ==================== MY DOUBLY LINKED LIST TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("MyDLL add() test1")
    public void shouldAdd() {
        MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();

        list.add(2);
        list.addFirst(5);
        list.addFirst(9);
        list.addFirst(0);  // {0, 9, 5, 2}

        assertEquals(4, list.getSize());
        assertEquals(0, list.peekFirst());
        assertEquals(2, list.peekLast());

        list = new MyDoublyLinkedList<>();
        list.add(2);
        list.add(5);
        list.addLast(9);
        list.addFirst(0);  // {0, 2, 5, 9}

        assertEquals(4, list.getSize());
        assertEquals(0, list.peekFirst());
        assertEquals(9, list.peekLast());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL remove() test1")
    public void shouldRemove() {
        MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        Number removedItem = list.removeFirst();  // {2, 3}

        assertEquals(1, removedItem);
        assertEquals(2, list.peekFirst());
        assertEquals(2, list.getSize());

        list = new MyDoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.addFirst(4);
        list.addFirst(5);  // {5, 4, 1, 2, 3}

        removedItem = list.removeLast();

        assertEquals(3, removedItem);
        assertEquals(2, list.peekLast());
        assertEquals(4, list.getSize());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL exception handling test1")
    public void shouldThrowExceptionOnEmptyList() {
        MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();
        assertThrows(NoSuchElementException.class, () -> list.peekLast());
        assertThrows(NoSuchElementException.class, () -> list.peekFirst());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL peek() test1")
    public void shouldPeek() {
        MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();

        list.addLast(1);
        list.add(2);
        list.addFirst(3);

        assertEquals(3, list.peekFirst());
        assertEquals(2, list.peekLast());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL clear() test1")
    public void shouldClear() {
        MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();

        list.addLast(1);
        list.add(2);
        list.addFirst(3);

        list.clear();

        assertEquals(0, list.getSize());
        assertFalse(list.iterator().hasNext());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL equals() test1")
    public void shouldCheckEquals() {
        MyDoublyLinkedList<Number> list1 = new MyDoublyLinkedList<>();
        MyDoublyLinkedList<Number> list2 = new MyDoublyLinkedList<>();

        list1.add(1);
        list1.add(2);
        list1.add(3);

        list2.add(1);
        list2.add(2);
        list2.add(3);

        assertTrue(list1.equals(list2));
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyDLL equals() test2")
    public void shouldCheckNotEquals() {
        MyDoublyLinkedList<Number> list1 = new MyDoublyLinkedList<>();
        MyDoublyLinkedList<Number> list2 = new MyDoublyLinkedList<>();

        list1.add(1);
        list1.add(2);

        list2.add(2);
        list2.add(1);

        assertFalse(list1.equals(list2));
    }


    // ==================== MYQUEUE TEST =================== //

    @Test
    @Tag("score:2")
    @DisplayName("MyQueue enqueue(), dequeue() and clear() test")
    public void shouldEnqueueAndDequeue() {
        MyQueue<Number> queue = new MyQueue<Number>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertFalse(queue.isEmpty());

        assertEquals(queue.dequeue(), 1);
        assertEquals(queue.dequeue(), 2);
        assertEquals(queue.dequeue(), 3);

        assertTrue(queue.isEmpty());

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.clear();

        assertTrue(queue.isEmpty());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyQueue equals() test")
    public void shouldCheckEqual() {
        MyQueue<Number> queue1 = new MyQueue<Number>();
        MyQueue<Number> queue2 = new MyQueue<Number>();

        queue1.enqueue(1);
        queue1.enqueue(2);
        queue1.enqueue(3);

        queue2.enqueue(1);
        queue2.enqueue(2);
        queue2.enqueue(3);

        assertTrue(queue1.equals(queue2));
    }

    // ==================== MYSTACK TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("MyStack push() and pop() test1")
    public void shouldPush() {
        MyStack<Number> stack = new MyStack<Number>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(stack.getSize(), 3);
        assertEquals(stack.peek(), 3);

        Number popped = stack.pop();

        assertEquals(3, popped);
        assertEquals(2, stack.getSize());
        assertEquals(2, stack.peek());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyStack isEmpty() test1")
    public void shouldReturnIsEmpty() {
        MyStack<Number> stack = new MyStack<Number>();
        assertTrue(stack.isEmpty());
    }

    @Test
    @Tag("score:1")
    @DisplayName("MyStack clear() test1")
    public void shouldReturnClear() {
        MyStack<Number> stack = new MyStack<Number>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        stack.clear();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.getSize());
    }
}


class Part2Test {

    // ==================== POSITION CLASS TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("Position move() test1")
    void positionMoveDir1() {
        Position pos = new Position(7, 5);
        pos.moveWest();
        pos.moveNorth();

        assertEquals(6, pos.getX());
        assertEquals(4, pos.getY());

        pos = new Position(7, 5);
        pos.moveEast();
        pos.moveSouth();

        assertEquals(8, pos.getX());
        assertEquals(6, pos.getY());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Position reset() test1")
    void positionReset1() {
        Position pos = new Position(7, 5);

        pos.reset(6, 9);

        assertEquals(6, pos.getX());
        assertEquals(9, pos.getY());

        Position pos2 = new Position(9, 6);

        pos.reset(pos2);
        assertEquals(9, pos.getX());
        assertEquals(6, pos.getY());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Position getDistance() test")
    void  positionGetDistance() {
        Position pos = new Position(7, 5);
        Position pos2 = new Position(0, 5);

        assertEquals(7, Position.getDistance(pos, pos2));
    }

    @Test
    @Tag("score:1")
    @DisplayName("Position equals() test1")
    void positionEqual1() {
        Position pos = new Position(7, 5);
        Position pos2 = new Position(9, 6);

        assertFalse(pos.equals(pos2));

        pos2.reset(pos);

        assertTrue(pos.equals(pos2));
    }


    // ==================== TARGETQUEUE CLASS TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("TargetQueue addTargets() test1")
    void  tqAddTargets1() {
        TargetQueue test = new TargetQueue();
        assertTrue(test.isEmpty());

        test.addTargets("(7,5)");
        assertFalse(test.isEmpty());

        Position pos = new Position(7, 5);
        assertEquals(pos, test.dequeue());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TargetQueue addTargets() test2")
    void  tqAddTargets2() {
        TargetQueue test = new TargetQueue();

        test.addTargets("(7,5).(0,5).(2,3)");

        Position pos = new Position(7, 5);
        Position pos2 = new Position(0, 5);
        Position pos3 = new Position(2, 3);

        assertFalse(test.isEmpty());

        assertEquals(pos, test.dequeue());
        assertEquals(pos2, test.dequeue());
        assertEquals(pos3, test.dequeue());

    }

    @Test
    @Tag("score:1")
    @DisplayName("TargetQueue addTargets() test3")
    void  tqAddTargets3() {
        TargetQueue test = new TargetQueue();

        assertThrows(IllegalArgumentException.class,
                () -> test.addTargets("(7,5)(0,5)"));
    }

    @Test
    @Tag("score:1")
    @DisplayName("TargetQueue addTargets() test4")
    void  tqAddTargets4() {
        TargetQueue test = new TargetQueue();

        assertThrows(IllegalArgumentException.class,
                () -> test.addTargets("(7,1).(0,)"));
    }

    @Test
    @Tag("score:5")
    @DisplayName("TargetQueue addTargets() GitHub Test 1 - Killua")
    void  tqAddTargets5() {
        TargetQueue test = new TargetQueue();

        test.addTargets("(1,2).(3,4).(5,6).");

        Position pos = new Position(1, 2);
        Position pos2 = new Position(3, 4);
        Position pos3 = new Position(5, 6);

        assertFalse(test.isEmpty());

        assertEquals(pos, test.dequeue());
        assertEquals(pos2, test.dequeue());
        assertEquals(pos3, test.dequeue());

        // Should not have any error even with "." at the end
        // https://edstem.org/us/courses/32649/discussion/2716504

    }

    @Test
    @Tag("score:5")
    @DisplayName("TargetQueue addTargets() GitHub Test 2 - Killua")
    void  tqAddTargets6() {
        TargetQueue test = new TargetQueue();

        test.addTargets(".");

        assertTrue(test.isEmpty());

        // Should not have any error even with just "." as the queue should just be empty
        // https://edstem.org/us/courses/32649/discussion/2716504
    }

    @Test
    @Tag("score:5")
    @DisplayName("TargetQueue addTargets() GitHub Test 3 - Killua")
    void  tqAddTargets7() {
        TargetQueue test = new TargetQueue();

        test.addTargets(".(1,2).(3,4)");

        assertFalse(test.isEmpty());

        Position pos = new Position(1, 2);
        Position pos2 = new Position(3, 4);

        assertEquals(pos, test.dequeue());
        assertEquals(pos2, test.dequeue());

        // Should not have any error even with "." in front
        // https://edstem.org/us/courses/32649/discussion/2716504
    }

    @Test
    @Tag("score:5")
    @DisplayName("TargetQueue addTargets() GitHub Test 4 - Killua")
    void  tqAddTargets8() {
        TargetQueue test = new TargetQueue();

        assertThrows(IllegalArgumentException.class,
                () -> test.addTargets("(1,2)..(3,4)"));

        // Should throw error since there is more than one period between each position
        // https://edstem.org/us/courses/32649/discussion/2716504
    }

    @Test
    @Tag("score:5")
    @DisplayName("TargetQueue addTargets() GitHub Test 5 - Killua")
    void  tqAddTargets9() {
        TargetQueue test = new TargetQueue();

        assertThrows(IllegalArgumentException.class,
                () -> test.addTargets("(1, 2).(3,4)"));

        // Should throw error when there is a space between characters
        // https://edstem.org/us/courses/32649/discussion/2754324
    }

    @Test
    @Tag("score:1")
    @DisplayName("TargetQueue clear() test")
    void  tqClear() {
        TargetQueue test = new TargetQueue();

        test.addTargets("(7,5)");
        assertFalse(test.isEmpty());

        test.clear();
        assertTrue(test.isEmpty());
    }

    // ==================== ACTIONQUEUE CLASS TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("ActionQueue loadFromEncodedString() test1")
    void  aqLoadFromEncodedString1() {
        ActionQueue test = new ActionQueue();

        test.loadFromEncodedString("3[E]"); // {East, East, East}
        for (int i = 0; i < 3; i++) {
            assertEquals(Direction.EAST, test.dequeue());
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("ActionQueue loadFromEncodedString() test2")
    void  aqLoadFromEncodedString2() {
        ActionQueue test = new ActionQueue();

        test.loadFromEncodedString("3[N]2[S]1[W]"); // { North, North, North, South, South, West }
        for (int i = 0; i < 6; i++) {
            if (i < 3){
                assertEquals(Direction.NORTH, test.dequeue());
            } else if (i < 5) {
                assertEquals(Direction.SOUTH, test.dequeue());
            } else {
                assertEquals(Direction.WEST, test.dequeue());
            }
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("ActionQueue loadFromEncodedString() test3")
    void  aqLoadFromEncodedString3() {
        ActionQueue test = new ActionQueue();

        test.loadFromEncodedString("3[SW]");
        // {South, West, South, West, South, West}

        for (int i = 0; i < 3; i++) {
            assertEquals(Direction.SOUTH, test.dequeue());
            assertEquals(Direction.WEST, test.dequeue());
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("ActionQueue loadFromEncodedString() test4")
    void  aqLoadFromEncodedString4() {
        ActionQueue test = new ActionQueue();

        assertThrows(IllegalArgumentException.class,
                () -> test.loadFromEncodedString("2S[W]"));
    }

    @Test
    @Tag("score:1")
    @DisplayName("ActionQueue clear() test")
    void  aqClear() {
        ActionQueue test = new ActionQueue();

        test.loadFromEncodedString("3[E]");
        assertFalse(test.isEmpty());

        test.clear();
        assertTrue(test.isEmpty());
    }
}

class Part3Test {

    // ==================== REGION CLASS TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("Region contains() test1")
    void regionContainsTest1() {
        Region region = new Region(0,0,5,5);
        Position goodPos = new Position(2,4);
        assertTrue(region.contains(goodPos));
    }

    // ==================== CATERPILLAR CLASS TEST =================== //
    @Test
    @Tag("score:1")
    @DisplayName("Caterpillar constructor test1")
    void caterpillarConstructorTest1() {
        Caterpillar caterpillar = new Caterpillar();
        Position startPos = new Position(7, 7);
        assertEquals(caterpillar.peekFirst(), startPos);
        assertEquals(1, caterpillar.getSize());
        assertEquals(startPos, caterpillar.getHead());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Caterpillar eat() test1")
    void caterpillarEatTest1() {
        Caterpillar caterpillar = new Caterpillar();
        Position adjPos = new Position(8, 7);

        caterpillar.eat(adjPos);

        assertEquals(adjPos, caterpillar.peekFirst());
        assertEquals(2, caterpillar.getSize());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Caterpillar move() test1")
    void caterpillarMoveTest1() {
        Caterpillar caterpillar = new Caterpillar();
        Position adjPos = new Position(8, 7);

        caterpillar.move(adjPos);

        assertEquals(adjPos, caterpillar.peekFirst());
        assertEquals(1, caterpillar.getSize());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Caterpillar selfCollision() test1")
    void caterpillarSelfCollision1() {
        Caterpillar caterpillar = new Caterpillar();
        assertFalse(caterpillar.selfCollision(new Position(7, 9)));
    }

    // ==================== WORLD CLASS TEST =================== //
    @Test // tests if all required private fields are not null when calling constructor
    @Tag("score:1")
    @DisplayName("World constructor test1")
    void worldConstructorTest1() throws IllegalAccessException {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String food = "(9,9)";
        targetQueue.addTargets(food);

        World world = new World(targetQueue, actionQueue);

        Field[] privateWorldFields = World.class.getDeclaredFields();
        for (Field privateField : privateWorldFields) {
            privateField.setAccessible(true);
            Object value = privateField.get(world);
            assertNotNull(value);
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("World step() test1")
    void worldStepTest1() {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String food = "(7,10)";
        String direction = "2[N]";

        targetQueue.addTargets(food);
        actionQueue.loadFromEncodedString(direction);

        World world = new World(targetQueue, actionQueue);

        world.step();  // move 1 step N from (7,7) to (7,8)

        assertEquals(GameState.MOVE, world.getState());
        assertEquals(1, world.getCaterpillar().getSize());
        assertEquals(new Position(7, 6), world.getCaterpillar().getHead());
    }

    @Test
    @Tag("score:1")
    @DisplayName("World step() test2")
    void worldStepTest2() {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String food = "(5,9)";
        String direction = "10[E]";

        actionQueue.loadFromEncodedString(direction);
        targetQueue.addTargets(food);
        World world = new World(targetQueue, actionQueue);

        //move 9 steps E, wall collision
        for (int i = 0; i < 9; i++) {  // move 9 steps E from (7,7) to (15,7)
            world.step();
        }

        assertEquals(GameState.WALL_COLLISION, world.getState());
        assertEquals(new Position(15, 7), world.getCaterpillar().getHead());
    }
    @Test
    @Tag("score:2")
    @DisplayName("World step() test3")
    void worldStepTest3() throws Exception {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String food = "(9,9).(14,7).(7,10)";
        String direction = "2[S]2[E]" ;

        targetQueue.addTargets(food);
        actionQueue.loadFromEncodedString(direction);

        World world = new World(targetQueue, actionQueue);

        for (int i = 0; i < 4; i++) {  // move 4 steps S from (7,7) to (7,3)
            world.step();
        }

        assertEquals(GameState.EAT, world.getState());
        assertEquals(2, world.getCaterpillar().getSize());
        assertEquals(new Position(9, 9), world.getCaterpillar().getHead());
        assertFalse(targetQueue.isEmpty());
        assertTrue(actionQueue.isEmpty());

    }

    @Test
    @Tag("score:1")
    @DisplayName("World getters test1")
    void worldGetStateTest1() {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String str_target_pos = "(9,9)";
        String str_encoded = "2[E]" ;

        actionQueue.loadFromEncodedString(str_encoded);
        targetQueue.addTargets(str_target_pos);

        World world = new World(targetQueue, actionQueue);

        GameState state = GameState.MOVE;
        assertEquals(world.getState(),state);

        Caterpillar caterpillar = new Caterpillar();
        assertEquals(world.getCaterpillar(), caterpillar);

        Position pos = new Position(9,9);
        assertEquals(world.getFood(), pos);
    }
    @Test
    @Tag("score:1")
    @DisplayName("World isRunning() test1")
    void worldIsRunningTest1() {
        TargetQueue targetQueue = new TargetQueue();
        ActionQueue actionQueue = new ActionQueue();

        String str_target_pos = "(9,9)";
        targetQueue.addTargets(str_target_pos);
        World world = new World(targetQueue, actionQueue);

        world.step();

        assertEquals(world.getState(), GameState.NO_MORE_ACTION);
        assertFalse(world.isRunning());

    }
}


