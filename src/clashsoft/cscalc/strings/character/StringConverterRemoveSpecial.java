package clashsoft.cscalc.strings.character;

import java.util.regex.Pattern;

import javax.swing.JCheckBox;

import clashsoft.cscalc.strings.DefaultStringConverter;

public class StringConverterRemoveSpecial extends DefaultStringConverter
{
	public JCheckBox checkBoxWhitespace;
	public JCheckBox checkBoxDigits;
	public JCheckBox checkBoxPunctuation;
	
	public StringConverterRemoveSpecial()
	{
		super("Remove special characters");
	}

	@Override
	public String getConvertedString(String input)
	{
		int len = input.length();
		StringBuilder builder = new StringBuilder(len);
		boolean whitespace = !this.checkBoxWhitespace.isSelected();
		boolean digits = !this.checkBoxDigits.isSelected();
		boolean punct = !this.checkBoxPunctuation.isSelected();
		
		for (int i = 0; i < len; i++)
		{
			char c = input.charAt(i);
			if (!isSpecial(c, whitespace, digits, punct))
			{
				builder.append(c);
			}
		}
		return builder.toString();
	}
	
	public static boolean isSpecial(char c, boolean whitespace, boolean digits, boolean punct)
	{
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return false;
		if (whitespace && Character.isWhitespace(c)) return false;
		if (digits && Character.isDigit(c)) return false;
		if (punct && Pattern.matches("\\p{Punct}", String.valueOf(c))) return false;
		return true;
	}
	
	@Override
	public void addArguments()
	{
		this.checkBoxWhitespace = this.addCheckBox("Remove Whitespaces", false);
		this.checkBoxDigits = this.addCheckBox("Remove Digits", false);
		this.checkBoxPunctuation = this.addCheckBox("Remove Punctuation", true);
	}
}
