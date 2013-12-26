package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.SlotImage;

public class DemonQuartermaster extends BaseCreature {
	public DemonQuartermaster(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		ownerPlayer.incrementStrength(CardType.DEMONIC, 1, true);

	}

	public void onAttack() {
		super.onAttack();
	}
	
	public void onDying() {
		super.onDying();

		try {

			SlotImage[] slots = owner.getSlots();

			addCreature("EnragedQuartermaster", slotIndex, slots[slotIndex]);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
