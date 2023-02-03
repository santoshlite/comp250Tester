package assignment1;

// To make the tester work, you might need to setUp some import libraries. To do so:
// 1: Hover over the red text saying junit (or other libraries that are red)
// 2: click add to classpath or import statement (depends on the IDE)
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.Arrays;


public class Tester {}

class AirportTest { // 2 points

    @Test
    @Tag("score:1") @DisplayName("Airport getFees() Test1")
    void getFees_Test1() {
        Airport a = new Airport(200, 100, 50);
        assertEquals(50, a.getFees(),
                "Airport: getFees() did not return the correct fees");
    }

    @Test
    @Tag("score:1") @DisplayName("Airport getDistance() Test1")
    void getDistance_Test1() {
        Airport a = new Airport(44, 120, 100);
        Airport b = new Airport(50, 112, 100);
        assertEquals(10, Airport.getDistance(a, b),
                "Airport: getDistance() did not return the correct distance");
    }

    //Test if the method rounds up properly
    @Test
    @Tag("score:1") @DisplayName("Airport getDistance() Test2")
    void getDistance_Test2() {
        Airport a = new Airport(44, 120, 100);
        Airport b = new Airport(55, 113, 100);
        assertEquals(14, Airport.getDistance(a, b), //Exact value 13.04 should be rounded up to 14
                "Airport: getDistance() did not return the correct distance");
    }
}

class RoomTest {    // 6 points

    @Test
    @Tag("score:1") @DisplayName("Room Constructor Test1")
    void roomConstructor_Test1() {
        Room room = new Room("double");
        assertEquals("double", room.getType(),
                "Room: getRoomType() did not return the correct type for a double room");
        assertEquals(9000, room.getPrice(),
                "Room: getPrice() did not return the correct price for a double room");

        room = new Room("queen");
        assertEquals("queen", room.getType(),
                "Room: getRoomType() did not return the correct type for a queen room");
        assertEquals(11000, room.getPrice(),
                "Room: getPrice() did not return the correct price for a queen room");

        room = new Room("king");
        assertEquals("king", room.getType(),
                "Room: getRoomType() did not return the correct type for a king room");
        assertEquals(15000, room.getPrice(),
                "Room: getPrice() did not return the correct price for a king room");

    }


    @Test
    @Tag("score:1") @DisplayName("Room Constructor Test4")
    void roomConstructor_Test4() {
        assertThrows(IllegalArgumentException.class, () -> new Room("twin"),
                "Room: constructor did not throw an exception for an invalid room type");
    }

    @Test
    @Tag("score:1") @DisplayName("Room Copy Constructor Test1")
    void copyConstructor_Test1() {
        Room room = new Room("double");
        Room copyRoom = new Room(room);

        assertNotSame(room, copyRoom,
                "Room: copy constructor did not create a new object");

        assertEquals("double", copyRoom.getType(),
                "Room: getRoomType() did not return the correct type for its copy");

        assertEquals(9000, copyRoom.getPrice(),
                "Room: getPrice() did not return the correct price for its copy");
    }

    @Test
    @Tag("score:1") @DisplayName("Room findAvailableRoom() Test1")
    void findAvailableRoom_Test1() {
        Room[] rooms = {new Room("king"), new Room("queen"), new Room("double")};
        assertEquals(rooms[1], Room.findAvailableRoom(rooms, "queen"),
                "Room: findAvailableRoom() did not return the correct room");
    }

    //Check if the method returns null when no room is found
    @Test
    @Tag("score:1") @DisplayName("Room findAvailableRoom() Test2")
    void findAvailableRoom_Test2() {
        Room[] rooms = {new Room("king"), new Room("king"), new Room("double")};
        assertNull(Room.findAvailableRoom(rooms, "queen"),
                "Room: findAvailableRoom() did not return the correct room");
    }


    @Test
    @Tag("score:1") @DisplayName("Room findAvailableRoom() Test3")
    void findAvailableRoom_Test3() {
        Room[] rooms = {new Room("king"), new Room("queen"), new Room("double"),new Room("queen")};
        assertEquals(rooms[1], Room.findAvailableRoom(rooms, "queen"),
                "Room: findAvailableRoom() did not return the correct room");
    }

