# Parking-lot-system
This is a simple parking lot system implemented in Java. It allows users to manage parking slots, park cars, and retrieve information about parked cars based on registration number or color.

**Installation and Setup**
==> Firstly, clone the repository to your local machine, using the command prompt.
    git clone https://github.com/venkatmama/Parking-lot-system.git
==> Now navigate to Parking-lot-system.
    cd Parking-lot-system
==> Now compile the Java file and run
    javac ParkingLot.java
    java ParkingLot

**Using the system**

These are the commands used for the parking lot system.

==> createparkinglot <capacity>: Creates a parking lot with the specified capacity.
==> park <registrationnumber> <color>: Parks a car with the given registration number and color.
==> leave <slotnumber>: Empties the specified parking slot.
==> status: Displays the current status of the parking lot.
==> registration_numbers_for_cars_with_colour <color>: Displays registration numbers of cars with the specified color.
==> slot_number_for_registration_number <registration_number>: Displays the slot number where the car with the specified registration number is parked.
==> exit: Exits the program.
