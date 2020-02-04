package ter.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


import ter.utility.Utility;

import javax.swing.SwingConstants;
import java.awt.Point;

/**
 * @author Valeria Bellusci, Emanuele Galasso This file is protected by
 *         Copyright
 */

public class WarningFile extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel labelStartPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarningFile frame = new WarningFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WarningFile() {
		setSize(425, 200);
		setLocation(50,100);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelStartPanel = new JLabel("Format: \n In this screen you can upload the file after editing it.");
		labelStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		labelStartPanel.setHorizontalAlignment(SwingConstants.CENTER);
		labelStartPanel.setBounds(20, 10, 379, 130);
		contentPane.add(labelStartPanel);

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
