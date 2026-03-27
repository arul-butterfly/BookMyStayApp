
    public class BookMyStayApp {

        // Abstract Class
        static abstract class Room {
            protected String roomType;
            protected int beds;
            protected double price;

            public Room(String roomType, int beds, double price) {
                this.roomType = roomType;
                this.beds = beds;
                this.price = price;
            }

            public abstract void displayDetails();
        }

        // Single Room
        static class SingleRoom extends Room {
            public SingleRoom() {
                super("Single Room", 1, 1500);
            }

            public void displayDetails() {
                System.out.println("Room Type: " + roomType);
                System.out.println("Beds: " + beds);
                System.out.println("Price: ₹" + price);
            }
        }

        // Double Room
        static class DoubleRoom extends Room {
            public DoubleRoom() {
                super("Double Room", 2, 2500);
            }

            public void displayDetails() {
                System.out.println("Room Type: " + roomType);
                System.out.println("Beds: " + beds);
                System.out.println("Price: ₹" + price);
            }
        }

        // Suite Room
        static class SuiteRoom extends Room {
            public SuiteRoom() {
                super("Suite Room", 3, 5000);
            }

            public void displayDetails() {
                System.out.println("Room Type: " + roomType);
                System.out.println("Beds: " + beds);
                System.out.println("Price: ₹" + price);
            }
        }

        // MAIN METHOD
        public static void main(String[] args) {

            Room single = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suite = new SuiteRoom();

            single.displayDetails();
            doubleRoom.displayDetails();
            suite.displayDetails();
        }
    }

