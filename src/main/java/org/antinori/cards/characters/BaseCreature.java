package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;

public class BaseCreature extends BaseFunctions implements Creature {
		
	public BaseCreature(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		
		this.game = game;
		this.card = card;
		this.cardImage = cardImage;
		this.isComputer = isComputer;
		this.slotIndex = slotIndex;
		
		this.enemyCards = isComputer?game.getBottomSlotCards():game.getTopSlotCards();
		this.teamCards = isComputer?game.getTopSlotCards():game.getBottomSlotCards();
		
		this.ownerPlayer = isComputer?game.opponent.getPlayerInfo():game.player.getPlayerInfo();
		this.opposingPlayer = isComputer?game.player.getPlayerInfo():game.opponent.getPlayerInfo();

		this.owner = isComputer?game.opponent:game.player;
		this.opponent = isComputer?game.player:game.opponent;
		
	}

	public void onSummoned() {
		
		System.out.println("onSummoned: " + card);
		
		ownerPlayer.decrementStrength(card.getType(), card.getCost());
		
		do {
			if (card.getName().equalsIgnoreCase("firedrake")) {
				break;
			}
			
			int nl = slotIndex - 1;
			int nr = slotIndex + 1;
			
			if (nl >= 0 && 
					teamCards[nl] != null && 
					teamCards[nl].getCard().getName().equalsIgnoreCase("merfolkoverlord")) {
				onAttack(); 
				break;
			}
			
			if (nr <= 5 && 
					teamCards[nr] != null && 
					teamCards[nr].getCard().getName().equalsIgnoreCase("merfolkoverlord")) {
				onAttack();
				break;
			}

		} while (false);

		
	}

	public void onAttack() {
		
		System.out.println("onAttack: " + card.getName() + " isComputer: " + isComputer + "" + enemyCards[slotIndex]);

		
		int attack = this.card.getAttack();
		
		boolean died = false;
		if (enemyCards[slotIndex] != null) {
			
			//damage opposing creature
			enemyCards[slotIndex].decrementLife(attack, game);
			
			int remainingLife = enemyCards[slotIndex].getCard().getLife();
			died = (remainingLife < 1);
			
			if (died) {
				disposeEnemy(enemyCards[slotIndex], slotIndex);
			}
			
			//TODO add battle description to log

		} else {
			
			PlayerImage pi = isComputer?game.player:game.opponent;

			pi.decrementLife(attack, game, false);
			
			int remainingLife = opposingPlayer.getLife();
			died = (remainingLife < 1);
			
			if (died) {
				game.handleGameOver();
			}
			
			//TODO add battle description to log
		}
		
		moveCardActorOnBattle();
		
	}

	public void onAttacked() {
		
		System.out.println("onAttacked: " + card);

		
	}
	
	public void onDying() {
		
	}


	
	protected void damagePlayer(boolean damageOwner, int value) {
		Player player = damageOwner?ownerPlayer:opposingPlayer;
		PlayerImage pi = damageOwner?owner:opponent;
		
		pi.decrementLife(value, game, false);
		
		if (player.getLife() < 1) {
			game.handleGameOver();
		}
	}


}
