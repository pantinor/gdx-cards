package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.concurrent.atomic.AtomicBoolean;


import com.badlogic.gdx.scenes.scene2d.Action;


public class BattleRoundThread implements Runnable {
	
	Cards game;
	PlayerImage player;
	PlayerImage opponent;
	
	CardImage summonedCardImage;
	int summonedSlot;
	
	AtomicBoolean opptSummoningFinished = new AtomicBoolean(false);

	CardImage spellCardImage;
	CardImage targetedCardImage;
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent, CardImage spellCardImage) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
		this.spellCardImage = spellCardImage;
	}
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent, CardImage spellCardImage, CardImage targetedCardImage) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
		this.spellCardImage = spellCardImage;
		this.targetedCardImage = targetedCardImage;
	}
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent, CardImage summons, int summonedSlot) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
		this.summonedCardImage = summons;
		this.summonedSlot = summonedSlot;
	}
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
	}
	
	public void run() {
		
		try {			
			game.startTurn();
			
			if (game == null || player == null || opponent == null) {
				throw new Exception("Null parameter, cannot take a round.");
			}

			Player pi = player.getPlayerInfo();
			Player oi = opponent.getPlayerInfo();
			
			CardImage[] bottomCards = game.getBottomSlotCards();
			CardImage[] topCards = game.getTopSlotCards();
			
			
			if (summonedCardImage != null) {
				
				summonedCardImage.getCreature().onSummoned();
				
			} else if (spellCardImage != null) {
				
				Spell spell = SpellFactory.getSpellClass(spellCardImage.getCard().getName(), game, spellCardImage.getCard(), spellCardImage, false);	
				spell.setTargeted(targetedCardImage);
				spell.onCast();
				
			}
						
			//TODO add summoning description to log
			
			int i = -1;
			for (CardImage attacker : bottomCards) {
				i++;
				if (summonedCardImage != null && i == summonedSlot) continue;
				if (attacker == null) continue;
				
				attacker.getCreature().onAttack();
			}
			
			
			//start computer turn
			startOfTurnCheck(true, opponent);

		
			CardImage opptSummons = null;

			SlotImage si = getOpenTopSlot();

			if (si != null) {
				
				CardImage opptPick = null;
				do {
					opptPick = oi.pickBestEnabledCard();
				} while (opptPick == null);
				
				if (!opptPick.getCard().isSpell()) {
			
					//summon the opponents creature card to an open slot
					opptSummons = opptPick.clone();
					
					game.stage.addActor(opptSummons);
					opptSummons.addListener(game.li);
					
					CardImage[] imgs = game.getTopSlotCards();
					imgs[si.getIndex()] = opptSummons;
					
					SlotImage[] slots = game.getTopSlots();
					slots[si.getIndex()].setOccupied(true);
					
					Creature summonedCreature = CreatureFactory.getCreatureClass(opptSummons.getCard().getName(), game, opptSummons.getCard(), opptSummons, true, si.getIndex());
					opptSummons.setCreature(summonedCreature);
					
					opptSummons.addAction(sequence(moveTo(si.getX() + 5, si.getY() + 26, 1.0f), new Action() {
						public boolean act(float delta) {
							opptSummoningFinished.set(true);
							return true;
						}
					}));
					
					//wait for summoning action to end
					while(!opptSummoningFinished.get()) {
						Thread.sleep(50);
					}
					
					summonedCreature.onSummoned();
					
					//TODO add summoning description to log
					
				} else {
					
					//cast a spell towards the player			
					Spell opptSpell = SpellFactory.getSpellClass(opptPick.getCard().getName(), game, opptPick.getCard(), opptPick, true);					
					opptSpell.onCast();

				}
					
			} else {
				
				System.out.println("No open top slots available for opponent to summon creature, casting a spell instead.");
				//TODO
				//cast a spell towards the player			
				//Spell opptSpell = SpellFactory.getSpellClass(opptPick.getCard().getName(), game, opptPick.getCard(), opptPick, true);					
				//opptSpell.onCast();
				
			}
						
			//go through all other opponent slots and do battle
			int j = -1;
			for (CardImage attacker : topCards) {
				j++;
				if (opptSummons != null  && j == si.getIndex()) continue;
				if (attacker == null) continue;
				
				attacker.getCreature().onAttack();
			}
						
			
			//increment strength by one
			pi.incrementStrengthAll(1);
			oi.incrementStrengthAll(1);
			
			CardType[] types = {CardType.FIRE, CardType.AIR, CardType.WATER, CardType.EARTH, CardType.OTHER};
			for (CardType type : types) {
				pi.enableDisableCards(type);
				oi.enableDisableCards(type);
			}
			
			//start of turn for player
			startOfTurnCheck(false, player);
			
		} catch (GameOverException e) {
			game.handleGameOver();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			game.finishTurn();
		}
		
		System.out.println("TurnThread done");

		
	}
	
	
	private void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		
		CardImage[] cards = isComputer?game.getTopSlotCards():game.getBottomSlotCards();

		
		for (int index = 0;index<6;index++) {
			CardImage ci = cards[index];
			if (ci == null) continue;
			
			if (ci.getCard().getName().equalsIgnoreCase("masterhealer")) {
				player.incrementLife(3, game);
				for (int j = 0;j<6;j++) {
					CardImage ci2 = cards[j];
					if (ci2 == null) continue;
					ci2.incrementLife(3, game);
				}
			}
			
		}
		
	}
		
	public SlotImage getOpenTopSlot() {
		SlotImage si = null;
		SlotImage[] slots = game.getTopSlots();
		
		boolean hasOpenSlot = false;
		for (SlotImage slot : slots) {
			if (!slot.isOccupied()) {
				hasOpenSlot = true;
				break;
			}
		}
		
		if (!hasOpenSlot) 
			return null;
		
		Dice dice = new Dice(1,6);
		do {
			int roll = dice.roll();
			si = slots[roll - 1];
			if (si.isOccupied()) {
				si = null;
			}
		} while (si == null);
		
		return si;
	}
}
