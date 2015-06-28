package org.antinori.cards.ai;

import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Player;


public abstract class Evaluation {
	
	public Move evaluate(Player player, Player opponent, CardImage[] playerSlots, CardImage[] oppoSlots) {
		
		for (CardType type : Player.TYPES) {
			
			for (CardImage ci : player.getCards(type)) {
				if (!ci.isEnabled()) continue;
				
				Player playerClone = player.cloneForEvaluation();
				Player opponentClone = opponent.cloneForEvaluation();
			}
		}

		
		return null;
		
	}
	

}
