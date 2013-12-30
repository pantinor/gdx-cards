package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class WallofReflection extends BaseCreature {
	public WallofReflection(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		//nothing
	}
	
	public int onAttacked(BaseFunctions attacker, int damage) throws GameOverException {
		int d = super.onAttacked(attacker, damage);
		damageOpponent(d);
		return d;
	}
}
