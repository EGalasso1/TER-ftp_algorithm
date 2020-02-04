package ter.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import ter.gui.MainPanel;
import ter.main.Edge;
import ter.main.GraphGeneratorManagement;
import ter.main.Master;
import ter.speedProject.Speed;

/**
 * These methods allow the calculation and execution of our work, that is, if a
 * function has a monotonic extension.
 * 
 * @author Valeria Bellusci, Emanuele Galasso This file is protected by
 *         Copyright
 */

/* se non ce monotonia visualizza il vertice che da problemi */
public class Utility {

//	private final static String fileBooleanDataFile = System.getProperty("user.dir") + File.separator + "src"
//			+ File.separator + "ter" + File.separator + "csv" + File.separator + "data.csv";
	private final static String separatorFile = ",-,";

	private static ArrayList<String> bComponent = new ArrayList<String>();

	public static DirectedWeightedPseudograph<String, DefaultWeightedEdge> analyzerGraph(int number,
			HashMap<String, Edge> edge) {

		DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		for (int i = 1; i <= number; i++) {
			graph.addVertex(String.valueOf(i));
		}

		for (Entry<String, Edge> s : edge.entrySet()) {
			Edge edgeClass = s.getValue();

			DefaultWeightedEdge e = graph.addEdge(edgeClass.getLeftEdge(), edgeClass.getRightEdge());
			graph.setEdgeWeight(e, Double.valueOf(edgeClass.getEdgeSign()));
		}
		/*
		 * graph.addVertex("1"); graph.addVertex("2"); graph.addVertex("3");
		 * 
		 * DefaultWeightedEdge e1 = graph.addEdge("1", "2"); graph.setEdgeWeight(e1,
		 * +1);
		 * 
		 * DefaultWeightedEdge e2 = graph.addEdge("2", "3"); graph.setEdgeWeight(e2,
		 * +1);
		 * 
		 * DefaultWeightedEdge e3 = graph.addEdge("3", "1"); graph.setEdgeWeight(e3,
		 * -1);
		 * 
		 * DefaultWeightedEdge e4 = graph.addEdge("3", "2"); graph.setEdgeWeight(e4,
		 * -1);
		 */
		return graph;
	}

	public static ArrayList<Integer> takeK(int i, DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph,
			ArrayList<String> neighbors) {
		ArrayList<Integer> value = new ArrayList<Integer>();
		for (DefaultWeightedEdge a : graph.incomingEdgesOf(String.valueOf(i + 1))) {
			if (graph.getEdgeWeight(a) == -1) {
//				System.out.print("		Weight of " + a + ": " + graph.getEdgeWeight(a));
//				System.out.println("	->Negative");
//				System.out.println("		Source: " + graph.getEdgeSource(a).toString());
				for (int k = 0; k < neighbors.size(); k++) {
					if (neighbors.get(k).equals(graph.getEdgeSource(a).toString())) {
						value.add(k);
						break;
					}
				}
			} else {
//				System.out.println("		Weight of " + a + ": " + graph.getEdgeWeight(a));
			}
//			System.out.println();

		}
		return value;
	}

	public static HashMap<String, String> takeG(HashMap<String, String> map, ArrayList<String> neighbors) {
		HashMap<String, String> mapHn = new HashMap<String, String>();
		String newKey = "";

		int i = 0;
		for (Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();

			String value = (mapElement.getValue());
			String[] a = key.split(",", 0);

			for (String st : neighbors) {
				newKey = newKey + a[Integer.valueOf(st) - 1];
			}
			newKey = newKey + ",";
			a = newKey.split(",", 0);

			if (!value.equals("*")) {
				if (mapHn.containsKey(a[i]) && (!mapHn.get(a[i]).equals(value))) {
					mapHn.put(a[i], (mapHn.get(a[i]) + "," + value));
				} else {
					mapHn.put(a[i], value);
				}
			}
			i++;
		}
		return mapHn;
	}

	public static String createX0(HashMap<String, String> map) {
		String x0 = "";

		for (Entry<String, String> mapElement : map.entrySet()) {

			String value = (mapElement.getValue());
			if (value.contains("0")) {
				if (x0.equals(""))
					x0 = x0 + mapElement.getKey();
				else
					x0 = x0 + "," + mapElement.getKey();
			}
		}
		return x0;
	}

