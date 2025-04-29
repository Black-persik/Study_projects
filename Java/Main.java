import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//TODO help me
/**
 * This class is a Main class that implement simulation among animals
 * @author Ivan Vasilev
 */
public class Main {
    /**{@value} the maximum number of days*/
    public static final int MAX_NUMBER_OF_DAYS = 30;
    /**{@value} the minimum number of days*/
    public static final int MIN_NUMBER_OF_DAYS = 1;
    /**{@value} the minimum amount of grass */
    public static final int MIN_AMOUNT_OF_GRASS = 0;
    /**{@value} the maximum amount of grass */
    public static final int MAX_AMOUNT_OF_GRASS = 100;
    /**{@value} the minimum number of animals*/
    public static final int MIN_NUMBER_OF_ANIMALS = 1;
    /**{@value} the maximum number of animals*/
    public static final int MAX_NUMBER_OF_ANIMALS = 20;
    /**{@value} the number of parameter for every animal */
    public static final int NUMBER_OF_PARAMETERS = 4;

    /**
     * methods implement simulation for animals during certain days
     * @param args
     * @throws FileNotFoundException if file is nor fount
     * @throws InvalidInputsException custom exception about invalid inputs
     * @throws InvalidNumberOfAnimalParametersException exception if the number of parameter is not appropriate
     */
    public static void main(String[] args) throws FileNotFoundException, InvalidInputsException,
            InvalidNumberOfAnimalParametersException {
        //import scanner
        Scanner scanner = new Scanner(new File("C:/Users/Ivan_Jobs/IdeaProjects/asiga_pizda_vsem_2/src/test_2.txt"));
        String daysStr = scanner.nextLine(); // string value days number
        try { //check, is it possible to convert string to integer value
            readIntNew(daysStr);
        } catch (InvalidInputsException e) {
            // if it is impossible, catch the exception and stop execution of program
            System.out.print(e.getMessage());
            System.exit(0);
        }
        int days = Integer.parseInt(daysStr); // convert to integer value
        if (days < MIN_NUMBER_OF_DAYS || days > MAX_NUMBER_OF_DAYS) { // if number of day in correct range
            try {
                throw new InvalidInputsException();
            } catch (InvalidInputsException e) { //is not stop execution
                System.out.print(e.getMessage());
                System.exit(0);
            }
        }
        String grassAmountStr = scanner.nextLine(); // string value of grass amount
        try {
            readFloatNew(grassAmountStr); // convert to float
        } catch (InvalidInputsException e) { // if its impossible, throw the exception
            System.out.print(e.getMessage());
            System.exit(0); //stop execution of program
        }
        float grassAmount = Float.parseFloat(grassAmountStr); // convert value to float
        // if amount is in not appropriate range
        if (grassAmount < MIN_AMOUNT_OF_GRASS || grassAmount > MAX_AMOUNT_OF_GRASS) {
            try {
                throw new GrassOutOfBoundsException();
            } catch (GrassOutOfBoundsException e) { // throw exception
                System.out.print(e.getMessage());
                System.exit(0); // stop executing the program
            }
        }
        Field field = new Field(grassAmount); // declaire new Field
        String numberOfAnimalsStr = scanner.nextLine(); // string number of animals
        try {
            readIntNew(numberOfAnimalsStr); // try to read value as an integer
        } catch (InvalidInputsException e) { // else throw the exception
            System.out.print(e.getMessage());
            System.exit(0); //stop executing the program
        }
        int numberOfAnimals = Integer.parseInt(numberOfAnimalsStr); // convert value of animals to integer
        if (numberOfAnimals < MIN_NUMBER_OF_ANIMALS || numberOfAnimals > MAX_NUMBER_OF_ANIMALS) { // check if the
            // number of animals is not in appropriate range
            try {
                throw new InvalidInputsException(); // throw the exception
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage()); //print the execution message
                System.exit(0); // stop execution the program
            }
        }
        readAnimals(); // call method to read information about animals
        runSimulation(days, field, readAnimals()); // call the method to run simulation for animals

    }

    /**
     * method throw exception if string value can not be converted into integer value
     * @param str try to convert this value into integer
     * @throws InvalidInputsException custom exception about invalid inputs
     */
    public static void readIntNew(String str) throws InvalidInputsException {
        try {
            Integer.parseInt(str); // try to convert this value into integer
        } catch (NumberFormatException e) {
            throw new InvalidInputsException(); // throw the exception
        }

    }
    /**
     * method throw exception if string value can not be converted into float value
     * @param str try to convert this value into float
     * @throws InvalidInputsException custom exception about invalid inputs
     */
    public static void readFloatNew(String str) throws InvalidInputsException {
        try {
            Float.parseFloat(str); // try to convert this value into float type
        } catch (NumberFormatException e) {
            throw new InvalidInputsException(); // throw the exception
        }

    }

    /**
     * method check the correctness of number of criteria for animal
     * @param line array of strings with animal's information
     * @throws InvalidNumberOfAnimalParametersException custom exception
     */
    public static void countOfParameters(String[] line) throws InvalidNumberOfAnimalParametersException {
        if (line.length == NUMBER_OF_PARAMETERS) { // if number is correct, return nothing
            return;
        } else { // else throw exception
            throw new InvalidNumberOfAnimalParametersException();
        }
    }

    /**
     * Method read information of animals from input.txt file
     * @return array of animals with its parameters
     * @throws FileNotFoundException exception if file is not found
     */
    private static List<Animal> readAnimals() throws FileNotFoundException {
        List<Animal> animals = new ArrayList<>(); // array of animals
        // import scanner to read the data
        Scanner scanner = new Scanner(new File("C:/Users/Ivan_Jobs/IdeaProjects/asiga_pizda_vsem_2/src/test_2.txt"));
        String daysStr = scanner.nextLine(); // string value days number
        try { //check, is it possible to convert string to integer value
            readIntNew(daysStr);
        } catch (InvalidInputsException e) {  // if it is impossible, catch the exception and stop execution of program
            System.out.print(e.getMessage());
            System.exit(0);
        }
        int days = Integer.parseInt(daysStr); // convert to integer value
        if (days < MIN_NUMBER_OF_DAYS || days > MAX_NUMBER_OF_DAYS) { // if number of day in correct range
            try {
                throw new InvalidInputsException();
            } catch (InvalidInputsException e) { //is not stop execution
                System.out.print(e.getMessage());
                System.exit(0);
            }
        }
        String grassAmountStr = scanner.nextLine(); // string value of grass amount
        try {
            readFloatNew(grassAmountStr); // convert to float
        } catch (InvalidInputsException e) { // if its impossible, throw the exception
            System.out.print(e.getMessage());
            System.exit(0); //stop execution of program
        }
        float grassAmount = Float.parseFloat(grassAmountStr); // convert value to float
        // if amount is in not appropriate range
        if (grassAmount < MIN_AMOUNT_OF_GRASS || grassAmount > MAX_AMOUNT_OF_GRASS) {
            try {
                throw new GrassOutOfBoundsException();
            } catch (GrassOutOfBoundsException e) { // throw exception
                System.out.print(e.getMessage());
                System.exit(0); // stop executing the program
            }
        }
        Field field = new Field(grassAmount); // declaire new Field
        String numberOfAnimalsStr = scanner.nextLine(); // string number of animals
        try {
            readIntNew(numberOfAnimalsStr); // try to read value as an integer
        } catch (InvalidInputsException e) { // else throw the exception
            System.out.print(e.getMessage());
            System.exit(0); //stop executing the program
        }
        int numberOfAnimals = Integer.parseInt(numberOfAnimalsStr); // convert value of animals to integer
        if (numberOfAnimals < MIN_NUMBER_OF_ANIMALS || numberOfAnimals > MAX_NUMBER_OF_ANIMALS) { // check if the
            // number of animals is not in appropriate range
            try {
                throw new InvalidInputsException(); // throw the exception
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage()); //print the execution message
                System.exit(0); // stop execution the program
            }
        }
        int counterForLoop = 0; // for checking, if the number of string for animals more or less than numberOfAnimals
        while (scanner.hasNextLine()) {
            counterForLoop += 1; // is it read one string increment the value
            String line = scanner.nextLine(); // read the next line
            String[] infoString = line.split(" "); // split this string
            if (counterForLoop  > numberOfAnimals) { // if the number of string for animals more than numberOfAnimals
                try {
                    throw new InvalidInputsException(); // throw invalid input exception
                } catch (InvalidInputsException e) {
                    System.out.print(e.getMessage());
                    System.exit(0); // stop the execution of program
                }
            }
            try { // check the correctness of number of criteria for animsl
                countOfParameters(infoString);
            } catch (InvalidNumberOfAnimalParametersException e) { // else throw exeption
                System.out.print(e.getMessage());
                System.exit(0); // stop execution of program
            }
            String type = infoString[0]; // check the correctness of animal's name
            if (!type.equals("Lion") && !type.equals("Zebra") && !type.equals("Boar")) {
                //else throw the exception and call getMessage() method
                try {
                    throw new InvalidInputsException();
                } catch (InvalidInputsException e) {
                    System.out.print(e.getMessage());
                    System.exit(0); // stop execution of the program
                }
            }
            try {  // try to convert the value from string to float
                readFloatNew(infoString[1]);
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage());
                System.exit(0); // stop execution of the program
            }
            float weight = Float.parseFloat(infoString[1]); // convert value into float type
            if (weight < Animal.MIN_WEIGHT || weight > Animal.MAX_WEIGHT) { // check if the value in appropriate range
                try { //else throw the exception
                    throw new WeightOutOfBoundsException();
                } catch (WeightOutOfBoundsException e) {
                    System.out.print(e.getMessage());
                    System.exit(0); // stop execution of the program
                }
            }
            try { // try to convert value into float type
                readFloatNew(infoString[2]);
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage()); // else call getMessage()
                System.exit(0); // and stop execution of program
            }
            float speed = Float.parseFloat(infoString[2]); // convert value into float type
            if (speed < Animal.MIN_SPEED || speed > Animal.MAX_SPEED) { // check if the value in
                // not in appropriate range
                try { // call the exception
                    throw new SpeedOutOfBoundsException();
                } catch (SpeedOutOfBoundsException e) {
                    System.out.print(e.getMessage()); // call method getMassage()
                    System.exit(0); // stop execution of the program
                }
            }
            try { // try to convert value into float type
                readFloatNew(infoString[2 + 1]);
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage()); //else call getMessage()
                System.exit(0); // and stop execution of program
            }
            float energy = Float.parseFloat(infoString[2 + 1]); // convert value into float type
            if (energy < Animal.MIN_ENERGY || energy > Animal.MAX_ENERGY) { // check if the value in
                // not in appropriate range
                try {
                    throw new EnergyOutOfBoundsException(); // call the exception
                } catch (EnergyOutOfBoundsException e) {
                    System.out.print(e.getMessage()); // call method getMassage()
                    System.exit(0); // stop execution of the program
                }
            }
            if (type.equals("Boar")) { // if the type is boar, add it into array of animals
                animals.add(new Boar(weight, speed, energy));
            } else if (type.equals("Lion")) { // if the type is lion, add it into array of animals
                animals.add(new Lion(weight, speed, energy));
            } else if (type.equals("Zebra")) { // if the type is boar, add it into array of animals
                animals.add(new Zebra(weight, speed, energy));
            }
        }
        if (counterForLoop < numberOfAnimals) { // / if the number of string for animals less than numberOfAnimals
            try {
                throw new InvalidInputsException(); // throw the exception
            } catch (InvalidInputsException e) {
                System.out.print(e.getMessage()); // call method getMassage()
                System.exit(0); // stop execution of the program
            }
        }
        scanner.close(); // close the file for reading
        return animals; // return the array of animals
    }

    /**
     * this method start simulation, which execute a certain time. During this simulation, animals can eat and die.
     * Alive animals make sound
     * @param days number of day for simulation
     * @param field value to contain information about the grass amount
     * @param animals array of animals with its information
     */
    private static void runSimulation(int days, Field field, List<Animal> animals)  {
        for (int d = 0; d < days; d++) { // loop for simulation, execute numberOfAnimals times
            removeDeadAnimals(animals); // remove all dead animals in start of the day
            for (int i = 0; i < animals.size(); i++) { // every animal will eat
                if (animals.get(i).getEnergy() > 0) { // only animal with energy > 0 ia able to eat
                    animals.get(i).eat(animals, field); // animal eai
                }
            }

            for (Animal animal : animals) { // in the end of every day all animal 1 percent of energy
                animal.decrementEnergy();
                System.out.println(animal.getClass() + " "  + animal.getEnergy()); // decreasing the energy
            }
            removeDeadAnimals(animals); // in the end of the day remove dead animals
            // in the end if each day,the amount of grass is doubled
            if (field.getGrassAmount() * 2 >= MAX_AMOUNT_OF_GRASS) { // if doubled value more than 100, set 100
                field.setGrassAmount(MAX_AMOUNT_OF_GRASS); // set the increasing amount of grass
            } else { // else less than 100
                field.setGrassAmount(field.getGrassAmount() * 2); // set the increasing amount of grass
            }
        }
        //print alive animals' sounds
        printAnimals(animals);

    }

    /**
     * this method print into console sound of alive animals
     * @param animals array of animals, which are alive
     */
    private static void printAnimals(List<Animal> animals) {
        AnimalSound lionSound = AnimalSound.LION; // set the lion sound
        AnimalSound boarSound = AnimalSound.BOAR;  // set the boar sound
        AnimalSound zebraSound = AnimalSound.ZEBRA;  // set the zebra sound
        for (Animal animal : animals) { // go from all alive animals
            if (animal instanceof Boar) { // if it is boar
                System.out.println(boarSound.getSound()); // print the sound
            }
            if (animal instanceof Lion) { // if it is lion
                System.out.println(lionSound.getSound()); // print the sound
            }
            if (animal instanceof Zebra) { // if it is zebra
                System.out.println(zebraSound.getSound()); // print the sound
            }
        }

    }

    /**
     * method remove all dead animals
     * @param animals array of  alive and maybe dead animals
     */
    private static void removeDeadAnimals(List<Animal> animals) {
        animals.removeIf(animal -> animal.getEnergy() <= 0); // if energy is less or equal to 0, remove it
    }

}

