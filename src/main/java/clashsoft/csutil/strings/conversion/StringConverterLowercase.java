package clashsoft.csutil.strings.conversion;

import clashsoft.csutil.strings.DefaultStringConverter;

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
}
