package com.ntz.app;

import com.ntz.algorithms.Clustering;
import com.ntz.algorithms.Cycle;
import com.ntz.data_structure.AHCGraph;
import com.ntz.utils.Diagnostic;

public class Application {

	public static void main(String[] args){
		Diagnostic.startAppWatch();
		//Convert general graph to AHCGraph
		String fileName = "resources/MI7";

		//Convert general graph to AHCGraph
//		AHCGraph ahcGraph = AHCGraphGenerator.generateFromCSV(fileName + ".csv");//.generateFromFile("resources/r-graph.stp");//generateFromImage("resources/img.txt");
		AHCGraph ahcGraph = AHCGraphGenerator.generateLaplaciasize1D(512);
//		System.out.println(ahcGraph.getMatrix());

		//Initialize graph data for AMG
		Initialize initializer = new Initialize(ahcGraph);
		initializer.perform();

		//Perform AMG
		Cycle vCycle = new Cycle();
		vCycle.perform();

		//Perform clustering
		Clustering clustering = new Clustering();
		clustering.perform();

		//************************************************//
		//**		For Clustering visualization use:   **//
		//**		http://app.raw.densitydesign.org/   **// 
		//************************************************//
		
		Diagnostic.endAppWatch();

		Diagnostic.print();

	}
}
