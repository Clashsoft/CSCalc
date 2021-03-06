package clashsoft.csutil.strings.insert;

import clashsoft.csutil.strings.DefaultStringConverter;

import javax.swing.*;

public class StringConverterInsertPos extends DefaultStringConverter
{
	public JTextField textField;
	public JSpinner   spinner;

	public StringConverterInsertPos()
	{
		super("Insert text at position");
	}

	@Override
	public void addArguments()
	{
		this.addLabel("Insert");
		this.textField = this.addTextField(null);
		this.addLabel("at position");
		this.spinner = this.addSpinner();
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

		return insert(input, this.textField.getText(), pos);
	}
}
