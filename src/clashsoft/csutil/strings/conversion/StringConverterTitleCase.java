package clashsoft.csutil.strings.conversion;

import javax.swing.JCheckBox;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterTitleCase extends DefaultStringConverter
{
	public JCheckBox	checkBoxAcceptAllWhiteSpaces;
	
	public StringConverterTitleCase()
	{
		super("Title Case");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		boolean allWhitespaces = this.checkBoxAcceptAllWhiteSpaces.isSelected();
		int len = input.length();
		StringBuilder builder = new StringBuilder(len);
		boolean whitespace = true;
		
		for (int i = 0; i < len; i++)
		{
			char c = input.charAt(i);
			
			if (whitespace)
			{
				if (Character.isLetter(c))
				{
					whitespace = false;
				}
				c = Character.toUpperCase(c);
			}
			else
			{
				if (c == ' ' || allWhitespaces && Character.isWhitespace(c))
				{
					whitespace = true;
				}
				else
				{
					c = Character.toLowerCase(c);
				}
			}
			builder.append(c);
		}
		return builder.toString();
	}
	
	@Override
	public void addArguments()
	{
		this.checkBoxAcceptAllWhiteSpaces = this.addCheckBox("Accept all whitespaces", true);
	}
}
