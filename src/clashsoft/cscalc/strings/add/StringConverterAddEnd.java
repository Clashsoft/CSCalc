package clashsoft.cscalc.strings.add;

import javax.swing.JTextField;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterAddEnd extends DefaultStringConverter
{
	public JTextField textField;
	
	public StringConverterAddEnd()
	{
		super("Add text to end");
	}

	@Override
	public void addArguments()
	{	
		this.addLabel("Add");
		this.textField = this.addTextField(null);
		this.addLabel("to end of text");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		return input + this.textField.getText();
	}
}
