package com.cogentautomation.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import com.cogentautomation.controller.FileListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewFolderChooser extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileListener fileListener;
	
	public void setFileListener(FileListener listener) {
		fileListener = listener;
	}

	/**
	 * Create the dialog.
	 */
	public ViewFolderChooser() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Select Destination Folder");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/cogentautomation/Cogent-Automation-Logo.png")));
		setAlwaysOnTop(true);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Save");
		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()) {
					case "ApproveSelection":
						fileListener.fileEmitted(fileChooser.getSelectedFile());
						break;
					case "CancelSelection":
					default:
						break;
				}
				setVisible(false);
			}
		});
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		getContentPane().add(fileChooser, BorderLayout.CENTER);
	}

}
