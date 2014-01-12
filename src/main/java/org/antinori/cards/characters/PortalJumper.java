package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class PortalJumper extends BaseCreature {
	public PortalJumper(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}
	
	@Override
	public void endOfTurnCheck() throws GameOverException {
		
		//try stun opposite slot
		if (opponent.getSlotCards()[slotIndex] != null) {
			opponent.getSlotCards()[slotIndex].getCreature().setSkipNextAttack(true);
		}
		
		tryMoveToAnotherRandomSlot(owner, cardImage, slotIndex, false);

	}
}
