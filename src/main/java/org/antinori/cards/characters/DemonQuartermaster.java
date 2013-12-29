package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.SlotImage;

public class DemonQuartermaster extends BaseCreature {
	public DemonQuartermaster(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		ownerPlayer.incrementStrength(CardType.DEMONIC, 1);

	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	public void onDying() throws GameOverException {
		super.onDying();

		try {

			SlotImage[] slots = owner.getSlots();

			addCreature("EnragedQuartermaster", slotIndex, slots[slotIndex]);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
