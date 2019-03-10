package clashsoft.csutil.strings.conversion;

import clashsoft.csutil.strings.DefaultStringConverter;

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