    @Test
    @Tag("score:1") @DisplayName("Room findAvailableRoom() Test4")
    void findAvailableRoom_Test4() {
        Room[] rooms = {};
        assertNull(Room.findAvailableRoom(rooms, "queen"),
                "Room: findAvailableRoom() did not return the correct room");
    }

    @Test
    @Tag("score:1") @DisplayName("Room findAvailableRoom() Test7")
    void findAvailableRoom_Test7() {
        Room[] rooms = {new Room("king"), null, new Room("queen"), new Room("double"),new Room("queen")};
        assertEquals(rooms[2], Room.findAvailableRoom(rooms, "queen"),
                "Room: findAvailableRoom() did not return the correct room");
    }

    @Test
    @Tag("score:1") @DisplayName("Room makeRoomAvailable() Test1")
    void makeRoomAvailable_Test1() {
        Room[] rooms = {new Room("double"), new Room("king"), new Room("queen")};
        assertFalse(Room.makeRoomAvailable(rooms, "king"),
                "Room: makeRoomAvailable() did not return the correct value"  );
    }

    //Check if the method returns false if the input room is not in the list
    @Test
    @Tag("score:1") @DisplayName("Room makeRoomAvailable() Test2")
    void makeRoomAvailable_Test2() {
        Room[] rooms = {new Room("double"), new Room("queen"), new Room("queen")};
        assertFalse(Room.makeRoomAvailable(rooms, "king"),
                "Room: makeRoomAvailable() did not return the correct value"  );
    }


    @Test
    @Tag("score:1") @DisplayName("Room makeRoomAvailable() Test3")
    void makeRoomAvailable_Test3() {
        Room doubleRoom = new Room("double");
        Room[] rooms = {doubleRoom, new Room("queen"), new Room("queen")};
        doubleRoom.changeAvailability();
        assertTrue(Room.makeRoomAvailable(rooms, "double"),
                "Room: makeRoomAvailable() did not return the correct value"  );
    }


    @Test
    @Tag("score:1") @DisplayName("Room makeRoomAvailable() Test6")
    void makeRoomAvailable_Test6() {

        Room king = new Room("double");
        Room[] rooms = {king, null};
        assertFalse(Room.makeRoomAvailable(rooms, "double"),
                "Room: makeRoomAvailable() did not return the correct value"  );
    }

    @Test
    @Tag("score:1") @DisplayName("Room makeRoomAvailable() Test7")
    void makeRoomAvailable_Test7() {

        Room aDouble = new Room("double");
        Room aKing = new Room("king");
        aKing.changeAvailability(); // Making the aKing room not available/false
        Room[] rooms = {aDouble, null, aKing}; // Should go through room null without return false
        assertTrue(Room.makeRoomAvailable(rooms, "king"),
                "Room: makeRoomAvailable() did not return the correct value"  );
    }

    @Test
    @Tag("score:1") @DisplayName("Room changeAvailability Test1")
    void changeAvailability_Test1() {
        Room room = new Room("double");
        Room[] rooms = {room};
        room.changeAvailability();
        assertTrue(Room.makeRoomAvailable(rooms, "double"),
                "Room: changeAvailability() did not change the availability of the room");

    }

    @Test
    //Check if the method returns false if all rooms are already available
    @Tag("score:1") @DisplayName("Room changeAvailability Test2")
    void changeAvailability_Test2() {
        Room room1 = new Room("double");
        Room room2 = new Room("double");
        Room[] rooms = {room1,room2};
        assertFalse(Room.makeRoomAvailable(rooms, "double"),
                "Room: changeAvailability() did not return false");

    }

}

