package ter.main;

import java.util.ArrayList;
import java.util.HashMap;

public class Master {

	private int ID = -1;
	private HashMap<String, String> mapH = null;
	private HashMap<String, String> mapGTilde = null;
	private HashMap<String, String> mapGCap = null;
	private ArrayList<String> setA = null;
	private ArrayList<String> setB = null;
	private boolean proof1;
	private boolean proof2;
	private boolean proof3;
	
	public Master(int ID, HashMap<String, String> mapH, HashMap<String, String> mapGTilde,
			HashMap<String, String> mapGCap, ArrayList<String> setA, ArrayList<String> setB, boolean proof1,
			boolean proof2, boolean proof3) {
		
		this.ID = ID;
		this.mapH = mapH;
		this.mapGTilde = mapGTilde;
		this.mapGCap = mapGCap;
		this.setA = setA;
		this.setB = setB;
		this.proof1 = proof1;
		this.proof2 = proof2;
		this.proof3 = proof3;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public HashMap<String, String> getMapH() {
		return mapH;
	}

	public void setMapH(HashMap<String, String> mapH) {
		this.mapH = mapH;
	}

	public HashMap<String, String> getMapGTilde() {
		return mapGTilde;
	}

	public void setMapGTilde(HashMap<String, String> mapGTilde) {
		this.mapGTilde = mapGTilde;
	}

	public HashMap<String, String> getMapGCap() {
		return mapGCap;
	}

	public void setMapGCap(HashMap<String, String> mapGCap) {
		this.mapGCap = mapGCap;
	}

	public ArrayList<String> getSetA() {
		return setA;
	}

	public void setSetA(ArrayList<String> setA) {
		this.setA = setA;
	}

	public ArrayList<String> getSetB() {
		return setB;
	}

	public void setSetB(ArrayList<String> setB) {
		this.setB = setB;
	}

	public boolean isProof1() {
		return proof1;
	}

	public void setProof1(boolean proof1) {
		this.proof1 = proof1;
	}

	public boolean isProof2() {
		return proof2;
	}

	public void setProof2(boolean proof2) {
		this.proof2 = proof2;
	}

	public boolean isProof3() {
		return proof3;
	}

	public void setProof3(boolean proof3) {
		this.proof3 = proof3;
	}


}
