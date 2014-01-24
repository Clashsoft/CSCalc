package clashsoft.cscalc.strings.character;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterRemoveVowels extends DefaultStringConverter
{
	public StringConverterRemoveVowels()
	{
		super("Remove vowels");
	}

	@Override
	public String getConvertedString(String input)
	{
		return input.replaceAll("[aeiouAEIOU]", "");
	}
}
