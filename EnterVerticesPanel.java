package ter.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

/**
 * @author Valeria Bellusci, Emanuele Galasso This file is protected by
 *         Copyright
 */

public class EnterVerticesPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private ArrayList<String> arrayShowDataError = null;

	private JPanel contentPane;
	private JLabel labelPartialB;
	private JLabel labelPartialBNumber;
	private JTextField textPartialB;
	private JButton buttonPartialB;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterVerticesPanel frame = new EnterVerticesPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EnterVerticesPanel() {
		setSize(266, 165);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelPartialB = new JLabel("<html>Partial Boolean network builder</html>");
		labelPartialB.setFont(new Font("MV Boli", Font.PLAIN + Font.BOLD, 12));
		labelPartialB.setHorizontalAlignment(SwingConstants.CENTER);
		labelPartialB.setBounds(23, 21, 209, 14);
		contentPane.add(labelPartialB);

		labelPartialBNumber = new JLabel("Enter number of Vertices:");
		labelPartialBNumber.setBounds(23, 54, 146, 23);
		contentPane.add(labelPartialBNumber);

		textPartialB = new JTextField();
		textPartialB.setHorizontalAlignment(JTextField.CENTER);
		textPartialB.setBounds(179, 54, 53, 23);
		contentPane.add(textPartialB);
		textPartialB.setColumns(10);

		buttonPartialB = new JButton("Create");
		buttonPartialB.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonPartialB.setBounds(23, 87, 209, 25);
		contentPane.add(buttonPartialB);

		buttonPartialB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				arrayShowDataError = new ArrayList<String>();
				
				if (textPartialB.getText().toString().isEmpty()) {
					arrayShowDataError.add("The box is empty.");
				}

				if (!isNumeric(textPartialB.getText().replaceAll("\\s","").toString())
						|| (Integer.valueOf(textPartialB.getText().replaceAll("\\s","").toString()) <= 1)) {

					arrayShowDataError.add("Enter a valid number.");
					
				}else if(Integer.valueOf(textPartialB.getText().replaceAll("\\s","").toString())>20) {
					StartPanel startPanel = new StartPanel();
					startPanel.setVisible(true);
					ShowInterface showInterface = new ShowInterface("There are many components, it is recommended to upload data from a file");
					showInterface.setVisible(true);
					setVisible(false);
					

				}else {

					CreatePartialBooleanNetwork createPBN = new CreatePartialBooleanNetwork(
							new HashMap<String, String>(), textPartialB.getText().replaceAll("\\s+","").toString(), false);

					createPBN.setVisible(true);
					setVisible(false);
				}
				if (arrayShowDataError.size() != 0) {

					ShowInterface showInterface = new ShowInterface(arrayShowDataError);
					showInterface.setVisible(true);

					arrayShowDataError.clear();
				}
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

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}