package clashsoft.cscalc.strings.character;

import clashsoft.cscalc.strings.DefaultStringConverter;

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
	
	@Override
	public void addArguments()
	{
	}
}
