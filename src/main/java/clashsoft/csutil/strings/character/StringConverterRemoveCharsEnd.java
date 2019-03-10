package clashsoft.csutil.strings.character;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterRemoveCharsEnd extends DefaultStringConverter
{
	public JSpinner spinner;

	public StringConverterRemoveCharsEnd()
	{
		super("Remove characters from end");
	}

	@Override
	public String getConvertedString(String input)
	{
		SpinnerNumberModel model = (SpinnerNumberModel) this.spinner.getModel();
		int len = input.length();
		int pos = ((Number) model.getValue()).intValue();

		model.setMaximum(len);
		if (pos > len)
		{
			pos = len;
			model.setValue(pos);
		}

		return input.substring(0, len - pos);
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Remove");
		this.spinner = this.addSpinner();
		this.addLabel("characters from the end");
	}
}
