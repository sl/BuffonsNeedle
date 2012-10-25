package com.zaptapgo.buffon.needle;

import javax.swing.JOptionPane;

import com.zaptapgo.buffon.Main;

public class Counter implements Runnable {

	public static int onTarget = 0;
	
	public static int offTarget = 0;

	public static int total = 0;
	
	private boolean debug;
	
	public Counter(boolean debug) {
		this.debug = debug;
	}
	
	@Override
	public void run() {
		//Calculate, and display an approximate result for Pi, this is done in a separate
		//thread so as not to cause temporary freezing while doing the calculation
		offTarget = total - onTarget;
		double piAppx = ((double) (2 * total) / (double) onTarget);
		String piPrint = "pi is equal to appx. : " + ((Double.isNaN(piAppx)) ?
				"??? (There are no needles on the grid!)" : piAppx);
		Main.onTarget.setText("On Target: " + onTarget);
		Main.offTarget.setText("Off Target: " + offTarget);
		Main.pi.setText("Appx Pi: " + ((Double.isNaN(piAppx) ? "?" : piAppx)));
		double percentOnTarget = 100.0F * ((double) onTarget / (double) total);
		Main.percent.setText("Percent Hit: " + ((Double.isNaN(percentOnTarget) ? "?" : percentOnTarget)));
		Main.results.pack();
		if (!debug) {
			JOptionPane.showMessageDialog(Main.frame, ("Hits: " + onTarget + "\nMisses: " + offTarget
					+ "\nTotal Throws: " + total + "\nPi was approximated to: " + piPrint));   
		}
	}

}
