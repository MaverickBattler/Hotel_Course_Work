package edu.java.lab2;

import javax.swing.table.DefaultTableModel;

/**
 * Основной класс многопоточности
 * @author Kirill Mokhnatkin, group 8308
 */
public class MultiThreading {
	DefaultTableModel curModel;
	/**
	 * @param tableWorkers модель таблицы, с которой работают потоки
	 */
	MultiThreading(DefaultTableModel tableWorkers)
	{
		curModel = tableWorkers;
	}
	/**
	 * Инициализация монитора и потоков, вызов функции старта
	 */
	public void mainf() {
	Object mutex = new Object(); 	
	ThreadEx [] threads = new ThreadEx[3]; 
	startThreads(mutex, threads); 
	}
	/**
	 * Создание и старт потоков
	 * @param mutex монитор
	 * @param threads созданный, но не инициализированный массив потоков
	 */
	private void startThreads (Object mutex, ThreadEx[] threads){ 
		
		threads[0] = new ThreadEx(1, mutex, curModel);
		threads[1] = new ThreadEx(2, mutex, curModel);
		threads[2] = new ThreadEx(3, mutex, curModel);
		threads[0].start();
		threads[1].start();
		threads[2].start();
	}
}