class HotelTest {       // 7 points
    @Test
    @Tag("score:2") @DisplayName("Hotel Constructor Test1")
    void deepCopyConstructor_Test1 () throws IllegalAccessException {
        Room[] rooms = {new Room("double"), new Room("queen"), new Room("king")};
        Hotel hotel = new Hotel("Hotel1", rooms);

        Room[] roomsCopy = new Room[rooms.length];
        String name = "";

        Field[] fields = Hotel.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == Room[].class) {
                field.setAccessible(true);
                roomsCopy = (Room[]) field.get(hotel);
            } else if (field.getType() == String.class) {
                field.setAccessible(true);
                name = (String) field.get(hotel);
            }
        }

        assertFalse(Arrays.deepEquals(rooms, roomsCopy),
                "Hotel: deep copy constructor did not create a deep copy of the input rooms array");
        assertEquals("Hotel1", name,
                "Hotel: Constructor did not set the name correctly");
    }
    @Test
    @Tag ("score:2") @DisplayName("Hotel reserveRoom() Test1")
    void reserveRoom_Test1() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        assertEquals(9000, hotel1.reserveRoom("double"),
                "Hotel: reserveRoom() did not return the correct price of the room after reserving it");
    }

    @Test
    @Tag ("score:1") @DisplayName("Hotel reserveRoom() Test2")
    void reserveRoom_Test2() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        assertThrows(IllegalArgumentException.class, () -> hotel1.reserveRoom("king"),
                "Hotel: reserveRoom() did not throw an exception for an invalid room type");
    }

    @Test
    @Tag ("score:1") @DisplayName("Hotel cancelRoom() Test1")
    void cancelRoom_Test1() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        hotel1.reserveRoom("double");
        assertTrue(hotel1.cancelRoom("double"),
                "Hotel: cancelRoom() did not return the correct value");
    }

    @Test
    @Tag ("score:1") @DisplayName("Hotel cancelRoom() Test2")
    void cancelRoom_Test2() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        assertFalse(hotel1.cancelRoom("king"),
                "Hotel: cancelRoom() did not return the correct value");
    }

    // empty room listing
    @Test
    @Tag ("score:1") @DisplayName("Hotel cancelRoom() Test3")
    void cancelRoom_Test3() {
        Room[] rooms = {};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        assertFalse(hotel1.cancelRoom("king"),
                "Hotel: cancelRoom() did not return the correct value");
    }

    // input null value for type of room
    @Test
    @Tag ("score:1") @DisplayName("Hotel cancelRoom() Test4")
    void cancelRoom_Test4() {
        Room[] rooms = {};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        assertFalse(hotel1.cancelRoom(null),
                "Hotel: cancelRoom() did not return the correct value");
    }

}

