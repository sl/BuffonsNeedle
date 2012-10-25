package com.zaptapgo.buffon.needle;

import java.util.concurrent.LinkedBlockingQueue;

import com.zaptapgo.buffon.Main;

public class Needle {
	
	public static LinkedBlockingQueue<Needle> needles = new LinkedBlockingQueue<Needle>();
	
	public double x1;
	public double x2;
	public double y1;
	public double y2;
	
	public Needle(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		try {
			needles.put(this);
		} catch (InterruptedException e) {}
		if (needles.size() > Main.MAXIMUM_DRAWN_NEEDLES) {
			try {
				needles.take();
			} catch (InterruptedException e) {
			}
		}
	}
}
