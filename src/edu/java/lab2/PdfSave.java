package edu.java.lab2;
import java.awt.FileDialog;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

/**
 * ����������� �������� ������, ���������� ������ � ���������� ��������� � pdf-����
 * @author Kirill Mokhnatkin, group 8308
 */
public class PdfSave extends JFrame{
	/**
	 * ����� ���� ���������� pdf ��������� � ���������� ���������� � ���������� � ������� pdf
	 * @param tableModel ������ ����������� �������
	 */
	PdfSave(DefaultTableModel tableModel) {
		try {
	    	  FileDialog save=new FileDialog(this, "���������� ������� ���������� � pdf-����...", FileDialog.SAVE);
	    	  save.setFile("*.pdf");
	    	  save.setVisible(true);
	    	  String fileName=save.getDirectory()+save.getFile();
	    	  PdfWriter writer = new PdfWriter(fileName);
	    	  PdfDocument pdf = new PdfDocument(writer);        
		      com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdf);    
		      float [] pointColumnWidths = {200F, 200F, 200F, 200F};   
		      Table reportTable = new Table(pointColumnWidths);    
		      
		      PdfFont font = PdfFontFactory.createFont("src/times.ttf", "CP1251", true);
		      
		      reportTable.setFont(font);
		      
		      reportTable.addCell(new Cell().setBold().add("�������"));
		      reportTable.addCell(new Cell().setBold().add("���"));
		      reportTable.addCell(new Cell().setBold().add("��������"));
		      reportTable.addCell(new Cell().setBold().add("���������"));
		      
		      for(int i = 0; i < tableModel.getRowCount(); ++i) {
		    	  reportTable.addCell(new Cell().add((String)tableModel.getValueAt(i, 0)));
		    	  reportTable.addCell(new Cell().add((String)tableModel.getValueAt(i, 1)));
		    	  reportTable.addCell(new Cell().add((String)tableModel.getValueAt(i, 2)));
		    	  reportTable.addCell(new Cell().add((String)tableModel.getValueAt(i, 3)));
		      }
		     
		      doc.add(reportTable);                  
		      doc.close();
			}	 
		catch (FileNotFoundException e) {
				e.printStackTrace();   
			}  		
		catch (java.io.IOException e) {
		e.printStackTrace();
		}
	}
	
}