/**
 * exception for invalid number of parameters
 */
class InvalidNumberOfAnimalParametersException extends Exception {
    /**
     * method return massage about invalid parameters
     * @return string with message
     */
    public String getMessage() {
        return "Invalid number of animal parameters\n";
    }
}

/**
 * custom exception for Invalid inputs
 */
class InvalidInputsException extends Exception {
    /**
     * method return massage about invalid inputs
     * @return string with message
     */
    public String getMessage() {
        return "Invalid inputs\n";
    }
}

/**
 * class Field contain information about grass amount
 */
class Field {
    /**{@value} max amount of grass*/
    public static final int MAX_GRASS_AMOUNT = 100;
    private float grassAmount; // amount of grass

    /**
     * Constructor for class Field
     * @param grassAmount amount of grass
     */
    public Field(float grassAmount) {
        this.grassAmount = grassAmount;
    }

    /**
     * get the amount of grass
     * @return amount of grass
     */
    public float getGrassAmount() {
        return grassAmount;
    }

    /**
     * doubled the amount of grass
     * @throws GrassOutOfBoundsException is the grass value is not in appropriate range
     */
    public void makeGrassGrow() throws GrassOutOfBoundsException {
        if (grassAmount * 2  > MAX_GRASS_AMOUNT) { // if more than 100
            throw new GrassOutOfBoundsException();
        } else {
            grassAmount *= 2;
        }
    }

