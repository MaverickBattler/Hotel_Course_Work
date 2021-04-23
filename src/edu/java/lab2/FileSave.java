package edu.java.lab2;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Класс окна сохранения таблицы в файл
 */
public class FileSave extends JFrame {
	/**
	 * Стандартный файловый диалог;
	 * в выбранный файл сохраняется активная таблица
	 * Перехватывается IOException
	 * @param str Заголовок окна
	 * @param table Модель таблицы, которая сохраняется в файл
	 */
	public FileSave(String str,DefaultTableModel table){
		 FileDialog save = new FileDialog(this,str,FileDialog.SAVE);
		 save.setFile("*.txt");// Установка начального каталога
		 save.setVisible(true);
		 //Определяем имя каталога или файла
		 String fileNameSave = save.getDirectory() + save.getFile();
		 if(fileNameSave == null) return; // Пользователь нажал отмена
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
