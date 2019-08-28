//Author: Matt Stropkey

import java.util.Scanner;
import java.util.NoSuchElementException;///not sure what this does, look at line 36
import java.io.File;
import java.io.FileNotFoundException;



public class CarTracker {
	
	
	//scanner to get input from user
	private static Scanner input = new Scanner(System.in);
	private static CarMinPQ milesPQ = new CarMinPQ('m');
	private static CarMinPQ pricePQ = new CarMinPQ('p');
	private static VinNumDLB<Integer> vins = new VinNumDLB();
	private static LPHash sortedCars = new LPHash();
	
	
	
	public static void main(String[] args) {
		
		int selection = -1;
		try{
		readFile();
		}catch(FileNotFoundException e)
		{
			System.out.println("Could not read file.");
		}
		
		while(selection != 0) {
			System.out.println();
			System.out.println("Car Tracker Main Menu");
			System.out.println("1. Add a car to track");
            System.out.println("2. Update an existing car");
            System.out.println("3. Remove car from tracker");
            System.out.println("4. Find lowest priced car");
            System.out.println("5. Find lowest mileage car");
            System.out.println("6. Find lowest priced car for a make and model");
            System.out.println("7. Find lowest milage car for a make and model");
            System.out.println("0. Quit");
            System.out.print("Selection: ");
			

			   
			 try {
                selection = input.nextInt();
            } catch (NoSuchElementException e) {
                selection = -1;
            } catch (IllegalStateException e) {
                selection = -1;
            }
            input.nextLine();

            switch (selection) {
                case 1:
                    addCar();
                    break;
                case 2:
                    updateCar();
                    break;
                case 3:
                    removeCar();
                    break;
                case 4:
                    lowestPrice();
                    break;
                case 5:
                    lowestMiles();
                    break;
                case 6:
                    lowestPriceModel();
                    break;
                case 7:
                    lowestMilesModel();
                    break; 
                case 0:
                    break;
                default:
                    // Invalid, just ignore and let loop again
                    break;
            }//end of switch statement
			
				/*Car myCar = milesPQ.minKey();
				System.out.println();
				System.out.println("Car with the minimum miles: ");
				System.out.println("Miles: " + myCar.getMiles());
				System.out.println("Price: " + myCar.getPrice());
				System.out.println("Vin: " + myCar.getVIN());
				System.out.println("Color: " + myCar.getColor());
				System.out.println("Make: " + myCar.getMake());
				System.out.println("Model: " + myCar.getModel());
				*/
        }//end of while loop
    }//end of main method
	
	  
	/**
	*Guide user through adding a car
	*/
	public static void addCar() {
		boolean valid = false;
		String vin = "";
		while(!valid)
		{
		System.out.print("Enter a 17-digit Vin Number: ");
		vin = input.nextLine();
		if(vin.length() == 17)
			valid = true;
		else 
			valid = false;
		}
		System.out.print("Make: ");
		String make = input.nextLine();
		System.out.print("Model: ");
		String model = input.nextLine();
		System.out.print("Price: ");
		double price = input.nextDouble();
		System.out.print("Number of miles: ");
		int	miles = input.nextInt();
			valid = true;
		System.out.print("Color of car: ");
		String carColor = input.next(); 
		///make sure input readers working correctly

		try{
			//try to add car to queue
			//cars.add(new Car(vin, make, model, price, miles, color));
			if(vins.contains(vin))
			{
				System.out.println("Car already in the system.");
				return;
			}
			Car newCar = new Car(vin, make, model, price, miles, carColor);
			milesPQ.insert(newCar);
			pricePQ.insert(newCar);
			sortedCars.put(newCar);

			vins.put(vin, newCar);
			//for testing
			//if(vins.contains(vin))
			//	System.out.println("Car in DLB");//for testing
			
		}	catch(NullPointerException e) {
			////////will need to throw some other sort of exception, just placed as a placeholder
		}
	}//end of addCar() method
	
