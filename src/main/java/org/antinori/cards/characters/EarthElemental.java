package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class EarthElemental extends BaseCreature {
	public EarthElemental(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrengthEarth());
		ownerPlayer.incrementStrength(CardType.EARTH, 1);
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	@Override
	public void startOfTurnCheck() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrength(CardType.EARTH));
	}
}
