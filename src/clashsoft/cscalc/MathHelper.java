package clashsoft.cscalc;

import java.text.DecimalFormatSymbols;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public strictfp class MathHelper
{
	/**
	 * A constant to turn the decimal places of a {@code double} to an
	 * {@code int}.
	 * <p>
	 * Equals 2<sup>32</sup>.
	 */
	public static double				DECIMAL_INT_FACTOR	= 2147483648D;
	
	public static ScriptEngineManager	scriptEngineManager	= new ScriptEngineManager();
	public static ScriptEngine			javaScriptEngine	= scriptEngineManager.getEngineByName("JavaScript");
	
	public static double clamp(double d, double min, double max)
	{
		return d < min ? min : (d > max ? max : d);
	}
	
	public static double power(double d1, double d2)
	{
		return Math.pow(d1, d2);
	}
	
	public static double root(double d1, double d2)
	{
		return Math.pow(d1, 1D / d2);
	}
	
	public static double lshift(double d1, double d2)
	{
		return d1 * Math.pow(2D, (int) d2);
	}
	
	public static double rshift(double d1, double d2)
	{
		return d1 / Math.pow(2D, (int) d2);
	}
	
	public static double rshiftu(double d1, double d2)
	{
		return d1 / Math.pow(2D, (int) d1);
	}
	
	public static double and(double d1, double d2)
	{
		return (long) d1 & (long) d2;
	}
	
	public static double or(double d1, double d2)
	{
		return (long) d1 | (long) d2;
	}
	
	public static double xor(double d1, double d2)
	{
		return (long) d1 ^ (long) d2;
	}
	
	public static double parse(String equation)
	{
		try
		{
			Object obj = javaScriptEngine.eval(equation);
			float f = ((Number) obj).floatValue();
			return f;
		}
		catch (Exception ex)
		{
			return 0F;
		}
	}
	
	public static double parse(String equation, double x)
	{
		return parse(equation.replace("x", "(" + x + ")"));
	}
	
	public static float parsef(String equation)
	{
		try
		{
			Object obj = javaScriptEngine.eval(equation);
			float f = ((Number) obj).floatValue();
			return f;
		}
		catch (Exception ex)
		{
			return 0F;
		}
	}
	
	public static float parsef(String equation, float x)
	{
		return parsef(equation.replace("x", "(" + x + ")"));
	}
	
	public static byte getDecimalPlaces(double d)
	{
		int i = getDecimal(d);
		return (byte) (Integer.lowestOneBit(i) - Integer.numberOfTrailingZeros(i));
	}
	
	public static long getInt(double d)
	{
		return (long) d;
	}
	
	public static int getDecimal(double d)
	{
		return (int) ((d % 1D) * DECIMAL_INT_FACTOR);
	}
	
	public static double getDouble(long i, int d)
	{
		return (double) i + ((double) d / DECIMAL_INT_FACTOR);
	}
	
	public static String toString(double d, int radix)
	{
		long integer = getInt(d);
		int decimal = getDecimal(d);
		String is = Long.toString(integer, radix);
		String ds = Integer.toString(decimal, radix).replaceAll("0*$", "");
		return toString(is, ds, radix);
	}
	
	protected static String toString(String value, String decimal, int radix)
	{
		int valueLength = value.length();
		int decimalLength = decimal.length();
		
		StringBuilder builder = new StringBuilder(valueLength + decimalLength + 3);
		
		switch (radix)
		{
		case 10:
			break;
		case 2:
			builder.append("0b");
			break;
		case 8:
			builder.append("0");
			break;
		case 16:
			builder.append("0x");
			break;
		default:
			builder.append("(" + radix + ") ");
			break;
		}
		
		builder.append(value);
		if (decimalLength > 0)
		{
			builder.append(DecimalFormatSymbols.getInstance().getDecimalSeparator());
			builder.append(decimal);
		}
		
		return builder.toString();
	}
}
