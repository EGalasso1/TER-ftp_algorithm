package ter.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.SystemColor;

public class ShowInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int height = 0;
	static String showString;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowInterface frame = new ShowInterface(showString);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ShowInterface() {

	}

	public ShowInterface(ArrayList<String> arrayString) {

		int arraySize = arrayString.size();
		if (arraySize < 2)
			height = 0;
		else
			height = arraySize * 10;

		setSize(255, height + 125);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textSwitch = new JTextPane();
		textSwitch.setBackground(SystemColor.control);

		StyledDocument doc = textSwitch.getStyledDocument();
		
		SimpleAttributeSet center = new SimpleAttributeSet();
		try {
			for (String s : arrayString)
				doc.insertString(doc.getLength(), s + "\n", null);
		} catch (BadLocationException e1) {

			e1.printStackTrace();
		}
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textSwitch.setEditable(false);

		textSwitch.setBounds(10, 11, 229, height + 40);
		contentPane.add(textSwitch);

		JButton buttonSwitch = new JButton("OK");
		buttonSwitch.setBounds(85, height + 60, 90, 25);
		contentPane.add(buttonSwitch);

		buttonSwitch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				setVisible(false);

			}
		});

	}

	public ShowInterface(String showString) {

		setSize(255, 125);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textSwitch = new JTextPane();
		textSwitch.setBackground(SystemColor.control);
		textSwitch.setBounds(10, 11, 229, 40);
		contentPane.add(textSwitch);

		StyledDocument doc = textSwitch.getStyledDocument();

		SimpleAttributeSet center = new SimpleAttributeSet();

		try {
			doc.insertString(0, showString, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textSwitch.setEnabled(true);
		textSwitch.setEditable(false);

		JButton buttonSwitch = new JButton("OK");
		buttonSwitch.setBounds(80, 60, 90, 25);
		contentPane.add(buttonSwitch);

		buttonSwitch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				setVisible(false);

			}
		});
	}
}
