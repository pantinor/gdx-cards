package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Fanatic extends BaseCreature {
	public Fanatic(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	public int onAttacked(BaseFunctions attacker, int damage) throws GameOverException {
		
		if (attacker.isSpell) damage = damage * 2;	
		
		return super.onAttacked(attacker, damage);
	}
}
