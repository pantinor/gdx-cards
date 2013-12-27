package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.characters.BaseCreature;

public class PowerChains extends BaseSpell {
	public PowerChains(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();
		

		
		if (this.targetedCardImage != null) {
			CardType type = this.targetedCardImage.getCard().getType();
			
			if (type != CardType.AIR && type != CardType.FIRE && type != CardType.WATER && type != CardType.EARTH) {
				return;
			}
			
			this.targetedCardImage.decrementLife(adjustDamage(12), game);

			int remainingLife = targetedCardImage.getCard().getLife();
			boolean died = (remainingLife < 1);

			if (died) {
				BaseCreature bc = (BaseCreature) targetedCardImage.getCreature();
				disposeCardImage(opponent, bc.slotIndex);
			}
			
			this.opposingPlayer.decrementStrength(type, 3, true);
			
		}
	}
}
