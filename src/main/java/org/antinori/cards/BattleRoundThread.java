package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.concurrent.atomic.AtomicBoolean;

import org.antinori.cards.network.Event;
import org.antinori.cards.network.NetworkEvent;
import org.antinori.cards.network.NetworkGame;

import com.badlogic.gdx.scenes.scene2d.Action;


public class BattleRoundThread extends Thread {
	
	Cards game;
	PlayerImage player;
	PlayerImage opponent;
	
	CardImage summonedCardImage;
	int summonedSlot;
	
	AtomicBoolean opptSummoningFinished = new AtomicBoolean(false);

	CardImage spellCardImage;
	CardImage targetedCardImage;
	int targetedSlot;;
	String targetedCardOwnerId;
	
	NetworkGame ng = Cards.NET_GAME;
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent, CardImage spellCardImage) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
		this.spellCardImage = spellCardImage;
	}
	
	public BattleRoundThread(Cards game, PlayerImage player, PlayerImage opponent, CardImage spellCardImage, CardImage targetedCardImage, String targetedCardOwnerId, int index) {
		this.game = game;
		this.player = player;
		this.opponent = opponent;
		this.spellCardImage = spellCardImage;
		this.targetedCardImage = targetedCardImage;
		this.targetedCardOwnerId = targetedCardOwnerId;
		this.targetedSlot = index;
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
			
			Cards.logScrollPane.add("________________________");
			
			//start of turn for player
			startOfTurnCheck(player);

			Player pi = player.getPlayerInfo();
			Player oi = opponent.getPlayerInfo();
			
			if (summonedCardImage != null) {
				
				if (Cards.NET_GAME != null) {
					NetworkEvent ne = new NetworkEvent(Event.CARD_ADDED, summonedSlot, summonedCardImage.getCard().getName(), player.getPlayerInfo().getId());
					Cards.NET_GAME.sendEvent(ne);
				}
				
				summonedCardImage.getCreature().onSummoned();
				
			} else if (spellCardImage != null) {
				
				if (Cards.NET_GAME != null) {
					NetworkEvent ne = new NetworkEvent(Event.SPELL_CAST, player.getPlayerInfo().getId());
					ne.setSpellName(spellCardImage.getCard().getName());
					if (targetedCardImage != null) {
						ne.setSpellTargetCardName(targetedCardImage.getCard().getName());
						ne.setSlot(this.targetedSlot);
						ne.setTargetedCardOwnerId(this.targetedCardOwnerId);
					}
					ne.setCaster(player.getPlayerInfo().getId());
					Cards.NET_GAME.sendEvent(ne);
				}
				
				Spell spell = SpellFactory.getSpellClass(spellCardImage.getCard().getName(), game, spellCardImage.getCard(), spellCardImage, player, opponent);	
				spell.setTargeted(targetedCardImage);
				spell.onCast();
								
			}
			
						
			
			int i = -1;
			for (CardImage attacker : player.getSlotCards()) {
				
				i++;
				if (summonedCardImage != null && i == summonedSlot) continue;
				if (summonedCardImage != null && attacker != null &&
						summonedCardImage.getCard().getName().equalsIgnoreCase("giantspider") && 
						attacker.getCard().getName().equalsIgnoreCase("forestspider")) continue;
				if (attacker == null) continue;
				
				if (Cards.NET_GAME != null) {
					NetworkEvent ne = new NetworkEvent(Event.CARD_ATTACKED, player.getPlayerInfo().getId());
					ne.setSlot(i);
					Cards.NET_GAME.sendEvent(ne);
				}
				
				attacker.getCreature().onAttack(); 
				
			}
			
			if (ng != null) {
				
				pi.incrementStrengthAll(1, true);
				
				ng.sendYourTurnSignal();
				
				//wait until the far end has sent over all cards and player info
				ng.read();
				
				
			} else {
				
				//start computer turn
				startOfTurnCheck(opponent);
				
				
				//do single player duel computer turn
				CardImage opptSummons = null;
	
				SlotImage si = getOpponentSlot();
	
				if (si != null) {
					
					CardImage opptPick = null;
					do {
						opptPick = oi.pickBestEnabledCard();
					} while (opptPick == null);
					
					if (!opptPick.getCard().isSpell()) {
										
						//summon the opponents creature card to an open slot
						opptSummons = opptPick.clone();
						
						game.stage.addActor(opptSummons);
						opptSummons.addListener(game.sdl);
						opptSummons.addListener(game.new TargetedCardListener(opponent.getPlayerInfo().getId(), si.getIndex()));
	
						
						CardImage[] imgs = opponent.getSlotCards();
						imgs[si.getIndex()] = opptSummons;
						
						SlotImage[] slots = opponent.getSlots();
						slots[si.getIndex()].setOccupied(true);
						
						Creature summonedCreature = CreatureFactory.getCreatureClass(opptSummons.getCard().getName(), game, opptSummons.getCard(), opptSummons, si.getIndex(), opponent, player);
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
						
					} else {
						
						//cast a spell towards the player			
						Spell opptSpell = SpellFactory.getSpellClass(opptPick.getCard().getName(), game, opptPick.getCard(), opptPick, opponent, player);					
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
				for (CardImage attacker : opponent.getSlotCards()) {
					j++;
					if (opptSummons != null  && j == si.getIndex()) continue;
					if (opptSummons != null  && attacker != null &&
							opptSummons.getCard().getName().equalsIgnoreCase("giantspider") && 
							attacker.getCard().getName().equalsIgnoreCase("forestspider")) continue;
					if (attacker == null) continue;
					
					attacker.getCreature().onAttack();
				}
				
				oi.incrementStrengthAll(1, false);
				pi.incrementStrengthAll(1, false);
							
			}
						
			
			for (CardType type : Player.TYPES) {
				pi.enableDisableCards(type);
				oi.enableDisableCards(type);
			}
			

			
		} catch (GameOverException e) {
			game.handleGameOver();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			game.finishTurn();
		}
		
		
	}
	
	private void startOfTurnCheck(PlayerImage player) {
		CardImage[] cards = player.getSlotCards();
		for (int index = 0; index<6; index++) {
			CardImage ci = cards[index];
			if (ci == null) continue;
			ci.getCreature().startOfTurnCheck();
		}
	}
		
	public SlotImage getOpponentSlot() {
		
		SlotImage si = null;
		SlotImage[] slots = opponent.getSlots();
		
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
