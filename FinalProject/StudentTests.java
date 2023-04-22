package finalproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collector.*;
import java.util.stream.Stream.*;

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
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    int key = 10;
    int val = 20;

    int placeAt = table.hashFunction(key);
    ArrayList<LinkedList<MyPair<Integer, Integer>>> buckets = table.getBuckets();

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

  //same hash value
  @Test
  @DisplayName("Put test 4")
  void putTest4() {
    MyHashTable<Object, Object> table = new MyHashTable<>();
    int key1 = 1;
    int val1 = 20;
    int key2 = 17;
    int val2 = 200;

    int placeAt1 = table.hashFunction(key1);
    int placeAt2 = table.hashFunction(key2);

    ArrayList<LinkedList<MyPair<Object, Object>>> buckets = table.getBuckets();

    Object output1 = table.put(key1, val1);
    Object output2 = table.put(key2, val2);

    assertNotNull(buckets.get(placeAt1));
    assertNotNull(buckets.get(placeAt1).peek());
    assertNotNull(buckets.get(placeAt2));
    assertNotNull(buckets.get(placeAt2).peek());

    assertEquals(key1, buckets.get(placeAt1).peek().getKey());
    assertEquals(val1, buckets.get(placeAt1).peek().getValue());
    assertEquals(key2, buckets.get(placeAt2).peekLast().getKey());
    assertEquals(val2, buckets.get(placeAt2).peekLast().getValue());

    assertNull(output1);
    assertNull(output2);
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

  // Overwrite the value and get with the key
  @Test
  @DisplayName("Get test 3")
  void getTest3() {
      MyHashTable<Object, Object> table = new MyHashTable<>();
      int key = 10;
      int val1 = 20;
      int val2 = 200;

      table.put(key, val1);
      table.put(key, val2);

      assertEquals(200, table.get(10));
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
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(1);
    expected.add(10);
    expected.add(100);

    ArrayList<Integer> keys = table.getKeySet();
    Collections.sort(keys);

    assertEquals(expected, keys);
  }

  // no duplicates
  @Test
  @DisplayName("getValuesSet test 1")
  void getValuesSetTest1() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(2);
    expected.add(20);
    expected.add(200);

    ArrayList<Integer> vals = table.getValueSet();
    Collections.sort(vals);

    assertEquals(expected, vals);
  }

  @Test
  @DisplayName("getValuesSet test 2")
  void getValuesSetTest2() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 2);
    table.put(10, 20);
    table.put(100, 200);
    table.put(3, 200);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(2);
    expected.add(20);
    expected.add(200);

    ArrayList<Integer> vals = table.getValueSet();
    Collections.sort(vals);

    assertEquals(expected, vals);
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

  // rehashing index test
  @Test
  @DisplayName("rehash test 3")
  void rehashTest3() {
      MyHashTable<Object, Object> table = new MyHashTable<>();

      assertEquals(16, table.numBuckets());

      table.put(1, 10);
      table.put(16, 20);
      table.put(17, 30);
      table.put(32, 40);

      ArrayList<LinkedList<MyPair<Object, Object>>> buckets1 = table.getBuckets();
      assertEquals(2, buckets1.get(0).size());
      assertEquals(2, buckets1.get(1).size());

      table.rehash();
      assertEquals(32, table.numBuckets());

      // if properly rehashed, the index of elements in the hashmap should be properly reorganized
      ArrayList<LinkedList<MyPair<Object, Object>>> buckets2 = table.getBuckets();
      assertEquals(1, buckets2.get(0).size());
      assertEquals(1, buckets2.get(1).size());
      assertEquals(1, buckets2.get(16).size());
      assertEquals(1, buckets2.get(17).size());
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
}

// For tests below, if you are using a different filepath than the one given to the parser,
// make sure to change it to your filepath in parserInit()
// the tests below use the sample csv for speed
class DataAnalyzerTests {
  Parser p = new Parser("/RateMyProf_Data_Gendered_Sample.csv");

  // normal operation
  @Test
  @DisplayName("RatingDistributionByProf Test 1")
  void ratingDistributionByProfTest1() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionByProf(p);
    MyHashTable<String, Integer> output = analyzer.getDistByKeyword("rebecca  tsosie");

