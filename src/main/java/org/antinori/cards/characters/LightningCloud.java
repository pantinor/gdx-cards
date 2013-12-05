package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class LightningCloud extends BaseCreature {
	public LightningCloud(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
		
		int attack = this.card.getAttack();		
		
		for (int i=0;i<6;i++) {
			if (i == slotIndex) continue;
					
			CardImage ci = enemyCards[i];
			if (ci == null) continue;
			
			ci.decrementLife(attack, game);
			
			int remainingLife = ci.getCard().getLife();
			boolean died = (remainingLife < 1);
			
			if (died) {
				disposeEnemy(ci, i);
			}
		}
		
		
	}
}
