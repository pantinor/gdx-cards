package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class ElfHermit extends BaseCreature {
	public ElfHermit(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		
		ownerPlayer.incrementStrength(CardType.EARTH, 2, !remoteEvent);

	}

	public void onAttack() {
		super.onAttack();
	}
}
