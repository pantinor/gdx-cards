package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;

public class MasterHealer extends BaseCreature {
	public MasterHealer(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
	}

	public void startOfTurnCheck(PlayerImage player) {
		for (int index = 0; index < 6; index++) {
			CardImage ci = player.getSlotCards()[index];
			if (ci == null)
				continue;
			player.incrementLife(3, game);
			for (int j = 0; j < 6; j++) {
				CardImage ci2 = player.getSlotCards()[j];
				if (ci2 == null)
					continue;
				ci2.incrementLife(3, game);
			}
		}
	}
}
