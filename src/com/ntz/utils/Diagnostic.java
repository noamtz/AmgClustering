package com.ntz.utils;

import com.ntz.data_structure.HierarchyGrids;

public class Diagnostic {

	private static boolean DEBUG = false;

	private static long cycleTime, appTime, clusteringTime, relaxTime,classifyTime;
	public static long numOfNodes, numOfCycles;

	public static double beforeNorm, afterNorm;

	public void reset() {
		cycleTime = -1;
	}

	public static long getCycleTime() {
		return cycleTime;
	}

	public static void startCycleWatch(){
		cycleTime = System.currentTimeMillis();
	}

	public static void endCycleWatch(){
		cycleTime = System.currentTimeMillis() - cycleTime;
	}

	public static void startAppWatch(){
		appTime = System.currentTimeMillis();
	}

	public static void endAppWatch(){
		appTime = System.currentTimeMillis() - appTime;
	}

	public static void startClusterWatch(){
		clusteringTime = System.currentTimeMillis();
	}

	public static void endClusterWatch(){
		clusteringTime = System.currentTimeMillis() - clusteringTime;
	}

	public static void startRelaxWatch(){
		relaxTime = System.currentTimeMillis();
	}

	public static void endRelaxWatch(){
		relaxTime = System.currentTimeMillis() - relaxTime;
		log("<Diagnostic>");
		log("	Elapsed time for Relaxation: " + relaxTime/1000.0 + " sec.");
		log("</Diagnostic>");
	}

	public static void startClassifyGridWatch(){
		classifyTime = System.currentTimeMillis();
	}

	public static void endClassifyGridWatch(){
		classifyTime = System.currentTimeMillis() - classifyTime;
		log("<Diagnostic>");
		log("	Elapsed time for Classify Grid: " + classifyTime/1000.0 + " sec.");
		log("</Diagnostic>");
	}


	public static void print(){
		log("<Diagnostic>");
		log("	Before: " + beforeNorm);
		log("	After: " + afterNorm);
		log("	Elapsed time for " + numOfCycles + " V-cycles: " + cycleTime/1000.0 + " sec. for: " + numOfNodes + " grid nodes");
		log("	Elapsed time for Clustering: " + clusteringTime/1000.0 + " sec.");
		log("	Elapsed time for Application: " + appTime/1000.0 + " sec.");
		log("    AppTime: " + appTime);
		log("</Diagnostic>");
		
		if(DEBUG) Utils.plot(HierarchyGrids.getInstance().getFinestGrid().v,"after");
	}

	public static void log(String msg) {
		log(msg, LogLevel.INFO);
	}

	public static void log(String msg , LogLevel level) {
		if(DEBUG){
			switch (level) {
			case INFO:
				System.out.println(msg);
				break;

			case ERROR:
				System.err.println(msg);
				break;

			case VERBOSE:
				System.out.println(msg);
				break;
			}
		}
	}

	public enum LogLevel{
		INFO(0),
		ERROR(1),
		VERBOSE(2);
		private int value;
		private LogLevel (int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
