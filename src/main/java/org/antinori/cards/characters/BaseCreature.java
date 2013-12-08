package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.network.Event;
import org.antinori.cards.network.NetworkEvent;

public class BaseCreature extends BaseFunctions implements Creature {

	public BaseCreature(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {

		this.game = game;
		this.card = card;
		this.cardImage = cardImage;

		this.slotIndex = slotIndex;

		this.owner = owner;
		this.opponent = opponent;

		this.ownerPlayer = owner.getPlayerInfo();
		this.opposingPlayer = opponent.getPlayerInfo();

	}

	public void onSummoned() {

		System.out.println("onSummoned: " + card);

		ownerPlayer.decrementStrength(card.getType(), card.getCost());

		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.CARD_ADDED, slotIndex, card.getName(), ownerPlayer.getId());
			Cards.NET_GAME.sendEvent(ne);
			NetworkEvent ne2 = new NetworkEvent(Event.PLAYER_STRENGTH_AFFECTED, ownerPlayer.getId(), card.getType(), -1);
			Cards.NET_GAME.sendEvent(ne2);
		}

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(owner, 1);
		}

		CardImage[] teamCards = owner.getSlotCards();

		for (int index = 0; index < 6; index++) {
			if (index == slotIndex)
				continue;
			CardImage ci = teamCards[index];
			if (ci == null)
				continue;
			if (ci.getCard().getName().equalsIgnoreCase("minotaurcommander")) {
				this.card.incrementAttack(1);
				sendNetworkEventAttackAffected(slotIndex, card.getName(), ownerPlayer.getId(), 1);
			}
		}

		if (nl >= 0 && teamCards[nl] != null) {

			String leftNeighbor = teamCards[nl].getCard().getName();

			if (leftNeighbor.equalsIgnoreCase("merfolkoverlord")) {
				onAttack();
			}

			if (leftNeighbor.equalsIgnoreCase("orcchieftain")) {
				this.card.incrementAttack(2);
				sendNetworkEventAttackAffected(nl, card.getName(), ownerPlayer.getId(), 2);
			}

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nl].getCard().incrementAttack(2);
				sendNetworkEventAttackAffected(nl, teamCards[nl].getCard().getName(), ownerPlayer.getId(), 2);
			}

		}

		if (nr <= 5 && teamCards[nr] != null) {

			String rightNeighbor = teamCards[nr].getCard().getName();

			if (rightNeighbor.equalsIgnoreCase("merfolkoverlord")) {
				onAttack();
			}

			if (rightNeighbor.equalsIgnoreCase("orcchieftain")) {
				this.card.incrementAttack(2);
				sendNetworkEventAttackAffected(nr, card.getName(), ownerPlayer.getId(), 2);
			}

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nr].getCard().incrementAttack(2);
				sendNetworkEventAttackAffected(nr, teamCards[nr].getCard().getName(), ownerPlayer.getId(), 2);
			}

		}

	}

	public void onAttack() {

		System.out.println("onAttack: " + card.getName());

		int attack = this.card.getAttack();

		CardImage[] enemyCards = opponent.getSlotCards();

		boolean died = false;
		if (enemyCards[slotIndex] != null) {

			// damage opposing creature
			enemyCards[slotIndex].decrementLife(attack, game);
			sendNetworkEventHealthAffected(slotIndex, enemyCards[slotIndex].getCard().getName(), opposingPlayer.getId(), -attack);

			int remainingLife = enemyCards[slotIndex].getCard().getLife();
			died = (remainingLife < 1);

			if (died) {
				disposeCardImage(opponent, slotIndex);
				if (Cards.NET_GAME != null) {
					NetworkEvent ne = new NetworkEvent(Event.CARD_REMOVED, slotIndex, enemyCards[slotIndex].getCard().getName(), opposingPlayer.getId());
					Cards.NET_GAME.sendEvent(ne);
				}
			}

			// TODO add battle description to log

		} else {

			opponent.decrementLife(attack, game, false);
			if (Cards.NET_GAME != null) {
				NetworkEvent ne = new NetworkEvent(Event.PLAYER_HEALTH_AFFECTED, 0, "", opposingPlayer.getId(), 0, -attack);
				Cards.NET_GAME.sendEvent(ne);
			}

			int remainingLife = opposingPlayer.getLife();
			died = (remainingLife < 1);

			if (died) {
				game.handleGameOver();
			}

			// TODO add battle description to log
		}

		game.moveCardActorOnBattle(cardImage, owner);

	}

	public void onAttacked() {

		System.out.println("onAttacked: " + card);

	}

	public void onDying() {

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		CardImage[] teamCards = owner.getSlotCards();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(owner, -1);
		}

		if (nl >= 0 && teamCards[nl] != null) {

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nl].getCard().decrementAttack(2);
				sendNetworkEventAttackAffected(nr, teamCards[nl].getName(), ownerPlayer.getId(), -2);
			}
			if (name.equalsIgnoreCase("minotaurcommander")) {
				teamCards[nl].getCard().decrementAttack(1);
				sendNetworkEventAttackAffected(nr, teamCards[nl].getName(), ownerPlayer.getId(), -1);
			}

		}

		if (nr <= 5 && teamCards[nr] != null) {

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nr].getCard().decrementAttack(2);
				sendNetworkEventAttackAffected(nr, teamCards[nr].getName(), ownerPlayer.getId(), -2);
			}
			if (name.equalsIgnoreCase("minotaurcommander")) {
				teamCards[nr].getCard().decrementAttack(1);
				sendNetworkEventAttackAffected(nr, teamCards[nr].getName(), ownerPlayer.getId(), -1);

			}

		}

	}

	protected void damagePlayer(PlayerImage pi, int value) {

		pi.decrementLife(value, game, false);

		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_HEALTH_AFFECTED, 0, "", pi.getPlayerInfo().getId(), 0, -value);
			Cards.NET_GAME.sendEvent(ne);
		}

		if (pi.getPlayerInfo().getLife() < 1) {
			game.handleGameOver();
		}
	}

	public void startOfTurnCheck(PlayerImage player) {

	}

}
