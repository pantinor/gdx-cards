package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.SlotImage;

public class GoblinRaider extends BaseCreature {
	public GoblinRaider(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		SlotImage[] slots = owner.getSlots();

		try {

			if (nl >= 0 && owner.getSlotCards()[nl] == null) {
				addCreature("GoblinRaider", nl, slots[nl]);
			}

			if (nr <= 5 && owner.getSlotCards()[nr] == null) {
				addCreature("GoblinRaider", nr, slots[nr]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