    /**
     * set the value for the grass
     * @param grassAmount amount of grass
     */
    public void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }
}

/**
 * exception if the amount of grass is out of bound
 */
class GrassOutOfBoundsException extends Exception {
    public String getMessage() {
        return "The grass is out of bounds\n";

    }
}

/**
 * abstract class Animal that contain the general information about animal
 */
abstract class Animal {
    /** {@value} minimum speed of animal*/
    public static final float MIN_SPEED = 5;
    /** {@value} maximum speed of animal*/
    public static final float MAX_SPEED = 60;
    /**{@value} minimum energy of animal*/
    public static final float MIN_ENERGY = 0;
    /**{@value} maximum energy of animal*/
    public static final float MAX_ENERGY = 100;
    /**{@value} minimum weight of animal*/
    public static final float MIN_WEIGHT = 5;
    /**{@value} maximum weight of animal*/
    public static final float MAX_WEIGHT = 200;
    private float weight;
    private float energy;
    private float speed;

    /**
     * Constructor for class Animal
     * @param weight of animal
     * @param speed of animal
     * @param energy of animal
     */
    protected Animal(float weight, float speed, float energy) {
        this.weight = weight;
        this.speed = speed;
        this.energy = energy;
    }

    /**
     * get weight of animal
     * @return weight animal
     */
    public float getWeight() {
        return weight;
    }
    /**
     * get speed of animal
     * @return speed animal
     */
    public float getSpeed() {
        return speed;
    }
    /**
     * get energy of animal
     * @return weight animal
     */
    public float getEnergy() {
        return energy;
    }
    /**
     * set energy for animal
     */
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    /**
     * set the sound for the animal
     */
    public void makeSound() {
        AnimalSound lionSound = AnimalSound.LION;
        AnimalSound zebraSound = AnimalSound.ZEBRA;
        AnimalSound boarSound = AnimalSound.BOAR;
    }

