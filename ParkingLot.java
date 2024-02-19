import java.util.*;

class Car {          // Class Car is defined. Objects registartionNumber and color
    private String registrationNumber;
    private String color;

    public Car(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }
}

class ParkingSlot {         // Class ParkingSlot is defined in which it has methodes to to check the slots availability.
    private int slotNumber;
    private Car parkedCar;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public boolean isSlotAvailable() {
        return parkedCar == null;
    }

    public void parkCar(Car car) {
        this.parkedCar = car;
    }

    public void removeCar() {
        this.parkedCar = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Car getParkedCar() {
        return parkedCar;
    }
}

public class ParkingLot {           //Class ParkingLot is defined in which properties like capacity, list of slots and registration numbers to slot and color to registration number.
                                    //These properties are initialized by using constructor "initializeParkingSlots".    
    private int capacity;
    private List<ParkingSlot> parkingSlots;
    private Map<String, ParkingSlot> registrationNumberToSlotMap;
    private Map<String, List<String>> colorToRegistrationNumbersMap;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkingSlots = new ArrayList<>();
        registrationNumberToSlotMap = new HashMap<>();
        colorToRegistrationNumbersMap = new HashMap<>();
        initializeParkingSlots();
    }

    private void initializeParkingSlots() {
        for (int i = 0; i < capacity; i++) {
            parkingSlots.add(new ParkingSlot(i + 1));
        }
    }

    public void parkCar(String registrationNumber, String color) {   //This methods is to park the car in lot which finds available slots and parks the car.
        ParkingSlot availableSlot = findAvailableSlot();
        if (availableSlot != null) {
            Car car = new Car(registrationNumber, color);
            availableSlot.parkCar(car);
            registrationNumberToSlotMap.put(registrationNumber, availableSlot);
            colorToRegistrationNumbersMap.computeIfAbsent(color, k -> new ArrayList<>()).add(registrationNumber);
            System.out.println("Allocated slot number: " + availableSlot.getSlotNumber());
        } else {
            System.out.println("Sorry, parking lot is full");
        }
    }

    private ParkingSlot findAvailableSlot() {       //This method finds available slots.
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isSlotAvailable()) {
                return slot;
            }
        }
        return null;
    }

    public void leave(int slotNumber) {     //This methods is used to free up the parking slot like leave the car with updating it registartion number and color.
        if (slotNumber > 0 && slotNumber <= capacity) {
            ParkingSlot slot = parkingSlots.get(slotNumber - 1);
            if (!slot.isSlotAvailable()) {
                Car parkedCar = slot.getParkedCar();
                slot.removeCar();
                registrationNumberToSlotMap.remove(parkedCar.getRegistrationNumber());
                List<String> registrationNumbers = colorToRegistrationNumbersMap.get(parkedCar.getColor());
                registrationNumbers.remove(parkedCar.getRegistrationNumber());
                System.out.println("Slot number " + slotNumber + " is free");
            } else {
                System.out.println("Slot number " + slotNumber + " is already empty");
            }
        } else {
            System.out.println("Invalid slot number");
        }
    }

    public void status() {      // This methods shows all the parked cars status in the parking lot.    
        System.out.println("Slot No. Registration No Colour");
        for (ParkingSlot slot : parkingSlots) {
            if (!slot.isSlotAvailable()) {
                Car parkedCar = slot.getParkedCar();
                System.out.println(slot.getSlotNumber() + " " + parkedCar.getRegistrationNumber() + " " + parkedCar.getColor());
            }
        }
    }

    public void registrationNumbersForCarsWithColor(String color) {     //This method is used to show registration numbers of car with particular color.
        List<String> registrationNumbers = colorToRegistrationNumbersMap.get(color);
        if (registrationNumbers != null && !registrationNumbers.isEmpty()) {
            System.out.println(String.join(", ", registrationNumbers));
        } else {
            System.out.println("No cars found with color " + color);
        }
    }

    public void slotNumberForRegistrationNumber(String registrationNumber) {    //This method is used to show Slot number of car with particular registration number.
        ParkingSlot slot = registrationNumberToSlotMap.get(registrationNumber);
        if (slot != null) {
            System.out.println("Slot number: " + slot.getSlotNumber());
        } else {
            System.out.println("No car found with registration number " + registrationNumber);
        }
    }

    public static void main(String[] args) {  //This is the main method that provides the ability to interact with command line arguments to operate the parking lot.
                                              //This reads the commands from the user and performs the appropriate methods.
        ParkingLot parkingLot = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("=> ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "createparkinglot":
                    int capacity = Integer.parseInt(parts[1]);
                    parkingLot = new ParkingLot(capacity);
                    System.out.println("Created a parking lot with " + capacity + " slots");
                    break;
                case "park":
                    if (parkingLot != null) {
                        parkingLot.parkCar(parts[1], parts[2]);
                    } else {
                        System.out.println("Parking lot is not created yet");
                    }
                    break;
                case "leave":
                    if (parkingLot != null) {
                        int slotNumber = Integer.parseInt(parts[1]);
                        parkingLot.leave(slotNumber);
                    } else {
                        System.out.println("Parking lot is not created yet");
                    }
                    break;
                case "status":
                    if (parkingLot != null) {
                        parkingLot.status();
                    } else {
                        System.out.println("Parking lot is not created yet");
                    }
                    break;
                case "registration_numbers_for_cars_with_colour":
                    if (parkingLot != null) {
                        parkingLot.registrationNumbersForCarsWithColor(parts[1]);
                    } else {
                        System.out.println("Parking lot is not created yet");
                    }
                    break;
                case "slot_number_for_registration_number":
                    if (parkingLot != null) {
                        parkingLot.slotNumberForRegistrationNumber(parts[1]);
                    } else {
                        System.out.println("Parking lot is not created yet");
                    }
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
