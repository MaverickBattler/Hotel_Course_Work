package edu.java.lab2;

import javax.swing.*;
import java.awt.*;
/*import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;*/
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Класс окна добавления строки в таблицу с текстовыми полями
 */
public class AddNew {
    private JFrame New;
    protected JButton apply;
    private JButton cancel;
    private JTextField[] textFields;
    private JLabel[] labels;
    private JComboBox cBox;
    private int amtTextFields;
    /**
     * Создание окна добавления строки в таблицу
     * @param FieldStrings массив строк, являющихся оглавлениями столбцов таблицы, в которую добавляется строка
     */
    public AddNew(String[] FieldStrings)
    {
    	New = new JFrame("Добавить");
        New.setSize(640,400);
        New.setLocation(100,100);
        New.setMinimumSize(new Dimension(640,400));
        New.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        apply = new JButton("Применить");
        cancel = new JButton("Отменить");
        if (FieldStrings[3]=="Номер комнаты")
        	amtTextFields=10;
        else
        	amtTextFields=3;
        cancel.addActionListener(e -> {
            New.dispose();
            for (int i=0; i<amtTextFields;i++)
            {
                textFields[i].setText("");
            }
        });
        /**
         * Отправление введенных данных в таблицу для добавления, поля проверяются на пустоту, 
         * если одно пустое - исключительная ситуация
         */
        apply.addActionListener(e -> {
        	try
        	{
        		for (JTextField i : textFields)
            	{
            		String text = i.getText();
            		if (text.length()==0) 
            			throw new FieldsNotEntered();
            	}
        		New.dispose();
            	for (int i=0; i<amtTextFields;i++)
                {
                    textFields[i].setText("");
                }
        	}
        	catch (FieldsNotEntered MyEx) {
        		JOptionPane.showMessageDialog(New,MyEx.getMessage());
        	}
        });
        /*JSpinner dateSpinner;
        Calendar calendar = new GregorianCalendar();
        Date initDate = calendar.getTime();
        
        calendar.add(Calendar.YEAR, -20);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 40);
        Date latestDate = calendar.getTime();
        AbstractSpinnerModel model = new SpinnerDateModel(initDate,
                                     earliestDate,
                                     latestDate,
                                     Calendar.YEAR);
        dateSpinner = new JSpinner(model);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));*/
        
        textFields = new JTextField[amtTextFields];
        labels = new JLabel[FieldStrings.length];
        for (int i = 0, j = 0; i < FieldStrings.length;i++)
        {
            labels[i] = new JLabel(FieldStrings[i]);
            New.add(labels[i]);
            if ((FieldStrings.length==4)&&(i==3))
            {
            	if (FieldStrings[i]=="Должность")
            		cBox = new JComboBox(new String[]{"Менеджер","Шеф-повар", "Администратор", "Уборщик", "Портье", "Охранник", "Директор", "Повар"});
            	else
            		cBox = new JComboBox(new String[] {"Занят", "Свободен"});
            	New.add(cBox);
            }
            else
            {
            	textFields[j]= new JTextField();
                New.add(textFields[j]);
                j++;
            }
        }
        New.add(apply);
        New.add(cancel);
        New.setBackground(new Color(0xFFFFFF));
        if (FieldStrings.length==4)
        	New.setLayout(new GridLayout(FieldStrings.length+3, 2, 5, 25));
        else 
        	New.setLayout(new GridLayout(FieldStrings.length+1, 2, 5, 3));
        	
    }
    /**
     * Установки видимости окна добавления строки в таблицу
     */
    public void show(){
        New.setVisible(true);
    }
    /**
     * Возврат строк данных, введенных пользователем; для таблиц работников и комнат одно значение выбирается ComboBox
     * @return строки данных, введенные в окне добавления строки;
     */
    public String[] getEnterFields()
    {
		String[] enteredInfo;
    	if (textFields.length==3)
    		enteredInfo = new String[] {textFields[0].getText(),textFields[1].getText(),textFields[2].getText(),(String)cBox.getSelectedItem()};
    	else
    		enteredInfo = new String[] {textFields[0].getText(),textFields[1].getText(),textFields[2].getText(),textFields[3].getText(),textFields[4].getText(),textFields[5].getText(),textFields[6].getText(),textFields[7].getText(),textFields[8].getText(),textFields[9].getText()};
    	return enteredInfo;
    }

}
