package com.ntz.app;

import com.ntz.data_structure.AHCGraph;
import com.ntz.utils.Utils;

public class AHCGraphGenerator {

	public static AHCGraph generateLaplaciasize1D(int size){
		AHCGraph graph = new AHCGraph(size);
		double h = Math.pow(size, 2);
		graph.addEdge(0, 0, 2*h);
		graph.addEdge(0, 1, -1*h);
		graph.addEdge(size-1, size-1, 2*h);
		graph.addEdge(size-1, size-2, -1*h);
		for(int i=1;i<size-1; i++){
			graph.addEdge(i, i-1, -1*h);
			graph.addEdge(i, i, 2*h);
			graph.addEdge(i, i+1, -1*h);
		}
		return graph;
	}
	
	public static AHCGraph generateFromImage(String path){
		return Utils.imageToGraph(path);
	}
	
	public static AHCGraph generateFromFile(String path){
		AHCGraph graph = Utils.getGraphFromSTPFile(path);
		graph.toMmatrix();
		return graph;
	}
	
}