    /**
     * decrement the value of the energy of the animal
     */
    public void decrementEnergy() {
        this.energy = energy - 1;

    }

    /**
     * abstract  method for each animal. Animal can eat
     * @param animals array of animals
     * @param field contain information about grass amount
     */
    public abstract void eat(List<Animal> animals, Field field);
}

/**
 * class Zebra is a child class of Animal and it also implements Herbivore interface
 */
class Zebra extends Animal implements Herbivore {
    /** {@value} coefficient for eating the grass*/
    public static final float COEFFICIENT = 0.1f;

    /**
     * Constructor for class Zebra
     * @param weight of zebra
     * @param speed of zebra
     * @param energy of zebra
     */
    public Zebra(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    /**
     * zebra can eat the grass
     * @param animals array of animals
     * @param field contain information about grass amount
     */
    @Override
    public void eat(List<Animal> animals, Field field) {
        float grass = field.getGrassAmount(); // the amount of the grass
        for (Animal animal : animals) { // go from the whole array of animals
            if (animal.equals(this)) { // we should find the certain animal Zebra
                float weight = animal.getWeight(); // get the wight of the animal
                if (grass > weight * COEFFICIENT) { // if grass amount more than 1/10 of zebra's weight
                    //decrease the grass amount
                    field.setGrassAmount((float) (grass - animal.getWeight() * COEFFICIENT));
                    // if energy will more or equal to 100
                    if ((animal.getEnergy() + weight * COEFFICIENT) > MAX_ENERGY) {
                        setEnergy(MAX_ENERGY); // set the maximum energy
                        break; // stop to go from the loop
                    }
                    animal.setEnergy(animal.getEnergy() + animal.getWeight() * COEFFICIENT); // zebra get more energy
                }
                break; // stop to go from the loop
            }
        }
    }

    /**
     * zebra can eat the grass
     * @param grazer is zebra
     * @param field contains information about the amount of grass
     */
    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grass = field.getGrassAmount(); // get grass amount
        if (grass > grazer.getWeight() * COEFFICIENT) { // if grass amount more than 1/10 of zebra's weight
            //decrease the grass amount
            field.setGrassAmount((float) (grass - grazer.getWeight() * COEFFICIENT));
            // if energy will more or equal to 100
            if (grazer.getEnergy() + grazer.getWeight() * COEFFICIENT >= MAX_ENERGY) {
                grazer.setEnergy(MAX_ENERGY); // set the maximum energy
                return; // stop the loop
            }
            grazer.setEnergy(grazer.getEnergy() + grazer.getWeight() * COEFFICIENT); // zebra get more energy
        }
    }
}

