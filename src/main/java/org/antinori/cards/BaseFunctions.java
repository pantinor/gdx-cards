package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.List;

public class BaseFunctions {
	
	protected Card card;
	protected CardImage cardImage;
	protected Cards game;
	public int slotIndex = -1;
	
	protected Player opposingPlayer ;
	protected Player ownerPlayer ;
	
	protected PlayerImage owner ;
	protected PlayerImage opponent ;
	
	protected boolean isSpell = false;
	
	protected void damageOpponent(int value) throws GameOverException {
		damagePlayer(opponent, value);
	}
		
	protected void damagePlayer(PlayerImage pi, int value) throws GameOverException {
		
		CardImage[] ownedCards = pi.getSlotCards();
		CardImage[] opposingCards = game.getOpposingPlayerImage(pi.getPlayerInfo().getId()).getSlotCards();
		
		for (int index=0;index<6;index++) {
			
			if (opposingCards[index] != null) {
				//if justicar is unblocked do extra damage to player
				if (opposingCards[index].getCard().getName().equalsIgnoreCase("justicar") && ownedCards[index] == null ) {
					value = value + 2;
				}
				if (opposingCards[index].getCard().getName().equalsIgnoreCase("vampiremystic")) {
					opposingCards[index].getCard().incrementAttack(2);
				}
			}
			
			if (ownedCards[index] != null) {
				if (ownedCards[index].getCard().getName().equalsIgnoreCase("chastiser")) {
					ownedCards[index].getCard().incrementAttack(2);
				}
			}

		}
		
		pi.decrementLife(value, game, isSpell);
		
		Cards.logScrollPane.add(cardImage.getCard().getCardname() + " dealt " + value + " damage to player.");
		
		if (pi.getPlayerInfo().getLife() < 1) {
			throw new GameOverException(pi.getPlayerInfo().getId());
		}
	}
	
	public void healAll(int value) {
		for (int index = 0; index < 6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null)	continue;
			ci.incrementLife(value, game);
		}
	}
	
	
	public final void disposeCardImage(PlayerImage player, int slotIndex) throws GameOverException {
		CardImage ci = player.getSlotCards()[slotIndex];
		
		//remove actor from stage
		ci.addAction(sequence(fadeOut(2),removeActor(ci)));
		
		player.getSlots()[slotIndex].setOccupied(false);
		player.getSlotCards()[slotIndex] = null;
		
		ci.getCreature().onDying();

	}
	
	protected void damageAllExceptCurrentIndex(int attack, PlayerImage pi) throws GameOverException {
		for (int index = 0; index < 6; index ++) {
			if (index == slotIndex) continue;
			CardImage ci = pi.getSlotCards()[index];
			if (ci == null) continue;
			damageSlot(ci, index, pi, attack);
		}
	}
	
	protected void damageSlot(CardImage ci, int index, PlayerImage pi, int attack) throws GameOverException {
		boolean died = ci.decrementLife(attack, game);
		Cards.logScrollPane.add(cardImage.getCard().getCardname() + " dealt " + attack + " damage to " + ci.getCard().getName());
		if (died) {
			disposeCardImage(pi, index);
		}
	}
	
	protected void damageSlots(int[] indexes, PlayerImage pi, int value) throws GameOverException {
		for (int index : indexes) {
			if (index < 0 || index > 5) continue;
			CardImage ci = pi.getSlotCards()[index];
			if (ci == null) continue;
			damageSlot(ci, index, pi, value);
		}
	}
	
	protected void damageNeighbors(int value) throws GameOverException {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		damageSlots(neighborSlots, owner, value);
	}
	
	protected void damageAll(PlayerImage pi, int value) throws GameOverException {
		int[] slots = {0,1,2,3,4,5};
		damageSlots(slots, pi, value);
	}
	
	protected void enhanceAttackNeighboring(int value) {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		enhanceAttackSlots(neighborSlots, owner, value);
	}
	
	protected void enhanceAttackAll(PlayerImage pi, int value) {
		int[] slots = {0,1,2,3,4,5};
		enhanceAttackSlots(slots, pi, value);
	}
	
	protected void enhanceAttackSlots(int[] slots, PlayerImage pi, int value) {
		for (int index : slots) {
			if (index < 0 || index > 5 || index == slotIndex) continue;
			CardImage ci = pi.getSlotCards()[index];
			if (ci == null) continue;
			ci.getCard().incrementAttack(value);
		}
	}
	
	
	protected void addCreature(String name, int index, SlotImage slot) throws Exception {
		
		CardImage orig = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, name);
		CardImage ci1 = orig.clone();
		
		Creature sp1 = CreatureFactory.getCreatureClass(name, game, ci1.getCard(), ci1, index, owner, opponent);
		ci1.setCreature(sp1);
		
		ci1.setFont(Cards.greenfont);
		ci1.setFrame(Cards.ramka);
		
		ci1.addListener(game.new TargetedCardListener(owner.getPlayerInfo().getId(), index));
		ci1.addListener(game.sdl);
		
		slot.setOccupied(true);
		owner.getSlotCards()[index] = ci1;
		ci1.setBounds(slot.getX() + 5, slot.getY() + 26, ci1.getFrame().getWidth(), ci1.getFrame().getHeight());
		
		Sounds.play(Sound.SUMMON_DROP);
		
		game.stage.addActor(ci1);
	}
	
	protected void changeSpellCard(String name) {
		
		List<CardImage> cards = ownerPlayer.getCards(CardType.DEATH);
		
		cardImage.remove();

		CardImage newCard = null;
		try {
			newCard = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		newCard.setFont(Cards.greenfont);
		newCard.setFrame(Cards.spellramka);
		newCard.addListener(game.sdl);
		newCard.setBounds(cardImage.getX(), cardImage.getY(), cardImage.getWidth(), cardImage.getHeight());
		newCard.addListener(game.li);

		cards.remove(cardImage);
		cards.add(newCard);

		game.stage.addActor(newCard);

		CardImage.sort(cards);
		
	}
	

		

}
