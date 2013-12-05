package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.PlayerImage;

public class FireElemental extends BaseCreature {

	public FireElemental(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		super.onSummoned();
		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		damageAll(false,3);
		damagePlayer(false,3);
	}
	
	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		this.card.setAttack(player.getPlayerInfo().getStrength(CardType.FIRE));
	}
}
