//author: Matt Stropkey

//for testing
import java.util.Scanner;
//This class will be used to store car brands and under each brand will have the different type of cars



public class LPHashModels
{

	//private static final int INIT_CAPACITY = 5;
	private static final int[] PRIMES = {3, 5, 7, 11, 19, 23,
			43, 47, 89, 97, 181, 191,
			379, 383, 761, 769, 1531, 1543};
	private int primeIndex;
	
	private int c;		//number of cars in table
	private int n;		//number of Strings in symbol table
	private int m;		//size of linear probing table
	private Node[] models;	//array holding the Brands
	
	//default constructor
	public LPHashModels()
	{
		this(PRIMES[1], 1);
	}
	
	
	//initialize the symbol table
	public LPHashModels(int capacity, int index)
	{
		m = capacity;		//initialize array to size of 4
		n = 0;				//number of Strings in hashmap
		models = new Node[m];
		primeIndex = index;
	}//end of constructor
	
	//determine if hashmap empty
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int getSize()
	{
		return c;
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
//		System.out.println("First hash value: " + val);
		if(models[val] != null)
		{
	//		System.out.println("Brands[val]: " + models[val].type);
			String make = models[val].type;
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
	//		System.out.println("Hash value too large: " + newIndex);
			newIndex = newIndex - m;
	//		System.out.println("Hash value after subtraction: " + newIndex);
		}
		boolean found = false;
	//	System.out.println("Second hash value: " + val);
		while(!found)
		{
		//	System.out.println("New Index: " + newIndex);
			if(models[newIndex] != null)
			{
				String make = models[newIndex].type;
				if(type.equalsIgnoreCase(make))
				{
					//System.out.println(make + " is the same as " + type);
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
		LPHashModels temp = new LPHashModels(PRIMES[primeIndex], primeIndex);
		for(int i = 0; i < m; i++)
		{
			if(models[i] != null)
			{
				temp.put(models[i]);
			//	System.out.println("Word copied to new array after resize: " + models[i].type);
			}//end of if
		}//end of for loop
		models = temp.models;
		m = temp.m;
	}//end of resize method
	
	
	//inserts the brand into the table
	public void put(Car car)//will need to change to car
	{
		if(car == null) throw new NullPointerException("The car cannot be null");
		
		
		//double the table size if 50% full
		if(n >= m/2)
		{
			if(primeIndex + 2 <= PRIMES.length)
			{
			//	System.out.println("Resizing array");
				resize();
			}
			else
				throw new ArrayIndexOutOfBoundsException("Array at the maximum size");
		}//end of if loop
		int i;
		i = hash(car.getModel().toLowerCase());
		//i = hash(car.getMake());
		if(models[i] != null && models[i].type.equalsIgnoreCase(car.getModel()))//found correct spot to hash to so look at models in spot
		{
			//System.out.println(car.getModel()+ " was added to index: " + i + " which holds " + models[i] );
			Node temp = models[i];
			if(temp.makeModMiles != null && temp.makeModPrice != null)
			{
				temp.makeModMiles.insert(car);
				temp.makeModPrice.insert(car);
			}
			else
				throw new NullPointerException("The queues for make/model are null");
			c++;
			//return;
		}//end of if loop
		else if(models[i] == null)//no models at current position
		{ 
			//System.out.println("models[i] == null so " + car.getModel() + " was placed at index: " + i);
			Node model = new Node();
			model.type = car.getModel();
			model.makeModMiles = new CarMinPQ('M');
			model.makeModMiles.insert(car);
			model.makeModPrice = new CarMinPQ('P');
			model.makeModPrice.insert(car);
			//Car temp = model.makeModPrice.minKey();
			//System.out.println("Car vin:" + temp.getVIN());
			models[i] = model;
			//
			c++;
			n++;
			//insert car type into linked list at this position or another hash table?
		}//end of else if
	}//end of put
	
	//used for resizing
	public void put(Node node)//will need to change to car
	{
		if(node == null) throw new NullPointerException("The car cannot be null");
		
		//double the table size if 50% full
		if(n >= m/2)
		{
			if(primeIndex + 2 <= PRIMES.length) 
			{
			//	System.out.println("Resizing array");
				resize();
			}
			else
				throw new ArrayIndexOutOfBoundsException("Array at the maximum size");
		}//end of if loop
		int i;
		i = hash(node.type.toLowerCase());
		//i = hash(car.getMake());
		if(models[i] != null && models[i].type.equalsIgnoreCase(node.type))//found correct spot to hash to so look at models in spot
		{
			//System.out.println(node.type + " was added to index: " + i + " which holds " + models[i].type );
			Node temp = models[i];
			c++;
			return;
		}//end of if loop
		else if(models[i] == null)//no models at current position
		{
			//System.out.println("models[i] == null so " + node.type + " was placed at index: " + i);
			models[i] = node;
			//
			c++;
			n++;
			//insert car type into linked list at this position or another hash table?
		}//end of else if
	}//end of put
	
	
	public boolean remove(String mod, int priceInd, int milesInd)
	{ 
		mod.toLowerCase();
		int i = hash(mod.toLowerCase());
		//System.out.println("i" + i);
		if(models[i] == null)  
		{
			throw new IllegalArgumentException("Car is not in the system so this method should not have been called.");
		}
		else if(models[i].type.equalsIgnoreCase(mod))
		{
			Node temp = models[i];
		//	System.out.println("Calling delete");
			temp.makeModPrice.delete(priceInd);
			temp.makeModMiles.delete(milesInd);
		//	System.out.println("REMOVED FROM QUEUES!!!!!!!!!!!!!!!!!!!");
			c--;
			if(temp.makeModMiles.isEmpty() && temp.makeModPrice.isEmpty())
			{
				models[i] = null;  
				n--;
			}
			return true;
		}
	//	System.out.println("NOT REMOVED FROM QUEUES");
		return false;
	}
	
	public void changeKey(Car car, char t)
	{
		int i = hash(car.getModel().toLowerCase());
		if(models[i] == null)
		{
			return;
		}
		else
		{
			Node temp = models[i];
			if(t == 'p')
				temp.makeModPrice.changeKey(car.getMakeModPriceIndex(), car);
			else if(t == 'm')
			temp.makeModMiles.changeKey(car.getMakeModMilesIndex(), car);
		}
	}
			
	
	public Car getMinPriceKey(String mod)
	{
		int i = hash(mod.toLowerCase());
		if(models[i] == null)
		{
	//		System.out.println("Car is not in the system");
		}
		else
		{
			Node temp = models[i];
			return temp.makeModPrice.minKey();
		}
		return null;
	}
	
	public Car getMinMilesKey(String mod)
	{
		int i = hash(mod.toLowerCase());
		if(models[i] == null)
		{
	//		System.out.println("Car is not in the system");
		}
		else
		{
			Node temp = models[i];
			return temp.makeModMiles.minKey();
		}
		return null;
	}
		
	
	private class Node {
		private String type;
		private CarMinPQ makeModMiles;
		private CarMinPQ makeModPrice;
	}
	//for testing

}