    assertEquals(0, output.get("1"));
    assertEquals(1, output.get("2"));
    assertEquals(1, output.get("3"));
    assertEquals(2, output.get("4"));
    assertEquals(3, output.get("5"));
  }

  // weird untrimmed and irregular cases keyword
  @Test
  @DisplayName("RatingDistributionByProf Test 2")
  void ratingDistributionByProfTest3() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionByProf(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("rebecca  tsosie");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword(" Rebecca  tsosIe ");

    assertEquals(output1.get("1"), output2.get("1"));
    assertEquals(output1.get("2"), output2.get("2"));
    assertEquals(output1.get("3"), output2.get("3"));
    assertEquals(output1.get("4"), output2.get("4"));
    assertEquals(output1.get("5"), output2.get("5"));

    assertEquals(0, output2.get("1"));
    assertEquals(1, output2.get("2"));
    assertEquals(1, output2.get("3"));
    assertEquals(2, output2.get("4"));
    assertEquals(3, output2.get("5"));
  }

  // normal operation, same example as pdf
  @Test
  @DisplayName("RatingCountPerProf Test 1")
  void ratingCountPerProfTest1() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionBySchool(p);
    MyHashTable<String, Integer> output = analyzer.getDistByKeyword("arizona state university");
    assertEquals(4, output.get("adam  chodorow\n3.88"));
    assertEquals(3, output.get("alan  matheson\n4.5"));
  }

  // weird untrimmed keyword
  @Test
  @DisplayName("RatingCountPerProf Test 2")
  void ratingCountPerProfTest2() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionBySchool(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("arizona state university");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword(" Arizona State University ");

    ArrayList<String> k1 = output1.getKeySet();
    ArrayList<String> k2 = output2.getKeySet();
    ArrayList<Integer> v1 = output1.getValueSet();
    ArrayList<Integer> v2 = output2.getValueSet();

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }

  // Correct gender output, F -> W
  @Test
  @DisplayName("GenderByKeyword Test 1")
  void genderByKeywordTest1() {
    p.read();
    DataAnalyzer analyzer = new GenderByKeyword(p);
    MyHashTable<String, Integer> output = analyzer.getDistByKeyword("caring");
    assertNotNull(output.get("M"));
    assertNotNull(output.get("F"));
    assertNotNull(output.get("X"));
  }

  // normal operation, same example as pdf
  @Test
  @DisplayName("GenderByKeyword Test 2")
  void genderByKeywordTest2() {
    p.read();
    DataAnalyzer analyzer = new GenderByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("caring");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword("smart");

    assertEquals(9, output1.get("F"));
    assertEquals(4, output1.get("M"));
    assertEquals(0, output1.get("X"));

    assertEquals(6, output2.get("F"));
    assertEquals(14, output2.get("M"));
    assertEquals(0, output2.get("X"));
  }

  @Test
  @DisplayName("GenderByKeyword Test 3")
  void genderByKeywordTest3() {
    p.read();
    DataAnalyzer analyzer = new GenderByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("caring");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword(" CaRiNg");

    ArrayList<String> k1 = output1.getKeySet();
    ArrayList<String> k2 = output2.getKeySet();
    ArrayList<Integer> v1 = output1.getValueSet();
    ArrayList<Integer> v2 = output2.getValueSet();
    Collections.sort(k1);
    Collections.sort(k2);
    Collections.sort(v1);
    Collections.sort(v2);

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }

  @Test
  @DisplayName("RatingByKeyword Test 1")
  void ratingByKeywordTest1() {
    p.read();
    DataAnalyzer analyzer = new RatingByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("fun");
    assertNotNull(output1.get("1"));
    assertNotNull(output1.get("2"));
    assertNotNull(output1.get("3"));
    assertNotNull(output1.get("4"));
    assertNotNull(output1.get("5"));
  }

  @Test
  @DisplayName("RatingByKeyword Test 2")
  void ratingByKeywordTest2() {
    p.read();
    DataAnalyzer analyzer = new RatingByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("fun");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword("FuN ");

    ArrayList<String> k1 = output1.getKeySet();
    ArrayList<String> k2 = output2.getKeySet();
    ArrayList<Integer> v1 = output1.getValueSet();
    ArrayList<Integer> v2 = output2.getValueSet();
    Collections.sort(k1);
    Collections.sort(k2);
    Collections.sort(v1);
    Collections.sort(v2);

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }

  // check for abundant words in one sentence
  @Test
  @DisplayName("RatingByKeyword Test 3")
  void ratingByKeywordTest3() {
    Parser parser1 = new Parser("");
    Parser parser2 = new Parser("");
    ArrayList<String[]> dataArray1 = new ArrayList<>();
    ArrayList<String[]> dataArray2 = new ArrayList<>();


    String[] arr1 = {"fun", "fun", "fun"};
    String[] arr2 = {"fun"};
    dataArray1.add(arr1);
    dataArray2.add(arr2);

    DataAnalyzer analyzer1 = new RatingByKeyword(parser1);
    DataAnalyzer analyzer2 = new RatingByKeyword(parser2);

    MyHashTable<String, Integer> output1 = analyzer1.getDistByKeyword("fun");
    MyHashTable<String, Integer> output2 = analyzer2.getDistByKeyword("fun");

    ArrayList<String> k1 = output1.getKeySet();
    ArrayList<String> k2 = output2.getKeySet();
    ArrayList<Integer> v1 = output1.getValueSet();
    ArrayList<Integer> v2 = output2.getValueSet();
    Collections.sort(k1);
    Collections.sort(k2);
    Collections.sort(v1);
    Collections.sort(v2);

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }

  @Test
  @DisplayName("RatingByGender Test 1")
  void ratingByGenderTest1() {
    p.read();
    DataAnalyzer analyzer = new RatingByGender(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("F, difficulty");
    assertNotNull(output1.get("1"));
    assertNotNull(output1.get("2"));
    assertNotNull(output1.get("3"));
    assertNotNull(output1.get("4"));
    assertNotNull(output1.get("5"));

  }

  @Test
  @DisplayName("RatingByGender Test 2")
  void ratingByGenderTest2() {
    p.read();
    DataAnalyzer analyzer = new RatingByGender(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("M, Quality ");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword("M, quality");

    ArrayList<String> k1 = output1.getKeySet();
    ArrayList<String> k2 = output2.getKeySet();
    ArrayList<Integer> v1 = output1.getValueSet();
    ArrayList<Integer> v2 = output2.getValueSet();
    Collections.sort(k1);
    Collections.sort(k2);
    Collections.sort(v1);
    Collections.sort(v2);

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }
}

