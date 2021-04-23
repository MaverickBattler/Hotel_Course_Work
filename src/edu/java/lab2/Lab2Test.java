package edu.java.lab2;

import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.Test;

/**
 * Класс тестов, проверяющий работу трех функций программы с помощью Assert и expected
 * @author Kirill Mokhnatkin, group 8308
 */
public class Lab2Test {
	/**
	 * Проверяет функцию checkIfSearchIsNotEmpty на возвращение корректного значения
	 */
	@Test
	public void testcheckIfSearchIsNotEmpty()
	{
		try
		{
		Assert.assertFalse(Lab2.checkIfSearchIsNotEmpty(new JTextField("sffdasf")));
		Assert.assertTrue(Lab2.checkIfSearchIsNotEmpty(new JTextField("Something")));
		} catch(AssertionError e){e.printStackTrace();}
	}
	/**
	 * Проверяет функцию checkIfAnythingSelected на возвращение корректного значения
	 */
	@Test
	public void testcheckIfAnythingSelected()
	{
		try
		{
		Assert.assertTrue(Lab2.checkIfAnythingSelected(new int[] {0,1}));
		Assert.assertFalse(Lab2.checkIfAnythingSelected(new int[] {}));
		} catch(AssertionError e){e.printStackTrace();}
	}
	/**
	 * Проверяет, правда ли "кинуто" исключение FieldsNotEntered
	 * @throws FieldsNotEntered класс исключения, который должен быть вызван функцией
	 */
	@Test(expected = FieldsNotEntered.class)
	    public void testFieldsNotEntered() throws FieldsNotEntered {
	        throw new FieldsNotEntered();
	}
}
