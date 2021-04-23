package edu.java.lab2;

import java.awt.FileDialog;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * ����������� �������� ������, ���������� ������ �������� ������� � ��������� XML ����
 * @author Kirill Mokhnatkin, group 8308
 */
public class SaveXML extends JFrame{
	/**
	 * �������� XML ���������, �������� ������ �������� ������� � ������ ������ � XML ����
	 * @param str ��������� ����������� ����
	 * @param tableModel ������ �������� �������
	 * @throws Exception ����������� ���������� ��� ������ � XML �����������
	 */
	SaveXML(String str, DefaultTableModel tableModel) throws Exception{
		FileDialog saveXML = new FileDialog(this,str,FileDialog.SAVE);
		saveXML.setFile("*.xml");// ��������� ���������� ��������
		saveXML.setVisible(true);
		//���������� ��� �������� ��� �����
		String fileNameSave = saveXML.getDirectory() + saveXML.getFile();
		if(fileNameSave == null) return; // ������������ ����� ������
		Document doc = getDocument();
		if (tableModel.getColumnName(3)=="���������")
		{
			// ������ �������� ������� workerslist � ��������� ��� � ��������
			Node workerslist = doc.createElement("workerslist");// ������� �������
			doc.appendChild(workerslist);// ��������� child
			for(int i = 0; i < tableModel.getRowCount(); i++){
				Element worker = doc.createElement("employee");
				workerslist.appendChild(worker);
				worker.setAttribute("surname", (String) tableModel.getValueAt(i, 0));
				worker.setAttribute("name", (String) tableModel.getValueAt(i, 1));
				worker.setAttribute("fathername", (String) tableModel.getValueAt(i, 2));
				worker.setAttribute("position", (String) tableModel.getValueAt(i, 3));
			}
		} 
		else if (tableModel.getColumnName(3)=="���������")
		{
			// ������ �������� ������� roomslist � ��������� ��� � ��������
			Node roomslist = doc.createElement("roomslist");// ������� �������
			doc.appendChild(roomslist);// ��������� child
			for(int i = 0; i < tableModel.getRowCount(); i++){
				Element room = doc.createElement("room");
				roomslist.appendChild(room);
				room.setAttribute("roomNumber", (String) tableModel.getValueAt(i, 0));
				room.setAttribute("space", (String) tableModel.getValueAt(i, 1));
				room.setAttribute("price", (String) tableModel.getValueAt(i, 2));
				room.setAttribute("availability", (String) tableModel.getValueAt(i, 3));
			}
		}
		else
		{
			// ������ �������� ������� renterslist � ��������� ��� � ��������
			Node renterslist = doc.createElement("renterslist");// ������� �������
			doc.appendChild(renterslist);// ��������� child
			for(int i = 0; i < tableModel.getRowCount(); i++){
				Element renter = doc.createElement("client");
				renterslist.appendChild(renter);
				renter.setAttribute("surname", (String) tableModel.getValueAt(i, 0));
				renter.setAttribute("name", (String) tableModel.getValueAt(i, 1));
				renter.setAttribute("fathername", (String) tableModel.getValueAt(i, 2));
				renter.setAttribute("roomNumber", (String) tableModel.getValueAt(i, 3));
				renter.setAttribute("dayArrive", (String) tableModel.getValueAt(i, 4));
				renter.setAttribute("monthArrive", (String) tableModel.getValueAt(i, 5));
				renter.setAttribute("yearArrive", (String) tableModel.getValueAt(i, 6));
				renter.setAttribute("dayLeave", (String) tableModel.getValueAt(i, 7));
				renter.setAttribute("monthLeave", (String) tableModel.getValueAt(i, 8));
				renter.setAttribute("yearLeave", (String) tableModel.getValueAt(i, 9));
			}
		}
		try{
			// �������� �������������� ���������
			Transformer trans=TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileNameSave)));
		// ������ �������� XML ���������������
		}catch(TransformerConfigurationException e){
			e.printStackTrace();
		// ������ ������ XML ���������������
		}catch(TransformerException e){
			e.printStackTrace();
		// ������ ����� - ������
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * �������� ���������������� ��� ������ � ����
	 * @return ��������, ����������� DocumentBuilder
	 * @throws Exception ����������� ���������� ��� ����������� xml ������
	 */
	private static Document getDocument() throws Exception {
		try {
			// �������� ������, ����������� ������ �������� XML - ���������� 
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// ����� ���������
			// ������� ������ ��������
			DocumentBuilder builder = f.newDocumentBuilder();
			// ��������� ( �������� ) ������ �� ����
			return builder.newDocument();
		} catch (Exception exception) {
			throw new Exception("XML parsing error!");
		}
	}
}

