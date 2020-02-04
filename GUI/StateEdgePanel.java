package ter.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.SwingConstants;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import ter.main.Edge;
import ter.utility.Utility;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class StateEdgePanel extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel jPanelState;
	private JScrollPane stateScrollPane;
	private JButton addStateButton;
	private JButton deleteStateButton;

	private JPanel jPanelEdge;
	private static JScrollPane edgeScrollPane;
	private JButton addEdgeButton;
	private JButton deleteEdgeButton;

	private JList<Object> stateList;
	private JList<Object> edgeList;
	private mxGraphComponent mxGraphComponent;
	private static DefaultListModel<Object> modelState = new DefaultListModel<Object>();
	private static DefaultListModel<Object> modelEdge = new DefaultListModel<Object>();

	protected static HashMap<String, String> map = new HashMap<String, String>();

	protected static HashMap<String, Edge> edgeHashMap = null;

	protected static DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph;

	private static String number;

	private ArrayList<String> arrayShowData = null;

	private JButton showGraphButton;
	private JButton startAlgorithmButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StateEdgePanel frame = new StateEdgePanel(number, map,false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StateEdgePanel(String number, HashMap<String, String> map,Boolean useFile) {

		graph = new DirectedWeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		edgeHashMap = new HashMap<String, Edge>();
		
		setTitle("Ter Project");
		setLocation(new Point(450, 220));
		setSize(new Dimension(645, 340));
		setResizable(false);

		// State

		jPanelState = new JPanel();
		jPanelState.setBounds(10, 10, 308, 235);
		jPanelState.setBorder(BorderFactory.createTitledBorder("States"));

		addStateButton = new JButton();
		addStateButton.setText("Add State");

		addStateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if(useFile!=true) {
					CreatePartialBooleanNetwork create = new CreatePartialBooleanNetwork(map, number, true);
					create.setVisible(true);
					}
					else {

						WarningFile temp = new WarningFile();
						temp.setVisible(true);
						JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

						jfc.setDialogTitle("Choose a file: ");
						jfc.setAcceptAllFileFilterUsed(false);

						FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
						jfc.addChoosableFileFilter(filter);

						int returnValue = jfc.showOpenDialog(null);


						if (returnValue == JFileChooser.APPROVE_OPTION) {

							HashMap<String, String> fileMap = Utility
									.readFile(jfc.getSelectedFile().getPath().toString());
							
							if (jfc.getSelectedFile().exists() && (!fileMap.isEmpty())) {
								String numberElement = null;

								for (Entry<String, String> mapElement : fileMap.entrySet()) {

									String key = mapElement.getKey();
									
									String[] lengthKey = key.split(",");
									numberElement = String.valueOf(lengthKey.length);

									break;
								}
								
								
								modelState.removeAllElements();
								map.clear();
								map.putAll(fileMap);
								StateEdgePanel.map=fileMap;
								updateState();
								temp.setVisible(false);
							} else {
								ShowInterface showInterface = new ShowInterface("The selected file is incorrect");
								showInterface.setVisible(true);
							}
						}else {

							temp.setVisible(false);
						}
					}

				} catch (Exception e1) {
					ShowInterface showInterface = new ShowInterface("Error.");
					showInterface.setVisible(true);
				}
			}
		});

		deleteStateButton = new JButton();
		deleteStateButton.setText("Delete State");
		deleteStateButton.setEnabled(false);

		modelState = new DefaultListModel<Object>();

		for (Entry<String, String> mapElement : map.entrySet()) {
			modelState.addElement(String
					.format("State: " + mapElement.getKey() + "                    Value: " + mapElement.getValue()));
		}
		
		stateScrollPane = new JScrollPane();
		deleteStateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showOptionDialog(null, "Delete element?", "Attention",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

				// interpret the user's choice
				if (choice == JOptionPane.YES_OPTION) {
					String[] strKey = stateList.getSelectedValue().toString().replaceAll(" ","").replaceAll("State:", "").replaceAll("Value:",";").split(";");
					map.remove(strKey[0]);
					modelState.remove(stateList.getSelectedIndex());
					new JList<Object>(modelState);
					deleteStateButton.setEnabled(false);
				}

			}
		});

		// Edge

		jPanelEdge = new JPanel();
		jPanelEdge.setBounds(331, 10, 290, 235);
		jPanelEdge.setBorder(BorderFactory.createTitledBorder("Edges"));

		addEdgeButton = new JButton();
		addEdgeButton.setText("Add Edge");

		addEdgeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CreateEdge createEdge = new CreateEdge(number, edgeHashMap);
					createEdge.setVisible(true);
				} catch (Exception e1) {
					ShowInterface showInterface = new ShowInterface("Error.");
					showInterface.setVisible(true);
				}
			}
		});

		deleteEdgeButton = new JButton();
		deleteEdgeButton.setText("Delete Edge");
		deleteEdgeButton.setEnabled(false);

		edgeList = new JList<Object>(modelEdge);
		edgeScrollPane = new JScrollPane(edgeList);

		edgeList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				deleteEdgeButton.setEnabled(true);
			}

		});

		deleteEdgeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showOptionDialog(null, "Delete element?", "Quit?", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);

				// interpret the user's choice
				if (choice == JOptionPane.YES_OPTION) {

					edgeHashMap.remove(takeRemoveString(edgeList.getSelectedValue().toString()));
					modelEdge.remove(edgeList.getSelectedIndex());
					new JList<Object>(modelEdge);
					deleteEdgeButton.setEnabled(false);

				}

			}
		});

		// Button
		showGraphButton = new JButton("Show Graph");
		showGraphButton.setBounds(333, 255, 120, 26);
		showGraphButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				graph = Utility.analyzerGraph(Integer.valueOf(number), edgeHashMap);
				Graph<String, DefaultWeightedEdge> graph2 = graph;

				JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<String, DefaultWeightedEdge>(
						graph2);

				Map<String, Object> edgeStyle = new HashMap<String, Object>();

				//edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
				edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
				edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
				edgeStyle.put(mxConstants.STYLE_NOLABEL, true);

				Object[] cells = graphAdapter.getChildEdges(graphAdapter.getDefaultParent());
				Object[] edgeCellArray = new Object[1];
				HashMap<DefaultWeightedEdge, mxICell> edgeToCellMap
				  = graphAdapter.getEdgeToCellMap();
				
				mxStylesheet stylesheet = graphAdapter.getStylesheet();
				for (Object o : cells) {
					mxCell c = (mxCell) o;
					if (c.getValue() != null) {
						String[] str = c.getValue().toString().replace("(", "").replaceAll(" ", "")
								.replace(")", "").split("[(:)\\s]");
						DefaultWeightedEdge defW = graph2.getEdge(str[0], str[1]);
						
						if (graph2.getEdgeWeight(defW) == 1.0) {
							edgeCellArray[0] = (Object)(edgeToCellMap.get(graph2.getEdge(str[0], str[1])));
							graphAdapter.setCellStyle("strokeColor=#33CC00",edgeCellArray); 
						}else if(graph2.getEdgeWeight(defW) == -1.0) {
							edgeCellArray[0] = (Object)(edgeToCellMap.get(graph2.getEdge(str[0], str[1])));
							graphAdapter.setCellStyle("strokeColor=#FF3333",edgeCellArray);
							
							
						}
					}
				}

				stylesheet.setDefaultEdgeStyle(edgeStyle);
				graphAdapter.setStylesheet(stylesheet);

				new mxCircleLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
				new mxParallelEdgeLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
				//mxUtils.getHexColorString(Color.GREEN)
				
				graphAdapter.setCellsEditable(false); graphAdapter.setCellsMovable(false);
				graphAdapter.setEdgeLabelsMovable(false);
				graphAdapter.setCellsDeletable(false);
				graphAdapter.setCellsDisconnectable(false);
				graphAdapter.setCellsResizable(false); graphAdapter.setCellsBendable(false);
				graphAdapter.setCellsCloneable(false);
				graphAdapter.setConnectableEdges(false);
				graphAdapter.setVertexLabelsMovable(false);
				graphAdapter.setSplitEnabled(false); graphAdapter.setCellsSelectable(false);
				
				
				JFrame frame = new JFrame("Ter Project");
				//frame.getContentPane().add(new mxGraphComponent(graphAdapter));
				mxGraphComponent = new mxGraphComponent(graphAdapter);
				frame.getContentPane().add(mxGraphComponent,BorderLayout.CENTER);
				frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}

		});

		startAlgorithmButton = new JButton("Start");
		startAlgorithmButton.setBounds(12, 255, 100, 26);
		startAlgorithmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				arrayShowData = new ArrayList<String>();

				if (map.isEmpty())
					arrayShowData.add("State list is empty.");

				if ((edgeList.getModel().getSize() == 0))
					arrayShowData.add("Edge list is empty.");

				if (arrayShowData.size() != 0) {

					ShowInterface showInterface = new ShowInterface(arrayShowData);
					showInterface.setVisible(true);
					arrayShowData.clear();

				} else {

					graph = Utility.analyzerGraph(Integer.valueOf(number), edgeHashMap);
					if (checkVertex(Integer.valueOf(number), graph)) {
						new MainPanel(number,map, graph,edgeHashMap,useFile);
						setVisible(false);

					}
				}

			}
		});
		GroupLayout jPanelGroupVertex = new GroupLayout(jPanelState);
		jPanelGroupVertex.setHorizontalGroup(jPanelGroupVertex.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanelGroupVertex.createSequentialGroup().addContainerGap()
						.addGroup(jPanelGroupVertex.createParallelGroup(Alignment.LEADING)
								.addComponent(stateScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(jPanelGroupVertex.createSequentialGroup().addComponent(addStateButton)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(deleteStateButton)))
						.addContainerGap()));
		jPanelGroupVertex.setVerticalGroup(jPanelGroupVertex.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanelGroupVertex.createSequentialGroup().addContainerGap()
						.addGroup(jPanelGroupVertex.createParallelGroup(Alignment.BASELINE).addComponent(addStateButton)
								.addComponent(deleteStateButton))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(stateScrollPane,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
						.addContainerGap()));
		jPanelGroupVertex.linkSize(SwingConstants.VERTICAL, new Component[] { addStateButton, deleteStateButton });
		jPanelGroupVertex.linkSize(SwingConstants.HORIZONTAL, new Component[] { addStateButton, deleteStateButton });

		stateList = new JList<Object>(modelState);
		stateScrollPane.setViewportView(stateList);

		stateList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				deleteStateButton.setEnabled(true);
			}

		});
		
		JButton generatorButton = new JButton("Generate a graph");
		generatorButton.setBounds(128, 255, 188, 26);
		generatorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edgeHashMap.clear();
				modelEdge.removeAllElements();
				new JList<Object>(modelEdge);
				/*TransitionNumberGraphPanel transitionNumberGraphPanel = new TransitionNumberGraphPanel(number, map, edgeHashMap);
				transitionNumberGraphPanel.setVisible(true);
				setVisible(false);*/
				edgeHashMap = Utility.graphGeneratorV2(number);
				if (edgeHashMap.isEmpty()) {
					System.out.println("EMPTY");
				}

				DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph = Utility.analyzerGraph(Integer.valueOf(number), edgeHashMap);
		//		System.out.println("MAPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP2 "+map.toString());

				new MainPanel(number,map, graph,edgeHashMap,useFile);
				setVisible(false);

			}
		});
		jPanelState.setLayout(jPanelGroupVertex);

		GroupLayout jPanelGroupEdge = new GroupLayout(jPanelEdge);
		jPanelEdge.setLayout(jPanelGroupEdge);
		jPanelGroupEdge.setHorizontalGroup(jPanelGroupEdge.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanelGroupEdge.createSequentialGroup().addContainerGap().addGroup(jPanelGroupEdge
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanelGroupEdge.createSequentialGroup().addComponent(addEdgeButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(deleteEdgeButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGap(0, 0, Short.MAX_VALUE))
						.addComponent(edgeScrollPane)).addContainerGap()));

		jPanelGroupEdge.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { addEdgeButton, deleteEdgeButton });

		jPanelGroupEdge.setVerticalGroup(jPanelGroupEdge.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanelGroupEdge.createSequentialGroup().addContainerGap()
						.addGroup(jPanelGroupEdge.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(addEdgeButton).addComponent(deleteEdgeButton))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(edgeScrollPane)
						.addContainerGap()));

		jPanelGroupEdge.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { addEdgeButton, deleteEdgeButton });
		getContentPane().setLayout(null);
		getContentPane().add(jPanelState);
		getContentPane().add(startAlgorithmButton);
		getContentPane().add(generatorButton);
		getContentPane().add(jPanelEdge);
		getContentPane().add(showGraphButton);

		// close all
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);

			}
		});
	}

	public static void updateEdge() {
		for (Entry<String, Edge> mapElement : edgeHashMap.entrySet()) {
			Edge edgeClass = mapElement.getValue();
			String row = "From " + edgeClass.getLeftEdge() + " to " + edgeClass.getRightEdge() + " with "
					+ edgeClass.getEdgeSign() + " edge.";
			if (!modelEdge.contains(row))
				modelEdge.addElement(row);
		}
	}

	public static void updateState() {

		for (Entry<String, String> mapElement : map.entrySet()) {
			String row = "State: " + mapElement.getKey() + "                    Value: " + mapElement.getValue();

			if (!modelState.contains(row))
				modelState.addElement(row);
			/*else {
				ShowInterface showInterface = new ShowInterface("Some elements are already present");
				showInterface.setVisible(true);
				/**TODO maybe
			}*/
		}
	}

	public String takeRemoveString(String s) {
		String newString = "";
		String[] val = s.split(" ");
		newString = val[1] + "," + val[5] + "," + val[3];
		return newString;
	}

	public boolean checkVertex(int number, DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph) {
		boolean validation = false;

		ArrayList<String> arrayCheckVertex = new ArrayList<String>();

		for (int i = 0; i < number; i++) {
			if (graph.incomingEdgesOf(String.valueOf(i + 1)).isEmpty()) {
				arrayCheckVertex.add("Vertex " + (i + 1) + " has no incoming link.");
			}
		}

		if (arrayCheckVertex.size() != 0) {

			ShowInterface showInterface = new ShowInterface(arrayCheckVertex);
			showInterface.setVisible(true);
			validation = false;
			arrayCheckVertex.clear();

		} else {
			validation = true;
		}
		return validation;
	}

	public static class MyEdge extends DefaultWeightedEdge {

		private static final long serialVersionUID = 1L;

		@Override
		public String toString() {
			return String.valueOf(getWeight());
		}
	}
}
