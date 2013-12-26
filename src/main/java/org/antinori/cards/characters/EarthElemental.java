package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class EarthElemental extends BaseCreature {
	public EarthElemental(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		ownerPlayer.incrementStrength(CardType.EARTH, 1, !remoteEvent);
	}

	public void onAttack() {
		super.onAttack();
	}
	
	@Override
	public void startOfTurnCheck() {
		this.card.setAttack(ownerPlayer.getStrength(CardType.EARTH));
	}
}
