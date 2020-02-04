package ter.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import ter.main.Edge;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class CreateEdge extends JFrame {
	private static final long serialVersionUID = 1L;

	private JList<Object> possibleNeighborsList = new JList<Object>();
	private JList<Object> functionList;
	private JPanel jPanelFunction;
	private JPanel jPanelPossibleNeighbors;
	private JScrollPane functionScrollPane;
	private JScrollPane possibleNeighborsScrollPane = new JScrollPane();

	private static String number;

	protected static DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	private JButton showData;

	private JTextField textLeftEdge;
	private JTextField textRightEdge;

	private DefaultListModel<Object> modelFunction = null;
	private DefaultListModel<Object> modelPossibleNeighbors = null;

	private JLabel labelSign;
	private JLabel labelFunction;
	private JRadioButton raioButtonNegative;
	private JRadioButton raioButtonPositive;
	private JButton createButton;
	private String signButton = new String();

	private JLabel labelNeighbors;
	private ButtonGroup group;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMap<String, Edge> edgeHashMap = null;
					CreateEdge frame = new CreateEdge(number, edgeHashMap);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CreateEdge(String number, HashMap<String, Edge> edgeHashMap) {
		getContentPane().setPreferredSize(new Dimension(560, 370));

		setTitle("Ter Project");
		setLocation(new Point(650, 190));
		setResizable(false);

		textLeftEdge = new JTextField();
		textLeftEdge.setEditable(false);
		textLeftEdge.setColumns(10);

		textRightEdge = new JTextField();
		textRightEdge.setEditable(false);
		textRightEdge.setColumns(10);
		textLeftEdge.setText("");
		textRightEdge.setText("");
		
		// State
		jPanelFunction = new JPanel();
		jPanelFunction.setBorder(BorderFactory.createTitledBorder("Function"));

		modelFunction = new DefaultListModel<Object>();

		for (int i = 1; i <= Integer.valueOf(number); i++) {
			modelFunction.addElement(i);
		}

		functionList = new JList<Object>(modelFunction);
		functionScrollPane = new JScrollPane(functionList);

		jPanelPossibleNeighbors = new JPanel();
		jPanelPossibleNeighbors.setBorder(BorderFactory.createTitledBorder("Possible neighbors"));

		modelPossibleNeighbors = new DefaultListModel<Object>();

		functionList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				modelPossibleNeighbors.clear();
				for (int i = 1; i <= Integer.valueOf(number); i++) {
					
						modelPossibleNeighbors.addElement(i);
				}
			
					textLeftEdge.setText(functionList.getSelectedValue().toString());
			}

		});
		possibleNeighborsList = new JList<Object>(modelPossibleNeighbors);
		possibleNeighborsScrollPane = new JScrollPane(possibleNeighborsList);

		possibleNeighborsList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				textRightEdge.setText(possibleNeighborsList.getSelectedValue().toString());

					textRightEdge.setText(possibleNeighborsList.getSelectedValue().toString());
			}

		});

		// SET Layout

		labelFunction = new JLabel("Selected Function:");

		labelNeighbors = new JLabel("Selected Neighbors:");

		labelSign = new JLabel("Select Edge Sign :");

		raioButtonPositive = new JRadioButton("+");
		raioButtonNegative = new JRadioButton("-");

		group = new ButtonGroup();
		group.add(raioButtonPositive);
		group.add(raioButtonNegative);

		createButton = new JButton("Create Link");
		createButton.setMinimumSize(new Dimension(100, 21));
		createButton.setMaximumSize(new Dimension(90, 21));
		createButton.setPreferredSize(new Dimension(80, 21));
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (checkData()) {

					if (group.isSelected(raioButtonPositive.getModel())) {
						signButton = "+1";
						//signButton = "POSITIVE";
					} else {
						signButton = "-1";
						//signButton = "NEGATIVE";
					}

					String ID = textLeftEdge.getText().toString() + "," + signButton + ","
							+ textRightEdge.getText().toString();
					String leftEdge = textLeftEdge.getText().toString();
					String edgeSign = signButton;
					String rightEdge = textRightEdge.getText().toString();

					Edge edge = new Edge(ID, leftEdge, edgeSign, rightEdge);
					edgeHashMap.put(ID, edge);

					ShowInterface showInterface = new ShowInterface("The link from " + leftEdge + " to " + rightEdge
							+ ", with sign " + edgeSign + " was created. Press FINISH to validate.");
					showInterface.setVisible(true);

					textLeftEdge.setText("");
					textRightEdge.setText("");
					group.clearSelection();

				}
			}
		});

		showData = new JButton("Finish");
		showData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				StateEdgePanel.edgeHashMap = edgeHashMap;
				StateEdgePanel.updateEdge();
				setVisible(false);
			}
		});

		GroupLayout jPanelGroupVertex = new GroupLayout(jPanelFunction);
		jPanelGroupVertex.setHorizontalGroup(jPanelGroupVertex.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanelGroupVertex.createSequentialGroup().addContainerGap()
						.addComponent(functionScrollPane, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
						.addContainerGap()));
		jPanelGroupVertex
				.setVerticalGroup(jPanelGroupVertex.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						jPanelGroupVertex.createSequentialGroup().addContainerGap()
								.addComponent(functionScrollPane, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addContainerGap()));
		jPanelFunction.setLayout(jPanelGroupVertex);

		GroupLayout jPanelGroupEdge = new GroupLayout(jPanelPossibleNeighbors);
		jPanelGroupEdge.setHorizontalGroup(jPanelGroupEdge.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanelGroupEdge
						.createSequentialGroup().addContainerGap().addComponent(possibleNeighborsScrollPane,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
						.addContainerGap()));
		jPanelGroupEdge.setVerticalGroup(jPanelGroupEdge.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, jPanelGroupEdge.createSequentialGroup().addContainerGap()
						.addComponent(possibleNeighborsScrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addContainerGap()));
		jPanelPossibleNeighbors.setLayout(jPanelGroupEdge);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(labelSign, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelNeighbors, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addComponent(textRightEdge, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
									.addComponent(raioButtonPositive)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(raioButtonNegative, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(jPanelFunction, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(labelFunction, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textLeftEdge, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addGap(16)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(createButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(74)
							.addComponent(showData, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
						.addComponent(jPanelPossibleNeighbors, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jPanelFunction, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
						.addComponent(jPanelPossibleNeighbors, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelFunction)
						.addComponent(textLeftEdge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelNeighbors, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(textRightEdge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelSign, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(raioButtonPositive)
						.addComponent(raioButtonNegative)
						.addComponent(createButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(showData, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(31))
		);

		getContentPane().setLayout(layout);
		pack();

	}

	public boolean checkData() {
		boolean validation = false;
		ArrayList<String> arrayShowDataError = new ArrayList<String>();

		if (!group.isSelected(raioButtonPositive.getModel()) && !group.isSelected(raioButtonNegative.getModel())) {
			arrayShowDataError.add("Select a sign.");
		}

		if (textLeftEdge.getText().toString().equals("")) {
			arrayShowDataError.add("Selecting a function.");
		}
		if (textRightEdge.getText().toString().equals("")) {
			arrayShowDataError.add("Selecting a neighbor.");
		}

		if (arrayShowDataError.size() != 0) {

			ShowInterface showInterface = new ShowInterface(arrayShowDataError);
			showInterface.setVisible(true);
			arrayShowDataError.clear();
			validation = false;

		} else {
			validation = true;
		}

		return validation;

	}
}

/*
 * for(Entry<String, Edge> ed : edgeHashMap.entrySet()) {
 * System.out.println("Key: "+ed.getKey()); Edge asd = ed.getValue();
 * System.out.println("Left: "+asd.getLeftEdge());
 * System.out.println("Sign: "+asd.getEdgeSign());
 * System.out.println("Right: "+asd.getRightEdge()); }
 */
// CreateGraph.graph = Utility.analyzerGraph();
