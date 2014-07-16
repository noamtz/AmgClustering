package com.ntz.data_structure;

import java.util.ArrayList;

import com.ntz.amg.Grid;

public class HierarchyGrids {

	private ArrayList<Grid> grids;
	
	private static HierarchyGrids hierarchyGrids;
	
	private HierarchyGrids(){
		grids = new ArrayList<Grid>();
	}
	
	public static HierarchyGrids getInstance(){
		if(hierarchyGrids == null)
			hierarchyGrids = new HierarchyGrids();
		return hierarchyGrids;
	}
	
	public void addGrid(Grid grid){
		grids.add(grid);
	}
	
	public Grid getFinestGrid(){
		return grids.get(0);
	}
	
	public Grid getCoarsest(){
		return grids.get(grids.size()-2);
	}
	
	public Grid getGrid(int level){
		if(grids.size() == 0) throw new RuntimeException("Hierarchy is empty");
		if(level >= grids.size() || level < 0) throw new RuntimeException("Illegal Grid level");
		return grids.get(level);
	}
	
	public int numOfGrids(){
		return grids.size();
	}
	
	public int numOfRelevantGrids(){
		int c = 0;
		for(Grid g:grids){
			if(g.nodes.length > 1) c++;
		}
		return c;
	}
	
	public boolean assembleHierarchy(){
		return false;
	}
	
	public int size(){
		return grids.size();
	}
	
	public void clear(){
		grids.clear();
	}
	
}
