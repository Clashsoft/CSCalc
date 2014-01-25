package clashsoft.csutil.strings.insert;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterInsertInfront extends DefaultStringConverter
{
	public JTextField textField;
	public JTextField textFieldPattern;
	public JComboBox comboBoxMode;
	
	public StringConverterInsertInfront()
	{
		super("Insert text in front of existing text");
	}

	@Override
	public void addArguments()
	{	
		this.addLabel("Insert");
		this.textField = this.addTextField(null);
		this.addLabel("before");
		this.textFieldPattern = this.addTextField(null);
		this.addLabel("Mode:");
		this.comboBoxMode = this.addComboBox("First Index", "Last Index", "Regular Expression");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String text = this.textField.getText();
		String pattern = this.textFieldPattern.getText();
		int mode = this.comboBoxMode.getSelectedIndex();
		int pos = indexOf(input, pattern, mode);
		return insert(input, text, pos);
	}
}
