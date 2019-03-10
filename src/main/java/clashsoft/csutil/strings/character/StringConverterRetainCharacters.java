package clashsoft.csutil.strings.character;

import javax.swing.JTextField;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterRetainCharacters extends DefaultStringConverter
{
	public JTextField	textFieldCharacters;
	
	public StringConverterRetainCharacters()
	{
		super("Retain characters");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String chars = this.textFieldCharacters.getText();
		int len = input.length();
		StringBuilder builder = new StringBuilder(len);
		for (int i = 0; i < len; i++)
		{
			char c = input.charAt(i);
			if (chars.indexOf(c) != -1)
			{
				builder.append(c);
			}
		}
		return builder.toString();
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Retain all of");
		this.textFieldCharacters = this.addTextField();
	}
}
