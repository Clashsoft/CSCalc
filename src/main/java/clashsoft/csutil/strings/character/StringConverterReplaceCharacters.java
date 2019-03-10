package clashsoft.csutil.strings.character;

import java.util.regex.Pattern;

import javax.swing.JTextField;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterReplaceCharacters extends DefaultStringConverter
{
	public JTextField	textFieldCharacters;
	public JTextField	textField;
	
	public StringConverterReplaceCharacters()
	{
		super("Replace characters");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String chars = this.textFieldCharacters.getText();
		if (!chars.isEmpty())
		{
			String replacement = this.textField.getText();
			return input.replaceAll("[" + Pattern.quote(chars) + "]", replacement);
		}
		return input;
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Replace any of");
		this.textFieldCharacters = this.addTextField();
		this.addLabel("with");
		this.textField = this.addTextField();
	}
}
