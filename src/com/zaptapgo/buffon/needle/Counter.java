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
		offTarget = total - onTarget;
		
//		BigDecimal onTargetBig ``= new BigDecimal(onTarget);
//		BigDecimal offTargetBig = new BigDecimal(offTarget);
//		BigDecimal needlesSizeBig = new BigDecimal(total);
	//	BigDecimal onTargetPercent = onTargetBig.divide(needlesSizeBig).multiply(new BigDecimal(100));
	//	BigDecimal offTargetPercent = offTargetBig.divide(needlesSizeBig).multiply(new BigDecimal(100));
		//System.out.println("Percent On Target: " + onTarget);
		//System.out.println("Percent Off Target: " + offTarget);
		
		// 2 * total drop / number of hits should appx equal pi
		//BigDecimal piAppx = needlesSizeBig.multiply(new BigDecimal(2));
	//	piAppx = piAppx.divide(onTargetBig)
		double piAppx = ((double) (2 * total) / (double) onTarget);
		String piPrint = "pi is equal to appx. : " + ((Double.isNaN(piAppx)) ?
				"??? (There are no needles on the grid!)" : piAppx);
		//System.out.println(piPrint);
		Main.onTarget.setText("On Target: " + onTarget);
		Main.offTarget.setText("Off Target: " + offTarget);
		Main.pi.setText("Appx Pi: " + ((Double.isNaN(piAppx) ? "???" : piAppx)));
		Main.results.pack();
		if (!debug) {
			JOptionPane.showMessageDialog(Main.frame, ("Hits: " + onTarget + "\nMisses: " + offTarget
					+ "\nTotal Throws: " + total + "\nPi was approximated to: " + piPrint));   
		}
	}

}
