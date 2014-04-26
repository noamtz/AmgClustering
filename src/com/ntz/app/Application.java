package com.ntz.app;

import com.ntz.algorithms.Clustering;
import com.ntz.algorithms.Cycle;
import com.ntz.data_structure.AHCGraph;
import com.ntz.data_structure.HierarchyGrids;
import com.ntz.utils.Diagnostic;

public class Application {

	public static void main(String[] args){
		Diagnostic.startAppWatch();
		
		//Convert general graph to AHCGraph
		AHCGraph ahcGraph = AHCGraphGenerator.generateFromFile("resources/r-graph.stp");//generateFromImage("resources/img.txt");
		
		//Initialize graph data for AMG
		Initialize initializer = new Initialize(ahcGraph);
		initializer.perform();
		
		//Perform AMG
		Cycle vCycle = new Cycle();
		vCycle.perform();
		
		//Perform clustering
		Clustering clustering = new Clustering();
		clustering.perform();
		
		Diagnostic.endAppWatch();
		
		Diagnostic.print();
		
		//System.out.println(HierarchyGrids.getInstance().getFinestGrid().A);
	}
}
