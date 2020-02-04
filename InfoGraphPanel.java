package ter.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import ter.main.Edge;
import ter.utility.Utility;

import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;


public class InfoGraphPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private static HashMap<String, Edge> map;
	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoGraphPanel frame = new InfoGraphPanel(map);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InfoGraphPanel(HashMap<String, Edge> edgeHashMap2) {
		
		setSize(339, 328);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPaneElement = new JPanel();
		contentPaneElement.setBounds(10, 59, 81, 31);
		contentPaneElement.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane(contentPaneElement);
		contentPaneElement.setLayout(new BoxLayout(contentPaneElement, BoxLayout.Y_AXIS));
		scrollPane.setBounds(10, 41, 304, 235);
		scrollPane.setVisible(true);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("List of graph arcs:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 10, 304, 21);
		contentPane.add(lblNewLabel);

		for (Entry<String, Edge> mapElement : edgeHashMap2.entrySet()) {
			Edge edgeClass = mapElement.getValue();
			String row = "From " + edgeClass.getLeftEdge() + " to " + edgeClass.getRightEdge() + " with "
					+ edgeClass.getEdgeSign() + " edge.";
			JTextField textPartialB = new JTextField(row);
			textPartialB.setEditable(false);
			textPartialB.setHorizontalAlignment(JTextField.CENTER);
			contentPaneElement.add(textPartialB);
		}
		
		// close all
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				setVisible(false);

			}
		});

	}
}
