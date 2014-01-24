package clashsoft.cscalc.strings.conversion;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterUppercase extends DefaultStringConverter
{
	public StringConverterUppercase()
	{
		super("UPPERCASE");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		return input.toUpperCase();
	}
}
