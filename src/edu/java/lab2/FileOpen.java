package edu.java.lab2;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

/**
 * ����� �������� ������� �� �����
 */
public class FileOpen extends JFrame{
	public static final Logger log= Logger.getLogger(Lab2.class);
	/**
	 * ����������� �������� ������;
	 * �� ���������� ����� ����������� �������� �������
	 * ���� ������������ ������� ��������, ��������� �� ������������
	 * �������� IOException
	 * @param str ��������� ����
	 * @param table ������ �������, ������� ����������� �� �����
	 */
	public FileOpen(String str,DefaultTableModel table){
		 FileDialog open = new FileDialog(this,str,FileDialog.LOAD);
		 open.setFile("*.txt");// ��������� ���������� ��������
		 open.setVisible(true);
		 //���������� ��� �������� ��� �����
		 String fileNameOpen = open.getDirectory() + open.getFile();
		 if(fileNameOpen == null) return; // ������������ ����� ������
		 try{
			 // ��������� ������ � ������� � �����
			 if (!(open.getFile()==null))
			 {
				 BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));
				 int rows = table.getRowCount();
				 for(int i = 0; i < rows; i++)
					 table.removeRow(0);
				 String stringToRead;
				 do{
					 stringToRead = reader.readLine();
					 if(stringToRead != null){
						 String[] stringArr = stringToRead.split("\\|");
						 if(stringArr.length == table.getColumnCount() && stringArr.length == 4)
							 table.addRow(new String[] {stringArr[0],stringArr[1],stringArr[2],stringArr[3]});
						 else if (stringArr.length == table.getColumnCount() && stringArr.length == 10)
							 table.addRow(new String[] {stringArr[0],stringArr[1],stringArr[2],stringArr[3],stringArr[4],stringArr[5],stringArr[6],stringArr[7],stringArr[8],stringArr[9]});
					 }
				 }while(stringToRead != null);
				 reader.close();
			 }
		 }catch(FileNotFoundException e) {
			 log.error("Error occured:", e);
			 e.printStackTrace();
		 }catch (IOException e) {
			 log.error("Error occured:", e);
			 e.printStackTrace();
		 }
	}
}
