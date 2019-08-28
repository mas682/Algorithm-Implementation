//Matt Stropkey

import java.util.NoSuchElementException;

public class CarMinPQ {
    private int maxN;        // maximum number of elements on PQ
    private int n;           // number of elements on PQ
    private Car[] cars;      // Cars[i] = priority of car
	private char type;

    /**
     * Initializes an empty indexed priority queue with indices between {@code 0}
     * and {@code maxN - 1}.
     */
    public CarMinPQ(char type) {
		maxN = 251; 	//starting size, shouldn't need something too large?
        n = 0;
        cars = new Car[maxN + 1];    // make this of length maxN??
		this.type = type;			//used to determine if queue for miles or price
		
    }

    /**
     * Returns true if this priority queue is empty.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }
	
	//returns the car from its position in the miles pq
	public Car getCar(int pos)
	{
		if(type == 'm')
			return cars[pos];
		else if(type == 'p')
			return cars[pos];
		else if(type == 'M')
			return cars[pos];
		else if(type == 'P')
			return cars[pos];
		return null;
	}

    /**
     * Associates key with index {@code i}.
     */
    public void insert(Car key) {
		//will need to make sure car not already in queue somehow by checking queue in radix trie
		
		if(key == null)
			return;
        n++;
		if(n >= maxN)
		{
			//resize the heap
		}
        cars[n] = key;//insert car
        swim(n);
		//if(type == 'M')
		//{ 
		//for(int m = 1; m < n+1; m++)
		//{
		//	System.out.println( m + ". " + cars[m].getVIN());
		//}
		//for(int o = 1; o < n+1; o++)
		//{
		//	System.out.println(cars[o].getVIN() + "has the index of " + cars[o].getMakeModMilesIndex());
		//}
		//}
    } 
	//returns the car at the root
    public Car minKey() {
        if (n == 0) throw new NoSuchElementException("No cars in system");
        return cars[1];
    }

    /**
     * Removes a minimum key and returns its associated index.
     */
    public int delMin() {
        if (n == 0) throw new NoSuchElementException("No cars in system");
		if(n == 1)
		{
			cars[1] = null;
			n--;
			cars = null;
			return 1;
		}
        int min = 1;
        exch(1, n--); 
        sink(1);
        assert min == n+1;
        cars[min] = null;    // to help with garbage collection 
        return min;
    } 


