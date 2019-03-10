package clashsoft.csutil.strings.conversion;

import javax.swing.JTextField;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterExpandCamelCase extends DefaultStringConverter
{
	public JTextField	textFieldSeperator;
	
	public StringConverterExpandCamelCase()
	{
		super("Expand CamelCase");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		String seperator = this.textFieldSeperator.getText();
		int len = input.length();
		StringBuilder builder = new StringBuilder(len + 10);
		
		for (int i = 0; i < len;)
		{
			int i1 = i + 1;
			char c = input.charAt(i);
			
			builder.append(c);
			if (i1 < len)
			{
				char c1 = input.charAt(i1);
				if (Character.isUpperCase(c1))
				{
					builder.append(seperator);
				}
			}
			
			i = i1;
		}
		return builder.toString();
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Seperator:");
		this.textFieldSeperator = this.addTextField(" ");
	}
}
