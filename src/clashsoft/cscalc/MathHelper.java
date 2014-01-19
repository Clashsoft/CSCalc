package clashsoft.cscalc;

public class MathHelper
{
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
		return Double.longBitsToDouble(Double.doubleToRawLongBits(d1) & Double.doubleToRawLongBits(d2));
	}
	
	public static double or(double d1, double d2)
	{
		return Double.longBitsToDouble(Double.doubleToRawLongBits(d1) | Double.doubleToRawLongBits(d2));
	}
	
	public static double xor(double d1, double d2)
	{
		return Double.longBitsToDouble(Double.doubleToRawLongBits(d1) ^ Double.doubleToRawLongBits(d2));
	}
}
