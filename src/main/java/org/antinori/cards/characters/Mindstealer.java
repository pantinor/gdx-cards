package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class Mindstealer extends BaseCreature {
	public Mindstealer(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}


	public int onAttacked(BaseFunctions attacker, int damage) throws GameOverException {
		boolean died = attacker.cardImage.decrementLife(this, damage, game);
		if (died) {
			disposeCardImage(opponent, slotIndex);
		}
		return 0;
	}
	
}
