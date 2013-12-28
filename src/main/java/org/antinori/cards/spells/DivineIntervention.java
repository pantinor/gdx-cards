package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class DivineIntervention extends BaseSpell {
	public DivineIntervention(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		ownerPlayer.incrementStrength(CardType.AIR, 2);
		ownerPlayer.incrementStrength(CardType.FIRE, 2);
		ownerPlayer.incrementStrength(CardType.EARTH, 2);
		ownerPlayer.incrementStrength(CardType.WATER, 2);

		this.owner.incrementLife(10, game);

		
	}
}
