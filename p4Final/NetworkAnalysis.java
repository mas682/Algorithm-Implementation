//Matt Stropkey

import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;


public class NetworkAnalysis{
	
	private static EdgeWeightedDigraph graph;
	private static Node[] copperConnect;
	
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		try{
		readFile(args[0]);
		}catch(FileNotFoundException e)
		{
			System.out.println("File: " + args[0] + " not found.");
		}
		int selection = -1;
		while(selection != 0) {
			System.out.println();
			System.out.println("Network Analysis Menu");
			System.out.println("1. Find lowest latency path between 2 vertices");
            System.out.println("2. Check copper connection");
            System.out.println("3. Maximum data to transmit between 2 vertices");
            System.out.println("4. Minimum average latency spanning tree");
            System.out.println("5. 2 verticie failure");
            System.out.println("0. Quit");
            System.out.print("Selection: ");
			

			   
			 try {
                selection = scan.nextInt();
            } catch (NoSuchElementException e) {
                selection = -1;
            } catch (IllegalStateException e) {
                selection = -1;
            }
            scan.nextLine();

            switch (selection) {
                case 1:
                    lowLatency();
                    break;
                case 2:
                    copperConnection();
                    break;
                case 3:
                    maxDataTransmit();
                    break;
                case 4:
                    avgSpanTree();
                    break;
                case 5:
                    vertFail();
                    break;
                case 0:
                    break;
                default:
                    // Invalid, just ignore and let loop again
                    break;
            }//end of switch statement
			
        }//end of while loop
		
		
	}//end of main method
	
	public static void lowLatency()
	{
		int selection = -1;
		int vert1 = 0, vert2 = 0, numEdges = 0, bandWidth = 0, totalBand = 0;
		double distance;
		Scanner scan = new Scanner(System.in);
		System.out.println();
		while(selection != 0)
		{
		System.out.print("Enter the first vertice (0-" + (graph.V()-1) + "): ");
		try{
		vert1 = scan.nextInt();
		selection = 0;
		if(vert1 >= graph.V() || vert1 < 0)
		{
			selection = -1;
			System.out.println("Enter a valid vertice\n");
		}
		}catch(InputMismatchException e)
		{
			System.out.println("Enter a number\n");
			selection = -1;
		}
		scan.nextLine();
		}//end while
		DijkstraSP path = new DijkstraSP(graph, vert1); 
		selection = -1;
		while(selection == -1)
		{
		System.out.print("Enter the second vertice (0-" + (graph.V()-1) + "): ");
		try{
		vert2 = scan.nextInt();
		selection = 0;
		if(vert2 >= graph.V() || vert2 < 0)
		{
			selection = -1;
			System.out.println("Enter a valid vertice\n");
		}
		}catch(InputMismatchException e)
		{
			System.out.println("Enter a number\n");
			selection = -1;
		}
		scan.nextLine();
		}//end while
		distance = path.distTo(vert2);
		System.out.println("\nLatency from " + vert1 + " to " + vert2 + " (in meters per nanosecond): " + distance);
		System.out.println("Path from " + vert1 + " to " + vert2 + ":");
		for(DirectedEdge e: path.pathTo(vert2))
		{
			numEdges++;
			System.out.println(e + " ");
			if(numEdges == 1)
			{
				totalBand = e.bandWidth();
				bandWidth = e.bandWidth();
			}
			else
			{
				if(e.bandWidth() < bandWidth)
				{
					totalBand = e.bandWidth();
					bandWidth = e.bandWidth();
				}
				else
				{
					//totalBand += bandWidth;
				}
			}
		}
		System.out.println("\nBandwidth available: " + totalBand);
	}//end lowLatency method
	
	public static void copperConnection()
	{
		boolean connected = true;
		for(int i = 0; i < copperConnect.length; i++)
		{
			if(copperConnect[i] == null || copperConnect[i].connectedToGraph() == false)
				connected = false;
		}
		if(connected)
			System.out.println("\nThe graph is copper connected.");
		else
			System.out.println("\nThe graph is not copper connected.");
			
	}//end copperConnection method
	
	
	public static void maxDataTransmit()
	{
		Scanner scan = new Scanner(System.in);
		int selection = -1, vert1 = 0, vert2 = 0, numEdges = 0, bandWidth = 0;
		while(selection == -1)
		{
		System.out.print("Enter the first vertice (0-" + (graph.V()-1) + "): ");
		try{
		vert1 = scan.nextInt();
		selection = 0;
		if(vert1 >= graph.V() || vert1 < 0)
		{
			selection = -1;
			System.out.println("Enter a valid vertice\n");
		}
		}catch(InputMismatchException e)
		{
			System.out.println("Enter a number\n");
			selection = -1; 
		}
		scan.nextLine();
		}//end while
		selection = -1; 
		while(selection == -1)   
		{
		System.out.print("Enter the second vertice:  (0-" + (graph.V()-1) + "): ");
		try{
		vert2 = scan.nextInt();
		selection = 0;
		if(vert2 >= graph.V() || vert2 < 0)
		{
			selection = -1;
			System.out.println("Enter a valid vertice\n");
		}
		}catch(InputMismatchException e)
		{
			System.out.println("Enter a number\n");    
			selection = -1; 
		}
		scan.nextLine();
		}//end while
		FlowNetwork flow = new FlowNetwork(graph.V());
		for(DirectedEdge e: graph.edges())
		{
			FlowEdge newEdge = new FlowEdge(e.from(), e.to(), e.bandWidth());
			flow.addEdge(newEdge);
		}
			
		FordFulkerson fork = new FordFulkerson(flow, vert1, vert2); 
		Double maxFlow = fork.value();
		System.out.println("\nMax flow(data) from " + vert1 + " to " + vert2 + ":");
		System.out.println(maxFlow);
	}//end maxDataTransmit method 
	
	public static void avgSpanTree() 
	{
		KruskalMST spanTree = new KruskalMST(graph);
		double weight = spanTree.weight()/spanTree.numEdges();
		System.out.println("\nAverage latency per edge (in meters per nanosecond): " + weight);
		System.out.println("\nMinimum Average Latency Spanning Tree:");
		for(DirectedEdge e: spanTree.edges())
		{
			System.out.println(e);
		}   
	}//end avgSpanTree method 
	
	public static void vertFail()
	{
		int [][] points = graph.articulationPoints();
		//if(points != null)
		int i = 0;
		int j = 0;
		if(points[i][j] != -1)
		{
			System.out.println("\nIf any of these pairs of vertices fail, the graph will be disconnected: ");
			while(points[i][j] != -1 && i < 10)
			{
					//System.out.println("\nIf these two vertices fail, the graph will be disconnected: ");
			System.out.println(points[i][0] + ", " + points[i][1]);
			i++; 
			}
		}			
		else
		{
			System.out.println("\nThere are no pairs of vertices that will cause the graph to become unconnected if they fail.");  
		}
		
			
	}//end vertFail method
	
	public static void readFile(String filename)
	throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(filename));
		boolean first = true; 
		boolean firstCopper = true;
		while(scan.hasNext())
		{
			if(first)
			{
				String str = scan.nextLine();
				graph = new EdgeWeightedDigraph(Integer.parseInt(str));
				copperConnect = new Node[Integer.parseInt(str)];
				first = false;
			}
			String str = scan.nextLine();
			String[] tokens = str.split(" ");
			
			int endPoint1 = Integer.parseInt(tokens[0]);
			int endPoint2 = Integer.parseInt(tokens[1]);
			String cable = tokens[2];
			int bandWidth = Integer.parseInt(tokens[3]);
			int length = Integer.parseInt(tokens[4]);
			if(firstCopper)
			{
				if(cable.equalsIgnoreCase("copper"))
				{
					copperConnect[endPoint1] = new Node(true, true);
					copperConnect[endPoint2] = new Node(true, true);
					firstCopper = false;
				}
			}
			else if(cable.equalsIgnoreCase("copper"))
			{
				if(copperConnect[endPoint1] != null && copperConnect[endPoint1].connectedToGraph() == true)
				{
					if(copperConnect[endPoint2] == null)
						copperConnect[endPoint2] = new Node(true, true);
					else if(copperConnect[endPoint2].connectedToGraph() == false)
						copperConnect[endPoint2].setConnectedToGraph(true);
				}
				else if(copperConnect[endPoint2] != null && copperConnect[endPoint2].connectedToGraph() == true)
				{
					if(copperConnect[endPoint1] == null)
						copperConnect[endPoint1] = new Node(true, true);
					else if(copperConnect[endPoint1].connectedToGraph() == false)
						copperConnect[endPoint1].setConnectedToGraph(true);
				}
				else
				{
					if(copperConnect[endPoint1] == null)
						copperConnect[endPoint1] = new Node(false, true);
					if(copperConnect[endPoint2] == null)
						copperConnect[endPoint2] = new Node(false, true);
				}


			}
			DirectedEdge newEdge = new DirectedEdge(endPoint1, endPoint2, cable, bandWidth, length);
			graph.addEdge(newEdge);
			newEdge = new DirectedEdge(endPoint2, endPoint1, cable, bandWidth, length);
			graph.addEdge(newEdge);
			//System.out.println(endPoint1 + " " + endPoint2 + " " + cable + " " + bandWidth + " " + length);
	}
	}
		
	private static class Node
	{
		private boolean connectedToGraph;
		private boolean hasCopperConnection;
		
		private Node(boolean con, boolean cop)
		{
			connectedToGraph = con;
			hasCopperConnection = cop;
		}
		
		private boolean connectedToGraph()
		{
			return connectedToGraph;
		}
		private boolean hasCopperConnection()
		{
			return hasCopperConnection;
		}
		private void setConnectedToGraph(boolean connect)
		{
			connectedToGraph = connect;
		}
	}
}