	public static String createX1(HashMap<String, String> map) {
		String x1 = "";

		for (Entry<String, String> mapElement : map.entrySet()) {
			
			String value = (mapElement.getValue());
			if (value.contains("1")) {
				if (x1.equals(""))
					x1 = x1 + mapElement.getKey();
				else
					x1 = x1 + "," + mapElement.getKey();
			}
		}
		return x1;
	}

	/**
	 * union
	 * 
	 * @return
	 */
	public static String X0joinX1(String x0, String x1) {
		String s = "";
		String[] tempx0 = x0.split(",", 0);
		for (int i = 0; i < tempx0.length; i++) {
			if (!x1.contains(tempx0[i])) {
				if (s.equals(""))
					s = s + tempx0[i];
				else
					s = s + "," + tempx0[i];
			}
		}
		if (s.equals(""))
			return x1;

		return s + "," + x1;
	}

	/**
	 * intersection
	 * 
	 * @return
	 */
	public static boolean X0meetX1(String x0, String x1) {
		boolean meet = false;

		if (!x0.equals("") && !x1.equals("")) {
			String[] s = x0.split(",", 0);
			int i = 0;
			while (i < s.length) {

				if (x1.contains(s[i])) {
					break;
				} else {
					meet = true;
				}

				i++;
			}
		} else {
			meet = true;
		}

		return meet;
	}

	public static HashMap<String, String> createGTilde(String join, String x0, String x1) {
		HashMap<String, String> mapGTilde = new HashMap<String, String>();
		String[] tempJoin = join.split(",", 0);
	
		for (int i = 0; i < tempJoin.length; i++) {
			if (x1.contains(tempJoin[i])) {
				mapGTilde.put(tempJoin[i], "1");
			} else if (x0.contains(tempJoin[i])) {
				mapGTilde.put(tempJoin[i], "0");
			} else {
				// For security
				System.out.println("ERROR");
				mapGTilde = null;
			}
		}

		return mapGTilde;
	}

	public static HashMap<String, String> createGCap(ArrayList<Integer> kValue, HashMap<String, String> mapGTilde) {
		HashMap<String, String> mapGCap = new HashMap<String, String>();
		String newKey = "";
		if (!kValue.isEmpty()) {
			for (Entry<String, String> mapElement : mapGTilde.entrySet()) {
				String key = mapElement.getKey();
				String value = (mapElement.getValue());
				char sChar[] = key.toCharArray();

				for (int i = 0; i < kValue.size(); i++) {

					if (String.valueOf(sChar[kValue.get(i)]).equals("1"))
						sChar[kValue.get(i)] = '0';
					else {
						sChar[kValue.get(i)] = '1';
					}
					newKey = (String.valueOf(sChar));

				}

				char newKeyChar[] = newKey.toCharArray();
				newKey = "";
				for (int j = 0; j < newKeyChar.length; j++) {
					if (newKey.equals(""))
						newKey = newKey + newKeyChar[j];
					else
						newKey = newKey + "," + newKeyChar[j];

				}
				mapGCap.put(newKey, value);
			}
		} else {
			for (Entry<String, String> mapElement : mapGTilde.entrySet()) {
				char newKeyChar[] = mapElement.getKey().toCharArray();
				String value = (mapElement.getValue());
				newKey = "";
				for (int j = 0; j < newKeyChar.length; j++) {
					if (newKey.equals(""))
						newKey = newKey + newKeyChar[j];
					else
						newKey = newKey + "," + newKeyChar[j];

				}
				mapGCap.put(newKey, value);
			}
		}
		return mapGCap;
	}

	/**
	 * This method reads CSV and loads all info into an HashMap
	 */
	public static HashMap<String, String> readFile(String path) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));

			String line = "";
			while ((line = br.readLine()) != null) {
				String str[] = line.split(separatorFile);
				map.put(str[0], str[1]);

			}

			br.close();
		} catch (Exception e) {

		}

		/*
		 * for (Entry<String, String> mapElement : map.entrySet()) { System.out.println(
		 * mapElement.getKey()); System.out.println(mapElement.getValue());
		 * System.out.println(); }
		 */
		return map;
	}

	/**
	 * This method loads a specific h function into an HashMap
	 */
	public static HashMap<String, String> takeH(HashMap<String, String> map, int i) {
		HashMap<String, String> mapHn = new HashMap<String, String>();

		for (Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			String value = (mapElement.getValue());
			String[] a = value.split(",", 0);
			mapHn.put(key, a[i]);
		}
		return mapHn;
	}


