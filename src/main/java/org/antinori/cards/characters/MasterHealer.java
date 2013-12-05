package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;

public class MasterHealer extends BaseCreature {
	public MasterHealer(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
	}
	
	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		CardImage[] cards = isComputer?game.getTopSlotCards():game.getBottomSlotCards();
		for (int index = 0;index<6;index++) {
			CardImage ci = cards[index];
			if (ci == null) continue;
			player.incrementLife(3, game);
			for (int j = 0;j<6;j++) {
				CardImage ci2 = cards[j];
				if (ci2 == null) continue;
				ci2.incrementLife(3, game);
			}
		}
	}
}