/**
 * class Zebra is a child class of Animal, and it also implements Carnivore interface
 */
class Lion extends Animal implements Carnivore<Lion> {
    /**
     * Constructor for class Lion
     * @param weight of lion
     * @param speed of lion
     * @param energy of lion
     */
    public Lion(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }
    /**
     * Lion can eat
     * @param animals array of animals
     * @param field contain information about grass amount
     */
    @Override
    public void eat(List<Animal> animals, Field field) {
        for (int i = 0; i < animals.size(); i++) { // go from the Animal array
            if (animals.get(i).equals(this)) { // if we find the appropriate animal
                Animal currentAnimal = animals.get(i); // hunter
                choosePrey(animals, (Lion) currentAnimal); // choose the prey
                if (i + 1 > animals.size() - 1) { // if the next animal is in start of the array
                    Animal nextAnimal = animals.getFirst(); // prey
                    huntPrey(currentAnimal, nextAnimal); // hunter hunt on the prey
                    break; // stop finding if hunt was
                } else {
                    Animal nextAnimal = animals.get(i + 1); // if the next animal is in start of the array
                    huntPrey(currentAnimal, nextAnimal); // hunter hunt on the prey
                    break; // stop finding if hunt was
                }
            }
        }
    }

    /**
     * hunter is going to hunt on the prey
     * @param hunter lion is the hunter
     * @param prey zebra, lion or a boar is the prey
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) {
        // if lion try to hunt on another lion
        if (prey instanceof Lion && !hunter.equals(prey)) {
            try { // throw exception
                throw new CannibalismException();
            } catch (CannibalismException e) {
                System.out.print(e.getMessage()); // print the Cannibalism message
            }
        } else if (hunter.equals(prey)) { // if only one animal in array
            try {
                throw new SelfHuntingException(); // throw Selfhunting exceprion
            } catch (SelfHuntingException e) {
                // print the massage
                System.out.print(e.getMessage());
            }
        }
        // if the prey is appropriate for lion
        if (prey instanceof Zebra || prey instanceof Boar) {
            // if the hunter's speed or energy is more than prey's speed or energy
            if (prey.getEnergy() < hunter.getEnergy() || prey.getSpeed() < hunter.getSpeed()) {
                prey.setEnergy(0); // prey was eaten => die => energy is zero
                // if the energy of hunter after prey is more than 100
                if (hunter.getEnergy() + prey.getWeight() > MAX_ENERGY) {
                    hunter.setEnergy(MAX_ENERGY); // set maximum energy
                    return;
                }
                hunter.setEnergy(hunter.getEnergy() + prey.getWeight()); // set the energy for hunter
            } else {
                // else prey is too fast => prey is alive, hunter do not eat
                try {
                    throw new TooStrongPreyException();
                } catch (TooStrongPreyException e) {
                    System.out.print(e.getMessage());
                }
            }
        }

    }

    /**
     * find the prey for hunter
     * @param animals array of animals
     * @param hunter lion
     * @return prey for the hunter
     */
    @Override
    public Animal choosePrey(List<Animal> animals, Lion hunter) {
        Animal prey = null;
        for (int i = 0; i < animals.size(); i++) { // go through the loop
            Animal currentAnimal = animals.get(i); //hunter
            if (i + 1 > animals.size() - 1) { // if the next animal on the first position of the loop
                Animal nextAnimal = animals.getFirst(); // prey
                prey = nextAnimal; //prey
                return nextAnimal;
            } else {
                Animal nextAnimal = animals.get(i + 1); //prey
                prey = nextAnimal; //prey
                return nextAnimal;
            }
        }
        return prey;

    }
}

