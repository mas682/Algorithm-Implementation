package cs1501.p1;

//@author Matt Stropkey
//January 16, 2016
//import java.util.Iterator;
//import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileWriter;


public class TrieDLB<Value> {

	private Node root;	//root of trie
	private int n;		//number of keys in trie
	
	private class Node {
		private char c;
		private Object val;
		//private Node left, mid, right;
		private Node right, mid;
	}
	
	private Node put(Node x, String key, Value val, int d)
	{
		
		//if(key == null)
		//{
		//	throw new IllegalArgumentException();
		//}
		//if(val == null)
		//{
		//	throw new NullPointerException();
		//}
		char b = key.charAt(d);
		if(x == null)
		{
			x = new Node();
			x.c = b;
		}
		if(x.c != b)
		{
			x.right = put(x.right, key, val, d);
			return null;
		}
		else if(d < key.length()-1)
		{
			x.mid = put(x.mid, key, val, d+1);
		}
		else
		{
			if(d == key.length() -1){
				if(x.val == null){
					n++;
				}
			}
			x.val = val;
		}
		return x;
	}
	
	public TrieDLB(){
	}
	
	public void put(String key, Value val)
	{
		
		if(key == null)
			throw new IllegalArgumentException("first argument to put() is null");
		//System.out.println("placing: " + key);
		if(val == null)
		{
			//delete(key);
		}
		else
			root = put(root, key, val, 0);
	}
	
	/*private Node put(Node x, String key, Value val, int d) {
		char c = key.charAt(d);
		if(x == null){
			x = new Node();
			x.c = c;
		}
		if(c < x.c)
		{
			x.left = put(x.left, key, val, d);
		}
		else if( c > x.c){
			x.right = put(x.right, key, val, d);
		}
		else if( d < key.length()-1)
			x.mid = put(x.mid, key, val, d+1);
		else
		{
			if(d == key.length() - 1){
				if(x.val == null){
					n++;
				} 
			}
			x.val = val;
		}
		return x;
		
	}
	*/
	public Value get(String key)
	{
		Node x = get(root, key, 0);
		if(x == null)
			return null;
		return (Value)x.val;
	}
	
