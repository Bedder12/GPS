import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
////        Graph graph = new Graph();
//        graph.readVertexFile("vertices.csv");
//        graph.readEdgeFile("edges.csv");
//
//    	
    	Graph g = new Graph("Gävle");
    	g.readVertexFile("H:\\Alg\\760_tatorter.csv");
        g.readEdgeFile("H:\\Alg\\edges_760_tatorter.csv");
        
     List<String> path = g.findShortestPath("Gävle", "Valbo");
      System.out.println("Shortest path: " + String.join(" -> ", path));

    	
    	
  
}}