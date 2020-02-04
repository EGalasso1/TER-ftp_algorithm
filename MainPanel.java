package ter.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import com.mxgraph.swing.mxGraphComponent;
import ter.main.Edge;
import ter.main.Master;
import ter.utility.Utility;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Panel;

public class MainPanel {

	public static ArrayList<Boolean> boolProof = new ArrayList<Boolean>();

	public static ArrayList<Master> masterList = new ArrayList<Master>();

	private JTextField textConclusion;
	private JTextField textInputMap;
	private JTextField textInputGraph;

	private JScrollPane scrollPane;

	private JLabel labelVertex;
	private JLabel labelFunctionG;
	private JLabel labelGTilde;
	private JLabel labelIncomingEdge;
	private JLabel labelGCap;
	private JLabel labelSetA;
	private JLabel labelSetB;

	private JTextField textVertexNumber;
	private JTextField textFunctionG;
	private JTextField textGTilde;
	private JTextField textIncomingEdge;
	private JTextField textGCap;
	private JTextField textSetA;
	private JButton buttonShowDetails;

	private static JFrame frame;

	private TitledBorder title;

	private JLabel labelConclusiveProof;
	private JLabel labelProof1;
	private JLabel labelProof2;
	private JLabel labelProof3;
	private JTextField textSetB;
	private JTextField textProof1;
	private JTextField textProof2;
	private JTextField textProof3;
	private JTextField textConclusiveProof;
	private mxGraphComponent mxGraphComponent;
	private static HashMap<String, String> map = null;

