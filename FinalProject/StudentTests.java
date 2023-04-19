package finalproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTests {
  // default constructor;
  @Test
  @DisplayName("Constructor test 1")
  void constructorTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    assertEquals(0, table.size());
    assertTrue(table.isEmpty());
    assertEquals(16, table.numBuckets());
    assertNotNull(table.getBuckets());
  }

  // constructor with capacity;
  @Test
  @DisplayName("Constructor test 2")
  void constructorTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>(999);
    assertEquals(0, table.size());
    assertTrue(table.isEmpty());
    assertEquals(999, table.numBuckets());
    assertNotNull(table.getBuckets());
  }

  // normal put
  @Test
  @DisplayName("Put test 1")
  void putTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    int placeAt = table.hashFunction(key);
    ArrayList<LinkedList<MyPair<Object, Object>>> buckets = table.getBuckets();

    Object output = table.put(key, val);

    assertNotNull(buckets.get(placeAt));
    assertNotNull(buckets.get(placeAt).peek());

    assertEquals(key, buckets.get(placeAt).peek().getKey());
    assertEquals(val, buckets.get(placeAt).peek().getValue());

    assertNull(output);
  }

  // replacing a value in an existing key
  @Test
  @DisplayName("Put test 2")
  void putTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;
    int newVal = 30;

    int placeAt = table.hashFunction(key);
    ArrayList<LinkedList<MyPair<Object, Object>>> buckets = table.getBuckets();

    table.put(key, val);
    Object output = table.put(key, newVal);

    assertNotNull(buckets.get(placeAt));
    assertNotNull(buckets.get(placeAt).peek());

    assertInstanceOf(MyPair.class, buckets.get(placeAt).peek());

    assertEquals(key, buckets.get(placeAt).peek().getKey());
    assertEquals(newVal, buckets.get(placeAt).peek().getValue());

    // check if it returns old value
    assertEquals(20, output);
  }

  // check for resize
  @Test
  @DisplayName("Put test 3")
  void putTest3() {
    MyHashTable<Object, Object> table = new MyHashTable<>(2);
    int key1 = 10;
    int val1 = 20;

    int key2 = 3;
    int val2 = 5;

    assertEquals(2, table.numBuckets());

    table.put(key1, val1);
    table.put(key2, val2);

    assertEquals(4, table.numBuckets());
    assertEquals(2, table.size());
  }

  // normal get operations
  @Test
  @DisplayName("Get test 1")
  void getTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    table.put(10, 20);

    assertEquals(20, table.get(10));
  }

  @Test
  @DisplayName("Get test 2")
  void getTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    table.put(10, 20);

    assertNull(table.get(3));
  }

  @Test
  @DisplayName("Remove test 1")
  void removeTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    table.put(10, 20);
    Object output = table.remove(10);

    assertEquals(20, output);
    assertNull(table.get(10));
    assertFalse(
      table
        .getBuckets()
        .stream()
        .anyMatch(x -> x
          .stream()
          .anyMatch(y -> y
            .getKey()
            .equals(key)))
    );
  }

  @Test
  @DisplayName("Remove test 2")
  void removeTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    table.put(10, 20);
    Object output = table.remove(0);

    assertNull(output);
    assertNotNull(table.get(10));
    assertTrue(
      table
        .getBuckets()
        .stream()
        .anyMatch(x -> x
          .stream()
          .anyMatch(y -> y
            .getKey()
            .equals(key)))
    );
  }

  @Test
  @DisplayName("getKeySet test 1")
  void getKeySetTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(1);
    expected.add(10);
    expected.add(100);

    assertEquals(expected, table.getKeySet().stream().sorted().toList());
  }

  // no duplicates
  @Test
  @DisplayName("getValuesSet test 1")
  void getValuesSetTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(2);
    expected.add(20);
    expected.add(200);

    assertEquals(expected, table.getValueSet().stream().sorted().toList());
  }

  @Test
  @DisplayName("getValuesSet test 2")
  void getValuesSetTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);
    table.put(3, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(2);
    expected.add(20);
    expected.add(200);

    assertEquals(expected, table.getValueSet().stream().sorted().toList());
  }

  @Test
  @DisplayName("getEntries test 1")
  void getEntriesTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);
    table.put(3, 200);

    ArrayList<Integer> expectedKeys = new ArrayList<Integer>();
    expectedKeys.add(1);
    expectedKeys.add(3);
    expectedKeys.add(10);
    expectedKeys.add(100);

    ArrayList<Integer> expectedVals = new ArrayList<Integer>();
    expectedVals.add(2);
    expectedVals.add(20);
    expectedVals.add(200);
    expectedVals.add(200);

    ArrayList<MyPair<Object, Object>> entries = table.getEntries();

    assertEquals(entries.stream().map(MyPair::getKey).sorted().toList(), expectedKeys);
    assertEquals(entries.stream().map(MyPair::getValue).sorted().toList(), expectedVals);
  }

  // check if rehash doubles table size
  @Test
  @DisplayName("rehash test 1")
  void rehashTest1() {
    MyHashTable<Object, Object> table = new MyHashTable<>();

    assertEquals(16, table.numBuckets());

    table.rehash();

    assertEquals(32, table.numBuckets());
  }

  // check if rehash disperses the entries
  @Test
  @DisplayName("rehash test 2")
  void rehashTest2() {
    MyHashTable<Object, Object> table = new MyHashTable<>();

    assertEquals(16, table.numBuckets());

    table.put(1, 10);
    table.put(2, 20);
    table.put(3, 30);
    table.put(4, 40);

    table.rehash();
    assertEquals(32, table.numBuckets());

    // if properly rehashed, the hashmap should be able to get values after resizing
    assertEquals(10, table.get(1));
    assertEquals(20, table.get(2));
    assertEquals(30, table.get(3));
    assertEquals(40, table.get(4));
  }

  // standard iteration
  @Test
  @DisplayName("iterator test 1")
  void iteratorTest1() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 10);
    table.put(2, 20);
    table.put(3, 30);

    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<Integer> vals = new ArrayList<>();
    for (MyPair<Integer, Integer> item : table) {
      keys.add(item.getKey());
      vals.add(item.getValue());
    }

    ArrayList<Integer> expectedKeys = new ArrayList<>();
    expectedKeys.add(1);
    expectedKeys.add(2);
    expectedKeys.add(3);

    ArrayList<Integer> expectedVals = new ArrayList<>();
    expectedVals.add(10);
    expectedVals.add(20);
    expectedVals.add(30);

    assertEquals(expectedKeys, keys);
    assertEquals(expectedVals, vals);
  }

  // check hasNext() returns
  @Test
  @DisplayName("iterator test 2")
  void iteratorTest2() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 10);
    Iterator<MyPair<Integer, Integer>> i = table.iterator();
    assertTrue(i.hasNext());
    i.next();
    assertFalse(i.hasNext());
  }

  @Test
  @DisplayName("iterator test 3")
  void iteratorTest3() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 10);
    Iterator<MyPair<Integer, Integer>> i = table.iterator();
    assertNotNull(i);
    MyPair<Integer, Integer> next = i.next();
    assertEquals(1, next.getKey());
    assertEquals(10, next.getValue());
    assertThrows(NoSuchElementException.class, i::next);
  }
}


