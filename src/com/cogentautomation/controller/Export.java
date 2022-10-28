package com.cogentautomation.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import com.cogentautomation.model.SheetData;
import com.cogentautomation.view.ViewError;

public class Export {
	
	private CellReference cellReference;
	private Row row;
	private Cell cell;
	private String output;
	private List<String> array;
	private int j = 0;
	private PrintWriter writer;
	private String newline = System.getProperty("line.separator");
	
	public void save(List<SheetData> data, File folder) {
		
		
		for (SheetData sd : data) {
			if (sd.isExport()) {
				try {
					writer = new PrintWriter(folder + "\\" + addExtention(sd.getFile()), "UTF-8");
					output = "MemMsg_SelectedSeq_PromptLine1 = \"\";" + newline + "MemMsg_SelectedSeq_PromptLine2 = \"\";" + newline + "MemMsg_SelectedSeq_PromptLine3 = \"\";" + newline + "MemMsg_SelectedSeq_PromptLine4 = \"\";" + newline;
					for (int i = sd.getFrom(); i <= sd.getTo(); i++) {
					
						cellReference = new CellReference(sd.getIndex() + i); 
						row = sd.getSheet().getRow(cellReference.getRow());
						cell = row.getCell(cellReference.getCol()); 
						
						if (cell != null) {
							if (cell.getCellType() != 3) {
								output += "IF floatMessageNo == " + round(cell.getNumericCellValue(), 1) + " THEN" + newline;
								
								cellReference = new CellReference(sd.getMessage() + i); 
								row = sd.getSheet().getRow(cellReference.getRow());
								cell = row.getCell(cellReference.getCol()); 
								
								if (cell != null) {
									array = split(40, cell.getStringCellValue());
									j = 1;
									for (String s : array) {
										output += "    MemMsg_SelectedSeq_PromptLine" + j++ + " = \"" + s + "\";" + newline;
									}
								}
								output += "ENDIF;" + newline;
							}
						}
					}
					writer.print(output);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					ViewError viewError = new ViewError("Unable to export files");
					viewError.setVisible(true);
				} catch (IllegalStateException e) {
					ViewError viewError = new ViewError(sd.getSheet().getSheetName() + " " + CellReference.convertNumToColString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex() + 1) + " contains a data type problem please correct and retry");
					viewError.setVisible(true);
				}
			}
		}
	}
	
	private List<String> split(int characters, String string) {
		List<String> split = new ArrayList<String>();
		String[] arr = string.split(" ");
		String current = "";
		for (String s : arr) {
			if (current.length() + s.length() > characters) {
				split.add(current.trim());
				current = "";
			}
			current += s + " ";
		}
		if (!current.trim().equals("")) {
			split.add(current.trim());
		}
		return split;
	}
	
	private double round(double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	private String addExtention(String string) {
		if (string.toUpperCase().endsWith(".TXT")) {
			return string;
		} else {
			return string + ".txt";
		}
	}
	
}
