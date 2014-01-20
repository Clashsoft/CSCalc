package clashsoft.cscalc.gui.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import clashsoft.cscalc.MathHelper;

public class BasicGraph extends Canvas
{
	private static final long	serialVersionUID	= 6584848915428177630L;
	
	public float				zoomX				= 50F;
	public float				zoomY				= 50F;
	
	public BasicGraph()
	{
		this.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				zoom(e.getPreciseWheelRotation());
			}
		});
		this.setBackground(Color.WHITE);
	}
	
	public Color getAxisColor()
	{
		return Color.BLACK;
	}
	
	public Color getIntColor()
	{
		return Color.GRAY;
	}
	
	public Color getLineColor()
	{
		return Color.LIGHT_GRAY;
	}
	
	public Color getGraphColor(int i)
	{
		float hue = (float)i / (float)this.getEquationCount();
		return Color.getHSBColor(hue, 1F, 1F);
	}
	
	public float getZoomX()
	{
		return this.zoomX;
	}
	
	public float getZoomY()
	{
		return this.zoomY;
	}
	
	public float getStepX()
	{
		return getZoomX() < 20 ? 1 : 0.25F;
	}
	
	public float getStepY()
	{
		return getZoomY() < 20 ? 1 : 0.25F;
	}
	
	public void zoom(double zoom)
	{
		zoom *= -10F;
		
		this.zoomX = (float) MathHelper.clamp(this.zoomX + zoom, 4, 250);
		this.zoomY = (float) MathHelper.clamp(this.zoomY + zoom, 4, 250);
		
		this.repaint();
	}
	
	public void setZoom(float zoom)
	{
		this.zoomX = zoom;
		this.zoomY = zoom;
		this.repaint();
	}
	
	public String getEquation(int i)
	{
		return "x";
	}
	
	public int getEquationCount()
	{
		return 1;
	}
	
	public float getFX(float x, int i)
	{
		return MathHelper.parsef(this.getEquation(i), x);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		float centerX = width / 2F;
		float centerY = height / 2F;
		
		float zoomX = getZoomX();
		float zoomY = getZoomY();
		float stepX = getStepX();
		float stepY = getStepY();
		
		this.drawGrid(g, width, height, centerX, centerY, zoomX, zoomY, stepX, stepY);
		
		for (int i = 0; i < this.getEquationCount(); i++)
		{
			this.drawGraph(g, i, width, height, centerX, centerY, zoomX, zoomY, stepX, stepY);
		}
	}
	
	public void drawGrid(Graphics g, int width, int height, float centerX, float centerY, float zoomX, float zoomY, float stepX, float stepY)
	{
		Color axisColor = getAxisColor();
		Color intColor = getIntColor();
		Color lineColor = getLineColor();
		
		int fontX = (int) centerX + 2;
		int fontY = (int) centerY + 12;
		
		g.setColor(axisColor);
		g.drawLine((int) centerX, 0, (int) centerX, height);
		g.drawLine(0, (int) centerY, width, (int) centerY);
		g.drawString("0.0", fontX, fontY);
		
		g.setColor(lineColor);
		for (float f = stepX; f < centerX; f += stepX)
		{
			boolean flag = f == (int) f;
			
			if (flag)
			{
				g.setColor(intColor);
			}
			
			float f1 = f * zoomX;
			int i1 = (int) (centerX - f1);
			int i2 = (int) (centerX + f1);
			g.drawLine(i1, 0, i1, height);
			g.drawLine(i2, 0, i2, height);
			
			if (flag)
			{
				g.drawString("-" + f, i1 + 2, fontY);
				g.drawString("" + f, i2 + 2, fontY);
				g.setColor(lineColor);
			}
		}
		for (float f = stepY; f < centerY; f += stepY)
		{
			boolean flag = f == (int) f;
			
			if (flag)
			{
				g.setColor(intColor);
			}
			
			float f1 = f * zoomY;
			int i1 = (int) (centerY - f1);
			int i2 = (int) (centerY + f1);
			g.drawLine(0, i1, width, i1);
			g.drawLine(0, i2, width, i2);
			
			if (flag)
			{
				g.drawString("" + f, fontX, i1 + 12);
				g.drawString("-" + f, fontX, i2 + 12);
				g.setColor(lineColor);
			}
		}
	}
	
	public void drawGraph(Graphics g, int equation, int width, int height, float centerX, float centerY, float zoomX, float zoomY, float stepX, float stepY)
	{
		g.setColor(this.getGraphColor(equation));
		float y = this.getFX(0F, equation) * zoomY * 2;
		for (float f1 = 0; f1 < centerX;)
		{
			float f0 = f1 + stepX;
			float fx1 = this.getFX(f1, equation);
			float fx2 = this.getFX(f0, equation);
			
			float x1 = f1 * zoomX;
			float x2 = f0 * zoomX;
			float y1 = fx1 * zoomY;
			float y2 = fx2 * zoomY;
			
			int xp1 = (int) (centerX + x1);
			int xp2 = (int) (centerX + x2);
			int yp1 = (int) (centerY - y1);
			int yp2 = (int) (centerY - y2);
			
			int xn1 = (int) (centerX - x1);
			int xn2 = (int) (centerX - x2);
			int yn1 = (int) (centerY - y + y1);
			int yn2 = (int) (centerY - y + y2);
			
			g.drawLine(xp1, yp1, xp2, yp2);
			g.drawLine(xp1 + 1, yp1 + 1, xp2 + 1, yp2 + 1);
			g.drawLine(xn1, yn1, xn2, yn2);
			g.drawLine(xn1 + 1, yn1 + 1, xn2 + 1, yn2 + 1);
			
			f1 = f0;
		}
	}
}
