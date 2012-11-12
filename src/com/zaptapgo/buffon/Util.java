package com.zaptapgo.buffon;

public class Util {
	
	public static double floorToMultiple(double d, double multiple) {
		return Math.floor(d / multiple) * multiple;
	}
	
	public static double ceilToMultiple(double d, double multiple) {
		return floorToMultiple(d, multiple) + multiple;
	}

}
