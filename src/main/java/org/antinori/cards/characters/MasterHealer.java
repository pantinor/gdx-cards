package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class MasterHealer extends BaseCreature {
	public MasterHealer(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}

	@Override
	public void startOfTurnCheck() throws GameOverException {

		owner.incrementLife(3, game);

		for (int index = 0; index < 6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null)	continue;
			ci.incrementLife(3, game);
		}

	}
}
