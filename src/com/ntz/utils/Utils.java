package com.ntz.utils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JFrame;


import com.ntz.data_structure.AHCGraph;
import com.ntz.data_structure.Edge;
import com.ntz.data_structure.EdgeWeightedGraph;
import com.ntz.data_structure.SparseMatrix;
import com.ntz.data_structure.SparseVector;

public class Utils {

	static boolean printTypedGrid = false;


	public static Properties getAppProperties() {
		Properties properties = new Properties();
		BufferedInputStream stream = null;
		try {
			stream = new BufferedInputStream(new FileInputStream("resources/app.properties"));
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return properties;
	}


	public static double norm(SparseVector vector){

		double max = 0;
		Iterator<Integer> itr = vector.Iterator();
		while(itr.hasNext()){
			int i = itr.next();
			if(max < Math.abs(vector.get(i)))
				max = Math.abs(vector.get(i));
		}
		return max;
	}

	public static double[] subtract(double[] a, double[] b){
		double[] res = new double[a.length];
		for(int i=0; i<res.length; i++)
			res[i] = a[i] - b[i];
		return res;
	}

	public static void add(double[] a, double[] b){
		for(int i=0; i<b.length; i++)
			a[i] += b[i];
	}

	public static double[] applyOperator(double[][] A, double[] v){
		double[] res = new double[v.length];
		for(int i=0; i<res.length; i++)
			for(int j=0; j<res.length; j++){
				//System.out.println(String.format("(%d,%d) : %f : %f", i, j, A[i-1][j-1],v[i]));
				res[i] += A[i][j] * v[j];
			}
		return res;
	}

	public static double[][] toMmatrix(double[][] A){
		double[][] M = new double[A.length][A[0].length];
		for(int i=0; i<M.length; i++){
			double count = 0;
			for(int j=0; j<M[0].length; j++){
				if(i!=j){
					count += A[i][j];
					M[i][j] = -A[i][j];
				}
			}
			M[i][i] = count;
		}
		return M;
	}
	//	public static void printMatrix(double[][] M){
	//		System.out.println();
	//		System.out.print("[");
	//		for(int i=0; i<M.length;i++){
	//			for(int j=0;j<M[0].length;j++){
	//				System.out.print((int)M[i][j] + " ");
	//			}
	//			System.out.println( (i<M.length-1) ?";" : "]");
	//			System.out.print(" ");
	//		}
	//		System.out.println();
	//	}

	public static void printMatrix(double[][] M){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("MATRIX.txt"));

			out.newLine();
			out.newLine();
			out.append("************************************************************************");
			out.append("			 PRINTING MATRIX			");
			out.append("************************************************************************");
			out.newLine();
			out.newLine();

			for(int i=0; i<M.length;i++){
				for(int j=0;j<M[0].length;j++){
					double target = 0;
					String s = (M[i][j] < 1) ? M[i][j] +" ": ((int)M[i][j]) + " " ;
					out.append(s);
				}
				out.append((i<M.length-1) ?";" : "]");
				out.newLine();
				out.append(" ");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printVector(double[] v){
		System.out.println();
		System.out.print("[");
		for(int i=0;i<v.length;i++){
			System.out.print(v[i] + " ");
		}
		System.out.print("]");
		System.out.println();
	}

	public static double[][] getGraphFromFile(String path){
		double A[][] = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = null;
			int rows = 0;
			while ((line = br.readLine()) != null) {
				String[] digits = line.trim().split(" ");
				if(A == null)
					A = new double[digits.length][digits.length];
				for(int j=0; j< A.length; j++)
					A[rows][j] = Double.parseDouble(digits[j]);
				rows++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return A;
	}

	public static AHCGraph getGraphFromSTPFile(String path){
		//double A[][] = null;
		AHCGraph graph = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = null;
			boolean end = false;
			while (!end && (line = br.readLine()) != null) {

				String[] split = line.trim().split(" ");
				if(split[0].contentEquals("SECTION") && split[1].contentEquals("Graph")){

					int numOfnodes = Integer.parseInt(br.readLine().trim().split(" ")[1]);
					graph =  new AHCGraph(numOfnodes);
						
					int numOfEdges = Integer.parseInt(br.readLine().trim().split(" ")[1]);
					while (!end && (line = br.readLine()) != null) {
						if(line.contains("END"))
							end = true;
						else{
							split = line.trim().split(" ");
							int src = Integer.parseInt(split[1]) - 1 ;
							int target = Integer.parseInt(split[2]) - 1;
							double weight = Double.parseDouble(split[3]);

							graph.addEdge(src, target, weight);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return graph;
	}

	public static void graphToMmatrix(double[][] G){
		for(int i=0; i<G.length; i++){
			int numOfN = 0;
			for(int k=0; k<G.length; k++)
				if(i != k)
					numOfN = (G[i][k] != 0) ? numOfN+1 : numOfN;

			for(int j=0; j<G.length; j++){
				if(i!=j){
					if(G[i][j] != 0)
						G[i][j] = -1*G[i][j];
				}
				else
					G[i][i] = numOfN;	
			}
		}

	}

	//	public static void main(String[] args){
	//		double[][] G = getGraphFromSTPFile("r-graph.stp");//getGraphFromFile("Clustering.txt");
	//		graphToMmatrix(G);
	//		printMatrix(G);
	//	}

	public static SparseMatrix toSparse(double[][] A){
		SparseMatrix M = new SparseMatrix(A.length, A[0].length,true);
		for(int i=0; i<A.length; i++)
			for(int j=0; j<A.length; j++)
				if(A[i][j] != 0)
					M.put(i, j, A[i][j]);
		return M;
	}

	public static void plot(SparseVector v , String name){
		ArrayList<Double> list = new ArrayList<Double>(); 
		double factor = (norm(v) < 0.01) ? 1000 : (norm(v) > 100) ? 1.0/1000 : 6;
		double[] varr = v.toArray();
		for(int i=0; i<varr.length;i++){
			list.add(varr[i]*factor);
		}
		DrawGraph mainPanel = new DrawGraph(list, norm(v));

		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static boolean hasZeroRows(SparseMatrix A){
		for(int i=0; i<A.dimensions()[0]; i++)
			if(A.getRow(i).nnz() == 0){
				System.err.println("HAS ZERO ROW AT: " + i);
				return true;
			}
		return false;
	}
	

public static AHCGraph getGraphFromCSVFile(String path){
		AHCGraph graph = null;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		double max = -1000;
		try {
	 
			br = new BufferedReader(new FileReader(path));
			int rowNum = 0;
			boolean isFirst = true;
			while ((line = br.readLine()) != null) {
	 
				String[] row = line.split(cvsSplitBy);
				if(isFirst){
					isFirst = false;
					graph =  new AHCGraph(row.length);
				}
				for(int i=0; i<row.length; i++){
					double weight = Double.parseDouble(row[i].trim());
					if(weight>max) max = weight;
					graph.addEdge(rowNum, i, weight != 0? weight :0);
				}
				rowNum++;
			}
	 
		} catch (FileNotFoundException e) {
			System.out.println(path);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("MAX VAL: " + max);
		return graph;
	}

public static AHCGraph getGraphFromImageCSVFile(String path){
	AHCGraph graph = null;
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	double max = -1000;
	try {
 
		br = new BufferedReader(new FileReader(path));
		int rowNum = 0;
		boolean isFirst = true;
		while ((line = br.readLine()) != null) {
 
			String[] row = line.split(cvsSplitBy);
			if(isFirst){
				isFirst = false;
				graph =  new AHCGraph(row.length);
			}
			for(int i=0; i<row.length; i++){
				double weight = Double.parseDouble(row[i].trim());
				if(weight>max) max = weight;
				graph.addEdge(rowNum, i, weight != 0? weight :0);
			}
			rowNum++;
		}
 
	} catch (FileNotFoundException e) {
		System.out.println(path);
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	System.out.println("MAX VAL: " + max);
	return graph;
}

public static EdgeWeightedGraph getGraph2FromCSVFile(String path){
	EdgeWeightedGraph graph = null;
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
 
	try {
 
		br = new BufferedReader(new FileReader(path));
		int rowNum = 0;
		boolean isFirst = true;
		while ((line = br.readLine()) != null) {
 
			String[] row = line.split(cvsSplitBy);
			if(isFirst){
				isFirst = false;
				graph =  new EdgeWeightedGraph(row.length);
			}
			for(int i=0; i<row.length; i++){
				double weight = Double.parseDouble(row[i].trim());
				graph.addEdge(new Edge(rowNum, i, 10-weight));
			}
			rowNum++;
		}
 
	} catch (FileNotFoundException e) {
		System.out.println(path);
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//	System.out.println(graph);
	return graph;
}

	//IMAGE GRAPH

	public static double[][] FromImage(String path){
		ArrayList<double[]> A = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				String split[] = line.split(",");

				double[] row = new double[split.length];
				for(int i=0; i<row.length; i++)
					row[i] = Double.parseDouble(split[i]);
				A.add(row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		double[][] I = null;
		if(A.size()>0){
			I = new double[A.size()][A.get(0).length];
			for(int i=0; i<A.size(); i++){
				I[i] = A.get(i);
			}
		}
		return I;
	}


	public static AHCGraph imageToGraph(String path){
		double[][] I = FromImage(path);
		int size = I.length*I[0].length;
		AHCGraph graph = new AHCGraph(size);

		for(int i=0; i< I.length; i++){
			for(int j=0; j< I[0].length; j++){

				int row = i*I[0].length +j;

				if(I[i][j] == 0){
					I[i][j] = 1;
				}

				projection(graph,I,row,i,j);//value at point
				
				projection(graph,I,row,i,j-1);//left
				projection(graph,I,row,i,j+1);//right
				projection(graph,I,row,i-1,j);//up				
				projection(graph,I,row,i+1,j);//down	

				projection(graph,I,row,i-1,j-1); //up-left
				projection(graph,I,row,i-1,j+1);//up-right			
				projection(graph,I,row,i+1,j-1); //down-left
				projection(graph,I,row,i+1,j+1);//down-right

			}
		}

		
//		for(int i=0;i<size;i++) {
//			for(int j=0;j<size;j++){
//				if(i!=j){
//					double val = graph.getEdge(i, j);
//					double valSymmetry = graph.getEdge(j, i);
//					if(val!=0 && valSymmetry==0){
//						graph.addEdge(i, j, val/2);
//						graph.addEdge(j,i, val/2);
//					}
//					if(graph.getEdge(i, j) != graph.getEdge(j, i)){
//						graph.addEdge(i, j, graph.getEdge(j, i));
//					}
//				}
//
//			}
//		}		

		for(int i=0;i<size;i++){
			double counter = 0;
			double val = graph.getEdge(i, i);
			for(int j=0;j<size;j++){
				double neighnor = graph.getEdge(i, j);
				if(i!=j && !(val==0||neighnor==0)){
					double avg = (neighnor+val)/2;
//					double similarity = Math.abs(neighnor-val)/avg*Math.abs(neighnor-val);
					double similarity = 1/(1+Math.abs(neighnor-val));
					graph.addEdge(i, j, similarity);
				}
				
			}
			graph.addEdge(i, i, 0);
		}
		
		
//		System.out.println(graph.getMatrix());
		
		for(int i=0;i<size;i++){
			double counter = 0;
			for(int j=0;j<size;j++){
				counter += Math.abs(graph.getEdge(i, j));
			}
			graph.addEdge(i, i, counter);
		}
//		System.out.println(graph.getMatrix());
		
		return graph;
	}
	
	public static double sigmoid(double x) {
	    return (1/( 1 + Math.pow(Math.E,(-1*x))));
	}

	private static void projection(AHCGraph graph, double[][] I,int row , int i,int j){
		int gind = i*I[0].length +j;

		if(gind >= 0 && gind < graph.size() && j>=0 && i>=0 && j<I[0].length && i<I.length){
			graph.addEdge(row,gind, I[i][j]);
		}
	}
}
