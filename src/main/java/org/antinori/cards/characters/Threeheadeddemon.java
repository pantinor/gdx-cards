package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.SlotImage;

public class Threeheadeddemon extends BaseCreature {
	public Threeheadeddemon(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
		
		int attack = this.card.getAttack();

		damageAllExceptCurrentIndex(attack);
		
		CardImage[] enemyCards = opponent.getSlotCards();

		if (enemyCards[slotIndex] != null) {
			//damage the opponent even if there is a card opposite
			damageOpponent(attack);
		}

	}
	
	public void onDying() {
		super.onDying();

		try {

			SlotImage[] slots = owner.getSlots();

			addCreature("DemonApostate", slotIndex, slots[slotIndex]);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	

}
