package com.ntz.data_structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/*************************************************************************
 *  Compilation:  javac SparseVector2.java
 *  Execution:    java SparseVector2
 *  
 *  A sparse vector, implementing using a symbol table.
 *
 *  [Not clear we need the instance variable N except for error checking.]
 *
 *************************************************************************/

public class SparseVector2 {
//    private final int N;             // length
//    private Map<Integer, Double> st;  // the vector, represented by index-value pairs
//
//    // initialize the all 0s vector of length N
//    public SparseVector2(int N) {
//        this.N  = N;
//        this.st = new HashMap<Integer, Double>();
//    }
//
//    // put st[i] = value
//    public void put(int i, double value) {
//        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
//        if (value == 0.0) st.remove(i);
//        else              st.put(i, value);
//    }
//
//    // return st[i]
//    public double get(int i) {
//        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
//        if (st.get(i)!=null) return st.get(i);
//        else                return 0.0;
//    }
//    
//    public Iterator<Integer> Iterator(){
//    	return st.keySet().iterator();
//    }
//
//    // return the number of nonzero entries
//    public int nnz() {
//        return st.size();
//    }
//
//    // return the size of the vector
//    public int size() {
//        return N;
//    }
//
//    // return the dot product of this vector a with b
//    public double dot(SparseVector2 b) {
//        SparseVector2 a = this;
//        if (a.N != b.N) throw new RuntimeException("Vector lengths disagree");
//        double sum = 0.0;
//        
//        // iterate over the vector with the fewest nonzeros
//        Iterator  it = a.st.keySet().iterator();
////        if (a.st.size() <= b.st.size()) {
////            for (int i : a.st)
////                if (b.st.contains(i)) sum += a.get(i) * b.get(i);
////        }
////        else  {
////            for (int i : b.st)
////                if (a.st.contains(i)) sum += a.get(i) * b.get(i);
////        }
//        return sum;
//    }
//
//    // return the 2-norm
//    public double norm2() {
//        SparseVector2 a = this;
//        return Math.sqrt(a.dot(a));
//    }
//    //return inf-norm
//	public double norm(){
//		SparseVector2 vector = this;
//		double max = 0;
//		Iterator<Integer> itr = vector.Iterator();
//		while(itr.hasNext()){
//			int i = itr.next();
//			if(max < Math.abs(vector.get(i)))
//				max = Math.abs(vector.get(i));
//		}
//		return max;
//	}
//
//    // return alpha * a
//    public SparseVector2 scale(double alpha) {
//        SparseVector2 a = this;
//        SparseVector2 c = new SparseVector2(N);
//        for (int i : a.st) c.put(i, alpha * a.get(i));
//        return c;
//    }
//
//    // return a + b
//    public SparseVector2 plus(SparseVector2 b) {
//        SparseVector2 a = this;
//        if (a.N != b.N) throw new RuntimeException("Vector lengths disagree");
//        SparseVector2 c = new SparseVector2(N);
//        for (int i : a.st) c.put(i, a.get(i));                // c = a
//        for (int i : b.st) c.put(i, b.get(i) + c.get(i));     // c = c + b
//        return c;
//    }
//    
//    public SparseVector2 opposite(){
//    	 SparseVector2 a = this;
//    	 SparseVector2 c = new SparseVector2(N);
//    	 for (int i : a.st)
//    		 c.put(i, -1*a.get(i));
//    	 return c;
//    }
//    
//    // return a - b
//    public SparseVector2 minus(SparseVector2 b) {
//        SparseVector2 a = this;
//        return a.plus(b.opposite());
//    }
//
//    // return a string representation
//    public String toString() {
//        String s = "";
//        for (int i : st) {
//            s += "(" + i + ", " + st.get(i) + ") ";
//        }
//        return s;
//    }
//
//    public double[] toArray(){
//    	double[] res = new double[N]; 
//    	for(int i : st)
//    		res[i] = st.get(i);
//    	return res;
//    }
//
//    // test client
//    public static void main(String[] args) {
//        SparseVector2 a = new SparseVector2(10);
//        SparseVector2 b = new SparseVector2(10);
//        a.put(3, 0.50);
//        a.put(9, 0.75);
//        a.put(6, 0.11);
//        a.put(6, 0.00);
//        b.put(3, 0.60);
//        b.put(4, 0.90);
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        //System.out.println("a dot b = " + a.dot(b));
//        System.out.println("a - b   = " + a.minus(b));
//    }

}
