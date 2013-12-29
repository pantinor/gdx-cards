package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class DevotedServant extends BaseCreature {
	public DevotedServant(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
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
		this.card.incrementAttack(1);
	}
	
	public void onDying() throws GameOverException {
		super.onDying();
		owner.getPlayerInfo().incrementStrength(CardType.VAMPIRIC, this.card.getAttack());

	}
}