	/**
	*Guide user through updating a car in system
	*/
	public static void updateCar()
	{
		if(vins.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		
		System.out.print("\nEnter a 17-digit Vin Number for the car to update: ");
		String vin = input.nextLine();
		
		Car myCar;
		try{
			myCar = vins.get(vin);
		}catch(NullPointerException e)
		{
			System.err.println("Car not in system.");
			return;
		}
		if(myCar == null)
		{
			System.out.println("Car not in the system.");
			return;
		}
		int selection = -1;
		
		while(selection != 0) {
			System.out.println();
			System.out.println("Update Car options");
			System.out.println("1. Change Price");
            System.out.println("2. Change Mileage");
            System.out.println("3. Change Color");
			System.out.println("0. Return to main");
			System.out.print("Selection: ");
			
			 try {
                selection = input.nextInt();
            } catch (NoSuchElementException e) {
                selection = -1;
            } catch (IllegalStateException e) {
                selection = -1;
            }
            input.nextLine(); 
			
			 switch (selection) {
                case 1:
					System.out.println();
                    System.out.print("Enter the new price: ");
					double price = input.nextDouble();
					myCar.setPrice(price);
					pricePQ.changeKey(myCar.getPriceIndex(), myCar);
					sortedCars.changeKey(myCar, 'p');
					//call method for car to change value
					
                    break;
                case 2:
					System.out.println();
                    System.out.print("Enter the new mileage: ");
					int miles = input.nextInt();
					myCar.setMiles(miles);
					milesPQ.changeKey(myCar.getMilesIndex(), myCar);
					sortedCars.changeKey(myCar, 'm');
					//catch exception if anything entered but int
                    break;
                case 3:
					System.out.println(); 
                    System.out.print("Enter the new color: ");
					String color = input.nextLine();
					myCar.setColor(color);
					//catch exception if they enter anything other than a string?
                    break; 
				case 0:
					break; 
                default:
                    // Invalid, just ignore and let loop again
                    break;
            }//end of switch statement
        }//end of while loop 
	}//end of update car method
	
	
	/** 
	*Request Vin number from user to remove from system
	*/
	public static void removeCar()
	{
		if(vins.isEmpty()) 
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		
		System.out.print("\nEnter the 17-digit Vin number for the car to remove: ");
		String vin = input.nextLine();
		Car removed = vins.remove(vin);
		boolean deleted = false;
		if(removed != null) 
		{
			//System.out.println("Car removed.");
			//System.out.println("Miles index: " + removed.getMilesIndex());
			milesPQ.delete(removed.getMilesIndex()); 
			pricePQ.delete(removed.getPriceIndex()); 
			deleted = sortedCars.remove(removed); 
			if(deleted == false)
			{
				System.out.println("Car not removed from sortedCars");
				return;
			}
			System.out.println("Car removed.");
			
			//will need to remove from all queues!
		}
		else
		{
			System.out.println("Car not found");
		}
		//reprompt...say enter 0 to return to main menu
	}//end of removeCar method
	
	/**
	*Find the lowest priced car in the queue
	*/
	public static void lowestPrice()
	{
		if(vins.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		Car lowPrice;
		if(pricePQ.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		lowPrice = pricePQ.minKey();
		if(lowPrice == null)
		{
			System.out.println("There are no cars in the system");
			return;
		}
				System.out.println();
				System.out.println("Car with the minimum price: ");
				System.out.println("Make: " + lowPrice.getMake());
				System.out.println("Model: " + lowPrice.getModel());
				System.out.printf("Price: $%.2f\n", lowPrice.getPrice());
				System.out.println("Color: " + lowPrice.getColor());
				System.out.println("Miles: " + lowPrice.getMiles());
				System.out.println("Vin: " + lowPrice.getVIN());

		
	}//end of lowestPrice method
	
	/**
	*Find the car with the lowest miles in the queue
	*/
	public static void lowestMiles()
	{ 
		if(vins.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		Car lowMiles;
		lowMiles = milesPQ.minKey();
		if(lowMiles == null) 
		{
			System.out.println("There are no cars in the system");
			return;
		}
				System.out.println();
				System.out.println("Car with the minimum miles: ");
				System.out.println("Make: " + lowMiles.getMake());
				System.out.println("Model: " + lowMiles.getModel());
				//System.out.println("Price: " + lowMiles.getPrice());
				System.out.printf("Price: $%.2f\n", lowMiles.getPrice());
				System.out.println("Color: " + lowMiles.getColor());
				System.out.println("Miles: " + lowMiles.getMiles());
				System.out.println("Vin: " + lowMiles.getVIN());
	}//end of lowestMiles method
	
	/**
	*Find the lowest cost of a car with a given make and model from user
	*/	
	public static void lowestPriceModel()
	{
		if(vins.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		
		
		System.out.print("\nEnter the make of the car: ");
		String make = input.nextLine();
		System.out.print("Enter the model for the car: ");
		String model = input.nextLine();
		Car minPrice = sortedCars.getMinPriceKey(make, model);
		if(minPrice == null)
		{
			System.out.println("There are no " + make + " " + model + "'s in the system.");
			return;
		}
		
				System.out.println();
				System.out.println(make + " " + model + " with the minimum price");
				//System.out.println("Price: " + minPrice.getPrice());
				System.out.printf("Price: $%.2f\n", minPrice.getPrice());
				System.out.println("Color: " + minPrice.getColor());
				System.out.println("Miles: " + minPrice.getMiles());
				System.out.println("Vin: " + minPrice.getVIN());

		//if no such car found, state it
	}//end of lowestPriceModel method
	
	/**
	*Find the car with the lowest mileage for a given make and model from user
	*/	
	public static void lowestMilesModel()
	{
		if(vins.isEmpty())
		{
			System.out.println("There are no cars in the system.");
			return;
		}
		
		System.out.print("\nEnter the make of the car: ");
		String make = input.nextLine();
		System.out.print("Enter the model for the car: ");
		String model = input.nextLine();
		Car minMiles = sortedCars.getMinMilesKey(make, model);
		if(minMiles == null)
		{
			System.out.println("There are no " + make + " " + model + "'s in the system.");
			return;
		}
		
				System.out.println();
				System.out.println( make + " " + model + " with the minimum miles");
				//System.out.println("Price: " + minMiles.getPrice());
				System.out.printf("Price: $%.2f\n", minMiles.getPrice());
				System.out.println("Color: " + minMiles.getColor());
				System.out.println("Miles: " + minMiles.getMiles());
				System.out.println("Vin: " + minMiles.getVIN());
		
		//peek at car in queue with lowest milage for given model
		//if no such car found, state it
	}//end of lowestMilesModel method
		
		
	public static void readFile()
	throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("cars.txt"));
		String skip = scan.nextLine();//skip first line of file
		while(scan.hasNext())
		{

			String str = scan.next();
			String[] tokens = str.split(":");
			
			if(vins.contains(tokens[0]))
			{
				System.out.println("Car already in the system.");
				return;
			}
			//for(int i = 0; i < tokens.length; i++)
			//{
			//	System.out.println(tokens[i]);
			//}
			Car newCar = new Car(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),Integer.parseInt( tokens[4]), tokens[5]);
			milesPQ.insert(newCar);
			pricePQ.insert(newCar);
			sortedCars.put(newCar);

			vins.put(tokens[0], newCar);
		}
		//System.out.println("Number of words: " + words.size());
	}
		
}//end of class