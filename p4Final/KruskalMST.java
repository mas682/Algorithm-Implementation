/**
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Matt Stropkey
 */
public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;          	// weight of MST
	private int numEdges;
    private Queue<DirectedEdge> mst = new Queue<DirectedEdge>();  // edges in MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public KruskalMST(EdgeWeightedDigraph G) {
        // more efficient to build heap by passing array of edges
        MinPQ<DirectedEdge> pq = new MinPQ<DirectedEdge>();
		numEdges = 0;
        for (DirectedEdge e : G.edges()) {
            pq.insert(e); 
        }

        // run greedy algorithm
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            DirectedEdge e = pq.delMin();
            int v = e.from();
            int w = e.to();
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                mst.enqueue(e);  // add edge e to mst
                weight += e.latency();
				numEdges++;
            }
        }

        // check optimality conditions
        assert check(G);
    }
	
	public int numEdges()
	{
		return numEdges;
	}

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<DirectedEdge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }
    
    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedDigraph G) {

        // check total weight
        double total = 0.0;
        for (DirectedEdge e : edges()) {
            total += e.latency();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (DirectedEdge e : edges()) {
            int v = e.from(), w = e.to();
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (DirectedEdge e : G.edges()) {
            int v = e.from(), w = e.to(); 
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (DirectedEdge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (DirectedEdge f : mst) {
                int x = f.from(), y = f.to();
                if (f != e) uf.union(x, y);
            }
            
            // check that e is min weight edge in crossing cut
            for (DirectedEdge f : G.edges()) {
                int x = f.from(), y = f.to();
                if (!uf.connected(x, y)) {
                    if (f.latency() < e.latency()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }




}
