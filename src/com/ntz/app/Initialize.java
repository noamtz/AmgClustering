package com.ntz.app;

import com.ntz.amg.Grid;
import com.ntz.data_structure.AHCGraph;
import com.ntz.data_structure.HierarchyGrids;
import com.ntz.data_structure.SparseMatrix;
import com.ntz.data_structure.SparseVector;
import com.ntz.utils.Diagnostic;

public class Initialize {
	
	private AHCGraph ahcGraph;
	
	public Initialize(AHCGraph ahcGraph) {
		this.ahcGraph = ahcGraph;
	}

	public void perform() {
		
		HierarchyGrids hierarchyGrids = HierarchyGrids.getInstance();
	
		SparseMatrix S = ahcGraph.getMatrix();
		Grid grid = new Grid(S);

		int N = S.size();
		
		grid.v = new SparseVector(N);
		grid.f = new SparseVector(N);

		for(int i=1; i<=N; i++){
			grid.v.put(i-1, (Math.sin(i*32*Math.PI/N) + Math.sin(i*6*Math.PI/N) + Math.sin(i*Math.PI/N)) * 1.0/3);
		}

		hierarchyGrids.addGrid(grid);
		
		Diagnostic.numOfNodes = N;
		Diagnostic.beforeNorm = grid.getNorm();
	}

}
