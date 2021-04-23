package edu.java.lab2;

import java.awt.FileDialog;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * ����������� �������� ������, ���������� ������ � ���������� ��������� � html-����
 * @author Kirill Mokhnatkin, group 8308
 */
public class HtmlSave extends JFrame{
	/**
	 * ����� ���� ���������� html-��������� � ���������� ���������� � ���������� � ������� html
	 * @param tableModel ������ ����������� �������
	 */
	HtmlSave(DefaultTableModel tableModel){
		PrintWriter writer = null;
		try {
			  FileDialog save=new FileDialog(this, "���������� ������� ���������� � html-����...", FileDialog.SAVE);
	    	  save.setFile("*.html");
	    	  save.setVisible(true);
	    	  String fileName=save.getDirectory()+save.getFile();
	    	  writer = new PrintWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println("<TABLE BORDER><TR><TH>�������<TH>���<TH>��������<TH>���������</TR>");
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			writer.println("<TR><TD>" + (String) tableModel.getValueAt(i,0)
					+ "<TD>" + (String) tableModel.getValueAt(i,1)
					+ "<TD>" + (String) tableModel.getValueAt(i,2)
					+ "<TD>" + (String) tableModel.getValueAt(i,3));
		}
		writer.println("</TABLE>");
		writer.close();
	}
	HtmlSave(DefaultTableModel tableModel, String fileName){
		PrintWriter writer = null;
		try {
	    	 writer = new PrintWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println("<TABLE BORDER><TR><TH>�������<TH>���<TH>��������<TH>���������</TR>");
		for(int i = 0; i < tableModel.getRowCount(); i++) {
			writer.println("<TR><TD>" + (String) tableModel.getValueAt(i,0)
					+ "<TD>" + (String) tableModel.getValueAt(i,1)
					+ "<TD>" + (String) tableModel.getValueAt(i,2)
					+ "<TD>" + (String) tableModel.getValueAt(i,3));
		}
		writer.println("</TABLE>");
		writer.close();
	}
}


