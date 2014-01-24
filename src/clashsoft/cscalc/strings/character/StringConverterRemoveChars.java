package clashsoft.cscalc.strings.character;

import java.util.regex.Pattern;

import javax.swing.JTextField;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterRemoveChars extends DefaultStringConverter
{
	public JTextField	textFieldCharacters;
	
	public StringConverterRemoveChars()
	{
		super("Remove characters");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String chars = this.textFieldCharacters.getText();
		if (!chars.isEmpty())
		{
			return input.replaceAll("[" + Pattern.quote(chars) + "]", "");
		}
		return input;
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Remove any of");
		this.textFieldCharacters = this.addTextField();
	}
}
