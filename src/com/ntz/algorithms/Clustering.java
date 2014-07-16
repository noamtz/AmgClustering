package com.ntz.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sun.security.krb5.internal.crypto.CksumType;

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


	public String perform(){
		Diagnostic.startClusterWatch();
		int[][] matrix = matrix();

		for(int i=0; i<matrix[0].length+1; i++)
			System.out.print("c"+(i+1) + ((i<matrix[0].length)?",":""));
		System.out.println();
		for(int i=0; i<matrix.length; i++){
			System.out.print((i+1)+",");
			for(int j=0; j<matrix[0].length; j++)
			{
				System.out.print((matrix[i][j]+1) + ((j<matrix[0].length-1)?",":""));
			}
			System.out.println();
		}
			
//		csv();		
		Diagnostic.endClusterWatch();
		return "";
	}

	public int[][] matrix(){
		int size = hierarchyGrids.numOfRelevantGrids();
		int numOfNodes = hierarchyGrids.getFinestGrid().nodes.length;
		
		int[][] matrix = new int[numOfNodes][size];
		
		
		Grid finest = hierarchyGrids.getGrid(0);
		
		int[] cNodes = new int[finest.numOfC()];
		
		for(GridNode node : finest.nodes){
			if(node.type == NodeType.C){
				cNodes[node.order] = node.id;
			}
		}
		System.out.println();
		
		int[] before=null;
		
		for(int i=0; i<size ; i++){
			Grid grid = hierarchyGrids.getGrid(i);

			
			if(i>0){
				int[] temp = new int[grid.numOfC()];
				
				for(GridNode node : grid.nodes){
					if(node.type == NodeType.C){
						temp[node.order] = cNodes[node.id];
					}
				}
				System.out.println();
				before = cNodes;
				cNodes = temp;
			}
			
			
			for(GridNode node : grid.nodes){

				int clusterId = -1;
				
				if(node.type == NodeType.F){
					double max = 0;
					int R = -1;
					for(GridNode c : node.Ci.values()){
						if(node.Dependence.get(c.id) > max){
							R = cNodes[c.order];
							max = node.Dependence.get(c.id);
						}	
					}
					
					clusterId = R >= 0 ? R : -1;
					
				} else {

					clusterId = i==0 ? node.id : cNodes[node.order];
				}
//				System.out.println(clusterId);
				if(clusterId < 0) throw new RuntimeException("Node with Id:" + (node.id) + " without Coarse interpolary set - solution: tune threshhold");
				
//				System.out.println(String.format("Matrix[%d][%d]=%d", node.id,i,clusterId));
				
				if(i>0) {
					for(int j=0; j<matrix.length; j++) {
						if(matrix[j][i-1] == before[node.id]){
							matrix[j][i] = clusterId;
//							System.out.println(String.format("matrix[%d][%d]=%d", j,i,clusterId));
						} else {
//							matrix[j][i] = before[node.id];
						}
					}
				} else {
					matrix[node.id][i] = clusterId;
//					System.out.println(String.format("Matrix[%d][%d]=%d", node.id,i,clusterId));
				}
			}
		}
		
		return matrix;
	}
	
	public void csv(){

		Grid g1 = hierarchyGrids.getGrid(1);
		GridNode[] n = g1.nodes;


		Map<Integer, Integer> ClustersFine = new HashMap<Integer, Integer>();
		System.out.println();
		System.out.println("F-POINTS:");
		for(GridNode f : n){
			if(f.type == NodeType.F){
				System.out.print(f.id+1 + " ");
			}
		}
		System.out.println();
		System.out.println("C-POINTS:");
		for(GridNode c : n){
			if(c.type == NodeType.C){
//				System.out.print((c.id+1)+"    ");
				System.out.println("[ID:" + (c.id+1) + " , ORDER:" + (c.order+1) + "]  ");
			}
		}
		System.out.println();
		System.out.println();
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
				if(R+1 != 0) {
					System.out.println((f.id+1) + "," + (R+1));//+ "," + 2
					ClustersFine.put(f.id+1, R);
				} else {
					System.err.println();
					System.err.println("ERROR: Node ID: " + f.id+1 + ", not have nodes in his Ci");
				}

			} 
		}
//		Map<Integer, String> ClustersCoarse = new HashMap<Integer, String>();
//		for(GridNode c : n){
//			if(c.type == NodeType.C){
//				if(c.S.values().size() == 0){
//					ClustersCoarse.put(c.id, "R" + (c.id+1));
//					ClustersFine.put(c.id+1, c.id);
//				} else {
//					boolean isExist = false;
//					for(GridNode cn : c.S.values()){
//						if(cn.type == NodeType.C){
//							if(ClustersCoarse.get(cn.id) != null){
//								ClustersCoarse.put(c.id, ClustersCoarse.get(cn.id));
//								ClustersFine.put(c.id+1, cn.id);
//								isExist = true;
//							}
//
//						}
//					}
//					if(!isExist){
//						ClustersCoarse.put(c.id, "R"+(c.id+1));
//						ClustersFine.put(c.id+1, c.id);
//					}
//				}
//			}
//		}
//		//		System.out.println();
//		//		System.out.println("Final Clustering: ");
//		Map<Integer, String> FinalClustering = new HashMap<Integer, String>();
//		for (Map.Entry<Integer, Integer> entry : ClustersFine.entrySet())
//		{
//			FinalClustering.put(entry.getKey()-1, ClustersCoarse.get(entry.getValue()));
//			//System.out.println(entry.getKey() + ":" + ClustersCoarse.get(entry.getValue()));
//		}
//
//
//		

		//		return print(FinalClustering, n);

	}
	
	public String print(Map<Integer, String> FinalClustering, GridNode[] n){
		ArrayList<String> Clusters = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(String r : FinalClustering.values()){
			if(Clusters.contains(r)){
				continue;
			}

			Clusters.add(r);
			//			System.out.println();
			//			System.out.println("Cluster " + r + ":");
			sb.append("\n");
			sb.append("Cluster " + r + ":");
			sb.append("\n");
			for(GridNode n1 : n){
				if(FinalClustering.get(n1.id) != null && FinalClustering.get(n1.id).contentEquals(r)){
					//					System.out.print((n1.id+1) + " ");
					sb.append((n1.id+1) + " ");
					sb.append("\n");
				}
			}

		}
		return sb.toString();
	}

	public void clusterGrid(Grid grid){

	}
}


