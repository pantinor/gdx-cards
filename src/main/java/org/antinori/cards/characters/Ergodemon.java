package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;

public class Ergodemon extends BaseCreature {
	public Ergodemon(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	public void onDying() throws GameOverException {
		super.onDying();
		
		for (CardType type : Player.TYPES) {
			opponent.getPlayerInfo().decrementStrength(type, 1);
		}

	}
}
