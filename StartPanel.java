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

import org.jfree.ui.RefineryUtilities;

import ter.utility.Utility;

import javax.swing.SwingConstants;

/**
 * @author Valeria Bellusci, Emanuele Galasso This file is protected by
 *         Copyright
 */

public class StartPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel labelStartPanel;
	private JButton buttonManualStartPanel;
	private JButton buttonFileStartPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartPanel frame = new StartPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartPanel() {

//      CALL TO CHART-------------------------------------
		// var ex = new DynamicDataDemo();
		// ex.setVisible(true);
//       -------------------------------------

		

		setSize(425, 200);
		setLocationRelativeTo(null);
		setTitle("Ter Project");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelStartPanel = new JLabel("Select the type of input:");
		labelStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		labelStartPanel.setHorizontalAlignment(SwingConstants.CENTER);
		labelStartPanel.setBounds(20, 48, 379, 14);
		contentPane.add(labelStartPanel);

		buttonManualStartPanel = new JButton("Manual");
		buttonManualStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonManualStartPanel.setBounds(142, 72, 135, 25);
		contentPane.add(buttonManualStartPanel);

		buttonManualStartPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EnterVerticesPanel terPanel = new EnterVerticesPanel();
				terPanel.setVisible(true);
				setVisible(false);
			}
		});

		buttonFileStartPanel = new JButton("File");
		buttonFileStartPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonFileStartPanel.setBounds(142, 113, 135, 25);
		contentPane.add(buttonFileStartPanel);

		JLabel titleLabel = new JLabel("<html>FPT algorithm for the interaction graph consistency problem</html>");
		titleLabel.setFont(new Font("MV Boli", Font.PLAIN + Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(20, 10, 379, 28);
		contentPane.add(titleLabel);

		buttonFileStartPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				jfc.setDialogTitle("Choose a file: ");
				jfc.setAcceptAllFileFilterUsed(false);

				FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					HashMap<String, String> fileMap = Utility.readFile(jfc.getSelectedFile().getPath().toString());

					if (jfc.getSelectedFile().exists() && (!fileMap.isEmpty())) {
						String numberElement = null;

						for (Entry<String, String> mapElement : fileMap.entrySet()) {

							String key = mapElement.getKey();

							String[] lengthKey = key.split(",");
							numberElement = String.valueOf(lengthKey.length);

							break;
						}
						StateEdgePanel state = new StateEdgePanel(numberElement, fileMap, true);
						state.setVisible(true);
						setVisible(false);
					} else {
						ShowInterface showInterface = new ShowInterface("The selected file is incorrect");
						showInterface.setVisible(true);
					}
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
}