class CustomerTest {    // 7 points
    @Test
    @Tag("score:1") @DisplayName("Customer Constructor Test1")
    void customerConstructor_Test1() {
        Customer customer = new Customer("bob", 100);
        assertEquals("bob", customer.getName(),
                "Customer: getName() did not return the correct name");
        assertEquals(100, customer.getBalance(),
                "Customer: getBalance() did not return the correct balance");
        assertEquals(0, customer.getBasket().getNumOfReservations(),
                "Customer: getBasket() did not return the correct number of reservations");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer addFunds() Test1")
    void addFunds_Test1() {
        Customer customer = new Customer("bob", 100);
        assertEquals(101, customer.addFunds(1),
                "Customer: addFunds() did not return the correct funds");
        assertEquals(101, customer.getBalance(),
                "Customer: getBalance() did not return the correct balance");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer addToBasket(Reservation) Test1")
    void addToBasket_Test1_Reservation() {
        Customer customer = new Customer("bob", 100);
        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("barcelo", rooms);
        HotelReservation reservation = new HotelReservation("bob", hotel, "double", 2);

        assertEquals(1, customer.addToBasket(reservation),
                "Customer: addToBasket(Reservation) did not return the correct number of reservations in the basket");
    }


    @Test
    @Tag("score:1") @DisplayName("Customer addToBasket(HotelReservation) Test2")
    void addToBasket_Test2_Reservation() {
        Customer customer = new Customer("bob", 100);
        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("barcelo", rooms);

        assertEquals(1, customer.addToBasket(hotel, "double", 2, false),
                "Customer: addToBasket() for the Hotel type did not return the correct number of reservations in the basket");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer addToBasket(Without Breakfast) Test3")
    void addToBasket_Test3_Reservation() {
        Customer customer = new Customer("Killua", 100);
        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("Greed Island", rooms);
        customer.addToBasket(hotel, "double", 2, false);

        int totalPrice = customer.getBasket().getTotalCost();

        assertEquals(18000, totalPrice,
                "Customer: addToBasket() for the Hotel type did not return the correct number of reservations in the basket");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer addToBasket(With Breakfast) Test4")
    void addToBasket_Test4_Reservation() {
        Customer customer = new Customer("Killua", 100);
        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("Greed Island", rooms);
        customer.addToBasket(hotel, "double", 2, true);

        int totalPrice = customer.getBasket().getTotalCost();

        assertEquals(20000, totalPrice,
                "Customer: addToBasket() for the Hotel type did not return the correct number of reservations in the basket");
    }


    @Test
    @Tag("score:1") @DisplayName("Customer addToBasket(FlightReservation) Test5")
    void addToBasket_Test5_Reservation() {
        Customer customer = new Customer("bob", 100);
        Airport airport1 = new Airport(100, 200, 1000);
        Airport airport2 = new Airport(10, 20, 2000);

        assertEquals(1, customer.addToBasket(airport1, airport2),
                "Customer: addToBasket() for the Flight type did not return the correct number of reservations in the basket");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer removeFromBasket(Reservation) Test1")
    void removeFromBasket_Test1() {
        Customer customer = new Customer("bob", 100);

        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("barcelo", rooms);
        Reservation reservation = new HotelReservation("bob", hotel, "double", 2);
        customer.addToBasket(reservation);

        assertTrue(customer.removeFromBasket(reservation),
                "Customer: removeFromBasket(Reservation) did not return the correct value");
    }


    @Test
    @Tag("score:1") @DisplayName("Customer removeFromBasket(Reservation) Test2")
    void removeFromBasket_Test2() {
        Customer customer = new Customer("Bob", 100);
        Customer customer2 = new Customer("Not Bob", 100);

        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("Average Hotel", rooms);
        Reservation reservation = new HotelReservation("Bob", hotel, "double", 2);
        customer.addToBasket(reservation);

        assertFalse(customer2.removeFromBasket(reservation),
                "Customer: removeFromBasket(Reservation) did not return the correct value");
    }


    // check if it works when we remove null
    @Test
    @Tag("score:1") @DisplayName("Customer removeFromBasket(Reservation) Test3")
    void removeFromBasket_Test3() {
        Customer customer = new Customer("Bob", 100);

        Room[] rooms = {};
        Hotel hotel = new Hotel("Average Hotel", rooms);

        Airport a1 = new Airport(44, 120, 100);
        Airport a2 = new Airport(45, 120, 100);

        assertFalse(customer.removeFromBasket(null),
                "Customer: removeFromBasket(Reservation) did not return the correct value");
    }

    @Test
    @Tag("score:1") @DisplayName("Customer checkOut() Test1")
    void checkout_Tes1() {
        Customer customer = new Customer("bob", 100000);

        Room[] rooms = {new Room("double")};
        Hotel hotel = new Hotel("barcelo", rooms);
        HotelReservation reservation = new HotelReservation("bob", hotel, "double", 2);
        customer.addToBasket(reservation);

        assertEquals(82000, customer.checkOut(),
                "Customer: checkOut() did not return the correct balance after checkOut");
    }


}

class ReservationTest {     // 2 points
    @Test
    @Tag("score:2") @DisplayName("Reservation reservationName() Test1")
    void reservationName() {
        Reservation fakeReservation = new ReservationTest.FakeReservation("Alex");
        assertEquals("Alex", fakeReservation.reservationName(),
                "Reservation: reservationName() returns the wrong name.");
    }
    private static class FakeReservation extends Reservation {

        public FakeReservation(String name) {
            super(name);
        }

        @Override
        public int getCost() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}

class HotelReservationTest {    // 4 points

    @Test
    @Tag("score:1") @DisplayName("HotelReservation getNumOfNights() Test1")
    void getNumOfNights() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        assertEquals(2, hotelReservation1.getNumOfNights(),
                "HotelReservation: getNumOfNights() returns the wrong number of nights.");
    }

    @Test
    @Tag("score:1") @DisplayName("HotelReservation getCost() Test1")
    void getCost() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        assertEquals(18000, hotelReservation1.getCost(),
                "HotelReservation: getCost() returns the wrong cost.");
    }

    @Test
    @Tag("score:1") @DisplayName("HotelReservation equals() Test1")
    void testEquals1() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        HotelReservation hotelReservation2 = hotelReservation1;

        assertTrue(hotelReservation1.equals(hotelReservation2),
                "HotelReservation: equals() returns the wrong value.");
    }

    @Test
    @Tag("score:1") @DisplayName("HotelReservation equals() Test2")
    void testEquals2() {
        Room[] rooms = {new Room("double"), new Room("king")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        HotelReservation hotelReservation2 = new HotelReservation("Bob", hotel1, "king", 1);
        assertFalse(hotelReservation1.equals(hotelReservation2), "HotelReservation: equals() returns the wrong value");
    }


    @Test
    @Tag("score:1") @DisplayName("HotelReservation equals() Test3")
    void testEquals3() {
        Room[] rooms = {new Room("double"), new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        HotelReservation hotelReservation2 = new HotelReservation("Alex", hotel1, "double", 2);
        assertTrue(hotelReservation1.equals(hotelReservation2), "HotelReservation: equals() returns the wrong value");
    }

    // check equality with null
    @Test
    @Tag("score:1") @DisplayName("HotelReservation equals() Test4")
    void testEquals4() {
        Room[] rooms = {new Room("double"), new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);
        HotelReservation hotelReservation1 = new HotelReservation("Alex", hotel1, "double", 2);
        HotelReservation hotelReservation2 = new HotelReservation("Alex", hotel1, "double", 2);
        assertFalse(hotelReservation1.equals(null), "HotelReservation: equals() returns the wrong value");
    }


}

class FlightReservationTest {   // 3 points

    @Test
    @Tag("score:1") @DisplayName("FlightReservation getCost() Test1")
    void getCost() {
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);
        FlightReservation flightReservation1 = new FlightReservation("Alex", airport1, airport2);
        assertEquals(5593, flightReservation1.getCost(),
                "FlightReservation: getCost() returns the wrong cost.");
    }

    @Test
    @Tag("score:1") @DisplayName("FlightReservation equals() Test1")
    void testEquals1() {
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);

        FlightReservation flightReservation1 = new FlightReservation("Alex", airport1, airport2);
        FlightReservation flightReservation2 = flightReservation1;

        assertTrue(flightReservation1.equals(flightReservation2),
                "FlightReservation: equals() returns the wrong value");
    }

    @Test
    @Tag("score:1") @DisplayName("FlightReservation equals() Test2")
    void testEquals2() {
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);

        FlightReservation flightReservation1 = new FlightReservation("Alex", airport1, airport2);
        FlightReservation flightReservation2 = new FlightReservation("Alex", airport2, airport1);

        assertFalse(flightReservation1.equals(flightReservation2),
                "FlightReservation: equals() returns the wrong value.");
    }


    // check equals(null)
    @Test
    @Tag("score:1") @DisplayName("FlightReservation equals() Test4")
    void testEquals4() {
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);

        FlightReservation flightReservation1 = new FlightReservation("Alex", airport1, airport2);
        FlightReservation flightReservation2 = new FlightReservation("Alex", airport1, airport2);

        assertFalse(flightReservation1.equals(null),
                "FlightReservation: equals() returns the wrong value");
    }
}

class BnBReservationTest {      // 2 points
    @Test
    @Tag("score:1") @DisplayName("BnBReservation reservationName() Test1")
    void reservationName() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);

