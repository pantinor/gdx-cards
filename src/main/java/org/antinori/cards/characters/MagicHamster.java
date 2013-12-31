package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class MagicHamster extends BaseCreature {
	public MagicHamster(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		
		super.onSummoned();
		
		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		CardImage[] teamCards = owner.getSlotCards();

		if (nl >= 0 && teamCards[nl] != null) {
			teamCards[nl].incrementLife(10, game);
		}
		
		if (nr <= 5 && teamCards[nr] != null) {
			teamCards[nr].incrementLife(10, game);
		}
		
		swapCard("NaturalHealing", CardType.BEAST, "MagicHamster", owner);
		
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	public void onDying() throws GameOverException {
		super.onDying();
		
		swapCard("MagicHamster", CardType.BEAST, "NaturalHealing", owner);

	}
	
}
