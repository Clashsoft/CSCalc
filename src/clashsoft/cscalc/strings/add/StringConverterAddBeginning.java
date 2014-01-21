package clashsoft.cscalc.strings.add;

import javax.swing.JTextField;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterAddBeginning extends DefaultStringConverter
{
	public JTextField textField;
	
	public StringConverterAddBeginning()
	{
		super("Add text to beginning");
	}

	@Override
	public void addArguments()
	{	
		this.addLabel("Add");
		this.textField = this.addTextField(null);
		this.addLabel("to beginning of text");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		return this.textField.getText() + input;
	}
}
