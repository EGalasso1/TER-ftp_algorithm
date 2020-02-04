package ter.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

public class CreatePartialBooleanNetwork extends JFrame {

	private static final long serialVersionUID = 1L;

	private ArrayList<JTextField> leftData = null;
	private ArrayList<JTextField> rightData = null;

	private JPanel contentPane;
	private JPanel contentPaneElement;
	private static String number;
	private static JTextField textPartialB = null;

	private int xAxis = 70;
	private int yAxis = 35;
	private int xAxisButton = 0;

	private JLabel labelPartialBLeft;
	private JButton buttonAddPartialB;
	private JButton buttonPartialB;
	private JLabel labelPartialBRight;

	private static boolean value = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMap<String, String> map = null;
					CreatePartialBooleanNetwork frame = new CreatePartialBooleanNetwork(map, number, value);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CreatePartialBooleanNetwork(HashMap<String, String> map, String number, Boolean value) {

		leftData = new ArrayList<JTextField>();
		rightData = new ArrayList<JTextField>();

		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(100, 100));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		contentPaneElement = new JPanel();
		contentPaneElement.setBounds(10, 59, 81, 31);
		contentPaneElement.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scrol = new JScrollPane(contentPaneElement);
		scrol.setViewportView(contentPaneElement);
		scrol.setLocation(10, 60);

		labelPartialBLeft = new JLabel("Enter 0 or 1 on the LEFT");
		labelPartialBLeft.setForeground(Color.RED);
		labelPartialBLeft.setBounds(10, 15, 205, 15);
		contentPane.add(labelPartialBLeft);

		labelPartialBRight = new JLabel("Enter 0,1 or * on the RIGHT");
		labelPartialBRight.setForeground(Color.RED);
		labelPartialBRight.setBounds(10, 40, 205, 15);
		contentPane.add(labelPartialBRight);

		for (int i = 0; i < Integer.valueOf(number); i++) {
			textPartialB = new JTextField();
			textPartialB.setHorizontalAlignment(JTextField.CENTER);
			textPartialB.setBounds(xAxis, 5, 25, 25);
			contentPaneElement.add(textPartialB);
			xAxis = xAxis + 30;
			leftData.add(textPartialB);
		}
		xAxis = xAxis + 30;

		JLabel space = new JLabel(" ");
		contentPaneElement.add(space);

		for (int i = 0; i < Integer.valueOf(number); i++) {
			textPartialB = new JTextField();
			textPartialB.setHorizontalAlignment(JTextField.CENTER);
			textPartialB.setBounds(xAxis, 5, 25, 25);
			contentPaneElement.add(textPartialB);
			xAxis = xAxis + 30;
			rightData.add(textPartialB);
		}

		scrol.setSize(xAxis + 50, yAxis);

		contentPaneElement.setSize(xAxis, yAxis);
		setSize(xAxis + 80, yAxis + 180);
		xAxisButton = (getSize().width / 2) - (109);

		buttonAddPartialB = new JButton("Add");
		buttonAddPartialB.setPreferredSize(new Dimension(120, 25));
		buttonAddPartialB.setBounds(xAxisButton + 8, 100, 205, 25);

		buttonPartialB = new JButton("Create");
		buttonPartialB.setPreferredSize(new Dimension(120, 25));
		buttonPartialB.setBounds(xAxisButton + 8, 130, 205, 25);

		contentPane.add(buttonAddPartialB);

		contentPaneElement.setLayout(new GridLayout(0, (Integer.valueOf(number) * 2) + 1, 10, 10));

