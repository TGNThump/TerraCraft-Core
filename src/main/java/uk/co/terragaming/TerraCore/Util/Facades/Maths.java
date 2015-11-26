package uk.co.terragaming.TerraCore.Util.Facades;

import java.util.Random;

public class Maths {
	
	private static Random rand = new Random();
	
	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
}
