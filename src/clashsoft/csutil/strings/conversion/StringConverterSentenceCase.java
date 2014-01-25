package clashsoft.csutil.strings.conversion;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterSentenceCase extends DefaultStringConverter
{
	public StringConverterSentenceCase()
	{
		super("Sentence case");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		int len = input.length();
		StringBuilder builder = new StringBuilder(len);
		boolean upper = true;
		
		for (int i = 0; i < len; i++)
		{
			char c = input.charAt(i);
			
			if (upper)
			{
				if (!Character.isWhitespace(c))
				{
					upper = false;
				}
				c = Character.toUpperCase(c);
			}
			else
			{
				if (c == '.')
				{
					upper = true;
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
}
