package com.ntz.algorithms;

import java.util.Iterator;

import com.ntz.amg.Grid;
import com.ntz.utils.Diagnostic;

public class Relaxation {
	
	public void relax(Grid grid ,int swipes){
		for(int swipe=0; swipe<swipes; swipe++){
			for(int i=0; i<grid.A.size(); i++){
				Iterator<Integer> itr = grid.A.getRow(i).Iterator();
				double sum = 0;
				while(itr.hasNext()){
					int j = itr.next();
					if(i != j)
						sum += grid.v.get(j) * grid.A.get(i, j);
				}
				grid.v.put(i, (grid.f.get(i) - sum) / grid.A.get(i, i));
			}
		}
	}
	
}