/**
 * class Boar is a child class of Animal, and it also implements Omnivore interface
 */
class Boar extends Animal implements Omnivore<Boar> {
    /** {@value} coefficient for eating the grass*/
    public static final float COEFFICIENT = 0.1f;

    /**
     * Constructor for Boar class
     * @param weight boar's weight
     * @param speed boar's speed
     * @param energy boar's energy
     */
    public Boar(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }
    @Override
    public void eat(List<Animal> animals, Field field) {
        for (int i = 0; i < animals.size(); i++) { // go through the loop
            if (animals.get(i).equals(this)) { // if it finds appropriate boar
                Animal currentAnimal = animals.get(i); // boar
                grazeInTheField(currentAnimal, field); // boar is eating the grass
                choosePrey(animals, (Boar) currentAnimal); // and boar also is hunting
                if (i + 1 > animals.size() - 1) { // is the next animal in the first possition of array
                    Animal nextAnimal = animals.getFirst(); // prey
                    huntPrey(currentAnimal, nextAnimal); // hunting
                    break;
                } else { //if next animal is not on the first position
                    Animal nextAnimal = animals.get(i + 1); //prey
                    huntPrey(currentAnimal, nextAnimal); // hunting
                    break;
                }
            }
        }
    }
    /**
     * boar can eat the grass
     * @param grazer is boar
     * @param field contains information about the amount of grass
     */
    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grass = field.getGrassAmount(); // get grass amount
        if (grass > grazer.getWeight() * COEFFICIENT) { // if grass amount more than 1/10 of zebra's weight
            //decrease the grass amount
            field.setGrassAmount((float) (grass - grazer.getWeight() * COEFFICIENT));
            // if energy will more or equal to 100
            if (grazer.getEnergy() + grazer.getWeight() * COEFFICIENT >= MAX_ENERGY) {
                grazer.setEnergy(MAX_ENERGY); // set the maximum energy
                return; // stop the loop
            }
            grazer.setEnergy(grazer.getEnergy() + grazer.getWeight() * COEFFICIENT); // boar get more energy
        }
    }
    /**
     * hunter is going to hunt on the prey
     * @param hunter lion is the hunter
     * @param prey zebra, lion or a boar is the prey
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) {
        // if boar try to hunt on another boar
        if (prey instanceof Boar && !hunter.equals(prey)) {
            try { // throw exception
                throw new CannibalismException();
            } catch (CannibalismException e) {
                System.out.print(e.getMessage()); // print the Cannibalism message
            }
        } else if (hunter.equals(prey)) { // if only one animal in array
            try {
                throw new SelfHuntingException(); // throw Selfhunting exceprion
            } catch (SelfHuntingException e) {
                // print the massage
                System.out.print(e.getMessage());
            }
        }
        // if the prey is appropriate for lion
        if (prey instanceof Zebra || prey instanceof Lion) {
            // if the hunter's speed or energy is more than prey's speed or energy
            if (prey.getEnergy() < hunter.getEnergy() || prey.getSpeed() < hunter.getSpeed()) {
                prey.setEnergy(0); // prey was eaten => die => energy is zero
                // if the energy of hunter after prey is more than 100
                if (hunter.getEnergy() + prey.getWeight() > MAX_ENERGY) {
                    hunter.setEnergy(MAX_ENERGY); // set maximum energy
                    return;
                }
                hunter.setEnergy(hunter.getEnergy() + prey.getWeight()); // set the energy for hunter
            } else {
                // else prey is too fast => prey is alive, hunter do not eat
                try {
                    throw new TooStrongPreyException();
                } catch (TooStrongPreyException e) {
                    System.out.print(e.getMessage());
                }
            }
        }

    }
    /**
     * find the prey for hunter
     * @param animals array of animals
     * @param hunter lion
     * @return prey for the hunter
     */
    @Override
    public Animal choosePrey(List<Animal> animals, Boar hunter) {
        Animal prey = null;
        for (int i = 0; i < animals.size(); i++) { // go through the loop
            Animal currentAnimal = animals.get(i); //hunter
            if (i + 1 > animals.size() - 1) { // if the next animal on the first position of the loop
                Animal nextAnimal = animals.getFirst(); // prey
                prey = nextAnimal; //prey
                return nextAnimal;
            } else {
                Animal nextAnimal = animals.get(i + 1); //prey
                prey = nextAnimal; //prey
                return nextAnimal;
            }
        }
        return prey;

    }


}

