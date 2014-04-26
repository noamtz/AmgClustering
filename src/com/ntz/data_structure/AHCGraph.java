package com.ntz.data_structure;

import com.ntz.utils.Diagnostic;
import com.ntz.utils.Diagnostic.LogLevel;

/**
 * AHCGraph is a class that represent an AMG Hierarchical Clustering Graph
 *  
 * @author Noam Tzumie
 *
 */
public class AHCGraph {
	
	private SparseMatrix S;
	
	public AHCGraph(double[][] A) {
		performConvertion(A,false);
	}
	
	public AHCGraph(int size){
		S = new SparseMatrix(size, size, true);
	}
	
	public void addEdge(int i, int j, double value){
		S.put(i, j, value);
	}
	
	public double getEdge(int i, int j){
		return S.get(i, j);
	}
	
	public int size(){
		return S.size();
	}
	
	public AHCGraph(double[][] A, boolean isMmatrix) {
		performConvertion(A,isMmatrix);
	}
	
	private void performConvertion(double[][] A, boolean isMmatrix){
		double[][] M = isMmatrix ? A : toMmatrix(A);
		toSparse(M);
	}
	
	private double[][] toMmatrix(double[][] A){
		double[][] M = new double[A.length][A[0].length];
		for(int i=0; i<A.length; i++){
			double count = 0;
			for(int j=0; j<A[0].length; j++){
				if(i!=j){
					count += A[i][j];
					M[i][j] = -A[i][j];
				}
			}
			M[i][i] = count;
		}
		return M;
	}
	
	private void toSparse(double[][] M){
		S = new SparseMatrix(M.length, M[0].length,true);
		for(int i=0; i<M.length; i++)
			for(int j=0; j<M.length; j++)
				if(M[i][j] != 0)
					S.put(i, j, M[i][j]);
	}
	
	public void toMmatrix(){
		for(int i=0; i<S.size(); i++){
			SparseVector v = S.getRow(i);
			double counter = 0;
			for(int j=0; j<v.size(); j++){
				double weight= -Math.abs(v.get(j));
				S.put(i, j, weight);
				if(i!=j && weight != 0){
					if(weight != S.get(j, i)){
						weight = weight/2;
						S.put(j, i, weight/2);
						S.put(i, j, weight/2);
					}
					counter += weight;
				}
			}

			S.put(i, i, Math.abs(counter));
		}
		if(!isSymmetric()){
			Diagnostic.log("Matrix is not Symmetric!", LogLevel.ERROR);
			System.exit(1);
		}
	}
	
	public boolean isSymmetric(){
		for(int i=0; i<S.size(); i++){
			for(int j=0; j<S.size(); j++){
				if(S.get(i, j) != S.get(j, i))
					return false;
			}
		}
		return true;
	}  
	
	public SparseMatrix getMatrix(){
		if(S == null) throw new RuntimeException("Adjancy matrix is not exist");
		return S;
	}

}