	private Node get(Node x, String key, int d)
	{
		if(x == null)
			return null;
		char f = key.charAt(d);
		if(x.c != f)
		{
			//if(x.c != null)
				System.out.println(x.c + " does not equal " + f);
				System.out.println("going right");
			x = get(x.right, key, d+1);
		}
		else if(d < key.length()-1)
		{
			System.out.println("going down middle");
			x = get(x.mid, key, d+1);
		//if(x.c == c)
		//{
		//	if(d < key.length() -1)
		//		return get(x.mid, key, d+1);
		//	else
		//		return x;
		//}
		//lse if(x.c != c)
		//	return get(x.right, key, d);
		}
		else 
			return x;
		return x;
	}
	
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }
	
	public int size() {
        return n;
    }
	
	 /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }
	
	
	/**
	*Removes the key from the set if the key is present.
	*@param key String to remove
	@throws IllegalArgumentException if the string to remove is null
	
	public void delete(String key) {
		if(key == null) throw new IllegalArgumentException("argument to delete() is null");
		root = delete(root, key, 0);
	}
	
	private Node delete(Node x, String key, int d){
		if(x == null)
			return null;
		if(d == key.length()) {
			if(x.val != null)
			{
				n--;//decrement number of keys
			}
			x.val = null;
		}
		else {
			char c = key.charAt(d);
			if(c < x.c)
				x.left = delete(x.left, key, d);
			else if(c > x.c)
				x.right = delete(x.right, key, d);
			else if(d < key.length()-1)
				x.mid = delete(x.mid, key, d+1);
			else
				return null;//may need to return something else
		}
		
		//remove subtrie rooted at x if it is completely empty
		if(x.val != null)
			return x;
		////////////////look at BST delete
		return null;
	}
	*/
	public LinkedQueue<String> keysWithPrefix(String prefix)
	{
		LinkedQueue<String> queue = new LinkedQueue<>();
		collect(get(root, prefix, 0), prefix, queue);
		return queue;
	}
	private void collect(Node x, String prefix, LinkedQueue<String> queue)
	{
		System.out.println(prefix);
		Node currentNode;
		if(x == null)
		{
			System.out.println("x is null");
			return;
		}
		if(x.val != null)
		{			queue.enqueue(prefix);
		}
		
		if(x.mid != null)
		{
			currentNode = x.mid;
			collect(x.mid, prefix+x.mid.c, queue);
			
			if(currentNode.right!= null)
			{
				currentNode = currentNode.right;
				collect(currentNode, prefix+currentNode.c, queue);
				while(currentNode.right != null)
				{
					currentNode = currentNode.right;
					collect(currentNode, prefix+currentNode.c, queue);
				}
			}
		}
	}
			
		
	/**
		Node currentNode = x;
		if(x.mid!= null)
		{
			currentNode = x.mid;
			collect(x.mid, prefix+x.mid.c, queue);
			
			if(currentNode.right != null)
			{
				currentNode = currentNode.right;
				collect(currentNode, prefix+currentNode.c, queue);
				while(currentNode.right != null)
				{
					currentNode = currentNode.right;
					collect(currentNode, prefix+currentNode.c, queue);
				}
				return;
			}
			
			if(currentNode.left != null)
			{
				currentNode = currentNode.left;
				collect(currentNode, prefix+currentNode.c, queue);
				while(currentNode.left != null)
				{
					currentNode = currentNode.left;
					collect(currentNode, prefix+currentNode.c, queue);
				}
				return;
			}
		}			
		
	}

	*/
	public static void save(String userInput) {
		try{
			File file = new File("cs1501/p1/user_history.txt");
			file.getParentFile().mkdirs();
			
			PrintWriter printString = new PrintWriter(new FileWriter(file, true));
			printString.println(userInput);
			printString.close();
		}
		catch(IOException e) {
			//couldn't save
			System.out.println("Error: Could not save data");
			e.printStackTrace();
		}
	}

	public static void readFile(String filename, TrieDLB words)
	throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(filename));
		int i = 1;
		while(scan.hasNext())
		{

			String symbol = scan.next();
			words.put(symbol, words.size()+1);
			i++;
		}
		
		System.out.println("Number of words: " + words.size());
	}
	public static String[] getSuggestions(String input, TrieDLB dictionary, TrieDLB user)
	{

		int i = 0;
			String[] suggestions = new String[5];
			LinkedQueue<String> temp = new LinkedQueue<>();
			if(!user.isEmpty())
			{
				temp = user.keysWithPrefix(input);
			}

			while(!(temp.isEmpty()) && i < 5)
			{
			suggestions[i] = temp.dequeue();
			i++;
			}
			if(i < 5)
			{
				if(!dictionary.isEmpty())
				{
				temp = dictionary.keysWithPrefix(input);
				String word;
				while(!(temp.isEmpty()) && i < 5)
				{
					word = temp.dequeue();
					for(int a = 0; a < i; a++)
					{
						if(suggestions[a] == word)
						{
							if(!temp.isEmpty())
							{
								word = temp.dequeue();
								break;
							}
							else
							{
								word = null;
							}
						}
					}
					suggestions[i] = word;
					i++;
				}
				}
			}
			
			return suggestions;
	}
	
	 public static void main(String[] args) {

        // build symbol table from standard input
        TrieDLB<Integer> st = new TrieDLB<Integer>();
		TrieDLB<Integer> userIn = new TrieDLB<Integer>();
		try{
			readFile("cs1501/p1/dictionary.txt", st);
		}catch (FileNotFoundException e) { 
		System.err.println("File dictionary.txt not found");
		}
		try{
			readFile("cs1501/p1/user_history.txt", userIn);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File userInput not found");
		}
		
		String input = null;
		String prefix = "";
		
		Scanner scan = new Scanner(System.in);
		
		String [] list = new String[5];
		long startTime = 0;
		long elapsedTime = 0;
		double seconds = 0;
		boolean repeat = true;
		boolean terminate = true;
		String[] suggestions = new String[5];
		do{
			repeat = true;
			prefix = "";
		do{
			startTime = 0;
			elapsedTime = 0;
			seconds = 0;
			int i = 1;
			int a = 0;
			String word = null;
			if(prefix.length() == 0)
			{
				boolean again = false;
				do{
					System.out.print("Enter your first character: ");
					again = false;
					if(scan.hasNextInt())
					{
						again = true;
					}
					input = scan.next();
					if(input.charAt(0) == '!')
					{
						terminate = false;
						repeat = false;
					}
					if(input.charAt(0) == '$')
					{
						again = true;
					}
				}while(input.length() > 1 || again);
				prefix = input;
				if(terminate)
				{
				startTime = System.nanoTime();
				list = getSuggestions(input, st, userIn);
				elapsedTime = System.nanoTime() - startTime;
				seconds = (double)elapsedTime/1000000000.0;
				System.out.printf("(%.6f s)\n", seconds);
				System.out.println("Predictions:");
				suggestions = new String[5];
				while(a < 5 && list[a] != null)
				{
					word = list[a];
					System.out.print("(" + i + ")  " + word + "		");
					suggestions[a] = word;
					a++;
					i++;
				}
				System.out.println();
				}
			}
			else
			{
				boolean again;
				do{
					again = false;
					System.out.print("Enter the next character: ");
					if(scan.hasNextInt())
					{
						input = scan.next();
						if(input.charAt(0) == '1' && suggestions[0] != null)
						{
							System.out.println("WORD COMPLETED: " + suggestions[0]);
							repeat = false;
							if(!userIn.contains(suggestions[0]))
							{
							save(suggestions[0]);
							}
							break;
						}
						else if(input.charAt(0) == '2' && suggestions[1] != null)
						{
							System.out.println("WORD COMPLETED: " + suggestions[1]);
							repeat = false;
							if(!userIn.contains(suggestions[1]))
							{
							save(suggestions[1]);
							}
							break;
						}
						else if(input.charAt(0) == '3' && suggestions[2] != null)
						{
							System.out.println("WORD COMPLETED: " + suggestions[2]);
							repeat = false;
							if(!userIn.contains(suggestions[2]))
							{
							save(suggestions[2]);
							}
							break;
						}
						else if(input.charAt(0) == '4' && suggestions[2] != null)
						{
							System.out.println("WORD COMPLETED: " + suggestions[3]);
							repeat = false;
							if(!userIn.contains(suggestions[3]))
							{
							save(suggestions[3]);
							}
							break;
						}
						else if(input.charAt(0) == '5' && suggestions[4] != null)
						{
							System.out.println("WORD COMPLETED: " + suggestions[4]);
							repeat = false;
							if(!userIn.contains(suggestions[4]))
							{
							save(suggestions[4]);
							}
							break;
						}
						else
						{
							System.out.println("Invalid number choice");
							again = true;
						}
					}
					if(again == false)
					{
						input = scan.next();
						if(input.charAt(0) == '$')
						{
							save(prefix);
							repeat = false;
						}
						if(input.charAt(0) == '!')
						{
							repeat = false;
							terminate = false;
						}
					}

				}while(input.length() > 1 || again);
				startTime = 0;
				elapsedTime = 0;
				seconds = 0;
				if(repeat && terminate)
				{
				prefix = prefix + input;
				startTime = System.nanoTime();
				list = getSuggestions(prefix, st, userIn);
				elapsedTime = System.nanoTime() - startTime;
				seconds = (double)elapsedTime/1000000000.0;
				System.out.printf("(%.6f s)\n", seconds);
				System.out.println("Predictions:");
				i = 1;
				a = 0;
				suggestions = new String[5];
				while(a < 5 && list[a] != null)
				{
					word = list[a];
					System.out.print("(" + i + ")  " + word + "		");
					suggestions[i-1] = word;
					i++;
					a++;
				}
				System.out.println();
				}
			}
		}while(input.charAt(0) != '$' && repeat);
		}while(terminate);
		System.out.println("\n\nAverage runtime: ");
		System.out.println("Bye!");
		
    }
}
