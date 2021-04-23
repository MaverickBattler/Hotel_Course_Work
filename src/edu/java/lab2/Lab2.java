package edu.java.lab2;
// Подключение графических библиотек
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import java.awt.event.*;
import java.util.regex.PatternSyntaxException;

/**
 * @author Кирилл Мохнаткин, группа 8308
 * @version 1.0.0
 * Основной класс лабораторной работы(курсовой работы), содержащий метод main()
 * Информация о работе программы и возникающих ошибках записывается в файл log_file.txt
 * INFO - общая информация
 * DEBUG - отладочная информация
 * WARN - предупреждение
*/
public class Lab2 {
	public static final Logger log= Logger.getLogger(Lab2.class);
	// Объявления графических компонентов
	private JFrame mainWindow;
	private DefaultTableModel modelWorkers;
	private DefaultTableModel modelRooms;
	private DefaultTableModel modelRenters;
	//private JMenuItem saveAllOption;
	private JMenuItem saveAsFileOption;
	private JMenuItem saveXML;
	private JMenuItem newOption;
	private JMenuItem openFileOption;
	private JMenuItem openXML;
	private JMenuItem formReportOption;
	private JMenuItem exitOption;
	private JMenuItem reportPDFOption;
	private JMenuItem reportHTMLOption;
	private JMenuItem multiThreadingOption;
	private JMenuItem aboutOption;
	private JButton addButton;
	private JButton deleteButton;
	private JToolBar toolBar;
	private JTabbedPane tabs;
	private JScrollPane scrollWorkers;
	private JScrollPane scrollRooms;
	private JScrollPane scrollRenters;
	private JTable workersTable;
	private JTable roomsTable;
	private JTable rentersTable;
	private JLabel searchLabel;
	private JTextField searchField;
	private JButton filterButton;
	private JButton resetButton;
	/**
	 * Класс исключения, создается, когда не была введена информация в строку поиска и нажата кнопка поиска
	*/
	public class NoSearchStringEx extends Exception {
	    public NoSearchStringEx()
	    {
	        super("Вы не ввели информацию для поиска");
	    }
	}
	/**
	 * Класс исключения, создается, когда не были выделены строки таблицы, которые нужно удалить, и нажата кнопка "Удалить"
	*/
	public class NoRowsSelectedEx extends Exception {
	    public NoRowsSelectedEx()
	    {
	        super("Вы не выделили строки таблицы, которые хотите удалить");
	    }
	}
	/**
	 * Проверяет текстовое поле, если оно пустое и нажата кнопка "Поиск", возвращается false, иначе true
	 * @param s текстовое поле, которое проверяется
	 * @return false, если текстовое поле пустое, иначе true
	 */
	static boolean checkIfSearchIsNotEmpty(JTextField s)
    {
        String searchString = s.getText();
        if(searchString.length()==0) 
        {
        	log.warn("Search field is empty, checked.");
        	return false;
        }
        else
        {
        	log.info("Search field is not empty, checked.");
        	return true;
        }
    }
	/**
	 * Проверяет массив целых чисел, являющийся набором индексов выделенных строк таблицы
	 * Если его длина 0, то возвращается false, иначе true
	 * @param selectedRows массив целых чисел, являющийся набором индексов выделенных строк таблицы
	 * @return false, если длина массива ноль, иначе true
	 */
	static boolean checkIfAnythingSelected(int[] selectedRows)
	{
		if (selectedRows.length==0)
		{
			log.warn("No rows selected,checked");
			return false;
		}
		else
		{
			log.info("Rows selected,checked.");
			return true;
		}
	}
	/**
	 * Добавляет элементы интерфейса на главное окно и показывает его
	 */
	public void show() {
		// Создание окна
		mainWindow = new JFrame("Гостиница");
		Dimension windowSize = new Dimension(800,600);
		mainWindow.setMinimumSize(windowSize);
		mainWindow.setLocation(150, 100);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Создание меню
		JMenuBar menuBar;
		JMenu menuFile;
		JMenu menuInfo;
		JMenu menuFormReport;
		menuBar = new JMenuBar();
		menuFile = new JMenu("Файл");
		menuBar.add(menuFile);
		menuInfo = new JMenu("Помощь");
		menuBar.add(menuInfo);
		menuFormReport = new JMenu("Сформировать отчет по работникам");
		//saveAllOption = new JMenuItem("Сохранить все");
		saveAsFileOption = new JMenuItem("Сохранить как");
		saveXML = new JMenuItem("Сохранить XML");
		newOption = new JMenuItem("Очистить таблицы");
		openFileOption = new JMenuItem("Открыть файл");
		openXML = new JMenuItem("Открыть XML");
		formReportOption = new JMenuItem("Сформировать отчет работы гостиницы");
		exitOption = new JMenuItem("Выход");
		reportPDFOption = new JMenuItem("Создать отчет в PDF");
		reportHTMLOption = new JMenuItem("Создать отчет в HTML");
		multiThreadingOption = new JMenuItem("Тест многопоточности");
		aboutOption = new JMenuItem("О программе");
		//menuFile.add(saveAllOption);
		menuFile.add(saveAsFileOption);
		menuFile.add(saveXML);
		menuFile.add(newOption);
		menuFile.add(openFileOption);
		menuFile.add(openXML);
		//menuFile.add(formReportOption);
		menuFile.add(multiThreadingOption);
		menuFormReport.add(reportPDFOption);
		menuFormReport.add(reportHTMLOption);
		menuFile.add(menuFormReport);
		menuFile.add(exitOption);
		menuInfo.add(aboutOption);
		mainWindow.setJMenuBar(menuBar);
		// Создание кнопок и прикрепление иконок
		addButton = new JButton(new ImageIcon("./img/add.png"));
		deleteButton = new JButton(new ImageIcon("./img/delete.png"));
		// Настройка подсказок для кнопок
		addButton.setToolTipText("Добавить строку в таблицу");
		deleteButton.setToolTipText("Удалить выделенные строки из таблицы");
		// Добавление кнопок на панель инструментов
		toolBar = new JToolBar("Панель инструментов");
		toolBar.add(addButton);
		toolBar.add(deleteButton);
		// Размещение панели инструментов
		mainWindow.setLayout(new BorderLayout());
		mainWindow.add(toolBar, BorderLayout.NORTH);
		// Создание таблиц с данными
		String [] columnsWorkers = {"Фамилия", "Имя", "Отчество", "Должность"};
		String [][] dataWorkers = {{"Федосеева", "Анна", "Богдановна", "Менеджер"},
				{"Комиссаров", "Арсений", "Владимирович", "Шеф-повар"}, {"Казаков", "Василий", "Михайлович", "Менеджер"}};
		String [] columnsRooms = {"Номер", "Места", "Стоимость", "Занятость"};
		String [][] dataRooms = {{"1", "4", "2000", "Занят"},
				{"2", "1", "3000", "Занят"}, {"3", "2", "3500", "Свободен"}};
		String [] columnsRenters = {"Фамилия", "Имя", "Отчество", "Номер комнаты", "День", "Месяц", "Год", "День", "Месяц", "Год"};
		String [][] dataRenters = {{"Кононов", "Яков", "Богданович", "3", "29", "10", "2020", "10", "11", "2020"},{"Архипов", "Богдан", "Платонович", "3", "29", "10", "2020", "10", "11", "2020"}}; 
		modelWorkers = new DefaultTableModel(dataWorkers, columnsWorkers);
		modelRooms = new DefaultTableModel(dataRooms, columnsRooms);
		modelRenters = new DefaultTableModel(dataRenters, columnsRenters);
		workersTable = new JTable(modelWorkers);
		roomsTable = new JTable(modelRooms);
		rentersTable = new JTable(modelRenters);
		TableColumn jobColumn = workersTable.getColumnModel().getColumn(3);
		JComboBox jobCBox = new JComboBox(new String[]{"Менеджер","Шеф-повар", "Администратор", "Уборщик", "Портье", "Охранник", "Директор", "Повар"});
		jobColumn.setCellEditor(new DefaultCellEditor(jobCBox));
		JComboBox ifOccupied = new JComboBox(new String[] {"Занят", "Свободен"});
		TableColumn ifOccupiedColumn = roomsTable.getColumnModel().getColumn(3);
		ifOccupiedColumn.setCellEditor(new DefaultCellEditor(ifOccupied));
		//Установка сортировки по столбцам
		workersTable.setAutoCreateRowSorter(true);
		roomsTable.setAutoCreateRowSorter(true);
		rentersTable.setAutoCreateRowSorter(true);
		scrollWorkers = new JScrollPane(workersTable);
		scrollRooms = new JScrollPane(roomsTable);
		scrollRenters = new JScrollPane(rentersTable);
		tabs = new JTabbedPane();
		tabs.addTab("Персонал гостиницы", null, scrollWorkers,
		                  "Информация о работниках гостиницы");
		tabs.addTab("Номера гостиницы", null, scrollRooms,
                "Информация о номерах гостиницы");
		tabs.addTab("Жильцы гостиницы", null, scrollRenters,
                "Информация о жильцах гостиницы");
		// Размещение таблиц с данными
		mainWindow.add(tabs, BorderLayout.CENTER);
		// Подготовка компонентов поиска
		searchField = new JTextField("");
		filterButton = new JButton("Поиск");
		resetButton = new JButton("Сброс поиска");
		searchLabel = new JLabel("Поиск по ключевому слову:  ");
		// Добавление компонентов на панель
		JPanel filterPanel = new JPanel();
		filterPanel.add(searchLabel);
		filterPanel.add(searchField);
		filterPanel.add(filterButton);
		filterPanel.add(resetButton);
		resetButton.setVisible(false);
		// Установление размера поля для ввода поискового слова
		Dimension size = new Dimension(100,25);
		searchField.setPreferredSize(size);
		// Размещение панели поиска внизу окна
		mainWindow.add(filterPanel, BorderLayout.SOUTH);
		// Визуализация экранной формы
		mainWindow.setVisible(true);
		// Создание окон добавления строки в таблицу
		AddNew workersWindow = new AddNew(columnsWorkers);
		AddNew roomsWindow = new AddNew(columnsRooms);
		AddNew rentersWindow = new AddNew(columnsRenters);
		// Добавление обработчиков событий
		/**
		 * При нажатии на кнопку поиска в таблице остаются строки, включающие в себя строку поиска;
		 * кнопка "Сброс поиска" становится видимой
		 */
		filterButton.addActionListener (new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Search button pressed");
				DefaultTableModel model;
				JTable table;
				if (tabs.getSelectedIndex()==0)
	            {
					log.info("Search by workers table");
					model = modelWorkers;
					table = workersTable;
	            }  
	            else if (tabs.getSelectedIndex()==1)
	            {
	            	log.info("Search by rooms table");
	            	model = modelRooms;
	            	table = roomsTable;
	            }
	            else
	            {
	            	log.info("Search by clients table");
	            	model = modelRenters;
	            	table = rentersTable;
	            }
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
				String text = searchField.getText();
	            sorter.setModel(model);
	            table.setRowSorter(sorter);
	            try {
	                if (!checkIfSearchIsNotEmpty(searchField))
	                	throw new NoSearchStringEx();
	                sorter.setRowFilter(RowFilter.regexFilter(text));
		            resetButton.setVisible(true);
	            } catch (PatternSyntaxException pse) {
	            	log.error("PatternSytaxException occured: ", pse);
	                System.err.println("Bad REGEX pattern");
	            } catch (NoSearchStringEx MyEx)
	            {
	            	log.warn("Search field is empty: ", MyEx);
	                JOptionPane.showMessageDialog(mainWindow,MyEx.getMessage());
	            }
			}
		});
		/**
		 * Сброс эффекта нажатия кнопки "Поиск", кнопка сброса становится невидимой
		 */
		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Search results discarded");
				TableRowSorter<TableModel> sorter;
				sorter = new TableRowSorter<>(modelWorkers);
                sorter.setModel(modelWorkers);
                workersTable.setRowSorter(sorter);
				sorter.setRowFilter(null);
            	sorter = new TableRowSorter<>(modelRooms);
                sorter.setModel(modelRooms);
                roomsTable.setRowSorter(sorter);
				sorter.setRowFilter(null);
            	sorter = new TableRowSorter<>(modelRenters);
                sorter.setModel(modelRenters);
                rentersTable.setRowSorter(sorter);
				sorter.setRowFilter(null);
                searchField.setText("");
                resetButton.setVisible(false);
			}
		});
		/**
		 * Вызов диалогового окна добавления строки в активную таблицу
		 */
		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
	            if (tabs.getSelectedIndex()==0)
	            {
	            	log.info("Adding a row to workers table");
	            	workersWindow.show();
	            }  
	            else if (tabs.getSelectedIndex()==1)
	            {
	            	log.info("Adding a row to rooms table");
	            	roomsWindow.show();
	            }
	            else if (tabs.getSelectedIndex()==2)
	            {
	            	log.info("Adding a row to renters table");
	            	rentersWindow.show();
	            }
			}
        });
		/**
		 * Удаление выделенных строк активной таблицы
		 */
		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				JTable currentTable;
				if (tabs.getSelectedIndex()==0)
	            {
					log.info("Deleting rows from workers table");
	            	currentTable = workersTable;
	            }  
	            else if (tabs.getSelectedIndex()==1)
	            {
	            	log.info("Deleting rows from rooms table");
	            	currentTable = roomsTable;
	            }
	            else
	            {
	            	log.info("Deleting rows from renters table");
	            	currentTable = rentersTable;
	            }
				int[] selectedRows;
				selectedRows = currentTable.getSelectedRows();
				try
				{
					if (!checkIfAnythingSelected(selectedRows))
						throw new NoRowsSelectedEx();
					int index = selectedRows[0];
					for (int i = 0; i < selectedRows.length;i++)
					{
						((DefaultTableModel)currentTable.getModel()).removeRow(index);
					}
				}
				catch(NoRowsSelectedEx MyEx)
				{
					log.warn("No rows selected to delete!");
					log.debug(MyEx);
					JOptionPane.showMessageDialog(mainWindow,MyEx.getMessage());
				}
			}
		});
		/**
		 * Добавление строки с введенными данными в таблицу(данные о работниках)
		 */
		workersWindow.apply.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Adding the row to workers table");
				String[] enterFields = workersWindow.getEnterFields();
				try
	        	{
	        		for (String i : enterFields)
	            	{
	            		if (i.length()==0) 
	            			throw new FieldsNotEntered();
	            	}
	        		modelWorkers.addRow(new Object[]{enterFields[0], enterFields[1], enterFields[2], enterFields[3]});
	        	}
	        	catch (FieldsNotEntered MyEx) { 
	        		log.warn("Not all fields were entered!");
	        		log.debug(MyEx);
	        		}
			}
		});
		/**
		 * Добавление строки с введенными данными в таблицу(данные о комнатах)
		 */
		roomsWindow.apply.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Adding the row to rooms table");
				String[] enterFields = roomsWindow.getEnterFields();
				try
	        	{
	        		for (String i : enterFields)
	            	{
	            		if (i.length()==0) 
	            			throw new FieldsNotEntered();
	            	}
	        		modelRooms.addRow(new Object[]{enterFields[0], enterFields[1], enterFields[2], enterFields[3]});
	        	}
	        	catch (FieldsNotEntered MyEx) {
	        		log.warn("Not all fields were entered!");
	        		log.debug(MyEx);
	        	}
			}
		});
		/**
		 * Добавление строки с введенными данными в таблицу(данные о проживающих)
		 */
		rentersWindow.apply.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Adding the row to renters table");
				String[] enterFields = rentersWindow.getEnterFields();
				try
	        	{
	        		for (String i : enterFields)
	            	{
	            		if (i.length()==0) 
	            			throw new FieldsNotEntered();
	            	}
	        		modelRenters.addRow(new Object[]{enterFields[0], enterFields[1], enterFields[2], enterFields[3], enterFields[4], enterFields[5], enterFields[6], enterFields[7], enterFields[8], enterFields[9]});
	        	}
	        	catch (FieldsNotEntered MyEx) {
	        		log.warn("Not all fields were entered!");
	        		log.debug(MyEx);
	        	}
			}
		});
		/*saveAllOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
					
			}
		});*/
		/**
		 * Вызов файлового меню сохранения файла
		 */
		saveAsFileOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				// Создание окна сохранения файла
				if (tabs.getSelectedIndex()==0)
				{
					log.info("Saving workers data...");
					new FileSave("Сохранить данные о работниках как...",modelWorkers);
				}
				else if (tabs.getSelectedIndex()==1)
				{
					log.info("Saving rooms data...");
					new FileSave("Сохранить данные о номерах как...",modelRooms);
				}
				else if (tabs.getSelectedIndex()==2)
				{
					log.info("Saving renters data...");
					new FileSave("Сохранить данные о жильцах как...",modelRenters);
				}
			}
		});
		/**
		 * Сохранение таблицы в XML файл
		 */
		saveXML.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				// Создание окна сохранения файла
				if (tabs.getSelectedIndex()==0)
					try {
						log.info("Saving workers data to XML...");
						new SaveXML("Сохранить данные о работниках в XML файл как...",modelWorkers);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
				else if (tabs.getSelectedIndex()==1)
					try {
						log.info("Saving rooms data to XML...");
						new SaveXML("Сохранить данные о номерах в XML файл как...",modelRooms);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
				else if (tabs.getSelectedIndex()==2)
					try {
						log.info("Saving renters data to XML...");
						new SaveXML("Сохранить данные о жильцах в XML файл как...",modelRenters);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
			}
		});
		/**
		 * Открытие таблицы из XML файла
		 */
		openXML.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				// Создание окна сохранения файла
				if (tabs.getSelectedIndex()==0)
					try {
						log.info("Loading workers data from XML...");
						new OpenXML("Открыть данные о работниках из XML файла...",modelWorkers);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
				else if (tabs.getSelectedIndex()==1)
					try {
						log.info("Loading rooms data from XML...");
						new OpenXML("Открыть данные о комнатах из XML файла...",modelRooms);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
				else if (tabs.getSelectedIndex()==2)
					try {
						log.info("Loading renters data from XML...");
						new OpenXML("Открыть данные о клиентах из XML файла...",modelRenters);
					} catch (Exception e) {
						log.debug("Error:",e);
						e.printStackTrace();
					}
			}
		});
		/**
		 * Удаление данных из всех таблиц
		 */
		newOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Removing all tables' data");
				//Очистка всех таблиц
				int rows = modelWorkers.getRowCount();
				for(int i = 0; i < rows; i++)
					modelWorkers.removeRow(0);
				rows = modelRooms.getRowCount();
				for(int i = 0; i < rows; i++)
					modelRooms.removeRow(0);
				rows = modelRenters.getRowCount();
				for(int i = 0; i < rows; i++)
					modelRenters.removeRow(0);
			}
		});
		/**
		 * Открытие из файла
		 */
		openFileOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				// Создание окна открытия файла
				if (tabs.getSelectedIndex()==0)
				{
					log.info("Loading workers data...");
					new FileOpen("Открыть файл с данными о работниках...",modelWorkers);
				}
				else if (tabs.getSelectedIndex()==1)
				{
					log.info("Loading rooms data...");
					new FileOpen("Открыть файл с данными о комнатах...",modelRooms);
				}
				else if (tabs.getSelectedIndex()==2)
				{
					log.info("Loading renters data...");
					new FileOpen("Открыть файл с данными о жильцах...",modelRenters);
				}
					
			}
		});
		/**
		 * Выход из программы
		 */
		exitOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Exiting program");
				System.exit(0);
			}
		});
		/**
		 * Создание PDF отчета о работниках гостиницы
		 */
		reportPDFOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Saving to PDF");
				new PdfSave(modelWorkers);
			}
		});
		/**
		 * Создание HTML отчета о работниках гостиницы
		 */
		reportHTMLOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Saving to HTML");
				new HtmlSave(modelWorkers);
			}
		});
		multiThreadingOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Starting multithreading event");
				MultiThreading mt = new MultiThreading(modelWorkers);
				mt.mainf();
			}
		});
		/**
		 * Вывод информации о программе
		 */
		aboutOption.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				log.info("Program info output");
				JOptionPane.showMessageDialog(mainWindow,"Данная программа является приложением для администратора гостиницы. \nВерсия программы - 1.0.0, автор - Мохнаткин Кирилл, группа 8308.");
			}
		});
	}
	/**
	 * Главная функция программы
	 * @param args массив параметров
	 */
	public static void main(String[] args) {
		log.info("Program started");
		// Создание и отображение экранной формы
		new Lab2().show();
		log.info("Main window has been built");
	}
}