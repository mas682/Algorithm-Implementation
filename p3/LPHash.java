//author: Matt Stropkey

//for testing
import java.util.Scanner;
//This class will be used to store car brands and under each brand will have the different type of cars



public class LPHash
{

	//private static final int INIT_CAPACITY = 5;
	private static final int[] PRIMES = {3, 5, 7, 11, 19, 23,
			43, 47, 89, 97, 181, 191,
			379, 383, 761, 769, 1531, 1543};
	private int primeIndex;
	
	private int c;		//number of cars in table
	private int n;		//number of car brands in table
	private int m;		//size of linear probing table
	//private String[] Brands;	//array holding the Brands
	private Node[] Brands;
	
	//default constructor
	public LPHash()
	{
		this(PRIMES[1], 1);
	}
	
	
	//initialize the symbol table
	public LPHash(int capacity, int index)
	{
		m = capacity;		//initialize array to size of 4
		n = 0;				//number of Strings in hashmap
		//Brands = new String[m];
		Brands = new Node[m];
		primeIndex = index;
	}//end of constructor
	
	//determine if hashmap empty
	public boolean isEmpty() {
		return n == 0; 
	}
	
	//determines if a car brand is in the hash map..may not need
	//public boolean contains(String type)
	//{
	//	if(type == null) throw new IllegalArgumentException("Car Brand does not exist");
	//	return get(type) != null;
	//}//end of contains method
	
	//hash function for brands
	private int hash(String type)
	{
		type.toLowerCase();
		int val = ((type.hashCode() & 0x7fffffff) % m);	//will return a value to hash to
		//System.out.println("First hash value: " + val);
		if(Brands[val] != null)
		{
			//System.out.println("Brands[val]: " + Brands[val]);
			//System.out.println("Brands[val]: " + Brands[val].Brand);
			//String make = Brands[val];
			String make = Brands[val].Brand;
			if(type.equalsIgnoreCase(make))//compare 2 strings...may not be right?//if they are equal, at correct car brand
				return val;
			return hash2(type, val);
		}//end of if
		return val;
	}//end of hash method
	
	private int hash2(String type, int firstHash)
	{
		type.toLowerCase();
		int secondHash = PRIMES[primeIndex - 1];
		int val = ((type.hashCode() & 0x7fffffff) % secondHash) + 1;
		int newIndex = firstHash + val;
		
		if(newIndex>= m)//may need to fix to make sure only doing when past end of array
		{
			//System.out.println("Hash value too large: " + newIndex);
			newIndex = newIndex - m;
			//System.out.println("Hash value after subtraction: " + newIndex);
		}
		boolean found = false;
		//System.out.println("Second hash value: " + val);
		while(!found)
		{
			//System.out.println("New Index: " + newIndex);
			if(Brands[newIndex] != null)
			{
				//String make = Brands[newIndex];
				String make = Brands[newIndex].Brand;
				if(type.equalsIgnoreCase(make))
				{
				//	System.out.println(make + " is the same as " + type);
					found = true;
					return newIndex;
				}//end of inner if
				else
				{
					newIndex += val;
					if(newIndex>= m)//may need to fix to make sure only doing when past end of array
					{
					//	System.out.println("Hash value too large: " + newIndex);
						newIndex = newIndex - m;
					//	System.out.println("Hash value after subtraction: " + newIndex);
						found = false;
					}//end if
				}//end of else
			}//end of if
			else
				found = true;//brands at the index is null	
		}//end while loop
		return newIndex;
	}//end of hash2 method
		
	//resizes the hash table
	private void resize()
	{
		primeIndex += 2;
		//System.out.println("New hash table size: " + PRIMES[primeIndex]); 
		LPHash temp = new LPHash(PRIMES[primeIndex], primeIndex);
		for(int i = 0; i < m; i++)
		{
			if(Brands[i] != null)
			{
				temp.put(Brands[i]);
				//temp.put(Brands[i].Brand);//may have to store cars inside node because this will not work
				//System.out.println("Word copied to new array after resize: " + Brands[i].Brand);
			}//end of if
		}//end of for loop
		Brands = temp.Brands;
		m = temp.m;
	}//end of resize method
	
	
	//this variation of put is used when resizing
	public void put(Node node)
	{
		if(node == null) throw new NullPointerException("Node cannot be null");
		//double the table size if 50% full
		if(n >= m/2)//not used
		{
			if(primeIndex + 2 <= PRIMES.length)
			{
				//System.out.println("Resizing array");
				resize();
			}
			else
				throw new ArrayIndexOutOfBoundsException("Array at the maximum size");
		}//end of if loop
		int i;
		i = hash(node.Brand.toLowerCase());
		if(Brands[i] != null && Brands[i].Brand.equalsIgnoreCase(node.Brand))//found correct spot to hash to so look at models in spot
		{
			//System.out.println(node.Brand + " was added to index: " + i + " which holds " + Brands[i].Brand);
			c++;
			return;
		}//end of if loop
		else if(Brands[i] == null)//no models at current position
		{
			//System.out.println("Brands[i] == null so " + node.Brand + " was placed at index: " + i);
			Brands[i] = node;
			c++;
			n++;
		}//end of else if
	}//end of put method for resizing
	
