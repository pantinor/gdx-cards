package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class ElvenHealer extends BaseCreature {

	public ElvenHealer(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}
	
	public void onAttack() {
		
		owner.incrementLife(3, game);
		
		super.onAttack();
		
	}


}
