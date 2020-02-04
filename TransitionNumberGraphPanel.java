package ter.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import ter.main.Edge;
import ter.main.GraphGeneratorManagement;
import ter.utility.Utility;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 * @author Valeria Bellusci, Emanuele Galasso This file is protected by
 *         Copyright
 */

public class TransitionNumberGraphPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel labelStartPanel;
	private JButton buttonManualStartPanel;
	private JTextField textField;

	private static HashMap<String, String> map = null;
	private static HashMap<String, Edge> edgeHashMap = null;
	private static String number;
	private JTextField numberOfComponent;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransitionNumberGraphPanel frame = new TransitionNumberGraphPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TransitionNumberGraphPanel() {
		setSize(425, 217);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelStartPanel = new JLabel("How many graphs do you want generate?");
		labelStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		labelStartPanel.setHorizontalAlignment(SwingConstants.LEFT);
		labelStartPanel.setBounds(20, 48, 379, 14);
		contentPane.add(labelStartPanel);

		textField = new JTextField();
		textField.setBounds(20, 72, 234, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		buttonManualStartPanel = new JButton("Generate");
		buttonManualStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonManualStartPanel.setBounds(264, 138, 135, 25);
		contentPane.add(buttonManualStartPanel);

		JLabel titleLabel = new JLabel("Extension of partial networks with Fixed points");
		titleLabel.setFont(new Font("MV Boli", Font.PLAIN + Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(20, 10, 379, 28);
		contentPane.add(titleLabel);

		numberOfComponent = new JTextField();
		numberOfComponent.setColumns(10);
		numberOfComponent.setBounds(20, 138, 234, 25);
		contentPane.add(numberOfComponent);

		JLabel numberOfComponentLabel = new JLabel("How many componets do you want generate for the network?");
		numberOfComponentLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		numberOfComponentLabel.setHorizontalAlignment(SwingConstants.LEFT);
		numberOfComponentLabel.setBounds(20, 114, 379, 14);
		contentPane.add(numberOfComponentLabel);

		buttonManualStartPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// forse genera un grafo in meno da vedere
				String value0 = "";
				String value1 = "";

				for (int i = 0; i < Integer.valueOf(numberOfComponent.getText().toString()); i++) {

					if (value0.equals(""))
						value0 = value0 + "0";
					else
						value0 = value0 + "," + "0";

					if (value1.equals(""))
						value1 = value1 + "1";
					else
						value1 = value1 + "," + "1";
				}

				map = new HashMap<String, String>();
				map.put(value0, value0);
				map.put(value1, value1);

				new GraphGeneratorManagement(Integer.valueOf(textField.getText().toString()),
						numberOfComponent.getText().toString(), map, null);

			}
		});

		// close all
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);

			}
		});

	}
}

/*
 * edgeHashMap = Utility.graphGenerator(number); if (edgeHashMap.isEmpty()) {
 * System.out.println("EMPTY"); }
 * 
 * DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph =
 * Utility.analyzerGraph(Integer.valueOf(number), edgeHashMap); new
 * MainPanel(number,map, graph,edgeHashMap,false); setVisible(false);
 */