package clashsoft.csutil.strings.character;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterTrim extends DefaultStringConverter
{
	public StringConverterTrim()
	{
		super("Remove trailing whitespaces");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		return input.trim();
	}
}
