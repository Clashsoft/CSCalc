package clashsoft.csutil.strings.character;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;
import java.util.regex.Pattern;

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
