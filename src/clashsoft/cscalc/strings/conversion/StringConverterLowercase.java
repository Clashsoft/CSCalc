package clashsoft.cscalc.strings.conversion;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterLowercase extends DefaultStringConverter
{
	public StringConverterLowercase()
	{
		super("lowercase");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		return input.toLowerCase();
	}
	
	@Override
	public void addArguments()
	{
	}
}