/*	public static ArrayList<String> createComp(String soFar, int iterations) {

		if (iterations == 0) {
			bComponent.add(soFar);

		} else {
			if (!soFar.equals("")) {
				createComp(soFar + "," + "0", iterations - 1);
				createComp(soFar + "," + "1", iterations - 1);
			} else {
				createComp(soFar + "0", iterations - 1);
				createComp(soFar + "1", iterations - 1);
			}
		}
		return bComponent;
	}*/

	/**
	 * This method takes two binary values as input and performs a bit by bit
	 * comparison
	 */
	public static int checkValue(String key1, String key2) {

		String str1[] = key1.split(",", 0);
		String str2[] = key2.split(",", 0);
		int max = 0;
		int min = 0;
		int result = -2;
		int i = 0;
		double value1 = 0;
		double value2 = 0;

		for (int x = 0; x < str1.length; x++) {
			value1 = value1 + Double.valueOf(str1[x]);
			value2 = value2 + Double.valueOf(str2[x]);
		}

		if (value1 != value2) {

			while (i < (str1.length)) {

				if ((Double.valueOf(str1[i])) < (Double.valueOf(str2[i]))) {
					min++;
				} else if ((Double.valueOf(str1[i])) > (Double.valueOf(str2[i])))
					max++;

				if (min > 0 && max > 0) {
					result = 0;
					break;
				} else if (min > 0) {
					result = -1;
				} else if (max > 0)
					result = 1;

				i++;
			}
		} else {
			result = 0;
		}
		return result;

	}

	/**
	 * This method create the A antichain set, the set of maximal elements of
	 * h^(-1)(0)
	 */
	public static ArrayList<String> createA(HashMap<String, String> map) {

		ArrayList<String> setA = new ArrayList<String>();
		ArrayList<String> newSetA = new ArrayList<String>();

		ArrayList<String> removed = new ArrayList<String>();

		ArrayList<String> newNewSetA = new ArrayList<String>();
		int checkValue = 0;
		boolean exit = false;

		for (Entry<String, String> mapElement : map.entrySet()) {
			String value = (mapElement.getValue());
			if (Integer.valueOf(value) == 0) {
				setA.add(mapElement.getKey());
			}

		}

		if (setA.size() <= 1) {
			for (String temp : setA) {
				newNewSetA.add(temp);
			}
		} else {

			/*
			 * while (exit == false) { exit = true; for (int i = 0; i < setA.size(); i++) {
			 * 
			 * for (int j = i + 1; j < setA.size(); j++) { System.out.println("I: " +
			 * setA.get(i) + "    J: " + setA.get(j)); checkValue = checkValue(setA.get(i),
			 * setA.get(j));
			 * 
			 * if (checkValue == (-1)) { exit = false; if (!exists(newSetA,
			 * setA.get(j))&&(!exists(removed, setA.get(j)))) { newSetA.add(setA.get(j));
			 * 
			 * } if (exists(newSetA, setA.get(i))) { newSetA.remove(setA.get(i));
			 * removed.add(setA.get(i)); } break; } else if (checkValue == (1)) { exit =
			 * false; if (!exists(newSetA, setA.get(i))&&(!exists(removed, setA.get(i)))) {
			 * newSetA.add(setA.get(i)); } if (exists(newSetA, setA.get(j))) {
			 * newSetA.remove(setA.get(j)); removed.add(setA.get(i)); } break; } else if
			 * (checkValue == (0)) { if (!exists(newSetA, setA.get(i))&&(!exists(removed,
			 * setA.get(i)))) { newSetA.add(setA.get(i)); } if (!exists(newSetA,
			 * setA.get(j))&&(!exists(removed, setA.get(j)))) { newSetA.add(setA.get(j)); }
			 * break; } }
			 * 
			 * } setA.clear(); for (String temp : newSetA) { setA.add(temp); } } }
			 */

			removed.clear();
			for (int i = 0; i < setA.size(); i++) {
				for (int j = i + 1; j < setA.size(); j++) {
					checkValue = checkValue(setA.get(i), setA.get(j));

					if (checkValue == (-1)) {
						if (!exists(newNewSetA, setA.get(j)) && (!exists(removed, setA.get(j)))) {
							newNewSetA.add(setA.get(j));

						}
						if (exists(newNewSetA, setA.get(i))) {
							newNewSetA.remove(setA.get(i));
							removed.add(setA.get(i));
							setA.add(setA.get(i));

						} else {
							removed.add(setA.get(i));
						}
					} else if (checkValue == (1)) {
						if (!exists(newNewSetA, setA.get(i)) && (!exists(removed, setA.get(i)))) {
							newNewSetA.add(setA.get(i));
						}
						if (exists(newNewSetA, setA.get(j))) {
							newNewSetA.remove(setA.get(j));
							removed.add(setA.get(j));
							setA.add(setA.get(j));

						} else {
							removed.add(setA.get(j));
						}
					} else if (checkValue == (0)) {
						if (!exists(newNewSetA, setA.get(i)) && (!exists(removed, setA.get(i)))) {
							newNewSetA.add(setA.get(i));
						}
						if (!exists(newNewSetA, setA.get(j)) && (!exists(removed, setA.get(j)))) {
							newNewSetA.add(setA.get(j));
						}

					}
				}
			}
		}

		return newNewSetA;
	}

	/**
	 * This method create the B antichain set, the set of minimal elements of
	 * h^(-1)(1)
	 */
	public static ArrayList<String> createB(HashMap<String, String> map) {

		ArrayList<String> setB = new ArrayList<String>();
		ArrayList<String> newSetB = new ArrayList<String>();

		ArrayList<String> removed = new ArrayList<String>();

		ArrayList<String> newNewSetB = new ArrayList<String>();

		int checkValue = 0;
		boolean exit = false;
		for (Entry<String, String> mapElement : map.entrySet()) {
			String value = (mapElement.getValue());
			if (Integer.valueOf(value) == 1) {
				setB.add(mapElement.getKey());
			}

		}
		if (setB.size() <= 1) {
			for (String temp : setB) {
				newNewSetB.add(temp);
			}
		} else {
			removed.clear();

			for (int i = 0; i < setB.size(); i++) {

				for (int j = i + 1; j < setB.size(); j++) {
					checkValue = checkValue(setB.get(i), setB.get(j));

					if (checkValue == (1)) {
						if (!exists(newNewSetB, setB.get(j)) && (!exists(removed, setB.get(j)))) {
							newNewSetB.add(setB.get(j));

						}
						if (exists(newNewSetB, setB.get(i))) {
							newNewSetB.remove(setB.get(i));
							removed.add(setB.get(i));
							setB.add(setB.get(i));
						} else {
							removed.add(setB.get(i));
						}
					} else if (checkValue == (-1)) {
						if (!exists(newNewSetB, setB.get(i)) && (!exists(removed, setB.get(i)))) {
							newNewSetB.add(setB.get(i));
						}
						if (exists(newNewSetB, setB.get(j))) {
							newNewSetB.remove(setB.get(j));
							removed.add(setB.get(j));
							setB.add(setB.get(j));
						} else {
							removed.add(setB.get(j));
						}
					} else if (checkValue == (0)) {
						if (!exists(newNewSetB, setB.get(i)) && (!exists(removed, setB.get(i)))) {
							newNewSetB.add(setB.get(i));
						}
						if (!exists(newNewSetB, setB.get(j)) && (!exists(removed, setB.get(j)))) {
							newNewSetB.add(setB.get(j));
						}

					}
				}
			}
		}

		return newNewSetB;
	}

	/**
	 * Method to verify the existence of an element, within an ArrayList
	 */
	public static boolean exists(ArrayList<String> set, String data) {
		boolean existsState = false;
		if (set.contains(data))
			existsState = true;

		return existsState;
	}