/**
 * interface Herbivore for animals
 */
interface  Herbivore {
    /**
     * animal can eat the grass
     * @param grazer animal who eat grass
     * @param field contain information about amount of grass
     */
    public void grazeInTheField(Animal grazer, Field field);
}

/**
 * Omnivore extends Herbivore and Carnivore interfaces
 * @param <T> T can be either Boar or Zebra
 */
interface Omnivore<T extends Animal> extends Herbivore, Carnivore<T> {
}

/**
 * interface Carnivore for lion and boar
 * @param <T> type can be either Lion or Boar
 */
interface Carnivore<T extends Animal>  {
    /**
     * choose a pray for hunter
     * @param animals list of animals
     * @param hunter hunter animal
     * @return prey for hunter
     */
    public Animal choosePrey(List<Animal> animals, T hunter);
    /**
     * method for hunting
     * @param hunter animal hunter can be Lion or Boar
     * @param prey every animal can be as a prey
     * @throws SelfHuntingException if animal try to hunter on itself
     * @throws TooStrongPreyException if animal try to hunt, but unsuccessfully
     * @throws CannibalismException if animal try to hunt on animal with the same type
     */
    public void huntPrey(Animal hunter, Animal prey) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException;
}

/**
 * exception for self-hunting
 */
class SelfHuntingException extends Exception {
    public String getMessage() {
        return "Self-hunting is not allowed\n";
    }
}

