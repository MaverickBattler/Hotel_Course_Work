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
 * Стандартный файловый диалог, сохранение данных активной таблицы в выбранный XML файл
 * @author Kirill Mokhnatkin, group 8308
 */
public class SaveXML extends JFrame{
	/**
	 * Открытие XML документа, разметка данных активной таблицы и запись данных в XML файл
	 * @param str Заголовок диалогового окна
	 * @param tableModel Модель активной таблицы
	 * @throws Exception Стандартные исключения при работе с XML документами
	 */
	SaveXML(String str, DefaultTableModel tableModel) throws Exception{
		FileDialog saveXML = new FileDialog(this,str,FileDialog.SAVE);
		saveXML.setFile("*.xml");// Установка начального каталога
		saveXML.setVisible(true);
		//Определяем имя каталога или файла
		String fileNameSave = saveXML.getDirectory() + saveXML.getFile();
		if(fileNameSave == null) return; // Пользователь нажал отмена
		Document doc = getDocument();
		if (tableModel.getColumnName(3)=="Должность")
		{
			// Создаём корневой элемент workerslist и добавляем его в документ
			Node workerslist = doc.createElement("workerslist");// создать элемент
			doc.appendChild(workerslist);// добавляем child
			for(int i = 0; i < tableModel.getRowCount(); i++){
				Element worker = doc.createElement("employee");
				workerslist.appendChild(worker);
				worker.setAttribute("surname", (String) tableModel.getValueAt(i, 0));
				worker.setAttribute("name", (String) tableModel.getValueAt(i, 1));
				worker.setAttribute("fathername", (String) tableModel.getValueAt(i, 2));
				worker.setAttribute("position", (String) tableModel.getValueAt(i, 3));
			}
		} 
		else if (tableModel.getColumnName(3)=="Занятость")
		{
			// Создаём корневой элемент roomslist и добавляем его в документ
			Node roomslist = doc.createElement("roomslist");// создать элемент
			doc.appendChild(roomslist);// добавляем child
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
			// Создаём корневой элемент renterslist и добавляем его в документ
			Node renterslist = doc.createElement("renterslist");// создать элемент
			doc.appendChild(renterslist);// добавляем child
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
			// Создание преобразование документа
			Transformer trans=TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileNameSave)));
		// Ошибка создания XML преобразователя
		}catch(TransformerConfigurationException e){
			e.printStackTrace();
		// Ошибка работы XML преобразователя
		}catch(TransformerException e){
			e.printStackTrace();
		// Ошибка ввода - вывода
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Документ подготавливается для записи в него
	 * @return документ, разобранный DocumentBuilder
	 * @throws Exception Стандартное исключение при расшифровке xml файлов
	 */
	private static Document getDocument() throws Exception {
		try {
			// Получаем парсер, порождающий дерево объектов XML - документов 
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
			// Создает пустой документ
			DocumentBuilder builder = f.newDocumentBuilder();
			// Разбирает ( получает ) данные по пути
			return builder.newDocument();
		} catch (Exception exception) {
			throw new Exception("XML parsing error!");
		}
	}
}

