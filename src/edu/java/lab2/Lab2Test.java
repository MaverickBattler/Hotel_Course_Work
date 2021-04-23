package edu.java.lab2;

import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.Test;

/**
 * ����� ������, ����������� ������ ���� ������� ��������� � ������� Assert � expected
 * @author Kirill Mokhnatkin, group 8308
 */
public class Lab2Test {
	/**
	 * ��������� ������� checkIfSearchIsNotEmpty �� ����������� ����������� ��������
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
	 * ��������� ������� checkIfAnythingSelected �� ����������� ����������� ��������
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
	 * ���������, ������ �� "������" ���������� FieldsNotEntered
	 * @throws FieldsNotEntered ����� ����������, ������� ������ ���� ������ ��������
	 */
	@Test(expected = FieldsNotEntered.class)
	    public void testFieldsNotEntered() throws FieldsNotEntered {
	        throw new FieldsNotEntered();
	}
}