		buttonAddPartialB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if ((leftData.size() / Integer.valueOf(number)) < Math.pow(2, Integer.valueOf(number))) {
						//&& ((leftData.size() != 4)||Integer.valueOf(number)==4)) {
					xAxis = 70;

					for (int i = 0; i < Integer.valueOf(number); i++) {
						textPartialB = new JTextField();
						textPartialB.setHorizontalAlignment(JTextField.CENTER);
						textPartialB.setBounds(xAxis, yAxis, 25, 25);
						contentPaneElement.add(textPartialB);
						xAxis = xAxis + 30;
						leftData.add(textPartialB);
					}
					xAxis = xAxis + 30;

					JLabel space = new JLabel(" ");
					contentPaneElement.add(space);

					for (int i = 0; i < Integer.valueOf(number); i++) {
						textPartialB = new JTextField();
						textPartialB.setHorizontalAlignment(JTextField.CENTER);
						textPartialB.setBounds(xAxis, yAxis, 25, 25);
						contentPaneElement.add(textPartialB);
						xAxis = xAxis + 30;
						rightData.add(textPartialB);
					}

					yAxis = yAxis + 30;

					if (yAxis < 350) {

						scrol.setSize(xAxis + 50, yAxis);
						buttonPartialB.setBounds(xAxisButton + 8, yAxis + 100, 203, 25);
						buttonAddPartialB.setBounds(xAxisButton + 8, yAxis + 70, 203, 25);

						setSize(xAxis + 80, yAxis + 180);

					}
					contentPaneElement.setSize(xAxis, yAxis);

				} else {
					ShowInterface showInterface = new ShowInterface("You can NO add more component");
					showInterface.setVisible(true);
				}
			}
		});

		contentPane.add(buttonPartialB);

		contentPane.add(scrol);

		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);
		setContentPane(contentPane);

		buttonPartialB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (checkData()) {

					int j = 0;
					String strLeft = "";
					String strRight = "";
					int size = leftData.size() / Integer.valueOf(number);

					for (int i = 0; i < size; i++) {
						for (j = 0; j < Integer.valueOf(number); j++) {

							if (strLeft.equals("")) {
								strLeft = strLeft + leftData.get(0).getText();
								strRight = strRight + rightData.get(0).getText();
							} else {
								strLeft = strLeft + "," + leftData.get(0).getText();
								strRight = strRight + "," + rightData.get(0).getText();
							}
							leftData.remove(0);
							rightData.remove(0);

						}
						if (!map.containsKey(strLeft)) {
							map.put(strLeft, strRight);
						}
						strLeft = "";
						strRight = "";
					}

					if (value == false) {
						StateEdgePanel graph = new StateEdgePanel(number, map,false);
						graph.setVisible(true);
						setVisible(false);
					} else {
						StateEdgePanel.map = map;
						StateEdgePanel.updateState();
						setVisible(false);
					}

				} else {
					ShowInterface showInterface = new ShowInterface("Check if all value are valid");
					showInterface.setVisible(true);
				}
			}
		});

		// close all

		if (!value) {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					System.exit(0);

				}
			});
		} else {
			setVisible(false);
		}

	}

	public boolean checkData() {
		ArrayList<Boolean> validation = new ArrayList<Boolean>();
		for (int i = 0; i < leftData.size(); i++) {

			if ((isNumeric(leftData.get(i).getText().toString()) && !leftData.get(i).getText().toString().isEmpty())
					&& ((Integer.valueOf(leftData.get(i).getText().toString()) == 0)
							|| (Integer.valueOf(leftData.get(i).getText().toString()) == 1))
					&& leftData.get(i).getText().length() == 1) {

				validation.add(true);
				leftData.get(i).setBackground(Color.WHITE);
			} else {
				validation.add(false);
				leftData.get(i).setBackground(Color.RED);
			}

			if (((isNumeric(rightData.get(i).getText().toString()) || rightData.get(i).getText().equals("*"))
					&& !rightData.get(i).getText().toString().isEmpty()) && rightData.get(i).getText().length() == 1) {

				if (!rightData.get(i).getText().equals("*")) {

					if ((Integer.valueOf(rightData.get(i).getText().toString()) == 0)
							|| (Integer.valueOf(rightData.get(i).getText().toString()) == 1)) {

						validation.add(true);
						rightData.get(i).setBackground(Color.WHITE);
					} else {
						validation.add(false);
						rightData.get(i).setBackground(Color.RED);
					}

				} else {
					validation.add(true);
					rightData.get(i).setBackground(Color.WHITE);
				}
			} else {
				validation.add(false);
				rightData.get(i).setBackground(Color.RED);
			}

		}
		if (validation.contains(false)) {
			validation.clear();
			return false;
		} else {
			validation.clear();
			return true;
		}
	}

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
