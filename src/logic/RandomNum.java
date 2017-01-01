package logic;

import java.util.Random;

public class RandomNum {
	public static int getRandomInt(int max) {
		Random rand = new Random();
		int i= rand.nextInt(max);
		return i;	
	}
}
