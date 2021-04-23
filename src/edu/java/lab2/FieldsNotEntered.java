package edu.java.lab2;

/**
 * Класс исключения, создается, когда пользователь нажал кнопку "Применить", но не заполнил хотя бы одно из текстовых полей
 */
class FieldsNotEntered extends Exception {
	public FieldsNotEntered()
	{
		super("Вы ввели не все поля");
	}
}