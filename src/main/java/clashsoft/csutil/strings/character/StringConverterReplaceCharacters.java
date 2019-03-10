package clashsoft.csutil.strings.character;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;
import java.util.regex.Pattern;

public class StringConverterReplaceCharacters extends DefaultStringConverter
{
	public JTextField textFieldCharacters;
	public JTextField textField;

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
