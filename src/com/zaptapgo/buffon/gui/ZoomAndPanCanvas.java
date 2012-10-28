package com.zaptapgo.buffon.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.zaptapgo.buffon.Main;
import com.zaptapgo.buffon.needle.Needle;

/**
 * A modification of the ZoomAndPanCanvas class, originally designed by Sorin Postelnicu,
 * but changed to allow for more flexible drawing, faster, less complex matrix mathematics,
 * and other functions specific to this application.
 */
 
public class ZoomAndPanCanvas extends Canvas {
 
	private static final long serialVersionUID = 1L;

	/**
	 * The canvas being used to represent the game. This should never change once it is set.
	 */
	public static ZoomAndPanCanvas maincanvas = null;
 
	/**
	 * Tells if the paint function has been called at least once
	 * Should never be modified after first call of paint
	 */
    public boolean init = true;
 
    public ZoomAndPanListener zoomAndPanListener;
    
    public Point[] points = {
            new Point(-100, -100),
            new Point(-100, 100),
            new Point(100, -100),
            new Point(100, 100)
    };
 
    public ZoomAndPanCanvas() {
        this.zoomAndPanListener = new ZoomAndPanListener(this);
        this.addMouseListener(zoomAndPanListener);
        this.addMouseMotionListener(zoomAndPanListener);
        this.addMouseWheelListener(zoomAndPanListener);
        this.setBackground(Color.BLACK);
        if(maincanvas == null){
        	maincanvas = this;
        }
    }
 
    public ZoomAndPanCanvas(int minZoomLevel, int maxZoomLevel, double zoomMultiplicationFactor) {
        this.zoomAndPanListener = new ZoomAndPanListener(this, minZoomLevel, maxZoomLevel, zoomMultiplicationFactor);
        this.addMouseListener(zoomAndPanListener);
        this.addMouseMotionListener(zoomAndPanListener);
        this.addMouseWheelListener(zoomAndPanListener);
    }
 
    /**
     * Gets the preferred size for the canvas
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }
 
    /**
     * Draws all GUI elements onto the canvas.
     * @param g1 ghe basic graphics element to be used in rendering.
     */
    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        if (init) {
            // Initialize the viewport by moving the origin to the center of the window,
            // and inverting the y-axis to point upwards.
            init = false;
            Dimension d = getSize();
            int xc = d.width / 2;
            int yc = d.height / 2;
            g.translate(xc, yc);
            g.scale(1, -1);
            // Save the viewport to be updated by the ZoomAndPanListener
            zoomAndPanListener.setCoordTransform(g.getTransform());
        } else {
            // Restore the viewport after it was updated by the ZoomAndPanListener
            g.setTransform(zoomAndPanListener.getCoordTransform());
        }
        
        //Do rendering of uniform verticle gridlines
        int alt = 0;
        switch (Main.gridMode) {
        case 0:
        	for (int x = -10000; x <= 10000; x += 100) {
        		if ((alt % 2) == 0) {
        			g.setColor(Color.DARK_GRAY);
        			g.fillRect(x + 1, -10000, 99, 20000);
        		}
        		alt++;
        	}
        	break;
        case 1:
        	g.setColor(Color.WHITE);
        	g.drawOval(-100, -100, 200, 200);
        	for (int rad = 20000; rad > 0; rad -= 100) {
        		if ((alt % 2) == 0) {
        			g.setColor(Color.DARK_GRAY);
        		} else {
        			g.setColor(Color.BLACK);
        		}
        		g.fillOval(-rad, -rad, (2 * rad) , (2 * rad));
        		alt++;
        	}
        	break;
        }
        g.setColor(Color.BLACK);
        g.fillRect(-20000, -20000, 9900, 40000);
        g.fillRect(-20000, 10000, 40000, 9900);
        g.fillRect(-20000, -20000, 40000, 10000);
        g.fillRect(10100, -20000, 9900, 40000);
        //TODO Add Option for horizontal gridlines, as well as Polar gridlines.
        
        //Draw needles
        for (Needle n : Needle.needles) {
        	if (n == null) {
        		System.out.println("One of the needles was null!");
        		continue;
        	}
        	g.setColor(Color.RED);
        	g.drawLine((int)n.x1, (int)n.y1, (int)n.x2, (int)n.y2);
        }
        //Do all rendering of gameworld items here
        g.setColor(Color.WHITE);
        g.drawLine(0, -10000, 0, 10000);
        g.drawLine(-10100, 0, 10100, 0);
        g.drawLine(-10100, -10000, -10100, 10000);
        g.drawLine(10100, -10000, 10100, 10000);
        g.drawLine(-10100, 10000, 10100, 10000);
        g.drawLine(-10100, -10000, 10100, -10000);
        
        //Do all GUI rendering here
        g.setTransform(new AffineTransform());
        Point mousescreen = ZoomAndPanListener.screenMouseLoc;
        Point2D.Float mouseworld = ZoomAndPanListener.worldMouseLoc;
        boolean canFetchScreenMouse = true;
        if(mousescreen == null){
        	canFetchScreenMouse = false;
        }
        boolean canFetchWorldMouse = true;
        if(mouseworld == null){
        	canFetchWorldMouse = false;
        }
        if(canFetchScreenMouse && canFetchWorldMouse){
        	g.setColor(new Color(1, 1, 1, 0.8f));
        	g.drawString(("(" + Math.round(mouseworld.getX()) + ", " + Math.round(mouseworld.getY()) + ")"),
        			(int)mousescreen.getX(), ((int)mousescreen.getY() - 10));
        	g.setColor(Color.WHITE);
        }
    }
 
}