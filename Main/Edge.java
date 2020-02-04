package ter.main;

public class Edge {

	private String ID = null;
	private String leftEdge = null;
	private String rightEdge = null;
	private String edgeSign = null;

	public Edge(String ID, String leftEdge, String edgeSign, String rightEdge) {
		this.ID = ID;
		this.leftEdge = leftEdge;
		this.edgeSign = edgeSign;
		this.rightEdge = rightEdge;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(String leftEdge) {
		this.leftEdge = leftEdge;
	}

	public String getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(String rightEdge) {
		this.rightEdge = rightEdge;
	}

	public String getEdgeSign() {
		return edgeSign;
	}

	public void setEdgeSign(String edgeSign) {
		this.edgeSign = edgeSign;
	}

}
