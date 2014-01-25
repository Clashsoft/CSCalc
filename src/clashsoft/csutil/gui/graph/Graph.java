package clashsoft.csutil.gui.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph extends BasicGraph
{
	private static final long	serialVersionUID	= -5668707800322302938L;
	
	private List<String>		equations			= new ArrayList();
	
	@Override
	public String getEquation(int i)
	{
		return this.equations.get(i);
	}
	
	@Override
	public int getEquationCount()
	{
		return this.equations.size();
	}
	
	public void setEquation(int i, String equation)
	{
		this.equations.set(i, equation);
		this.repaint();
	}
	
	public void addEquation(String equation)
	{
		this.equations.add(equation);
		this.repaint();
	}
	
	public boolean hasEquation(String equation)
	{
		return this.equations.contains(equation);
	}
	
	public void removeAllEquations()
	{
		this.equations.clear();
		this.repaint();
	}
	
	public void removeEquation(int i)
	{
		this.equations.remove(i);
		this.repaint();
	}
}
