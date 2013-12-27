package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class DivineIntervention extends BaseSpell {
	public DivineIntervention(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();
		
		ownerPlayer.incrementStrength(CardType.AIR, 2, !remoteEvent);
		ownerPlayer.incrementStrength(CardType.FIRE, 2, !remoteEvent);
		ownerPlayer.incrementStrength(CardType.EARTH, 2, !remoteEvent);
		ownerPlayer.incrementStrength(CardType.WATER, 2, !remoteEvent);

		this.owner.incrementLife(10, game, !remoteEvent);

		
	}
}
