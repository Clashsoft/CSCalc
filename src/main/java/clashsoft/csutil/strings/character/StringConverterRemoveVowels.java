package clashsoft.csutil.strings.character;

import clashsoft.csutil.strings.DefaultStringConverter;

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
