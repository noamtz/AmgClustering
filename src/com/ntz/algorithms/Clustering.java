package com.ntz.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ntz.amg.Grid;
import com.ntz.amg.GridNode;
import com.ntz.amg.GridNode.NodeType;
import com.ntz.data_structure.HierarchyGrids;
import com.ntz.utils.Diagnostic;

/**
 * This class take the results of the AMG and produce clusters
 * from two grids (this done only for the 2 first grids for now). 
 * @author Noam Tzumie
 *
 */
public class Clustering {

	HierarchyGrids hierarchyGrids;
	public Clustering(){
		this.hierarchyGrids= HierarchyGrids.getInstance();
	}
	
	
	public void perform(){
		Diagnostic.startClusterWatch();
		Grid g1 = hierarchyGrids.getGrid(0);
		GridNode[] n = g1.nodes;

		Map<Integer, Integer> ClustersFine = new HashMap<Integer, Integer>();
//		System.out.println();
//		System.out.println("F-POINTS:");
//		for(GridNode f : n){
//			if(f.type == NodeType.F){
//				System.out.print(f.id+1 + " ");
//			}
//		}
//		System.out.println();
//		System.out.println("C-POINTS:");
//		for(GridNode c : n){
//			if(c.type == NodeType.C){
//				System.out.print(c.id+1 + " ");
//			}
//		}
//		System.out.println();
		//Map between
		for(GridNode f : n){
			if(f.type == NodeType.F){
				double max = 0;
				int R = -1;
				for(GridNode c : f.Ci.values()){
					if(f.Dependence.get(c.id) > max){
						R = c.id;
						max = f.Dependence.get(c.id);
					}	
				}
				ClustersFine.put(f.id+1, R);
			} 
		}
		Map<Integer, String> ClustersCoarse = new HashMap<Integer, String>();
		for(GridNode c : n){
			if(c.type == NodeType.C){
				if(c.S.values().size() == 0){
					ClustersCoarse.put(c.id, "R" + (c.id+1));
					ClustersFine.put(c.id+1, c.id);
				} else {
					boolean isExist = false;
					for(GridNode cn : c.S.values()){
						if(cn.type == NodeType.C){
							if(ClustersCoarse.get(cn.id) != null){
								ClustersCoarse.put(c.id, ClustersCoarse.get(cn.id));
								ClustersFine.put(c.id+1, cn.id);
								isExist = true;
							}
							
						}
					}
					if(!isExist){
						ClustersCoarse.put(c.id, "R"+(c.id+1));
						ClustersFine.put(c.id+1, c.id);
					}
				}
			}
		}
//		System.out.println();
//		System.out.println("Final Clustering: ");
		Map<Integer, String> FinalClustering = new HashMap<Integer, String>();
		for (Map.Entry<Integer, Integer> entry : ClustersFine.entrySet())
		{
			FinalClustering.put(entry.getKey()-1, ClustersCoarse.get(entry.getValue()));
		    //System.out.println(entry.getKey() + ":" + ClustersCoarse.get(entry.getValue()));
		}
		
		print(FinalClustering, n);
		
		Diagnostic.endClusterWatch();
		
	}

	public void print(Map<Integer, String> FinalClustering, GridNode[] n){
		ArrayList<String> Clusters = new ArrayList<String>();
		for(String r : FinalClustering.values()){
			if(Clusters.contains(r))
				continue;
			Clusters.add(r);
			System.out.println();
			System.out.println("Cluster " + r + ":");
			for(GridNode n1 : n)
				if(FinalClustering.get(n1.id) != null && FinalClustering.get(n1.id).contentEquals(r)){
					System.out.print((n1.id+1) + " ");
				}
		}
	}
	
	public void clusterGrid(Grid grid){
		
	}
}
