package com.cogentautomation.model;

import org.apache.poi.ss.usermodel.Sheet;

public class SheetData {
	private String name;
	private boolean export = false;
	private int from = 0;
	private int to = 0;
	private String index;
	private String message;
	private String file;
	private Sheet sheet;
	
	public SheetData(Sheet sheet) {
		setSheet(sheet);
		setName(sheet.getSheetName());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Sheet getSheet() {
		return sheet;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
}
