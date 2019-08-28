//author: Matt Stropkey


public class VinNumDLB<Value>{
	
	private Node root;	//root of trie
	private int n;//number of cars in DLB
	
	
	//method to check if string valid
	//key is the vin number
	//val is the position of the car in a the queue
	public void put(String key, Car car)
	{
		if(key == null)
			throw new IllegalArgumentException("The Vin number to enter is null");
		if(car == null)
		{
			throw new IllegalArgumentException("The Value argument is null");
		}
		else
			root = put(root, key, car, 0);
	}
	
	private Node put(Node x, String key, Car car, int d) {
		char c = key.charAt(d);
		//System.out.println(c);
		if(x == null){
			x = new Node();
			x.c = c;
		}
		
		if(c != x.c)
		{
			x.right = put(x.right, key, car, d);
		}
		else if(d < key.length()-1)		//if x.c = c, go down
		{
			x.mid = put(x.mid, key, car, d+1);
		}
		
		else
		{
			if(d == key.length() - 1)	//if at end of key
			{
				x.val = car;	//x.val = the position in the queue where the car is
				n++;
			}
		}
		
		return x;
	}
	
	public Car get(String key)//may need to change value to int?
	{
		Node x = get(root, key, 0);
		//System.out.println("The value returned: " + (Value)x.val);  
		if(x == null)
			return null;
		//System.out.println("The value returned: " + (Value)x.val); 
		return (Car)x.val; 
	}
	
	//gets the value of the vin number to return
	private Node get(Node x, String key, int d)
	{
		if(x == null)
			return null;
		char c = key.charAt(d);
		//System.out.println(c);
		if(c != x.c)
			return get(x.right, key, d);
		else if(c == x.c)
		{
			if(d == key.length()-1)
				return x;
			else
				return get(x.mid, key, d+1);
		}
		//else if(d < key.length()-1)
		//	return get(x.mid, key, d+1);
		else
			return x;
	}
	
	//determines if the dlb contains a vin number
	public boolean contains(String key)
	{
		Node x = get(root, key, 0);
		if(x == null)
			return false;
		return (Value)x.val != null;
	}
	
	public Car remove(String key)
	{
		Car removed = remove(root, key, 0);
		return removed;
	}
 
	
	private Car remove(Node x, String key, int d)
	{
		Car removed = null;
		//need case if vin number to remove is not even in the system
		if(x == null)
			return null;
		char c = key.charAt(d);
		 
		if(c != x.c)
			removed = remove(x.right, key, d);
		else if(c == x.c)
		{
			if(d == key.length()-1 && x.val != null)
			{	
				removed = (Car)x.val;
				x.val = null;
				n--;
				if(x.right == null)
				{
					x = null;
				}
				return removed;
			}
			else
				removed = remove(x.mid, key, d+1);
		}
		if(x.right == null && x.mid == null)//upon return, delete if not pointing to anything
		{
			x = null;
		}
		return removed;
	}
	
	public boolean isEmpty()
	{
		return n == 0;
	}
					
		

	//nodes for the DLB
		private class Node {
		private char c;
		private Object val;
		private Node mid, right; 
	}
	
	
	
	//for testing
	/*
	public static void main(String[] args)
	{
		VinNumDLB<Integer> nums = new VinNumDLB();
		
		nums.put("abc1234", 12);
		nums.put("zfe1256", 21);
		nums.put("abf1543", 5);
		
		if(nums.contains("abc1234"))
			System.out.println("Contains value 1");
		if(nums.contains("zfe1256"))
			System.out.println("Contains value 2");
		if(nums.contains("abf1543"))
			System.out.println("Contains value 3");
		
		int values;
		values = nums.get("abf1543");
		System.out.println("Value in system: " + values);
		if(nums.remove("abc1234"))
			System.out.println("Value removed");
		if(!nums.contains("abc1234"))
			System.out.println("Does not contain 1");
		if(nums.contains("zfe1256"))
			System.out.println("Contains value 2");
		if(nums.contains("abf1543"))
			System.out.println("Contains value 3");
		
		System.exit(0);
		
		
}
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
}//end of VinNumDLB class