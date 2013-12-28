package org.antinori.cards.spells;import java.util.Arrays;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class NaturesFury extends BaseSpell {
	public NaturesFury(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		
		int[] attacks = {0,0,0,0,0,0};

		for (int index = 0; index < 6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null)	continue;
			attacks[index] = ci.getCard().getAttack();
		}
		
		Arrays.sort(attacks); //sort ascending
		
		int damage = attacks[4] + attacks[5];
		
		damageOpponent(adjustDamage(damage));

	}
}
