package com.ntz.amg;


import java.util.HashMap;
import java.util.Map;

import com.ntz.amg.GridNode.NodeType;
import com.ntz.data_structure.SparseMatrix;
import com.ntz.data_structure.SparseVector;


public class Grid {

	public GridNode nodes[];
	public SparseMatrix A;
	public SparseVector v, f, residual;
	
	public SparseMatrix Interpolation, Restriction, A2h;
	
	public Map<Integer, Integer> nodesIdentity;
	
	public Grid(SparseMatrix A){
		nodes = new GridNode[A.size()];
		this.A = A;
		for(int i=0;i<A.size(); i++){
			nodes[i] = new GridNode(i, A.get(i, i));
		}
		nodesIdentity = new HashMap<Integer, Integer>();
	}
	
	public boolean hasOnlyC(){
		for(GridNode n : nodes)
			if(n.type == NodeType.F || n.type == NodeType.UNASSIGN)
				return false;
		return true;
	}
	
	public int numOfC(){
		int res = 0;
		for(GridNode g : nodes){
			if(g.type == NodeType.C) res++;
		}
		return res;
	}
	
	public SparseVector restrict(SparseVector v){
		return Restriction.times(v);
	}
	
	public SparseVector interpolate(SparseVector v){
		return Interpolation.times(v);
	}
	
	public void print(){
		for(int i=0; i<nodes.length; i++){
				GridNode gp = nodes[i];
				System.out.print((gp.id+1)+gp.type.toString()+ " ");
			if((i+1) % Math.sqrt(nodes.length)==0)
				System.out.println();
		}
		System.out.println();
	}
	
	public double getNorm(){
		return v.norm();
	}
	
	public void plot(){
		//Utils.plot(v, "Grid");
	}
}