        BnBReservation bnBReservation = new BnBReservation("Alex", hotel1, "double", 2);
        assertEquals("Alex", bnBReservation.reservationName(),
                "BnBReservation: getCost() returns the wrong name.");
    }

    @Test
    @Tag("score:1") @DisplayName("BnBReservation getCost() Test1")
    void getCost() {
        Room[] rooms = {new Room("double")};
        Hotel hotel1 = new Hotel("Hotel1", rooms);

        BnBReservation bnBReservation = new BnBReservation("Alex", hotel1, "double", 2);
        assertEquals(20000, bnBReservation.getCost(),
                "BnBReservation: getCost() returns the wrong value.");
    }
}


class BasketTest {      // 7 points

    @Test
    @Tag("score:1") @DisplayName("Basket add() Test1")
    void addTest1() {
        Basket basket = new Basket();
        Reservation reservation1 = new BasketTest.FakeReservation("Alex");
        Reservation reservation2 = new BasketTest.FakeReservation("Bob");

        int number = basket.add(reservation1);
        number = basket.add(reservation2);

        assertEquals(2, number,
                "Basket: add() returns the wrong number of reservations now in the basket.");
    }

    @Test
    @Tag("score:1") @DisplayName("Basket add() Test2")
    void addTest2() {
        Basket basket = new Basket();
        int number = 0;
        for(int i = 0; i < 30; i++){
            number = basket.add(new HotelReservation("Marty", new Hotel("Gatewater", new Room[]{new Room("queen")}), "queen", 4));
        }
        assertEquals(30, number, "Basket: add() returns the wrong number of reservations now in the basket when adding many items.");
    }

