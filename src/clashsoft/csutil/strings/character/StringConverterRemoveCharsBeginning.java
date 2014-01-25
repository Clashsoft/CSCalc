package clashsoft.csutil.strings.character;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import clashsoft.csutil.strings.DefaultStringConverter;

public class StringConverterRemoveCharsBeginning extends DefaultStringConverter
{
	public JSpinner spinner;
	
	public StringConverterRemoveCharsBeginning()
	{
		super("Remove characters from beginning");
	}
	
	@Override
	public String getConvertedString(String input)
	{
		SpinnerNumberModel model = (SpinnerNumberModel)this.spinner.getModel();
		int len = input.length();
		int pos = ((Number)model.getValue()).intValue();
		
		model.setMaximum(len);
		if (pos > len)
		{
			pos = len;
			model.setValue(pos);
		}
		
		return input.substring(pos);
	}
	
	@Override
	public void addArguments()
	{
		this.addLabel("Remove");
		this.spinner = this.addSpinner();
		this.addLabel("characters from the beginning");
	}
}