/**
 * exception for unsuccessful hunting
 */
class TooStrongPreyException extends Exception {
    public String getMessage() {
        return "The prey is too strong or too fast to attack\n";
    }
}

/**
 * exception for cannibalism
 */
class CannibalismException extends Throwable {
    public String getMessage() {
        return "Cannibalism is not allowed\n";
    }
}

/**
 * exception for not appropriate weight for animal
 */
class WeightOutOfBoundsException extends Exception {
    public String getMessage() {
        return "The weight is out of bounds\n";
    }
}
/**
 * exception for not appropriate energy for animal
 */
class EnergyOutOfBoundsException extends Exception {
    public String getMessage() {
        return "The energy is out of bounds\n";
    }
}
/**
 * exception for not appropriate speed for animal
 */
class SpeedOutOfBoundsException extends NumberFormatException {
    public String getMessage() {
        return "The speed is out of bounds\n";
    }
}

/**
 * enumerator, which contain information about animal sound
 */
enum AnimalSound {
    LION("Roar"), // lion says Roar
    ZEBRA("Ihoho"), // zebra says Ihoho
    BOAR("Oink"); //  boar says Oink
    private final String sound;

    /**
     * Constructor for AnimalSound
     * @param sound animal's sound
     */
    AnimalSound(String sound) {
        this.sound = sound;
    }

    /**
     * get sound of the animal
     * @return animal's sound
     */
    public String getSound() {
        return sound;
    }
}