    @Test
    @Tag("score:2") @DisplayName("Basket getProducts() Test1")
    void getProducts2() {
        Basket basket = new Basket();
        HotelReservation hotelReservation1 = new HotelReservation("Alex", new Hotel("Hotel1", new Room[]{new Room("double")}), "double", 2);
        FlightReservation flightReservation1 = new FlightReservation("Bob", new Airport(44, 120, 100), new Airport(50, 112, 110));

        basket.add(hotelReservation1);
        basket.add(flightReservation1);

        Reservation[] expected = {hotelReservation1, flightReservation1};
        Reservation[] output = basket.getProducts();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], output[i],
                    "Basket: getProducts() returns the wrong order of reservations.");
        }
    }

    @Test
    @Tag("score:1") @DisplayName("Basket getNumOfReservations Test1")
    void getNumOfReservations() {
        Basket basket1 = new Basket();
        Reservation reservation1 = new BasketTest.FakeReservation("Alex");
        Reservation reservation2 = new BasketTest.FakeReservation("Bob");

        basket1.add(reservation1);
        basket1.add(reservation2);

        assertEquals(2, basket1.getNumOfReservations(),
                "Basket: getNumOfReservations() returns the wrong number of reservations");
    }


    @Test
    @Tag("score:1") @DisplayName("Basket remove() Test1")
    void remove() {
        Basket basket1 = new Basket();
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);
        FlightReservation reservation1 = new FlightReservation("Alex", airport1, airport2);

        basket1.add(reservation1);

        boolean test = basket1.remove(reservation1);

        assertEquals(0, basket1.getNumOfReservations(),
                "Basket: remove() didn't remove the reservation");

        assertTrue(test, "Basket: remove() returns the wrong value");

    }

    @Test
    @Tag("score:1") @DisplayName("Basket remove() Test2")
    void remove2() {
        Basket basket1 = new Basket();
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);
        boolean status;

        FlightReservation reservation1 = new FlightReservation("Gon", airport1, airport2);
        FlightReservation reservation2 = new FlightReservation("Killua", airport1, airport2);

        basket1.add(reservation1);
        basket1.add(reservation2);
        basket1.add(reservation1);
        basket1.add(reservation1);
        // basket1 == {reservation1, reservation2, reservation1, reservation1, . . .}
        basket1.remove(reservation1);
        // basket1 == {reservation2, reservation1, reservation1, . . .} AND NOT {reservation2, . . .}

        status = (basket1.getProducts()[0].equals(reservation2) && basket1.getProducts()[1].equals(reservation1) && basket1.getProducts()[2].equals(reservation1));

        assertTrue(status, "Basket: remove() didn't remove the FIRST reservation input");
    }

    @Test
    @Tag("score:1") @DisplayName("Basket clear() Test1")
    void clear() {
        Basket basket1 = new Basket();
        Reservation reservation1 = new BasketTest.FakeReservation("Alex");
        basket1.add(reservation1);
        basket1.add(reservation1);
        basket1.clear();
        assertEquals(0, basket1.getNumOfReservations(),
                "Basket: clear() doesn't clear the array of reservations in the basket");
    }


    @Test
    @Tag("score:1") @DisplayName("Basket getTotalCost Test1")
    void getTotalCost() {
        Basket basket1 = new Basket();
        Airport airport1 = new Airport(44, 120, 100);
        Airport airport2 = new Airport(50, 112, 110);
        FlightReservation flightReservation1 = new FlightReservation("Alex", airport1, airport2);
        basket1.add(flightReservation1);
        basket1.add(flightReservation1);

        assertEquals(11186, basket1.getTotalCost(),
                "Basket: getTotalCost() returns the wrong cost.");
    }

    private static class FakeReservation extends Reservation {

        public FakeReservation(String name) {
            super(name);
        }

        @Override
        public int getCost() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
