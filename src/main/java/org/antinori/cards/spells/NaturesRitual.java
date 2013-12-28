package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class NaturesRitual extends BaseSpell {
	public NaturesRitual(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		if (this.targetedCardImage != null) {
			this.targetedCardImage.incrementLife(8, game);
		}
		
		this.owner.incrementLife(8, game);

		
	}
}
