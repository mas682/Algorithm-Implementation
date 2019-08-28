/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

 
public class LZW2 {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    //private static final int W = 9;         // codeword width
	private static int W = 9;			//codewords originally size 9

	//
	//original:private static final int MAX = 65536;//2^16
	//temp:
	private static final int MAX = 65535;
	
	
	

    public static void compress(char mode) { 
		
		double totalBits = 0;
		double currentRatio = 0;
		double oldRatio = 0;
		double updatedRatio = 0;
		double compBits = 0;
        String input = BinaryStdIn.readString();// input = whole file text
		boolean reset = false; //used for reset mode
		boolean monitor = false;// used for monitor mode
		double monVal = 0;
		boolean setOld = false;

		//System.err.print("Input is: \n" + input + "\nEnd Input\n");//see what the whole input string is
		
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
		{
            st.put("" + (char) i, i);
		}//adds all the ascii characters to the Trie

		if(mode == 'r')
		{
			BinaryStdOut.write(st.get("r"), W);//write r to first thing in compressed file
			reset = true;
		}
		else if(mode == 'm')
		{
			monitor = true;
			BinaryStdOut.write(st.get("m"), W);
			monVal = 1.1;
		}
		else
		{
			BinaryStdOut.write(st.get("n"), W);
		}
		
		
        int code = R+1;  // R is codeword for EndOfFile
		
		int maxWords = (int) Math.pow(2, W) - 1;
        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
			//will take whole input, return the first character as longest prefix first time
			
			//System.err.print("Encoded word: " + s + " into file:" + st.get(s) + "in size " + W + "\n" );
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding./////
			///converts the value of the codeword to binary and writes it to file
			//first value will just be first character

			//System.err.print("The codeword " + s + " has the encoding of " + st.get(s)+ "\n");
            int t = s.length();
			if(monitor)
			{
			totalBits = totalBits + (t*8);
			compBits = compBits + W;
			currentRatio = totalBits/compBits;
				//if(code == MAX)
				if(setOld)
				{
					oldRatio = currentRatio;
					//code++;
					setOld = false;
					
					
				System.err.println("*****************Monitor*************************");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				//System.err.println("Old Ratio: " + oldRatio);
				//System.err.println("Total Bits: " + totalBits);
				//System.err.println("Compressed Bits: " + compBits);
				}
				
				
			//System.err.println("The codeword " + s + " has the bit length of " + (t*8));
			//System.err.println("The compressed version has bit length of " + W);
			//System.err.println("TotalBits so far: " + totalBits);
			//System.err.println("Total compressedBits so far: " + compBits);
			//System.err.println("Compression ratio: " + cRatio);
			//t = the length of the prefix, in first iteration will just be 1
			}
			//if prefix size is less than size of whole file and codeword is less than the max number of code words
           //original: if (t < input.length() && code < maxWords) 		// Add s to symbol table./////L changed to maxWords
			if (t < input.length() && code <= maxWords) 		// Add s to symbol table./////L changed to maxWords
			{
				String sub = input.substring(0, t+1);
				//System.err.print("The string to put is: ##" + sub + "##\n");
				st.put(sub, code++);
                //st.put(input.substring(0, t + 1), code++);
				//System.err.print("The codeword " + sub + " has the encoding of " + st.get(sub)+ "\n");
				
				
				
				///////////////
				if(code + 1 == MAX +1)
				{
				//	System.err.println("set old set to true! and code =" + code);
					setOld = true;
				}
			}
			//if prefix size is less than the size of the whole file and code word is bigger than maxsize of codeword for current
			//binary size, and still less than the absolute max number of code words
			//original: else if(t < input.length() && code >= maxWords && code < MAX)
			else if(t < input.length() && code > maxWords && code < MAX+1)
			{
				//System.err.print("*******************INCREASING SIZE****************************\n");
				//System.err.print("*******************INCREASING SIZE****************************\n");
				//System.err.print("max words: " + maxWords + "\n");
				//System.err.print("*******************INCREASING SIZE****************************\n");
				//System.err.print("*******************INCREASING SIZE****************************\n");
				//System.err.print("*******************INCREASING SIZE****************************\n");
				
				W = W + 1;//increase the codeword size
				maxWords =  (int) Math.pow(2, W) - 1;
				
				//System.err.print("new max words: " + maxWords + "\n"); 
			
				if( t < input.length() && code < MAX)//not at end of input, and code len != 17
				{
					String sub = input.substring(0, t+1);
				//	System.err.print("The string to put is: ##" + sub + "##\n");
					st.put(sub, code++);
					//st.put(input.substring(0, t+1), code++);//add to symbol tabel
				//	System.err.print("The codeword " + sub + " has the encoding of " + st.get(sub)+ "\n");
				}	
			}
			
			/////////////////DO NOT TOUCH ANYTHING ABOVE HERE! WORKS PERFECT FOR DO NOTHING MODE
			
			
			
			////////newest edition/////////
			//////////////used for reset...will need to add condition so only does this in reset mode
			////also need to make it so expand knows it is doing this/////
			else if(t < input.length() && code >= maxWords && code >= MAX && reset)
			{
				
				W = 9;
				maxWords = (int)Math.pow(2,W) -1;
				System.err.println("*******************************RESET*************");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				st = new TST<Integer>();
				
				for (int i = 0; i < R; i++)
				{
					st.put("" + (char) i, i);
					//System.err.print("adding to TST: " + (char)i + "\n");
				}//adds all the ascii characters to the Trie
				//st.put("", R+1);
				code = R+1;  // R is codeword for EndOfFile
				if(t < input.length() && code < MAX)
				{
					String sub =  input.substring(0, t+1);
					
					st.put(sub, code++);
					
				//	System.err.print("The codeword " + sub + " has the encoding of " + st.get(sub)+ "\n");
				}
			}
			
			
			//////////reset mode should be fine!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			///////////////////////////////new addition for monitor mode////////////
			/////////////////////////need variable so it knows when in monitor mode
			else if(t < input.length() && code >= maxWords && code >= MAX && monitor)
			{
				//if(setOld)
				//{
				//	oldRatio = currentRatio;
				//	setOld = false;
				//System.err.println("*****************Monitor*************************");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("#################################################");
				//System.err.println("Old Ratio: " + oldRatio);
				//System.err.println("Total Bits: " + totalBits);
				//System.err.println("Compressed Bits: " + compBits);
				

				updatedRatio = oldRatio/currentRatio;
				//System.err.println("Old Ratio: " + oldRatio);
				//System.err.println("Current ratio: " + currentRatio);
				//System.err.println("Updated Ratio: " + updatedRatio);
				//original: if(updatedRatio > monVal)
				if(updatedRatio > monVal)//changed for testing purposes
				{
				W = 9;
				maxWords = (int)Math.pow(2, W) -1;
				//setOld = true;
				System.err.println("*****************Monitor*************************");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				st = new TST<Integer>();
				
				for (int i = 0; i < R; i++)
				{
				st.put("" + (char) i, i);
				}//adds all the ascii characters to the Trie
				code = R+1;  // R is codeword for EndOfFile
				if(t < input.length() && code < MAX)
				{
					String sub =  input.substring(0, t+1);
					
					st.put(sub, code++);
					
				//	System.err.print("*****The codeword " + sub + " has the encoding of " + st.get(sub)+ "********************************\n");
				}
				}

					
				
			}
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            input = input.substring(t);            // Scan past s in input.
			//goes to next character in string
			
			//System.err.print("Next input is: " + input + "\n");
        }
        //BinaryStdOut.write(R, W);/////////////I think this passes the final size value and how big file can be at max
		BinaryStdOut.write(R, W);//passing alphabet size of 256, with the size of the codewords//will need to fix
		W = 9;//finished compressing so reset value of length of codewords in binary for expansion
        BinaryStdOut.close();
    } 


    public static void expand() {
        String[] st = new String[MAX + 1];
		boolean reset = false;//////////used for reset mode
		boolean monitor = false;
		boolean setOld = false;
		boolean monMode = false;
        int i; // next available codeword value
		double totalBits = 0;
		double currentRatio = 0;
		double oldRatio = 0;
		double compBits = 0;
		double monVal = 0;
		double updatedRatio = 0;

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF//changed to i?
	
		int codeword = BinaryStdIn.readInt(W);//get the mode
		String val = st[codeword];
		if( val.equals("r"))
		{
			reset = true;
		}
		else if(val.equals("m"))
		{
			monitor = true;
			monVal = 1.1;
		}
		else if(val.equals("n"))
		{
		}
		else
		{
			System.err.println("Mode not found");
		}
		
        codeword = BinaryStdIn.readInt(W);	//READ FIRST CODE WORD
		System.err.print("first codeword value: " + codeword + "\n");
        if (codeword == R) return;           // expanded message is empty string
        val = st[codeword];			//find the value of the codeword in the array
		//System.err.print("val : " + val + "\n");
		
		
		int maxWords =  (int) Math.pow(2, W) - 1;
        while (true) {
			int t = val.length();
			if(monitor)
			{
			totalBits = totalBits + (t*8);
			compBits = compBits + W;
			currentRatio = totalBits/compBits;
				if(monMode)
				{	
					updatedRatio = oldRatio/currentRatio;
					if( updatedRatio > monVal)
						W = 9;
				}
			
				//if(i == MAX + 1)
				if(setOld)
				{
				oldRatio = currentRatio;
				//i++;
				setOld = false;
				monMode = true;
					
				System.err.println("*****************Monitor*************************");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				//System.err.println("Old Ratio: " + oldRatio);
				}

			}
            BinaryStdOut.write(val);//write the decompressed value to expanded file
            codeword = BinaryStdIn.readInt(W); //I think this is reading the next codeword?
			//System.err.print("codeword: ##" + codeword + "##\n");
			
            if (codeword == R) break;//if at end of file, done
            String s = st[codeword];	//s = the newest codeword
			//System.err.print("codeword decoded: " + s + "\n");
			
			//System.err.print(" i: " + i + "\ncodeword: " + codeword + "\n");
            if (i == codeword)
			{
				s = val + val.charAt(0);   // special case hack
			}
			//finding first codeword
			if(i == MAX + 1)
			{
				for(int j = 0; j < MAX + 1; j++)
			//		System.err.println(j + ". " + st[j]);
				i++;
			}
			
            //original: if (i+1 < maxWords && i+1 < MAX)
			if (i+1 <= maxWords && i < MAX + 1)
			{
				String nextWord = val + s.charAt(0);
				if(nextWord != null)
				//System.err.print("Nextword to insert to table: " + nextWord + "\n");
				st[i++] = nextWord;
				//System.err.print("The value for the inserted word is: " + i + "\n");
				if(i + 1 == MAX + 1)
				{
					setOld = true;
					//System.err.println("i+1" + i+1 + " = " + MAX + 1);
				}

				//st[i++] = val + s.charAt(0);
			}
			else if(i == MAX && i < MAX + 1)//kind of a special case
			{
				String nextWord = val + s.charAt(0);
				if(nextWord != null)
				//System.err.print("Nextword to insert to table: " + nextWord + "\n");
				st[i++] = nextWord;
				//System.err.print("The value for the inserted word is: " + i + "\n");
				
				//added for reset mode
				if(i+1 >= maxWords && i+1 >= MAX + 1 && reset)
				{
					W = 9;
				}
			//	if(monitor)
			//	{
			//		setOld = true;
			//	}
				//else if( i + 1 >= maxWords && i +1 >= MAX && monitor)
				//{
				//	updatedRatio = old
				

			}
			//original: else if(i+1 >= maxWords && i+1 < MAX)
			else if(i+1 > maxWords && i+1 < MAX + 1)
			{
				String nextWord = val + s.charAt(0);
				//System.err.print("Nextword to insert to table: " + nextWord + "\n");
				st[i++] = nextWord;
				//System.err.print("The value for the inserted word is: " + i + "\n");
				
				//for(int j = 0; j <= maxWords; j++)
				//	System.err.println(j + ". " + st[j]);
				//while(i > maxWords && i < MAX)
				//{
				System.err.println("Insider err!!!\n");
				W++; //increase size of codewords
				compBits = compBits - 1;
				maxWords = (int) Math.pow(2, W) -1;//update maxWords
				//}
				//original:
				//String nextWord = val + s.charAt(0);
				//System.err.print("Nextword to insert to table: " + nextWord + "\n");
				//st[i++] = nextWord;
				//System.err.print("The value for the inserted word is: " + i + "\n");
				
				
				//st[i++] = val + s.charAt(0);
				//W = 9;
				//maxWords = (int)Math.pow(2,W);
			}
			//////////////////////DO NOT TOUCH ANYTHING ABOVE HERE!!!!!!! WORKS IN DO NOTHING MODE
			
			
			/////////////////////new addition for reset//////////////
			//////////////need to add variable so it is only done when in reset mode
			else if(i+1 >= maxWords && i+1 >= MAX + 1 && reset)
			{
				
				System.err.println("*******************************RESET*************");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				System.err.println("#################################################");
				W = 9;///reset value of WARNING
				maxWords = (int) Math.pow(2, W) -1 ;//reset max words
				String nextWord = val + s.charAt(0);
				st = new String[MAX+ 1];
				for (i = 0; i < R; i++)
					st[i] = "" + (char) i;
				st[i++] = "";//////should work at i and not i++
				st[i++] = nextWord;
				//for(int j = 0; j <= maxWords; j++)
				//	System.err.println(j + ". " + st[j]);
			}
			////////////////////DO NOT CHANGE ANYTHING ABOVE HERE, WORKS IN RESET MODE!!!
			
			
			
			
			else if( i+1 >= maxWords && i+1 >= MAX + 1 && monitor)
			{

				//updatedRatio = oldRatio/currentRatio;
				//System.err.println("Old Ratio: " + oldRatio);
				//System.err.println("Current ratio: " + currentRatio);
				//System.err.println("Updated Ratio: " + updatedRatio);
				//System.err.println("Total bits: " + totalBits);
				//System.err.println("Compressed bits: " + compBits);
				
				if(updatedRatio > monVal)/////will need changed, just used to test
				{

				W = 9;///reset value of WARNING
				maxWords = (int) Math.pow(2, W) -1;//reset max words
				monMode = false;
				String nextWord = val + s.charAt(0);
				//System.err.println("NEXTWORD INSIDE OF MONITOR: "+ nextWord);
				st = new String[MAX + 1];
				for (i = 0; i < R; i++)
					st[i] = "" + (char) i;

				st[i++] = "";//////should work at i and not i++
				st[i++] = nextWord;
				//for(int j = 0; j <= maxWords; j++)
				//	System.err.println(j + ". " + st[j]);
				//System.err.print("The value for the inserted word is: " + i + "\n");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MONITOR RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
			//if less than max size, add codeword to codebook
			
            val = s;
			//update to write new decoded word to file
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
		try{
        if(args[0].equals("-") && args[1].equals("n")) compress('n');
		else if(args[0].equals("-") && args[1].equals("r")) compress('r');
		else if(args[0].equals("-") && args[1].equals("m")) compress('m');
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.print("Illegal command line argument");
		}
    }

}