	//inserts the brand into the table
	public void put(Car car)
	//public void put(String type)//will need to change to car
	{
		if(car == null) throw new IllegalArgumentException("Car cannot be null");
		
		//double the table size if 50% full
		if(n >= m/2)
		{
			if(primeIndex + 2 <= PRIMES.length)
			{
				//System.out.println("Resizing array");
				resize();
			} 
			else
				throw new ArrayIndexOutOfBoundsException("Array at the maximum size");
		}//end of if loop
		int i;
		//i = hash(type);
		i = hash(car.getMake().toLowerCase());
		if(Brands[i] != null && Brands[i].Brand.equalsIgnoreCase(car.getMake()))//found correct spot to hash to so look at models in spot
		{
			//System.out.println(type+ " was added to index: " + i + " which holds " + Brands[i] );
			//System.out.println(car.getMake() + " was added to index " + i + " which holds " + Brands[i].Brand);
			Node tempNode = Brands[i];
			tempNode.models.put(car);
			c++;
			return;
		}//end of if loop
		else if(Brands[i] == null)//no models at current position
		{
			//System.out.println("Brands[i] == null so " + type + " was placed at index: " + i);
			//System.out.println("Brands[i] == null so " + car.getMake() + " was placed at index: " + i);
			//Brands[i] = type;
			Node newNode = new Node();
			newNode.Brand = car.getMake();
			newNode.models = new LPHashModels();
			newNode.models.put(car);
			Brands[i] = newNode;
			c++;
			n++;
			//insert car type into linked list at this position or another hash table?
		}//end of else if
	}//end of put
	
	public void changeKey(Car car, char t)
	{
		int i = hash(car.getMake().toLowerCase());
		if(Brands[i] == null)
		{
		//	System.out.println("Car not found in the system."); 
		}
		else
		{
			Node temp = Brands[i];
			LPHashModels mods = temp.models;
			mods.changeKey(car, t);
		}
	}
	
	public boolean remove(Car car)
	{
		int i = hash(car.getMake().toLowerCase());
		boolean removed = false; 
		if(Brands[i] == null)//if the car is not in the system, should have never been called
		{
			throw new NullPointerException("The car is not in the system");
		}
		else if(Brands[i] != null && Brands[i].Brand.equalsIgnoreCase(car.getMake()))//if car in the system, go to LPHashModels remove method
		{
			Node tempNode = Brands[i];
			//System.out.println("MakeModPriceIndex: " + car.getMakeModPriceIndex());
			//System.out.println("MakeModMilesIndex: " + car.getMakeModMilesIndex());
			removed = tempNode.models.remove(car.getModel(), car.getMakeModPriceIndex(), car.getMakeModMilesIndex());
			c--;
			if(removed && tempNode.models.isEmpty())//if the value was removed from the model hash, and there are no more cars in the model hash, delete this node
			{
				Brands[i] = null;
				n--;
			} 
		}
		else				
		{
			throw new IllegalArgumentException("The index of the array does not match the car");
		}
		return removed;
	}
			
	
	public Car getMinPriceKey(String make, String carMod)
	{
		int i = hash(make.toLowerCase());
		if(Brands[i] == null)
		{
		//	System.out.println("Car not found in the system.");
		}
		else
		{
			Node temp = Brands[i];
			LPHashModels mods = temp.models;
			return mods.getMinPriceKey(carMod.toLowerCase());
		}
		return null;		
	}
	
	public Car getMinMilesKey(String make, String carMod)
	{ 
		int i = hash(make.toLowerCase());
		if(Brands[i] == null)
		{
	//		System.out.println("Car not found in the system.");
		}
		else
		{
			Node temp = Brands[i];
			LPHashModels mods = temp.models;
			return mods.getMinMilesKey(carMod);
		}
		return null;		
	}
		
	private class Node {
		private String Brand;
		private LPHashModels models;
	}
	//for testing
	//public static void main(String[] args)
	//{
	//	LPHash myHash = new LPHash();
	//	boolean run = true;
	//	Scanner scan = new Scanner(System.in);
	//	int i = 0;
	//	while(run)
	//	{
	//		
	//		System.out.print("Enter a Brand of car: ");
	//		String input = scan.nextLine();
	//		Car newCar = new Car("1234", input, "sample", 1200, 1000, "blue");
	//		myHash.put(newCar);
	//		if(i > 30)
	//			run = false;
	//		i++;
	//	}
	//}//end of main
			
	
	
	
}//end of class
