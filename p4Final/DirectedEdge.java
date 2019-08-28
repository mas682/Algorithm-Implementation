/**
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *	@author Matt Stropkey
 */

public class DirectedEdge implements Comparable<DirectedEdge> { 
    private final int v;
    private final int w;
   // private final double weight;
	private final String cable;
	private final int bandWidth;
	private final int length;
	private double latency;
	

    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
     * the given {@code weight}.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public DirectedEdge(int v, int w, String type, int band, int len) {
        if (v < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
       // if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
       // this.weight = weight;
	   cable = type;
	   bandWidth = band;
	   length = len;
	   	if(type.equalsIgnoreCase("copper"))
		{
			latency = length/230000000.0;
			latency = latency*1000000000.0;
			
		}
		else
		{
			latency = length/2000000000.0;
			latency = latency * 1000000000.0;
		}
		
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
  //  public double weight() {
   //     return weight;
    //}
	
	public String cable(){
		return cable; 
	}
	
	public int bandWidth(){
		return bandWidth;
	}
	public int length(){
		return length;
	}
	public double latency(){
		return latency;
	}
	

    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + "latency: " + String.format("%f", latency);
    }

	@Override
	public int compareTo(DirectedEdge that){
		return Double.compare(this.latency, that.latency);
	}

}

