package com.ntz.app;

import com.ntz.algorithms.Clustering;
import com.ntz.algorithms.Cycle;
import com.ntz.data_structure.AHCGraph;
import com.ntz.data_structure.HierarchyGrids;
import com.ntz.utils.Diagnostic;

public class Application {

	public static void main(String[] args){
		Diagnostic.startAppWatch();

		AHCGraph ahcGraph = AHCGraphGenerator.generateFromFile("resources/r-graph.stp");//generateFromImage("resources/img.txt");
		
		Initialize initializer = new Initialize(ahcGraph);
		
		initializer.perform();
		
		Cycle vCycle = new Cycle();
		vCycle.perform();

		Clustering clustering = new Clustering();
		clustering.perform();
		
		Diagnostic.endAppWatch();
		
		Diagnostic.print();
		
		//System.out.println(HierarchyGrids.getInstance().getFinestGrid().A);
	}
}
