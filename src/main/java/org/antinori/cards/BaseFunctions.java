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
	
	
	public final void disposeCardImage(PlayerImage player, int slotIndex) {
		CardImage ci = player.getSlotCards()[slotIndex];
		
		ci.getCreature().onDying();
		
		//remove actor from stage
		ci.addAction(sequence(fadeOut(2),removeActor(ci)));
		
		player.getSlots()[slotIndex].setOccupied(false);
		player.getSlotCards()[slotIndex] = null;

		
	}
	
	protected void damageSlots(int[] indexes, PlayerImage pi, int value) {
		
		for (int index : indexes) {
			
			if (index < 0 || index > 5) continue;
					
			CardImage ci = pi.getSlotCards()[index];
			if (ci == null) continue;
						
			ci.decrementLife(value, game);
			
			int remainingLife = ci.getCard().getLife();
			boolean died = (remainingLife < 1);
			
			if (died) {
				disposeCardImage(pi, index);
			}
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
		int[] slots = new int[6];
		for (int i=0;i<6;i++) slots[i] = i;
		damageSlots(slots, pi, value);
	}
	
	protected void enhanceAttackNeighboring(int value) {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		enhanceAttackSlots(neighborSlots, owner, value);
	}
	
	protected void enhanceAttackAll(PlayerImage pi, int value) {
		int[] slots = new int[6];
		for (int i=0;i<6;i++) slots[i] = i;
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
		

}
