package com.cogentautomation.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cogentautomation.model.SheetData;
import com.cogentautomation.view.ViewError;

import net.miginfocom.swing.MigLayout;

public class EditView {
	
	private ViewError viewError;

	public List<SheetData> open(JPanel panel_1, File file, JScrollPane scrollPane) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			ViewError viewError = new ViewError("Unable to export files");
			viewError.setVisible(true);
		}

		panel_1.removeAll();
		
		scrollPane.setViewportView(panel_1);
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
		panel_1.add(lblFilename, "cell 5 0");
		
		List<SheetData> sheets = new ArrayList<SheetData>();
		for (int i=0; i<wb.getNumberOfSheets(); i++) {
			SheetData sheet = new SheetData(wb.getSheetAt(i));
		    sheets.add(sheet);
		    JCheckBox chckbxSheet = new JCheckBox(wb.getSheetName(i));
		    chckbxSheet.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					sheet.setExport(chckbxSheet.isSelected());
				}
			});
			panel_1.add(chckbxSheet, "cell 0 " + (i+1) + ",growx");
			
			JSpinner spinner = new JSpinner();
			spinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					sheet.setFrom((Integer)spinner.getValue());
				}
			});
			panel_1.add(spinner, "cell 1 " + (i+1) + ",growx");
			
			JSpinner spinner_1 = new JSpinner();
			spinner_1.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					sheet.setTo((Integer)spinner_1.getValue());
				}
			});
			panel_1.add(spinner_1, "cell 2 " + (i+1) + ",growx");
			
			JTextField textField_1 = new JTextField();
			textField_1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					sheet.setIndex(textField_1.getText());
					validate("^[A-Za-z]+$", textField_1);
				}
			});
			panel_1.add(textField_1, "cell 3 " + (i+1) + ",growx");
			textField_1.setColumns(10);
			
			JTextField textField_2 = new JTextField();
			textField_2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					sheet.setMessage(textField_2.getText());
					validate("^[A-Za-z]+$", textField_2);
				}
			});
			panel_1.add(textField_2, "cell 4 " + (i+1) + ",growx");
			textField_2.setColumns(10);
			
			JTextField textField_3 = new JTextField();
			textField_3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					sheet.setFile(textField_3.getText());
					validate("^[A-Za-z0-9\\.\\_]+$", textField_3);
				}
			});
			panel_1.add(textField_3, "cell 5 " + (i+1) + ",growx");
			textField_3.setColumns(10);
		}
		panel_1.revalidate();
		return sheets;
	}
	
	private void validate(String pattern, JTextField field) {
		if (!field.getText().equals("")) {
			if (!Pattern.matches(pattern, field.getText())) {
				field.setText("");
				viewError = new ViewError("Invalid Input");
				viewError.setVisible(true);
			}
		}
	}
}
