package edu.java.lab2;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ����������� �������� ������, �������� XML ����� � ������ ��������� ������ � �������� �������
 * @author Kirill Mokhnatkin, group 8308
 */
public class OpenXML extends JFrame{
	
	private static String fileNameOpen;
	/**
	 * �������� XML ���������, ��� ����������� �� �������� � ������ ��������� ������ � �������
	 * @param str ��������� ����������� ����
	 * @param tableModel ������ �������� �������, ������ ��� ������� ����������� �� �����
	 * @throws Exception ����������� ����������, ����������� ��� ������ � XML ������
	 */
	OpenXML(String str, DefaultTableModel tableModel) throws Exception {
		FileDialog openXML = new FileDialog(this,str,FileDialog.LOAD);
		openXML.setFile("*.xml");// ��������� ���������� ��������
		openXML.setVisible(true);
		//���������� ��� �������� ��� �����
		fileNameOpen = openXML.getDirectory() + openXML.getFile();
		if(fileNameOpen == null) return; // ������������ ����� ������
		if (!(openXML.getFile()==null))
		{
			tableModel.setRowCount(0);
			try {
				Document doc = getDocument();
				doc.getDocumentElement().normalize();
				if (tableModel.getColumnName(3)=="���������")
				{
					// ��������� ������ ��������� workers
					NodeList nlWorkers = doc.getElementsByTagName("employee");
					for ( int temp = 0; temp < nlWorkers.getLength(); temp ++){
						Node elem = nlWorkers.item(temp);
						NamedNodeMap attrs = elem.getAttributes();
						String surname = attrs.getNamedItem("surname").getNodeValue();
						String name = attrs.getNamedItem("name").getNodeValue();
						String fathername = attrs.getNamedItem("fathername").getNodeValue();
						String position = attrs.getNamedItem("position").getNodeValue();
						tableModel.addRow(new String[] {surname, name, fathername, position });
					}
				} 
				else if (tableModel.getColumnName(3)=="���������")
				{
					// ��������� ������ ��������� rooms
					NodeList nlRooms = doc.getElementsByTagName("room");
					for ( int temp = 0; temp < nlRooms.getLength(); temp ++){
						Node elem = nlRooms.item(temp);
						NamedNodeMap attrs = elem.getAttributes();
						String roomNumber = attrs.getNamedItem("roomNumber").getNodeValue();
						String space = attrs.getNamedItem("space").getNodeValue();
						String price = attrs.getNamedItem("price").getNodeValue();
						String availability = attrs.getNamedItem("availability").getNodeValue();
						tableModel.addRow(new String[] {roomNumber, space, price, availability });
					}
				}
				else
				{
					// ��������� ������ ��������� renters
					NodeList nlRenters = doc.getElementsByTagName("client");
					for ( int temp = 0; temp < nlRenters.getLength(); temp ++){
						Node elem = nlRenters.item(temp);
						NamedNodeMap attrs = elem.getAttributes();
						String surname = attrs.getNamedItem("surname").getNodeValue();
						String name = attrs.getNamedItem("name").getNodeValue();
						String fathername = attrs.getNamedItem("fathername").getNodeValue();
						String roomNumber = attrs.getNamedItem("roomNumber").getNodeValue();
						String dayArrive = attrs.getNamedItem("dayArrive").getNodeValue();
						String monthArrive = attrs.getNamedItem("monthArrive").getNodeValue();
						String yearArrive = attrs.getNamedItem("yearArrive").getNodeValue();
						String dayLeave = attrs.getNamedItem("dayLeave").getNodeValue();
						String monthLeave = attrs.getNamedItem("monthLeave").getNodeValue();
						String yearLeave = attrs.getNamedItem("yearLeave").getNodeValue();
						tableModel.addRow(new String[] {surname, name, fathername, roomNumber, dayArrive, monthArrive, yearArrive, dayLeave, monthLeave, yearLeave});
					}
				}
			// ������ ��� ������ XML �����
			}catch(SAXException e){
				e.printStackTrace();
			// ������ ����� - ������
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * �������� XML ��������� �� �������������� ������, ��� ����������� �� �������� � ������ ��������� ������ � �������
	 * @param tableModel ������ �������, ������� ����������� � xml ����
	 * @throws Exception ����������� ���������� ������ � ������� xml
	 */
	OpenXML(DefaultTableModel tableModel) throws Exception {
			fileNameOpen = "C:\\Eclipse\\Projects\\lab2_10_5sem\\Hotel\\workersList.xml";
			tableModel.setRowCount(0);
			try {
				Document doc = getDocument();
				doc.getDocumentElement().normalize();
				// ��������� ������ ��������� workers
				NodeList nlWorkers = doc.getElementsByTagName("employee");
				for ( int temp = 0; temp < nlWorkers.getLength(); temp ++){
					Node elem = nlWorkers.item(temp);
					NamedNodeMap attrs = elem.getAttributes();
					String surname = attrs.getNamedItem("surname").getNodeValue();
					String name = attrs.getNamedItem("name").getNodeValue();
					String fathername = attrs.getNamedItem("fathername").getNodeValue();
					String position = attrs.getNamedItem("position").getNodeValue();
					tableModel.addRow(new String[] {surname, name, fathername, position });
				}
			// ������ ��� ������ XML �����
			}catch(SAXException e){
				e.printStackTrace();
			// ������ ����� - ������
			}catch(IOException e){
				e.printStackTrace();
			}
	}
	
	private static Document getDocument() throws Exception {
		try {
			// �������� ������, ����������� ������ �������� XML - ���������� 
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// ����� ���������
			// ������� ������ ��������
			DocumentBuilder builder = f.newDocumentBuilder();
			return builder.parse(new File(fileNameOpen));
		} catch (Exception exception) {
			throw new Exception("XML parsing error!");
		}
	}
}
