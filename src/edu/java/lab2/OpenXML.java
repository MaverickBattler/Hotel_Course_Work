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
 * Стандартный файловый диалог, открытие XML файла и запись считанных данных в активную таблицу
 * @author Kirill Mokhnatkin, group 8308
 */
public class OpenXML extends JFrame{
	
	private static String fileNameOpen;
	/**
	 * Открытие XML документа, его расшифровка по разметке и запись считанных данных в таблицу
	 * @param str Заголовок диалогового окна
	 * @param tableModel Модель активной таблицы, данные для которой считываются из файла
	 * @throws Exception Стандартные исключения, возникающие при работе с XML файлом
	 */
	OpenXML(String str, DefaultTableModel tableModel) throws Exception {
		FileDialog openXML = new FileDialog(this,str,FileDialog.LOAD);
		openXML.setFile("*.xml");// Установка начального каталога
		openXML.setVisible(true);
		//Определяем имя каталога или файла
		fileNameOpen = openXML.getDirectory() + openXML.getFile();
		if(fileNameOpen == null) return; // Пользователь нажал отмена
		if (!(openXML.getFile()==null))
		{
			tableModel.setRowCount(0);
			try {
				Document doc = getDocument();
				doc.getDocumentElement().normalize();
				if (tableModel.getColumnName(3)=="Должность")
				{
					// Получение списка элементов workers
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
				else if (tableModel.getColumnName(3)=="Занятость")
				{
					// Получение списка элементов rooms
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
					// Получение списка элементов renters
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
			// Ошибка при чтение XML файла
			}catch(SAXException e){
				e.printStackTrace();
			// Ошибка ввода - вывода
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Открытие XML документа по фиксированному адресу, его расшифровка по разметке и запись считанных данных в таблицу
	 * @param tableModel модель таблицы, которая сохраняется в xml файл
	 * @throws Exception стандартное исключение работы с файлами xml
	 */
	OpenXML(DefaultTableModel tableModel) throws Exception {
			fileNameOpen = "C:\\Eclipse\\Projects\\lab2_10_5sem\\Hotel\\workersList.xml";
			tableModel.setRowCount(0);
			try {
				Document doc = getDocument();
				doc.getDocumentElement().normalize();
				// Получение списка элементов workers
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
			// Ошибка при чтение XML файла
			}catch(SAXException e){
				e.printStackTrace();
			// Ошибка ввода - вывода
			}catch(IOException e){
				e.printStackTrace();
			}
	}
	
	private static Document getDocument() throws Exception {
		try {
			// Получаем парсер, порождающий дерево объектов XML - документов 
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
			// Создает пустой документ
			DocumentBuilder builder = f.newDocumentBuilder();
			return builder.parse(new File(fileNameOpen));
		} catch (Exception exception) {
			throw new Exception("XML parsing error!");
		}
	}
}
