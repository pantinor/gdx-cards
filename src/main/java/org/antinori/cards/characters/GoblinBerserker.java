package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;

public class GoblinBerserker extends BaseCreature {

	public GoblinBerserker(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}
	
	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
	}
	
	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		damageNeighbors(true, 2);
	}
}