	private static HashMap<String, Edge> edgeHashMap2;
	public static DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = null;
	private static String number;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new MainPanel(number, map, graph, edgeHashMap2,false);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainPanel(String number, HashMap<String, String> map,
			DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph, HashMap<String, Edge> edgeHashMap2,Boolean useFile) {

		Utility.buildAlgorithm(map, graph);

		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(new Dimension(999, 458));
		frame.setLocation(new Point(270, 150));
		frame.setTitle("Ter Project");
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		title = BorderFactory.createTitledBorder("FPT algorithm for the interaction graph consistency problem");

		JPanel fptPanel = new JPanel();
		fptPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		fptPanel.setBounds(316, 10, 661, 117);
		fptPanel.setBorder(title);
		frame.getContentPane().add(fptPanel);
		fptPanel.setLayout(null);

		JLabel labelConclusion = new JLabel("Conclusion:");
		labelConclusion.setBounds(10, 25, 88, 18);
		fptPanel.add(labelConclusion);

		textConclusion = new JTextField();
		textConclusion.setBounds(108, 25, 224, 20);
		textConclusion.setEditable(false);
		fptPanel.add(textConclusion);
		textConclusion.setColumns(10);

		JLabel labelInputMap = new JLabel("Input Map:");
		labelInputMap.setBounds(10, 50, 88, 20);
		fptPanel.add(labelInputMap);

		textInputMap = new JTextField();
		textInputMap.setBounds(108, 50, 441, 20);
		textInputMap.setEditable(false);
		textInputMap.setText(map.toString());
		fptPanel.add(textInputMap);
		textInputMap.setColumns(10);

		JLabel labelInputGraph = new JLabel("Input Graph:");
		labelInputGraph.setBounds(10, 75, 88, 19);
		fptPanel.add(labelInputGraph);

		textInputGraph = new JTextField();
		textInputGraph.setBounds(108, 75, 441, 20);
		textInputGraph.setEditable(false);
		textInputGraph.setText(graph.toString());
		fptPanel.add(textInputGraph);
		textInputGraph.setColumns(10);

		JCheckBox infoInputGraphButton = new JCheckBox();
		infoInputGraphButton.setBounds(551, 75, 30, 19);
		infoInputGraphButton.setIcon(new ImageIcon(MainPanel.class.getResource("/img/info.png")));
		fptPanel.add(infoInputGraphButton);

		infoInputGraphButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				InfoGraphPanel infoGraphPanel = new InfoGraphPanel(edgeHashMap2);
				infoGraphPanel.setVisible(true);

			}
		});

		title = BorderFactory.createTitledBorder("Show Data");

		JPanel showDataPanel = new JPanel();
		showDataPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		showDataPanel.setBounds(316, 137, 661, 281);
		showDataPanel.setBorder(title);
		frame.getContentPane().add(showDataPanel);
		showDataPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 641, 209);
		showDataPanel.add(scrollPane);

		buttonShowDetails = new JButton("SHOW DETAILS");
		buttonShowDetails.setBounds(10, 244, 169, 27);
		buttonShowDetails.setEnabled(false);
		showDataPanel.add(buttonShowDetails);

		HashMap<String, Edge> edgeHashMap = new HashMap<String, Edge>();
		JButton backButton = new JButton("Back");
		backButton.setBounds(566, 244, 85, 27);
		showDataPanel.add(backButton);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String signedEdge = "";

				for (DefaultWeightedEdge edgeComponent : graph.edgeSet()) {

					if (graph.getEdgeWeight(edgeComponent) == 1.0) {
						signedEdge = "+1";
					} else {
						signedEdge = "-1";
					}

					String ID = graph.getEdgeSource(edgeComponent).toString() + "," + signedEdge + ","
							+ graph.getEdgeTarget(edgeComponent).toString();
					String leftEdge = graph.getEdgeSource(edgeComponent).toString();
					String edgeSign = signedEdge;
					String rightEdge = graph.getEdgeTarget(edgeComponent).toString();

					Edge edge = new Edge(ID, leftEdge, edgeSign, rightEdge);

					edgeHashMap.put(ID, edge);

				}

				StateEdgePanel stateEdgePanel = new StateEdgePanel(number, map,useFile);
				stateEdgePanel.setVisible(true);

				StateEdgePanel.edgeHashMap.clear();
				StateEdgePanel.edgeHashMap = edgeHashMap;
				StateEdgePanel.updateEdge();
				frame.setVisible(false);
			}
		});

		buttonShowDetails.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.setSize(new Dimension(1410, 458));
				frame.setLocation(new Point(80, 150));
				buttonShowDetails.setVisible(false);
			}
		});

		title = BorderFactory.createTitledBorder("Show Details");

		JPanel showDetailsPanel = new JPanel();
		showDetailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		showDetailsPanel.setBounds(987, 10, 397, 408);
		showDetailsPanel.setBorder(title);
		frame.getContentPane().add(showDetailsPanel);
		showDetailsPanel.setLayout(null);

		labelVertex = new JLabel("Vertex number:");
		labelVertex.setBounds(10, 26, 94, 18);
		showDetailsPanel.add(labelVertex);

		textVertexNumber = new JTextField();
		textVertexNumber.setBounds(114, 24, 273, 20);
		showDetailsPanel.add(textVertexNumber);
		textVertexNumber.setEditable(false);
		textVertexNumber.setColumns(10);

		labelFunctionG = new JLabel("Function G:");
		labelFunctionG.setBounds(10, 54, 94, 20);
		showDetailsPanel.add(labelFunctionG);

		textFunctionG = new JTextField();
		textFunctionG.setColumns(10);
		textFunctionG.setBounds(114, 54, 273, 20);
		textFunctionG.setEditable(false);
		showDetailsPanel.add(textFunctionG);

		labelGTilde = new JLabel("G Tilde:");
		labelGTilde.setBounds(10, 84, 94, 17);
		showDetailsPanel.add(labelGTilde);

		textGTilde = new JTextField();
		textGTilde.setColumns(10);
		textGTilde.setBounds(114, 81, 273, 20);
		textGTilde.setEditable(false);
		showDetailsPanel.add(textGTilde);

		labelIncomingEdge = new JLabel("Incoming Edge:");
		labelIncomingEdge.setBounds(10, 111, 94, 20);
		showDetailsPanel.add(labelIncomingEdge);

		textIncomingEdge = new JTextField();
		textIncomingEdge.setColumns(10);
		textIncomingEdge.setBounds(114, 111, 273, 20);
		textIncomingEdge.setEditable(false);
		showDetailsPanel.add(textIncomingEdge);

		labelGCap = new JLabel("G Cap:");
		labelGCap.setBounds(10, 141, 94, 20);
		showDetailsPanel.add(labelGCap);

		textGCap = new JTextField();
		textGCap.setColumns(10);
		textGCap.setBounds(114, 141, 273, 20);
		textGCap.setEditable(false);
		showDetailsPanel.add(textGCap);

		labelSetA = new JLabel("Set A: ");
		labelSetA.setBounds(10, 171, 94, 20);
		showDetailsPanel.add(labelSetA);

		textSetA = new JTextField();
		textSetA.setColumns(10);
		textSetA.setBounds(114, 171, 273, 20);
		textSetA.setEditable(false);
		showDetailsPanel.add(textSetA);

		labelSetB = new JLabel("Set B: ");
		labelSetB.setBounds(10, 201, 94, 20);
		showDetailsPanel.add(labelSetB);
		showDetailsPanel.setBorder(title);

		title = BorderFactory.createTitledBorder("Proofs");

		textSetB = new JTextField();
		textSetB.setEditable(false);
		textSetB.setColumns(10);
		textSetB.setBounds(114, 201, 273, 20);
		showDetailsPanel.add(textSetB);

		JPanel proofsPanel = new JPanel();
		proofsPanel.setBounds(10, 250, 377, 148);
		showDetailsPanel.add(proofsPanel);
		proofsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		proofsPanel.setBorder(title);
		proofsPanel.setLayout(null);

		labelProof3 = new JLabel("Result Proof 1: ");
		labelProof3.setBounds(10, 23, 88, 18);
		proofsPanel.add(labelProof3);

		textProof1 = new JTextField();
		textProof1.setEditable(false);
		textProof1.setColumns(10);
		textProof1.setBounds(108, 21, 259, 20);
		proofsPanel.add(textProof1);

		labelProof2 = new JLabel("Result Proof 2:");
		labelProof2.setBounds(10, 51, 88, 20);
		proofsPanel.add(labelProof2);

		textProof2 = new JTextField();
		textProof2.setEditable(false);
		textProof2.setColumns(10);
		textProof2.setBounds(108, 51, 259, 20);
		proofsPanel.add(textProof2);

		labelProof1 = new JLabel("Result Proof 3: ");
		labelProof1.setBounds(10, 81, 88, 20);
		proofsPanel.add(labelProof1);

		textProof3 = new JTextField();
		textProof3.setEditable(false);
		textProof3.setColumns(10);
		textProof3.setBounds(108, 81, 259, 20);
		proofsPanel.add(textProof3);

		labelConclusiveProof = new JLabel("Conclusion:");
		labelConclusiveProof.setBounds(10, 111, 88, 20);
		proofsPanel.add(labelConclusiveProof);

		textConclusiveProof = new JTextField();
		textConclusiveProof.setEditable(false);
		textConclusiveProof.setColumns(10);
		textConclusiveProof.setBounds(108, 111, 259, 20);
		proofsPanel.add(textConclusiveProof);

		JLabel labelDeveloper = new JLabel("<html> Developed by:  Galasso Emanuele, Bellusci Valeria</html>");
		labelDeveloper.setFont(new Font("MV Boli", Font.PLAIN, 12));
		labelDeveloper.setHorizontalAlignment(SwingConstants.LEFT);
		labelDeveloper.setVerticalAlignment(SwingConstants.TOP);
		labelDeveloper.setBounds(10, 389, 296, 29);

		frame.getContentPane().add(labelDeveloper);

		JPanel panelGraph = new JPanel();
		panelGraph.setBounds(new Rectangle(10, 10, 10, 10));

		title = BorderFactory.createTitledBorder("Graph");
		panelGraph.setBorder(title);

		DirectedWeightedPseudograph<String, DefaultWeightedEdge> graphMain = Utility
				.analyzerGraph(Integer.valueOf(number), edgeHashMap2);
		Graph<String, DefaultWeightedEdge> graph2 = graphMain;

		JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<String, DefaultWeightedEdge>(
				graph2);

		Map<String, Object> edgeStyle = new HashMap<String, Object>();

		// edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
		edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		edgeStyle.put(mxConstants.STYLE_NOLABEL, true);

		Object[] cells = graphAdapter.getChildEdges(graphAdapter.getDefaultParent());
		Object[] edgeCellArray = new Object[1];
		HashMap<DefaultWeightedEdge, mxICell> edgeToCellMap = graphAdapter.getEdgeToCellMap();

		mxStylesheet stylesheet = graphAdapter.getStylesheet();
		for (Object o : cells) {
			mxCell c = (mxCell) o;
			if (c.getValue() != null) {
				String[] str = c.getValue().toString().replace("(", "").replaceAll(" ", "").replace(")", "")
						.split("[(:)\\s]");
				DefaultWeightedEdge defW = graph2.getEdge(str[0], str[1]);

				if (graph2.getEdgeWeight(defW) == 1.0) {
					edgeCellArray[0] = (Object) (edgeToCellMap.get(graph2.getEdge(str[0], str[1])));
					graphAdapter.setCellStyle("strokeColor=#33CC00", edgeCellArray);
				} else if (graph2.getEdgeWeight(defW) == -1.0) {
					edgeCellArray[0] = (Object) (edgeToCellMap.get(graph2.getEdge(str[0], str[1])));
					graphAdapter.setCellStyle("strokeColor=#FF3333", edgeCellArray);

				}
			}
		}

		stylesheet.setDefaultEdgeStyle(edgeStyle);
		graphAdapter.setStylesheet(stylesheet);

		new mxCircleLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
		new mxParallelEdgeLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
		// mxUtils.getHexColorString(Color.GREEN)

		graphAdapter.setCellsEditable(false);
		graphAdapter.setCellsMovable(false);
		graphAdapter.setEdgeLabelsMovable(false);
		graphAdapter.setCellsDeletable(false);
		graphAdapter.setCellsDisconnectable(false);
		graphAdapter.setCellsResizable(false);
		graphAdapter.setCellsBendable(false);
		graphAdapter.setCellsCloneable(false);
		graphAdapter.setConnectableEdges(false);
		graphAdapter.setVertexLabelsMovable(false);
		graphAdapter.setSplitEnabled(false);
		graphAdapter.setCellsSelectable(false);
		panelGraph.setLayout(null);

		mxGraphComponent = new mxGraphComponent(graphAdapter);
		mxGraphComponent.setBounds(10, 60, 276, 260);

		panelGraph.add(mxGraphComponent);

		panelGraph.setBounds(10, 10, 296, 369);

		frame.getContentPane().add(panelGraph);

		JLabel lblNewLabel = new JLabel("Representation of the input graph:");
		lblNewLabel.setBounds(10, 22, 276, 28);
		panelGraph.add(lblNewLabel);
		
		JLabel lblGreenPositive = new JLabel("Green: Positive");
		lblGreenPositive.setBounds(10, 330, 276, 13);
		panelGraph.add(lblGreenPositive);
		
		JLabel lblRedNegative = new JLabel("Red: Negative");
		lblRedNegative.setBounds(10, 346, 276, 13);
		panelGraph.add(lblRedNegative);

		
		System.out.println("MAIN PROOF: ------ "+boolProof.toString());
		if (!boolProof.contains(false)) {

			textConclusion.setText("There is a monotonous extension.");

		} else {

			textConclusion.setText("There NO monotonous extension.");
		}

		createTable( masterList);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				frame.setVisible(false);
				System.exit(0);
			}
		});
	}

	public void createTable(ArrayList<Master> masterList) {

		String[] columnNames = { "N°", "Function G", "G Tilde", "Inc. Edge", "G Cap", "Set A", "Set B", "Proof 1",
				"Proof 2", "Proof 3", "Final Proof" };

		Object[][] conclusiveData = new Object[masterList.size()][columnNames.length];
		int i = 0;

		for (Master mast : masterList) {
			conclusiveData[i][0] = mast.getID();
			conclusiveData[i][1] = mast.getMapH();
			conclusiveData[i][2] = mast.getMapGTilde();
			conclusiveData[i][3] = graph.incomingEdgesOf(String.valueOf(i + 1));
			conclusiveData[i][4] = mast.getMapGCap();
			conclusiveData[i][5] = mast.getSetA();
			conclusiveData[i][6] = mast.getSetB();
			conclusiveData[i][7] = mast.isProof1();
			conclusiveData[i][8] = mast.isProof2();
			conclusiveData[i][9] = mast.isProof3();

			boolean singleProof;

			if (mast.isProof1()) {
				if (mast.isProof2() || mast.isProof3()) {
					singleProof = true;
				} else {
					singleProof = false;
				}
			} else {
				singleProof = false;
			}
			conclusiveData[i][10] = String.valueOf(singleProof);
			i++;

		}
		final JTable table = new JTable(conclusiveData, columnNames);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				int row = table.getSelectedRow();

				try {


					textVertexNumber.setText("");
					textVertexNumber.setText(table.getValueAt(row, 0).toString());

					textFunctionG.setText("");
					textFunctionG.setText(table.getValueAt(row, 1).toString());

					textGTilde.setText("");
					textGTilde.setText(table.getValueAt(row, 2).toString());

					textIncomingEdge.setText("");
					textIncomingEdge.setText(table.getValueAt(row, 3).toString());

					textGCap.setText("");
					textGCap.setText(table.getValueAt(row, 4).toString());

					textSetA.setText("");
					textSetA.setText(table.getValueAt(row, 5).toString());

					textSetB.setText("");
					textSetB.setText(table.getValueAt(row, 6).toString());

					textProof1.setText("");
					textProof1.setText(table.getValueAt(row, 7).toString());

					textProof2.setText("");
					textProof2.setText(table.getValueAt(row, 8).toString());

					textProof3.setText("");
					textProof3.setText(table.getValueAt(row, 9).toString());

					textConclusiveProof.setText("");
					textConclusiveProof.setText(table.getValueAt(row, 10).toString());
					buttonShowDetails.setEnabled(true);
				} catch (Exception e) {

				}

			}

		});

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(7).setPreferredWidth(60);
		table.getColumnModel().getColumn(8).setPreferredWidth(60);
		table.getColumnModel().getColumn(9).setPreferredWidth(60);
		table.getColumnModel().getColumn(10).setPreferredWidth(75);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);

	}
}
