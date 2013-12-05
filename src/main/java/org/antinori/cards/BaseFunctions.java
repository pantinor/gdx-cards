package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.scenes.scene2d.Action;

public class BaseFunctions {
	
	protected Card card;
	protected CardImage cardImage;
	protected Cards game;
	protected boolean isComputer;
	public int slotIndex = -1;
	
	protected CardImage[] enemyCards ;
	protected CardImage[] teamCards ;
	
	protected Player opposingPlayer ;
	protected Player ownerPlayer ;
	protected PlayerImage owner ;
	protected PlayerImage opponent ;
	
	
	protected final void disposeEnemy(CardImage creature, int slotIndex) {
		
		creature.getCreature().onDying();
		
		//remove actor from stage
		creature.addAction(sequence(fadeOut(2),removeActor(creature)));
		
		if (isComputer) {
			game.getBottomSlots()[slotIndex].setOccupied(false);
			game.getBottomSlotCards()[slotIndex] = null;
		} else {
			game.getTopSlots()[slotIndex].setOccupied(false);
			game.getTopSlotCards()[slotIndex] = null;
		}
		
	}

	
	protected void damageSlots(int[] indexes, boolean ownerSide, int value) {
		for (int index : indexes) {
			if (index < 0 || index > 5) continue;
			
			//like Bargul, dont injure self
			if (ownerSide && index == slotIndex) continue;
					
			CardImage ci = ownerSide?teamCards[index]:enemyCards[index];
			if (ci == null) continue;
						
			ci.decrementLife(value, game);
			
			int remainingLife = ci.getCard().getLife();
			boolean died = (remainingLife < 1);
			
			if (died) {
				disposeEnemy(ci, index);
			}
		}
	}

	
	protected void damageOpposingSlot(int value) {
		int[] slots = new int[1];
		slots[0] = slotIndex;
		damageSlots(slots, false, value);
	}
	
	protected void damageNeighbors(boolean ownerSide, int value) {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		damageSlots(neighborSlots, ownerSide, value);
	}
	
	protected void damageAll(boolean ownerSide, int value) {
		int[] slots = new int[6];
		for (int i=0;i<6;i++) slots[i] = i;
		damageSlots(slots, ownerSide, value);
	}
	
	protected void enhanceAttackNeighboring(boolean ownerSide, int value) {
		int[] neighborSlots = new int[2];
		neighborSlots[0] = slotIndex - 1;
		neighborSlots[1] = slotIndex + 1;
		enhanceAttackSlots(neighborSlots, ownerSide, value);
	}
	
	protected void enhanceAttackAll(boolean ownerSide, int value) {
		int[] slots = new int[6];
		for (int i=0;i<6;i++) slots[i] = i;
		enhanceAttackSlots(slots, ownerSide, value);
	}
	
	protected void enhanceAttackSlots(int[] slots, boolean ownerSide, int value) {
		for (int index : slots) {
			if (index < 0 || index > 5 || index == slotIndex) continue;
			CardImage ci = ownerSide?teamCards[index]:enemyCards[index];
			if (ci == null) continue;
			ci.getCard().incrementAttack(value);
		}
	}
		
	protected final void moveCardActorOnBattle() {

		game.attackSound.play();
		
		final AtomicBoolean doneBattle = new AtomicBoolean(false);
		
		cardImage.addAction(sequence(moveBy(0, isComputer?-20:20, 0.5f), moveBy(0, isComputer?20:-20, 0.5f), new Action() {
			public boolean act(float delta) {
				doneBattle.set(true);
				return true;
			}
		}));
		
		//wait for battle action to end
		while(!doneBattle.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
				
	}
	
	protected final void moveCardActorOnMagic() {

		game.magicSound.play();
		
		final AtomicBoolean done = new AtomicBoolean(false);
		
		PlayerImage actor = isComputer?game.opponent:game.player;
    
		actor.addAction(sequence(moveBy(0, isComputer?-20:20, 0.5f), moveBy(0, isComputer?20:-20, 0.5f), new Action() {
			public boolean act(float delta) {
				done.set(true);
				return true;
			}
		}));
		
		//wait for spell action to end
		while(!done.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
				
	}

}
