package edu.java.lab2;
import java.lang.Thread;

import javax.swing.table.DefaultTableModel;

/**
 * ����� ������
 * @author Kirill Mokhnatkin, group 8308
 */
class ThreadEx extends Thread {
	private Object mutex;
	private int name;
	DefaultTableModel curModel;
	/**
	 * ������������� ���������� ������
	 * @param n ����� ������
	 * @param mutex �������
	 * @param curModel �������, � ������� �������� ������
	 */
	public ThreadEx(int n, Object mutex, DefaultTableModel curModel) { 
		this.curModel = curModel;
		this.mutex = mutex; 
		this.name = n; 
		setPriority(NORM_PRIORITY - n); 
	} 
	/**
	 * ������ ������
	 * ������ �������� ������������, ������ ����� ������ ���� �����
	 */
	public void run() {
		if(this.name == 1) {
			synchronized (mutex) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					new OpenXML(curModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(this.name == 2) {
			synchronized (mutex) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				curModel.addRow(new String[] {"��������","�������","����������","�����"});
				mutex.notify();
			}
		}
		if(this.name == 3) {
			synchronized (mutex) { 
				mutex.notifyAll();
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new HtmlSave(curModel, "C:\\Eclipse\\Projects\\lab2_10_5sem\\Hotel\\workersReport.html");
			}
		} 
	}
}
