package clashsoft.csutil.gui.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import clashsoft.csutil.MathHelper;

public class BasicGraph extends Canvas
{
	private static final long	serialVersionUID	= 6584848915428177630L;
	
	private int					mouseX				= 0;
	private int					mouseY				= 0;
	
	public float				zoomX				= 50F;
	public float				zoomY				= 50F;
	
	public boolean				isPainting			= false;
	
	public BasicGraph()
	{
		this.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				BasicGraph.this.zoom(e.getPreciseWheelRotation());
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				BasicGraph.this.mouseX = e.getX();
				BasicGraph.this.mouseY = e.getY();
				BasicGraph.this.repaint();
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
		float hue = (float) i / (float) this.getEquationCount();
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
		return 0.0625F;
	}
	
	public float getStepY()
	{
		return 0.0625F;
	}
	
	public void zoom(double zoom)
	{
		zoom *= -4F;
		
		this.zoomX = (float) MathHelper.clamp(this.zoomX + zoom, 4D, 256D);
		this.zoomY = (float) MathHelper.clamp(this.zoomY + zoom, 4D, 256D);
		
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
		float f = MathHelper.parsef(this.getEquation(i), x);
		return f;
	}
	
	@Override
	public void paint(final Graphics g)
	{
		if (!this.isPainting)
		{
			this.isPainting = true;
			
			super.paint(g);
			
			int mouseX = this.mouseX;
			int mouseY = this.mouseY;
			
			int width = this.getWidth();
			int height = this.getHeight();
			
			float centerX = width / 2F;
			float centerY = height / 2F;
			
			float zoomX = this.getZoomX();
			float zoomY = this.getZoomY();
			float stepX = this.getStepX();
			float stepY = this.getStepY();
			
			this.drawGrid(g, width, height, centerX, centerY, zoomX, zoomY, 0.25F, 0.25F);
			
			for (int i = 0; i < this.getEquationCount(); i++)
			{
				this.drawGraph(g, i, width, height, centerX, centerY, zoomX, zoomY, stepX, stepY);
			}
			
			g.setColor(Color.BLUE);
			g.drawLine(mouseX, 0, mouseX, height);
			g.drawLine(0, mouseY, width, mouseY);
			
			if (this.getEquationCount() > 0)
			{
				float x = (mouseX - centerX) / zoomX;
				float fx = this.getFX(x, 0);
				g.drawString("f(" + x + ")=" + fx, mouseX + 12, mouseY + 22);
			}
			
			this.isPainting = false;
		}
	}
	
	public void drawGrid(Graphics g, int width, int height, float centerX, float centerY, float zoomX, float zoomY, float stepX, float stepY)
	{
		Color axisColor = this.getAxisColor();
		Color intColor = this.getIntColor();
		Color lineColor = this.getLineColor();
		
		int fontX = (int) centerX + 2;
		int fontY = (int) centerY + 12;
		
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
			boolean flag = f % 1F == 0F;
			
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
		
		g.setColor(axisColor);
		g.drawLine((int) centerX, 0, (int) centerX, height);
		g.drawLine(0, (int) centerY, width, (int) centerY);
		g.drawString("0.0", fontX, fontY);
	}
	
	public void drawGraph(Graphics g, int equation, int width, int height, float centerX, float centerY, float zoomX, float zoomY, float stepX, float stepY)
	{
		g.setColor(this.getGraphColor(equation));
		for (float f0 = -centerX; f0 < centerX;)
		{
			float f1 = f0 + stepX;
			float fx1 = this.getFX(f0, equation);
			float fx2 = this.getFX(f1, equation);
			
			if (!Float.isNaN(fx1) && !Float.isNaN(fx2))
			{
				float x1 = f0 * zoomX;
				float x2 = f1 * zoomX;
				float y1 = fx1 * zoomY;
				float y2 = fx2 * zoomY;
				
				int xp1 = (int) (centerX + x1);
				int xp2 = (int) (centerX + x2);
				int yp1 = (int) (centerY - y1);
				int yp2 = (int) (centerY - y2);
				
				g.drawLine(xp1, yp1, xp2, yp2);
				g.drawLine(xp1, yp1 + 1, xp2, yp2 + 1);
			}
			
			f0 = f1;
		}
	}
}
