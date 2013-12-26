package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

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
	
	
	//flag to avoid endless looping of network events
	protected boolean remoteEvent = false;
	
	protected void damageOpponent(int value) {
		damagePlayer(opponent, value);
	}
		
	protected void damagePlayer(PlayerImage pi, int value) {

		pi.decrementLife(value, game, isSpell, !remoteEvent);
		
		Cards.logScrollPane.add(pi.getPlayerInfo().getPlayerClass().getTitle() + "'s " 
				+ cardImage.getCard().getCardname() + " attacked opponent for " + value + " damage.");

		if (pi.getPlayerInfo().getLife() < 1) {
			game.handleGameOver();
		}
	}
	
	
	public final void disposeCardImage(PlayerImage player, int slotIndex) {
		CardImage ci = player.getSlotCards()[slotIndex];
		
		ci.getCreature().onDying();
		
		//remove actor from stage
		ci.addAction(sequence(fadeOut(2),removeActor(ci)));
		
		player.getSlots()[slotIndex].setOccupied(false);
		player.getSlotCards()[slotIndex] = null;

		
	}
	

	
	protected void damageAllExceptCurrentIndex(int attack) {
		for (int index = 0; index < 6; index ++) {
			
			if (index == slotIndex) continue;

			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null) continue;

			damageSlot(ci, index, attack);
		}
	}
	
	protected void damageSlot(CardImage ci, int index, int attack) {
		
		ci.decrementLife(attack, game);

		int remainingLife = ci.getCard().getLife();
		boolean died = (remainingLife < 1);
		
		Cards.logScrollPane.add(this.owner.getPlayerInfo().getPlayerClass().getTitle() + "'s " 
				+ cardImage.getCard().getCardname() + " attacked "
				+ ci.getCard().getCardname() 
				+ " for " + attack + " damage.");

		if (died) {
			disposeCardImage(opponent, index);
		}
	}
	
	protected void damageSlots(int[] indexes, PlayerImage pi, int value) {
		
		for (int index : indexes) {
			
			if (index < 0 || index > 5) continue;
					
			CardImage ci = pi.getSlotCards()[index];
			if (ci == null) continue;
						
			damageSlot(ci, index, value);
		}
	}

	
	protected void damageOpposingSlot(int value) {
		int[] slots = new int[1];
		slots[0] = slotIndex;
		damageSlots(slots, opponent, value);
	}
	
	protected void damageNeighbors(int value) {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		damageSlots(neighborSlots, owner, value);
	}
	
	protected void damageAll(PlayerImage pi, int value) {
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
		
		game.stage.addActor(ci1);
	}
	

		

}