    /**
     * Change the keys position within the heap
     */
    public void changeKey(int i, Car key) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        if (cars[i] == null) throw new NoSuchElementException("index is not in the priority queue");
        cars[i] = key;
        swim(i);
        sink(i);
    }

    /**
     * Remove the key associated with index {@code i}.
     *
     * @param  i the index of the key to remove
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no key is associated with index {@code i}
     */
    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        if (cars[i] == null) throw new NoSuchElementException("Car is not in the system");
        int index = i;
		//if(type == 'P');
		//System.out.println("The car index is at: " i);
		//System.out.println("Value of n: " + n);
		//System.out.println("Car index: " + index);
		if(n == 1)
		{
			cars[n] = null;
			n--;
			cars = null;
			return;
		}
		if(n == 2)
		{
			if(i == 1)
			{
				cars[1] = null;
				cars[1] = cars[2];
				n--;
				return;
			}
			else
				cars[2] = null;
				n--;
				return;
		}
			
        exch(index, n--);
		//System.out.println("Value of n after: " + n);
        swim(index);
        sink(index);
        cars[n] = null;
		n--;//decrement n...may be doing at line 91?
    }


   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean greater(int i, int j) {
		if(type == 'm')
		{	
			return cars[i].getMiles() > cars[j].getMiles();//compare the milage
		}
		else if(type == 'p')
		{
			return cars[i].getPrice() > cars[j].getPrice();//compare the price
		}
		else if(type == 'M')
		{
			return cars[i].getMiles() > cars[j].getMiles();
		}
		else if(type == 'P')
		{
			return cars[i].getPrice() > cars[j].getPrice();
		}
		return false;
    }

    private void exch(int i, int j) {
		Car swap = cars[i];
		Car temp = cars[j];
        cars[i] = temp;
        cars[j] = swap;
		if(type == 'm')
		{	
			//for(int m = 1; m < n+1; m++)
			//{
			//	System.out.println( m + ". " + cars[m].getVIN());
		//	}
			//System.out.println("Cars swapped");
			cars[i].setMilesIndex(i);
			cars[j].setMilesIndex(j);
		}
		else if(type == 'p')
		{
			//System.out.println("Cars swapped");
			cars[i].setPriceIndex(i);
			cars[j].setPriceIndex(j);
			
		}
		else if(type == 'M')
		{
			//System.out.println(type);
			//for(int m = 1; m < n+1; m++)
			//{
			//	System.out.println( m + ". " + cars[m].getVIN());
			//}
			cars[i].setMakeModMilesIndex(i);
			cars[j].setMakeModMilesIndex(j);
			//System.out.println(cars[i].getVIN() + " has the index of in exchange " + cars[i].getMakeModMilesIndex());
			//System.out.println(cars[j].getVIN() + " has the index of in exchange " + cars[j].getMakeModMilesIndex());
		}
		else if(type == 'P')
		{
			//System.out.println(type);
			for(int m = 1; m < n+1; m++)
			{
				//System.out.println( m + ". " + cars[m].getVIN());
			}
			cars[i].setMakeModPriceIndex(i);
			cars[j].setMakeModPriceIndex(j);
		}
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private void swim(int k) {
		//System.out.println("Swim called");
		if(k == 1)
		{
			if(type == 'm')
			{
				cars[k].setMilesIndex(k);
				return;
			}
			else if(type == 'p')
			{
				cars[k].setPriceIndex(k);
				return;
			}
			else if(type == 'M')
			{
				//System.out.println("Inside of else if");
				cars[k].setMakeModMilesIndex(k);
				//System.out.println(cars[k].getVIN() + " has the index of in swim " + k);
				return;
			}
			else if(type == 'P')
			{
				cars[k].setMakeModPriceIndex(k);
				return;
			}
		}
		boolean move = false;
		if(k > 1)
			move = greater(k/2, k);
		if(k > 1 && !move)
		{
			//System.out.println("Inside of if");
			if(type == 'm')
			{
				cars[k].setMilesIndex(k);
			}
			else if(type == 'p')
			{
				cars[k].setPriceIndex(k);
			}
			else if(type == 'M')
			{
				
				cars[k].setMakeModMilesIndex(k);
			//	System.out.println(cars[k].getVIN() + " has the index of in swim " + k);
			}
			else if(type == 'P')
			{
				cars[k].setMakeModPriceIndex(k);
				//System.out.println(cars[k].getVIN() + " has the index of " + k);
			}
		}
        while (k > 1 && greater(k/2, k)) {
			//System.out.println("Calling exchange");
            exch(k, k/2);
            k = k/2;
			if(k > 1)
				move = greater(k/2, k);
        }
    }

    private void sink(int k) {
		if(2*k > n)
		{
			if(type == 'm')
			{
				cars[k].setMilesIndex(k);
			}
			else if(type == 'p')
			{
				cars[k].setPriceIndex(k);
			}
			else if(type == 'M')
			{
				cars[k].setMakeModMilesIndex(k);
			//	System.out.println(cars[k].getVIN() + " has the index of in sink " + k);
			}
			else if(type == 'P')
			{
				cars[k].setMakeModPriceIndex(k);
			}
		}
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j))
			{
				if(type == 'm')
				{
					cars[k].setMilesIndex(k);
				}
				else if(type == 'p')
				{
					cars[k].setPriceIndex(k);
				}
				if(type == 'M')
				{
					cars[k].setMakeModMilesIndex(k);
				//	System.out.println(cars[k].getVIN() + " has the index of in sink " + k);
				}
				else if(type == 'P')
				{
					cars[k].setMakeModPriceIndex(k);
				}
				break;
			}
            exch(k, j);
            k = j;
        }
    }

	//may want to list out cars in heap?
	
   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the keys on the
     * priority queue in ascending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
	*/

}

