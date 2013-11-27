package org.antinori.cards;

import java.util.Random;

public class Dice {

	int num;
	int sides;

	public Dice() {
		num = 1;
		sides = 6;
	}

	public Dice(int number) {
		sides = 6;
		num = number;
	}

	public Dice(int number, int num_sides) {
		sides = num_sides;
		num = number;
	}

	public int roll() {
		Random r = new Random();
		int sum = 0;
		for (int i = 0; i < num; i++) {
			sum += r.nextInt(sides) + 1;
		}
		return sum;
	}

}
