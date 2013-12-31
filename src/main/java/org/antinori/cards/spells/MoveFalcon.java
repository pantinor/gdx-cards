package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class MoveFalcon extends BaseSpell {
	public MoveFalcon(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		for (int index=0;index<6;index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null) continue;
			if (ci.getCard().getName().equalsIgnoreCase("deathfalcon")) {
				//move the falcon to the targeted slot
				moveCardToAnotherSlot(owner, ci, index, targetSlot);
				break;
			}
		}
		
		damageAll(opponent, adjustDamage(4));

	}
}
