//@author Matt Stropkey
//January 16, 2016


import java.util.Scanner;

public class DLBEx
{
public static void main(String[]args)
{
	System.out.print("Enter your first character: ");
	Scanner scan = new Scanner(System.in);
	String input;
	//will need to adjust for other characters such as numbers
	char a = scan.next().charAt(0);
	System.out.println(a);
	if(a == 1)
		System.out.println("The values are the same");
	else
		System.out.println("The values do not match");
	
}

public class TrieDLB<Value> {
	private Node root;	//root of trie
	private int n;		//number of keys in trie
	
	private static class Node {
		private char c;
		private Object val;
		private Node left, mid, right;
	}
	
	public Trie(){
	}
	
	public void put(String key, Value val)
	{
		if(key == null)
			throw new IllegalArgumentException("first argument to put() is null");
		if(val == null)
			//delete(key);
		else
			root = put(root, key, val, 0);
	}
	
	private Node put(Node x, String key, Value val, int d) {
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
		else
			if(d == key.length)
				if(x.val == null)
					n++;
			x.val = val;
		return x;
		
	}

		//char c = key.charAt(d);
		//x.next(c) = put(x.next(c), key, val, d+1);
		//return x;
	//}
	
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
		char c = key.charAt(d);
		if(c < x.c)
			return get(x.left, key, d);
		else if(c > x.c)
			return get(x.right, key, d);
		else if(d < key.length()-1)
			return get(x.mid, key, d+1);
		else
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
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }


public class TrieST<Value> {
    private static final int R = 256;        // extended ASCII


    private Node root;      // root of trie
    private int n;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }




    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where . symbol is treated as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of given length (-1 if no such match)
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    /**
     * Removes the key from the set if the key is present.
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

    /**
     * Unit tests the {@code TrieST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}





















}