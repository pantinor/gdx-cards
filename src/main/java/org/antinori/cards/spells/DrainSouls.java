package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class DrainSouls extends BaseSpell {
	public DrainSouls(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		
		super.onCast();
		
		int count = 0;
		for (int index = 0; index < 6; index ++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null) continue;
			disposeCardImage(owner, ci.getCreature().getIndex());
			count ++;
		}
		for (int index = 0; index < 6; index ++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null) continue;
			disposeCardImage(opponent, ci.getCreature().getIndex());
			count ++;
		}
		int heal = count * 2;
		this.owner.incrementLife(heal, game);
		
		swapCard("RageofSouls", CardType.DEATH, "DrainSouls", owner);

	}
}
