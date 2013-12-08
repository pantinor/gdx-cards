package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;

public class SeaSprite extends BaseCreature {

	public SeaSprite(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
	}

	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		damagePlayer(owner, 2);
	}
}
