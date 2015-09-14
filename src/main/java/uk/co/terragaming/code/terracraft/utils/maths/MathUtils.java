package uk.co.terragaming.code.terracraft.utils.maths;

import java.util.Random;


public class MathUtils {
	
	private static Random rand = new Random();
	
	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
}
