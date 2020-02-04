package ter.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import ter.utility.Utility;

public class GraphGeneratorManagement {

	private static HashMap<String, String> map = null;
	private static DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = null;
	private static HashMap<String, Edge> edgeHashMap = null;
	private static String number;

	public static ArrayList<DirectedWeightedPseudograph<String, DefaultWeightedEdge>> arrayTrueGraph = new ArrayList<DirectedWeightedPseudograph<String, DefaultWeightedEdge>>();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new GraphGeneratorManagement(0, number, map, edgeHashMap);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GraphGeneratorManagement(int numberOfGraph, String number, HashMap<String, String> map, HashMap<String, Edge> edgeHashMap) {
		
		ArrayList<DirectedWeightedPseudograph<String, DefaultWeightedEdge>> arrayTrueGraphManag = new ArrayList<DirectedWeightedPseudograph<String, DefaultWeightedEdge>>();
		ArrayList<Double> arrayPercentage = new ArrayList<Double>();
		ArrayList<Double> arrayPercentageFinal = new ArrayList<Double>();

		System.out.println("MAP: "+map.toString());
		String[] arrayNewLine = new String[Integer.valueOf(number)];
		for (int i = 0; i < Integer.valueOf(number); i++) {
			arrayNewLine[i] = "0";
		}

		for (int i = 0; i < numberOfGraph; i++) {

			edgeHashMap = Utility.graphGeneratorV2(number);
			if (edgeHashMap.isEmpty()) {
				System.out.println("EMPTY");
			}

			graph = Utility.analyzerGraph(Integer.valueOf(number), edgeHashMap);
			System.out.println("GRAFO: "+graph.toString() );
			Utility.buildAlgorithm(map, graph);
		}

		System.out.println();
		String stringNewLine = "";
		for (DirectedWeightedPseudograph<String, DefaultWeightedEdge> temp : arrayTrueGraph) {
			arrayTrueGraphManag.add(temp);
			System.out.println("TEMP "+temp);
		}

		double percentage = 0.0;
		percentage = (arrayTrueGraph.size() / (double)numberOfGraph);
		arrayPercentage.add(percentage);
		System.out.println("ARRAY: "+arrayPercentage.toString());
		System.out.println();
		System.out.println("PERCENTAGE REMAINED GRAPH: " + (percentage*100) + "%");
		arrayPercentageFinal.add(percentage*100);
		System.out.println("##############################################################################################################################################");
		
		// second part

		for (int i = Integer.valueOf(number) - 1; i >= 0; i--) {
			arrayNewLine[i] = "1";
			for (int j = 0; j < arrayNewLine.length; j++) {
				if (stringNewLine.equals(""))
					stringNewLine = stringNewLine + arrayNewLine[j];
				else
					stringNewLine = stringNewLine + "," + arrayNewLine[j];
			}

			map.put(stringNewLine, stringNewLine);

			System.out.println("MAP: "+map.toString());
			stringNewLine = "";

			arrayTrueGraph.clear();
			System.out.println();
			for (DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph1 : arrayTrueGraphManag) {
				System.out.println();
				System.out.println("GRAFO: "+graph1.toString() );

				Utility.buildAlgorithm(map, graph1);
			}

			if (arrayTrueGraph.size() != 0) {
				percentage = ((arrayTrueGraph.size()) / (double)arrayTrueGraphManag.size());
				arrayPercentage.add(percentage);
			}else {
				arrayPercentage.add((double) 0.0);
				
			}

			System.out.println("ARRAY: "+arrayPercentage.toString());
			System.out.println();
			Double finalPercentage=1.0;
			for(Double temp : arrayPercentage) {
				finalPercentage = finalPercentage* temp;
			}
			percentage = (arrayTrueGraph.size() / (double)arrayTrueGraphManag.size());
			
			arrayTrueGraphManag.clear();
			for (DirectedWeightedPseudograph<String, DefaultWeightedEdge> temp : arrayTrueGraph) {
				arrayTrueGraphManag.add(temp);

				System.out.println("TEMP "+temp);
			}
			System.out.println("PERCENTAGE REMAINED GRAPH: " + (finalPercentage*100) + "%");
			System.out.println("##############################################################################################################################################");
			arrayPercentageFinal.add(finalPercentage*100);
		}
		System.out.println("SIZE MAP: "+map.size());
		System.out.println();
		System.out.println("FINAL: "+arrayPercentageFinal.toString());
		System.exit(0);
	}

}