/*

	public static ArrayList<String> createA_Pos(ArrayList<String> set) {
		ArrayList<String> setApos = new ArrayList<String>();
		ArrayList<String> bComponentA = new ArrayList<String>();

		bComponentA = createComp("", set.get(0).replace(",", "").length());

		for (int i = 0; i < set.size(); i++) {
			setApos.add(set.get(i));
			for (int j = 0; j < bComponentA.size(); j++) {
				int value = checkValue(set.get(i), bComponentA.get(j));

				if (value == -1 && !exists(setApos, bComponentA.get(j))) {
					setApos.add(bComponentA.get(j));

				}
			}

		}

		bComponentA.clear();
		return setApos;
	}


	public static ArrayList<String> createA_Neg(ArrayList<String> set) {
		ArrayList<String> setAneg = new ArrayList<String>();
		ArrayList<String> bComponentAneg = new ArrayList<String>();

		bComponentAneg = createComp("", set.get(0).replace(",", "").length());

		for (int i = 0; i < set.size(); i++) {
			setAneg.add(set.get(i));
			for (int j = 0; j < bComponentAneg.size(); j++) {

				int value = checkValue(set.get(i), bComponentAneg.get(j));
				if (value == 1 && !exists(setAneg, bComponentAneg.get(j))) {
					setAneg.add(bComponentAneg.get(j));

				}
			}

		}

		bComponentAneg.clear();
		return setAneg;
	}

	public static ArrayList<String> createB_Pos(ArrayList<String> set) {
		ArrayList<String> setBpos = new ArrayList<String>();
		ArrayList<String> bComponentB = new ArrayList<String>();

		bComponentB = createComp("", set.get(0).replace(",", "").length());

		for (int i = 0; i < set.size(); i++) {
			setBpos.add(set.get(i));
			for (int j = 0; j < bComponentB.size(); j++) {
				int value = checkValue(set.get(i), bComponentB.get(j));
				if (value == -1 && !exists(setBpos, bComponentB.get(j))) {
					setBpos.add(bComponentB.get(j));

				}
			}

		}

		bComponentB.clear();
		return setBpos;

	}

	public static ArrayList<String> createB_Neg(ArrayList<String> set) {
		ArrayList<String> setBneg = new ArrayList<String>();
		ArrayList<String> bComponentBneg = new ArrayList<String>();

		bComponentBneg = createComp("", set.get(0).replace(",", "").length());

		for (int i = 0; i < set.size(); i++) {
			setBneg.add(set.get(i));
			for (int j = 0; j < bComponentBneg.size(); j++) {

				int value = checkValue(set.get(i), bComponentBneg.get(j));
				if ((value == 1) && (!exists(setBneg, bComponentBneg.get(j)))) {
					setBneg.add(bComponentBneg.get(j));
				}
			}
		}

		bComponentBneg.clear();

		return setBneg;

	}*/

	/**
	 * This method demonstrates the first proof of the document taken into
	 * consideration
	 */
	public static boolean proof1(ArrayList<String> setA, ArrayList<String> setB) {

		boolean bproof1 = false;
		if (!setA.isEmpty() && !setB.isEmpty()) {
			for (int i = 0; i < setB.size(); i++) {
				for (int j = 0; j < setA.size(); j++) {

					int value = checkValue(setB.get(i), setA.get(j));
					if (value == -1) {
						return false;
					} else if ((value == 1) || value == 0) {
						bproof1 = true;
					}
				}
			}
		} else {
			bproof1 = true;
		}
		return bproof1;

	}

	/**
	 * This method demonstrates the second proof of the document taken into
	 * consideration
	 */
	public static boolean proof2(ArrayList<String> setA, ArrayList<String> setB) {

		String[] arrayA = new String[setA.size()];
		String[] arrayB = new String[setB.size()];
		for (int i = 0; i < setA.size(); i++) {

			arrayA[i] = setA.get(i).replace(",", "");
		}
		if (arrayA.length != 0) {
			if (strBitwiseAND(arrayA, arrayA.length).contains("1")) {

				for (int i = 0; i < setB.size(); i++) {
					arrayB[i] = setB.get(i).replace(",", "");
				}
				if (arrayB.length != 0) {
					if (strBitwiseOR(arrayB, arrayB.length).contains("0")) {

						return false;
					}
				} else {
					return false;
				}

			}
		} else {
			for (int i = 0; i < setB.size(); i++) {
				arrayB[i] = setB.get(i).replace(",", "");
			}
			if (arrayB.length != 0) {
				if (strBitwiseOR(arrayB, arrayB.length).contains("0")) {

					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method demonstrates the third proof of the document taken into
	 * consideration
	 */
	public static boolean proof3(ArrayList<String> setA, ArrayList<String> setB) {
	
		boolean exit = false;
		String str = null;
		int resultOfA = 0;
		int resultOfB = 0;
		int i = 0;// AUB
		int j = 0;// Pos dell'elemento i
		ArrayList<String> setAunionB = new ArrayList<String>();

		for (String temp : setA) {
			setAunionB.add(temp);
		}
		for (String temp : setB) {
			setAunionB.add(temp);
		}

		if (setAunionB.isEmpty()) {
			return true;
		} else {
			while (exit == false && i < setAunionB.size()) {

				str = setAunionB.get(i);

				while (j < str.length() && exit == false) {

					StringBuilder string = new StringBuilder(str);
					if (str.charAt(j) == '0')
						string.setCharAt(j, '1');
					else
						string.setCharAt(j, '0');

					for (int x = 0, y = 0; x < setA.size() || y < setB.size(); x++, y++) {

						if (x < setA.size()) {
							resultOfA = checkValue(string.toString(), setA.get(x));
							// System.out.println("Ris A: " + resultOfA);
							// System.out.println();
						}
						if (y < setB.size()) {
							resultOfB = checkValue(string.toString(), setB.get(y));
//							System.out.println("Ris B: " + resultOfB);
							// System.out.println();
						}
						if (((resultOfA == 1) || (resultOfA == 0)) && ((resultOfB == (-1) || resultOfB == (0)))) {
							exit = true;
							break;
						}

					}

					j = j + 2;
				}

				i++;
				j = 0;
			}

		}
		return exit;
	}

	/**
	 * AND function
	 */
	public static String strBitwiseAND(String[] arr, int n) {
		String res = "";

		// To store the largest and the smallest
		// string's size, We need this to add
		// '0's in the resultant string
		int smallest_size = Integer.MAX_VALUE;
		int largest_size = Integer.MIN_VALUE;

		// Reverse each string
		// Since we need to perform AND operation
		// on bits from Right to Left
		for (int i = 0; i < n; i++) {

			StringBuilder temp = new StringBuilder();
			temp.append(arr[i]);
			arr[i] = temp.reverse().toString();

			// Update the respective length values
			smallest_size = Math.min(smallest_size, arr[i].length());
			largest_size = Math.max(largest_size, arr[i].length());
		}

		// Traverse bits from 0 to smallest string's size
		for (int i = 0; i < smallest_size; i++) {
			boolean all_ones = true;

			for (int j = 0; j < n; j++) {

				// If at this bit position, there is a 0
				// in any of the given strings then AND
				// operation on current bit position
				// will be 0
				if (arr[j].charAt(i) == '0') {

					all_ones = false;
					break;
				}
			}

			// Add resultant bit to result
			res += (all_ones ? '1' : '0');
		}

		// Add 0's to the string.
		for (int i = 0; i < largest_size - smallest_size; i++)
			res += '0';

		// Reverse the string
		// Since we started from LEFT to RIGHT
		StringBuilder temp = new StringBuilder();
		temp.append(res);
		res = temp.reverse().toString();

		// Return the resultant string
		return res;
	}

	/**
	 * OR function
	 */
	public static String strBitwiseOR(String[] arr, int n) {
		String res = "";

		// To store the largest and the smallest
		// string's size, We need this to add
		// '0's in the resultant string
		int smallest_size = Integer.MAX_VALUE;
		int largest_size = Integer.MIN_VALUE;

		// Reverse each string
		// Since we need to perform AND operation
		// on bits from Right to Left

		for (int i = 0; i < n; i++) {
			StringBuilder temp = new StringBuilder();
			temp.append(arr[i]);
			arr[i] = temp.reverse().toString();

			// Update the respective length values
			smallest_size = Math.min(smallest_size, arr[i].length());
			largest_size = Math.max(largest_size, arr[i].length());

		}
		// Traverse bits from 0 to smallest string's size
		for (int i = 0; i < smallest_size; i++) {
			boolean all_ones = true;

			for (int j = 0; j < n; j++) {

				// If at this bit position, there is a 0
				// in any of the given strings then AND
				// operation on current bit position
				// will be 0
				if (arr[j].charAt(i) == '1') {

					all_ones = false;
					break;
				}
			}

			// Add resultant bit to result
			res += (all_ones ? '0' : '1');

		}

		// Add 0's to the string.
		for (int i = 0; i < largest_size - smallest_size; i++)
			res += '1';

		// Reverse the string
		// Since we started from LEFT to RIGHT
		StringBuilder temp = new StringBuilder();
		temp.append(res);
		res = temp.reverse().toString();

		// Return the resultant string
		return res;
	}

	public static HashMap<String, Edge> graphGenerator(String numberOfVertices) {

		HashMap<String, Edge> edgeHashMap = new HashMap<String, Edge>();
		ArrayList<String> arrayGraphGenerator = new ArrayList<String>();

		for (int i = 1; i <= Integer.valueOf(numberOfVertices); i++) {
			arrayGraphGenerator.add(String.valueOf(i));
		}

		for (int i = 0; i < arrayGraphGenerator.size(); i++) {

			for (int j = 0; j < arrayGraphGenerator.size(); j++) {
				double rand = Math.random();

				if ((rand >= (0.5))) {
					String ID = arrayGraphGenerator.get(i) + "," + "+1" + "," + arrayGraphGenerator.get(j);
					Edge edge = new Edge(ID, arrayGraphGenerator.get(i), "+1", arrayGraphGenerator.get(j));
					edgeHashMap.put(ID, edge);
				}

			}
		}

		return edgeHashMap;

	}

	public static HashMap<String, Edge> graphGeneratorV2(String numberOfVertices) {

		HashMap<String, Edge> edgeHashMap = new HashMap<String, Edge>();
		ArrayList<String> arrayGraphGenerator = new ArrayList<String>();
		ArrayList<String> arrayGraphGeneratorCopy = new ArrayList<String>();

		ArrayList<String> arrayRandomGenerator = new ArrayList<String>();

		for (int i = 1; i <= Integer.valueOf(numberOfVertices); i++) {
			arrayGraphGenerator.add(String.valueOf(i));
		}
		for (String string : arrayGraphGenerator) {
			arrayGraphGeneratorCopy.add(string);
		}

		for (int i = 0; i < Integer.valueOf(numberOfVertices); i++) {
			arrayRandomGenerator.add(String.valueOf((int) (Math.random() * (arrayGraphGenerator.size() + 1))));
		}

		for (int i = 0; i < arrayGraphGenerator.size(); i++) {

			for (int j = 0; j < Integer.valueOf(arrayRandomGenerator.get(i)); j++) {

				int tempRandom = (int) (Math.random() * (arrayGraphGeneratorCopy.size()));
				String sign;
				if (Math.random() >= 0.5) {
					sign = "+1";
				} else {
					sign = "-1";
				}

				String value = arrayGraphGeneratorCopy.get(tempRandom);
				String ID = arrayGraphGenerator.get(i) + "," + sign + "," + value;

				arrayGraphGeneratorCopy.remove(value);
				Edge edge = new Edge(ID, arrayGraphGenerator.get(i), sign, value);
				edgeHashMap.put(ID, edge);
			}
			arrayGraphGeneratorCopy.clear();
			for (String string : arrayGraphGenerator) {
				arrayGraphGeneratorCopy.add(string);
			}
		}
		return edgeHashMap;
	}

	public static void buildAlgorithm(HashMap<String, String> map,
			DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph) {

		boolean proof1;
		boolean proof2;
		boolean proof3;

		ArrayList<Boolean> boolProof = null;

		ArrayList<Master> masterList = null;

		boolProof = new ArrayList<Boolean>();

		HashMap<String, String> mapH = new HashMap<String, String>();
		HashMap<String, String> mapG = new HashMap<String, String>();
		HashMap<String, String> mapGTilde = new HashMap<String, String>();
		HashMap<String, String> mapGCap = new HashMap<String, String>();

		ArrayList<String> setA = new ArrayList<String>();
		ArrayList<String> setB = new ArrayList<String>();
		ArrayList<String> neighbors = new ArrayList<String>();

		masterList = new ArrayList<Master>();

		long start = System.nanoTime();

		Map.Entry<String, String> entry = map.entrySet().iterator().next();

		// System.out.println();
		System.out.println("FPT algorithm for the interaction graph consistency problem");
		// System.out.println();

//		System.out.println(" Input Map: " + map);
//		System.out.println();
//		System.out.println(" Input graph: " + graph);
//		System.out.println();
//		System.out.println("			  Vertex set: " + graph.vertexSet());
//		System.out.println();
//		System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println();

		String key = entry.getKey();
		String newKey = key.replaceAll(",", "");
		for (int i = 0; i < newKey.length(); i++) {

			mapH = Utility.takeH(map, i);
//			System.out.println("			Vertex: " + (i + 1));
//			System.out.println();
//			System.out.println(" G" + (i + 1) + ": " + mapH);
//			System.out.println();
//			System.out.println(graph);
			for (DefaultWeightedEdge e : graph.incomingEdgesOf(String.valueOf(i + 1))) {
				neighbors.add(graph.getEdgeSource(e));
			}

			// Bellissima funzione che ordina i neighbors
			Collections.sort(neighbors);

			if (!neighbors.isEmpty()) {
				mapG = Utility.takeG(mapH, neighbors);
				if (mapG.isEmpty()) {
					boolProof.add(true);
					masterList.add(new Master((i + 1), mapH, null, null, null, null, true, true, true));
				} else {
					String x0 = Utility.createX0(mapG);
					String x1 = Utility.createX1(mapG);
					String join = Utility.X0joinX1(x0, x1);

					if (Utility.X0meetX1(x0, x1)) {

						mapGTilde = Utility.createGTilde(join, x0, x1);

//					System.out.println(" GTilde: " + mapGTilde);
//					System.out.println();
//					System.out.println(
//							" Incoming Edge of " + (i + 1) + ": " + graph.incomingEdgesOf(String.valueOf(i + 1)));
//					System.out.println();

						ArrayList<Integer> kValue = Utility.takeK(i, graph, neighbors);

						mapGCap = Utility.createGCap(kValue, mapGTilde);
//					System.out.println(" GCap: " + mapGCap);

						if (mapGCap.isEmpty()) {
							boolProof.add(true);

							masterList.add(new Master((i + 1), mapH, null, null, null, null, true, true, true));
							break;
						}

						setA = Utility.createA(mapGCap);
						/*if (!setA.isEmpty()) {
							setAneg = Utility.createA_Neg(setA);
							// NON USATO setApos = Utility.createA_Pos(setA);
						}*/

						setB = Utility.createB(mapGCap);
						/*if (!setB.isEmpty()) {
							// NON USATO setBneg = Utility.createB_Neg(setB);
							//setBpos = Utility.createB_Pos(setB);
						}*/

						boolProof.add(true);

//					System.out.println();
//					System.out.println(" A: " + setA);
//					System.out.println(" B: " + setB);
//					System.out.println();

						proof1 = Utility.proof1(setA, setB);
						proof2 = Utility.proof2(setA, setB);
						if (proof2 != true) {
							proof3 = Utility.proof3(setA, setB);
						} else {
							proof3 = false;
						}
//					System.out.println(" Proof1: " + proof1);
//					System.out.println(" Proof2: " + proof2);
//					System.out.println(" Proof3: " + proof3);

						if (proof1) {
							boolProof.add(true);
							if (proof2 || proof3) {
								boolProof.add(true);
							} else {
								boolProof.add(false);
							}
						} else {
							boolProof.add(false);
						}

//					System.out.println();
//					System.out.println("		Proof result: " + !singleProofs.contains(false));
//					System.out.println();

					} else {
						System.out.println("X0meetX1 false");
						boolProof.add(false);
						break;
					}

//				System.out.println("-------------------------------------------------------");
//				System.out.println();

					/* TODO se viene passato null non aggiorna la lista-tabella */
					masterList.add(new Master((i + 1), mapH, mapGTilde, mapGCap, setA, setB, proof1, proof2, proof3));
				}
					MainPanel.masterList = masterList;
					neighbors.clear();
				
			} else {

				boolProof.add(false);
				System.out.println("Neighbors empty");
				break;
			}

		}

		long finish = System.nanoTime();
		// System.out.print(" Conclusion: ");

		if (!boolProof.contains(false)) {

			System.out.println("There is a monotonous extension. YESSSS----------------");

			// System.out.println();
			// System.out.println();
			// System.out.println("AGGIUNTO: " + graph);
		//	 GraphGeneratorManagement.arrayTrueGraph.add(graph);

		} else {

			System.out.println("There is NO monotonous extension.");
		}

		// System.out.println("PROOOOFFF::: -------------------- " +
		// boolProof.toString());
		MainPanel.boolProof = boolProof;
		MainPanel.graph = graph;
		double time = finish - start;
		// System.out.println("START: "+start);
		// System.out.println("END: "+finish);
		// System.out.println("TIME: "+time);

		// System.out.println();
		System.out.println("TIME sec: " + (time / 1000000000) + " sec");
		// Speed.time.add(time / 1000000000);
	}
}