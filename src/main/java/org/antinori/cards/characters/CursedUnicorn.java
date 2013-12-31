package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class CursedUnicorn extends BaseCreature {
	public CursedUnicorn(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}
	
	public int onAttacked(BaseFunctions attacker, int damage) throws GameOverException {
		
		if (opponent.getSlotCards()[slotIndex] != null && attacker.isSpell) {
			damageSlot(opponent.getSlotCards()[slotIndex], slotIndex, opponent, damage);
			return damage;
		} else {
			return super.onAttacked(attacker, damage);
		}
	}
	
	public void startOfTurnCheck() throws GameOverException {
		if (opponent.getSlotCards()[slotIndex] != null) {
			cardImage.decrementLife(this, 5, game);
		}
	}
}
