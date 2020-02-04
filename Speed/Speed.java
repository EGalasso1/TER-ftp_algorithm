package ter.speedProject;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import ter.utility.Utility;

public class Speed {

	final int NumOfComponent = 900; // NUM COMPONENTI
	final int NumOfGraph = 10; // NUM DI GRAFI
	final int NumOfNetwork = 5;
	public static ArrayList<Double> time = null;
	ArrayList<Double> finalAvg = null;
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new Speed();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Speed() {
		int numberOfNetwork = NumOfNetwork;
		time = new ArrayList<Double>();
		finalAvg = new ArrayList<Double>();
		for (int i = 0; i < numberOfNetwork; i++) {
			HashMap<String, String> hashMapNetwork = networkGenerator();
			System.out.println("MAP ALGO: "+hashMapNetwork);
			for (int j = 0; j < NumOfGraph; j++) {
				
				DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = Utility.analyzerGraph(NumOfComponent,
						Utility.graphGeneratorV2(String.valueOf(NumOfComponent)));
				System.out.println(graph.toString());
				Utility.buildAlgorithm(hashMapNetwork, graph);
			}
			finalAvg.add(findAvgNetwork(time));
			time.clear();
			System.out.println("\n\n\n");
		}
		
		System.out.println("AVG Single networks: "+finalAvg.toString());
		System.out.println("Final AVG: "+findAvgNetwork(finalAvg));
	}

	public HashMap<String, String> networkGenerator() {

		String[] valueHashOfNetworkArray = { "0", "1", "*" };

		int numberOfComponent = NumOfComponent; 
		//int numberOfState = (int) (Math.random() * ( ( ((Math.pow(2, numberOfComponent)/2) - 1) + 1)) + 1  );
		HashMap<String, String> networkMap = new HashMap<String, String>();
		int numberOfState = 100;
		for (int j = 0; j < numberOfState; j++) {
			String key = "";
			String value = "";
			for (int x = 0; x < numberOfComponent; x++) {
				
				//create key
				int randomKeyNetwork = (int) (Math.random() * 2);

				if (key.equals(""))
					key = key + randomKeyNetwork;
				else
					key = key + "," + randomKeyNetwork;

				//create value
				int randomValueNetwork = (int) (Math.random() * 3);

				if (value.equals(""))
					value = value + valueHashOfNetworkArray[randomValueNetwork];
				else 
					value = value + "," + valueHashOfNetworkArray[randomValueNetwork];
					
				
			}
			networkMap.put(key, value);
		}
		return networkMap;
	}

	public Double findAvgNetwork(ArrayList<Double> time) {
		double avg=0.0;

		for(Double value:time) {
			avg=avg+value;
		}
		return (avg/time.size());
	}
}
