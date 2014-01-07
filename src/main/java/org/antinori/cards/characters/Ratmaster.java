package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.Dice;
import org.antinori.cards.GameOverException;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;

public class Ratmaster extends BaseCreature {
	public Ratmaster(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}
	
	@Override
	public void startOfTurnCheck() throws GameOverException {
		damageAll(opponent, 6);
		
		Dice dice = new Dice(1,5);
		CardType type = Player.TYPES[dice.roll() - 1];
		opposingPlayer.decrementStrength(type, 3);
	}


}
