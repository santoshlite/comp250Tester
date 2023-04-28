package finalproject;

import javafx.util.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.time.*;

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

    //check if rehash handled keys with same hashvalues
  @Test
  @DisplayName("rehash test 4")
  void rehashTest4() {
    MyHashTable<Integer, String> tester = new MyHashTable<Integer, String>(3);

    tester.put(3, "key 3");
    tester.put(9, "key 9");

    tester.rehash();

    ArrayList<LinkedList<MyPair<Integer, String>>> buckets = tester.getBuckets();
    assertEquals(2, tester.size());
    assertEquals(6, tester.numBuckets());
    assertEquals(2, buckets.get(3).size());
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

  // check hasNext() returns
  @Test
  @DisplayName("iterator test 3")
  void iteratorTest3() {
    MyHashTable<Integer, Integer> table = new MyHashTable<>();
    table.put(1, 10);
    table.put(17,100);
    table.put(4, 20);
    Iterator<MyPair<Integer, Integer>> i = table.iterator();
    assertTrue(i.hasNext());
    assertEquals(1, Objects.requireNonNull(i.next()).getKey());
    assertTrue(i.hasNext());
    assertEquals(17, Objects.requireNonNull(i.next()).getKey());
    assertTrue(i.hasNext());
    assertEquals(4, Objects.requireNonNull(i.next()).getKey());
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

  // null testing
  @Test
  @DisplayName("RatingDistributionByProf Test 3")
  void ratingDistributionByProfTest4() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionByProf(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("Invalid");

    assertNull(output1);
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

  // round the average rating to two decimal number
  @Test
  @DisplayName("RatingCountPerProf Test 3")
  void ratingCountPerProfTest3() {
    Parser parser = new Parser("");

    HashMap<String, Integer> fields = new HashMap<>();
    fields.put("professor_name", 0);
    fields.put("school_name", 1);
    fields.put("department_name", 2);
    fields.put("post_date", 3);
    fields.put("student_star", 4);
    fields.put("student_difficult", 5);
    fields.put("comments", 6);
    fields.put("gender", 7);

    parser.fields = fields;
    String[] prof1 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "08/26/2014", "5", "1", "Diana is a great professor and counselor!I took the class because I need extra units but I learned so much. She\'s very helpful and willing to answer any questions.It was like having a counseling appointment for 6 weeks.Now,I make my appointments with her.She as motivated as if it was her very first time teaching or counseling.We need more like her!", "F"};
    String[] prof2 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "04/28/2013", "5", "1", "Super easy, funny, enthusiastic and helpful!!", "F"};
    String[] prof3 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "04/28/2013", "4.5", "1", "She gives an extremely helpful and very fun class environment. I recommend this class for any freshmen who don\\'t quite understand the concept of GPA\\'s or regulations in LBCC.", "F"};
    String[] prof4 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "04/28/2013", "4", "1", "She gives an extremely helpful and very fun class environment. I recommend this class for any freshmen who don\\'t quite understand the concept of GPA\\'s or regulations in LBCC.", "F"};

    parser.data.add(prof1);
    parser.data.add(prof2);
    parser.data.add(prof3);
    parser.data.add(prof4);

    DataAnalyzer analyzer = new RatingDistributionBySchool(parser);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("Long Beach City College");

    ArrayList<String> k1 = output1.getKeySet();

    ArrayList<String> expect = new ArrayList<>();
    expect.add("diana  oqimachi" + "\n" + "4.63");

    assertEquals(expect, k1);
  }

  // null testing
  @Test
  @DisplayName("RatingCountPerProf Test 4")
  void ratingCountPerProfTest4() {
    p.read();
    DataAnalyzer analyzer = new RatingDistributionBySchool(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("Invalid");

    assertNull(output1);
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

  // null testing
  @Test
  @DisplayName("GenderByKeyword Test 4")
  void genderByKeywordTest4() {
    p.read();
    DataAnalyzer analyzer = new GenderByKeyword(p);

    MyHashTable<String, Integer> output = analyzer.getDistByKeyword("invalidInput");
    assertNull(output);
  }
  
  @Test
  @DisplayName("GenderByKeyword Test 5")
  void genderByKeywordTest5() {
    p.read();
    DataAnalyzer analyzer = new GenderByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("   KiND  ");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword(" humor      ");
    
    assertEquals(15, output1.get("M"));

    assertEquals(8, output1.get("F"));
    
    assertEquals(1, output1.get("X"));

    assertEquals(3, output2.get("M"));

    assertEquals(7, output2.get("F"));
    
    assertEquals(0, output2.get("X"));
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

    HashMap<String, Integer> fields = new HashMap<>();
    fields.put("professor_name", 0);
    fields.put("school_name", 1);
    fields.put("department_name", 2);
    fields.put("post_date", 3);
    fields.put("student_star", 4);
    fields.put("student_difficult", 5);
    fields.put("comments", 6);
    fields.put("gender", 7);

    parser1.fields = fields;
    parser2.fields = fields;

    String[] arr1 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "08/26/2014", "5", "1", "fun"};
    String[] arr2 = {"Diana  Oqimachi", "Long Beach City College", "Counseling department", "08/26/2014", "5", "1", "Fun fun fun!"};
    dataArray1.add(arr1);
    dataArray2.add(arr2);

    parser1.data = dataArray1;
    parser2.data = dataArray2;

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
    Collections.sort(v1);

    assertEquals(k1, k2);
    assertEquals(v1, v2);
  }

  // null testing
  @Test
  @DisplayName("RatingByKeyword Test 4")
  void ratingByKeywordTest4() {
    p.read();
    DataAnalyzer analyzer = new RatingByKeyword(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("InVaLIdInPut");
    assertNull(output1);
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

  //test for invalid input strings
  @Test
  @DisplayName("RatingByGender Test 3")
  void ratingByGenderTest3() {
    p.read();
    DataAnalyzer analyzer = new RatingByGender(p);
    
    //See Ed post #1628 & #1741 for edge case clarification
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("M,       difficulty");
    MyHashTable<String, Integer> output2 = analyzer.getDistByKeyword("F       , difficulty");
    MyHashTable<String, Integer> output3 = analyzer.getDistByKeyword("F       ,       difficulty");
    MyHashTable<String, Integer> output4 = analyzer.getDistByKeyword("M, in valid");
    MyHashTable<String, Integer> output5 = analyzer.getDistByKeyword("x, quality");
    MyHashTable<String, Integer> output6 = analyzer.getDistByKeyword("m, dIfFiCULTY");

    assertNull(output1);
    assertNull(output2);
    assertNull(output3);
    assertNull(output4);
    assertNull(output5);
    assertNotNull(output6);
  }
  
  @Test
  @DisplayName("RatingByGender Test 4")
  void ratingByGenderTest4() {
    p.read();
    DataAnalyzer analyzer = new RatingByGender(p);
    MyHashTable<String, Integer> output1 = analyzer.getDistByKeyword("F, quality");
    assertEquals(62,output1.get("1"));
    assertEquals(42,output1.get("2"));
    assertEquals(44,output1.get("3"));
    assertEquals(83,output1.get("4"));
    assertEquals(198,output1.get("5"));
  }
}

// The below tests do not assert any outputs,
// they time the runtime of each of your functions
// with different number of entries and print out the time taken
// You should observe with your own due diligence whether the increase in runtime is
// your desired time complexity:
//   constant O(1) for getDistByKeyword
//   linear by bucket numbers O(m) for instantiation
// You can plot it in your preferred data visualization method for some visuals

// NOTE:
// This method of stress testing is naive and not production-level
// due to factors such different computer hardwares, compiler optimizations, and more
// Please only use the data as a mere reference.
class TimeTests {
  // parser with no data
  private final String[] NAMES = new String[]
    {"Sam",
      "Rebecca",
      "Albert",
      "Eric",
      "Ludwig",
      "XQC",
      "Hasan"};
  private final String[] UNI_WORDS = new String[]
    {"Wild",
      "Chicken",
      "Omega",
      "Theta",
      "Polytechnic",
      "Kappa"};
  private final String[] DEPARTMENTS = new String[]
    {"Mathematics",
      "Philosophy",
      "Physics",
      "Statistics",
      "Computer Science",
      "Linguistic"};
  private final String[] DAY = new String[]
    {"01", "02", "03", "04", "05", "06", "07", "08", "09",
      "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
      "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
      "30", "31"};
  private final String[] MONTH = new String[]
    {"01", "02", "03", "04", "05", "06", "07", "08", "09",
      "10", "11", "12"};
  private final String[] YEAR = new String[]{"1999", "2000", "2001", "2002", "2003"};
  private final String[] RATINGS = new String[]{"1", "2", "3", "4", "5"};

  Parser p = new Parser("/RateMyProf_Data_Gendered.csv");

  private void parserInitFields() {
    p.fields = new HashMap<>();
    p.fields.put("professor_name", 0);
    p.fields.put("school_name", 1);
    p.fields.put("department_name", 2);
    p.fields.put("post_date", 3);
    p.fields.put("student_star", 4);
    p.fields.put("student_difficult", 5);
    p.fields.put("comments", 6);
    p.fields.put("gender", 7);
  }

  private final int[] NUMBERS_OF_ENTRIES
    = new int[]{100, 1000, 5000, 10000, 30000, 50000, 70000, 90000, 110000};

  // fill the parser with randomly generated dummy data
  private void parserInitData(int numberOfEntries) {
    p.data = new ArrayList<>();

    for (int i = 0; i < numberOfEntries; i++) {
      java.util.Random random = new java.util.Random();
      String name = NAMES[random.nextInt(NAMES.length)];
      String uni1 = UNI_WORDS[random.nextInt(UNI_WORDS.length)];
      String uni2 = UNI_WORDS[random.nextInt(UNI_WORDS.length)];
      String department = DEPARTMENTS[random.nextInt(DEPARTMENTS.length)];
      String day = DAY[random.nextInt(DAY.length)];
      String month = MONTH[random.nextInt(MONTH.length)];
      String year = YEAR[random.nextInt(YEAR.length)];
      String quality = RATINGS[random.nextInt(RATINGS.length)];
      String difficulty = RATINGS[random.nextInt(RATINGS.length)];

      p.data.add(new String[]
        {name,
          String.format("%s %S University", uni1, uni2),
          department + " department",
          String.format("%s/%s/%s", month, day, year),
          quality, difficulty,
          "Lorem Ipsum Most amazing instructor! She makes class fun and interesting! Cares about her students and wants nothing more than to see you succeed!", "F"});
    }
  }

  private <T> Pair<DataAnalyzer, Long> extract(Supplier<DataAnalyzer> constructor) {
    Instant start = Instant.now();
    DataAnalyzer analyzer = constructor.get();
    Instant finish = Instant.now();

    long time = Duration.between(start, finish).toMillis();
    return new Pair<>(analyzer, time);
  }

  //returns time taken
  private long query(DataAnalyzer analyzer, String keyword) {
    Instant start = Instant.now();
    analyzer.getDistByKeyword(keyword);
    Instant finish = Instant.now();

    return Duration.between(start, finish).toMillis();
  }

  @Test
  @DisplayName("Time for RatingDistributionByProf")
  void ratingDistributionByProfTime() {
    parserInitFields();
    Supplier<DataAnalyzer> constructor = () -> new RatingDistributionByProf(p);
    String keyword = "Sam";

    for (int num : NUMBERS_OF_ENTRIES) {
      parserInitData(num);
      Pair<DataAnalyzer, Long> extracted = extract(constructor);
      DataAnalyzer analyzer = extracted.getKey();
      Object time = extracted.getValue();

      System.out.println("The time taken to extract information for " + num + " entries is " + time + "ms");
      System.out.println("The time taken to query a keyword for " + num + " entries is " + query(analyzer, keyword) + "ms");
      System.out.println();
    }
  }

  @Test
  @DisplayName("Time for RatingDistributionBySchool")
  void ratingDistributionBySchoolTime() {
    parserInitFields();
    Supplier<DataAnalyzer> constructor = () -> new RatingDistributionBySchool(p);
    String keyword = "Wild Chicken University";

    for (int num : NUMBERS_OF_ENTRIES) {
      parserInitData(num);

      Pair<DataAnalyzer, Long> extracted = extract(constructor);
      DataAnalyzer analyzer = extracted.getKey();
      Object time = extracted.getValue();

      System.out.println("The time taken to extract information for " + num + " entries is " + time + "ms");
      System.out.println("The time taken to query a keyword for " + num + " entries is " + query(analyzer, keyword) + "ms");
      System.out.println();
    }
  }

  @Test
  @DisplayName("Time for RatingByGender")
  void ratingByGenderTime() {
    parserInitFields();
    Supplier<DataAnalyzer> constructor = () -> new RatingByGender(p);
    String keyword = "F, difficulty";

    for (int num : NUMBERS_OF_ENTRIES) {
      parserInitData(num);

      Pair<DataAnalyzer, Long> extracted = extract(constructor);
      DataAnalyzer analyzer = extracted.getKey();
      Object time = extracted.getValue();

      System.out.println("The time taken to extract information for " + num + " entries is " + time + "ms");
      System.out.println("The time taken to query a keyword for " + num + " entries is " + query(analyzer, keyword) + "ms");
      System.out.println();
    }
  }

  @Test
  @DisplayName("Time for GenderByKeyword")
  void genderByKeywordTime() {
    parserInitFields();
    Supplier<DataAnalyzer> constructor = () -> new GenderByKeyword(p);
    String keyword = "fun";

    for (int num : NUMBERS_OF_ENTRIES) {
      parserInitData(num);

      Pair<DataAnalyzer, Long> extracted = extract(constructor);
      DataAnalyzer analyzer = extracted.getKey();
      Object time = extracted.getValue();

      System.out.println("The time taken to extract information for " + num + " entries is " + time + "ms");
      System.out.println("The time taken to query a keyword for " + num + " entries is " + query(analyzer, keyword) + "ms");
      System.out.println();
    }
  }

  @Test
  @DisplayName("Time for RatingByKeyword")
  void ratingByKeyword() {
    parserInitFields();
    Supplier<DataAnalyzer> constructor = () -> new RatingByKeyword(p);
    String keyword = "fun";

    for (int num : NUMBERS_OF_ENTRIES) {
      parserInitData(num);

      Pair<DataAnalyzer, Long> extracted = extract(constructor);
      DataAnalyzer analyzer = extracted.getKey();
      Object time = extracted.getValue();

      System.out.println("The time taken to extract information for " + num + " entries is " + time + "ms");
      System.out.println("The time taken to query a keyword for " + num + " entries is " + query(analyzer, keyword) + "ms");
      System.out.println();
    }
  }
}

