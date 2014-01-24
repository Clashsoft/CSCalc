package clashsoft.cscalc.strings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clashsoft.cscalc.gui.GUI;

public abstract class DefaultStringConverter implements IStringConverter
{
	public String	name;
	
	public GUI		gui;
	public JPanel	panel;
	
	public DefaultStringConverter(String name)
	{
		this.name = name;
	}
	
	public JLabel addLabel(String text)
	{
		JLabel label = new JLabel(text);
		panel.add(label);
		return label;
	}
	
	public JTextField addTextField()
	{
		return addTextField(null);
	}
	
	public JTextField addTextField(String text)
	{
		JTextField textField = new JTextField();
		textField.setSize(134, 28);
		textField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				update();
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				update();
			}
			
		});
		this.panel.add(textField);
		return textField;
	}
	
	public JCheckBox addCheckBox(String text)
	{
		return this.addCheckBox(text, false);
	}
	
	public JCheckBox addCheckBox(String text, boolean selected)
	{
		JCheckBox checkBox = new JCheckBox(text, selected);
		checkBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				update();
			}
		});
		this.panel.add(checkBox);
		return checkBox;
	}
	
	public JSpinner addSpinner()
	{
		return this.addSpinner(Integer.MAX_VALUE);
	}
	
	public JSpinner addSpinner(int max)
	{
		return this.addSpinner(0, max);
	}
	
	public JSpinner addSpinner(int min, int max)
	{
		return this.addSpinner(0, min, max, 1);
	}
	
	public JSpinner addSpinner(int value, int min, int max, int step)
	{
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
		spinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				update();
			}
		});
		this.panel.add(spinner);
		return spinner;
	}
	
	public <E> JComboBox<E> addComboBox(E... items)
	{
		return addComboBox(0, items);
	}
	
	public <E> JComboBox<E> addComboBox(int selectedIndex, E... items)
	{
		JComboBox comboBox = new JComboBox<>(items);
		comboBox.setSelectedIndex(selectedIndex);
		comboBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				update();
			}
		});
		this.panel.add(comboBox);
		return comboBox;
	}
	
	public void update()
	{
		this.gui.updateStringConverter();
	}
	
	@Override
	public void addArguments(GUI gui, JPanel panel)
	{
		this.gui = gui;
		this.panel = panel;
		
		this.addArguments();
	}
	
	public void addArguments()
	{
		this.panel.setVisible(false);
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}
	
	public static String replaceAll(String string, String pattern, String replacement, boolean regex)
	{
		if (regex)
		{
			return string.replaceAll(pattern, replacement);
		}
		else
		{
			return string.replace(pattern, replacement);
		}
	}
	
	public static String replaceFirst(String string, String pattern, String replacement, boolean regex)
	{
		int len = pattern.length();
		int pos = indexOf(string, pattern, regex ? 2 : 0);
		if (pos < 0 || pos > len)
		{
			return string;
		}
		return new StringBuilder(string).replace(pos, len, replacement).toString();
	}
	
	public static String replaceLast(String string, String pattern, String replacement, boolean regex)
	{
		int len = pattern.length();
		int pos = indexOf(string, pattern, regex ? 3 : 1);
		if (pos < 0 || pos > len)
		{
			return string;
		}
		return new StringBuilder(string).replace(pos, len, replacement).toString();
	}
	
	public static String insert(String string, String s, int pos)
	{
		int len = string.length();
		if (pos < 0 || pos > len)
		{
			return string;
		}
		StringBuilder builder = new StringBuilder(string);
		builder.insert(pos, s);
		return builder.toString();
	}
	
	public static int indexOf(String text, String pattern, int mode)
	{
		boolean regex = (mode & 2) != 0;
		boolean last = (mode & 1) != 0;
		if (regex) // Regex
		{
			try
			{
				Matcher matcher = Pattern.compile(pattern).matcher(text);
				if (!matcher.find())
				{
					return -1;
				}
				else
				{
					return matcher.start();
				}
			}
			catch (PatternSyntaxException ex)
			{
				return -1;
			}
		}
		else
		{
			if (last) // Last Index
			{
				return text.lastIndexOf(pattern);
			}
			else
			{
				return text.indexOf(pattern);
			}
		}
	}
}
