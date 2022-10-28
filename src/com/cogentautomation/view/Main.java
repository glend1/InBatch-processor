package com.cogentautomation.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cogentautomation.controller.EditView;
import com.cogentautomation.controller.Export;
import com.cogentautomation.controller.FileListener;
import com.cogentautomation.model.SheetData;

import javax.swing.BoxLayout;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private ViewFileChooser fileChooser;
	private ViewFolderChooser folderChooser;
	private JPanel panel_1;
	private List<SheetData> data = new ArrayList<SheetData>();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Fake InBatch Thing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 630);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/cogentautomation/Cogent-Automation-Logo.png")));
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[424px,grow]", "[23px][206px,grow][23px]"));
		
		EditView ev = new EditView();
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,growx,aligny top");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		textField.setEnabled(false);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fileChooser = new ViewFileChooser();
				fileChooser.setFileListener(new FileListener() {

					@Override
					public void fileEmitted(File file) {
						textField.setText(file.getName());
						setData(ev.open(panel_1, file, scrollPane));
					}
				});
				fileChooser.setVisible(true);
			}
		});
		panel.add(btnBrowse);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		contentPane.add(scrollPane, "cell 0 1,grow");
		
		panel_1 = new JPanel();
		panel_1.setBorder(null);
		/*scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new MigLayout("", "[grow][][][grow][grow][grow]", "[][]"));
		
		JLabel lblSheetname = new JLabel("Sheet Name");
		panel_1.add(lblSheetname, "cell 0 0");
		
		JLabel lblRowFrom = new JLabel("Row From");
		panel_1.add(lblRowFrom, "cell 1 0");
		
		JLabel lblRowTo = new JLabel("Row To");
		panel_1.add(lblRowTo, "cell 2 0");
		
		JLabel lblColumnIndex = new JLabel("Column Index");
		panel_1.add(lblColumnIndex, "cell 3 0");
		
		JLabel lblColumnMessage = new JLabel("Column Message");
		panel_1.add(lblColumnMessage, "cell 4 0");
		
		JLabel lblFilename = new JLabel("File Name");
		panel_1.add(lblFilename, "cell 5 0");*/
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				folderChooser = new ViewFolderChooser();
				folderChooser.setFileListener(new FileListener() {

					@Override
					public void fileEmitted(File folder) {
						Export ex = new Export();
						ex.save(data, folder);
					}
				});
				folderChooser.setVisible(true);
			}
		});
		contentPane.add(btnExport, "cell 0 2,growx,aligny top");
	}

	public List<SheetData> getData() {
		return data;
	}

	public void setData(List<SheetData> data) {
		this.data = data;
	}
}
