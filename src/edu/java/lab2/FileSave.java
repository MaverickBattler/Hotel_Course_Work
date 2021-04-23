package edu.java.lab2;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * ����� ���� ���������� ������� � ����
 */
public class FileSave extends JFrame {
	/**
	 * ����������� �������� ������;
	 * � ��������� ���� ����������� �������� �������
	 * ��������������� IOException
	 * @param str ��������� ����
	 * @param table ������ �������, ������� ����������� � ����
	 */
	public FileSave(String str,DefaultTableModel table){
		 FileDialog save = new FileDialog(this,str,FileDialog.SAVE);
		 save.setFile("*.txt");// ��������� ���������� ��������
		 save.setVisible(true);
		 //���������� ��� �������� ��� �����
		 String fileNameSave = save.getDirectory() + save.getFile();
		 if(fileNameSave == null) return; // ������������ ����� ������
		 try{
			 System.out.println(fileNameSave);
			 BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameSave));
			 for(int i = 0; i < table.getRowCount(); i++){
				 if (i!=0) 
					 writer.write("\r\n");
				 for(int j = 0; j < table.getColumnCount(); j++){
					 writer.write((String)table.getValueAt(i,j));
					 if(j<table.getColumnCount()) writer.write("|");
				 }
			 }
			 writer.close();
		 }catch (IOException ex) {
			 ex.printStackTrace();
		 }
	}
}
