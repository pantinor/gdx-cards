package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BaseFunctions {
	
	protected Card card;
	protected CardImage cardImage;
	protected Cards game;
	protected int slotIndex = -1;
	
	protected Player opposingPlayer ;
	protected Player ownerPlayer ;
	
	protected PlayerImage owner ;
	protected PlayerImage opponent ;
	
	public boolean isSpell = false;
	public boolean mustSkipNexAttack = false;

	
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
				if (opposingCards[index].getCard().getName().equalsIgnoreCase("iceguard")) {
					value = value/2;
				}
			}
			
			if (ownedCards[index] != null) {
				if (ownedCards[index].getCard().getName().equalsIgnoreCase("chastiser")) {
					ownedCards[index].getCard().incrementAttack(2);
				}
				if (ownedCards[index].getCard().getName().equalsIgnoreCase("whiteelephant")) {
					damageSlot(ownedCards[index], index, pi, value);
					return;
				}
			}

		}
		
		if (card.getName().equalsIgnoreCase("GoblinSaboteur")) {
			removeRandomCheapestCard(opposingPlayer);
		}
		
		pi.decrementLife(value, game);
		
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
		disposeCardImage(player, slotIndex, false);
	}
	
	public final void disposeCardImage(PlayerImage player, int slotIndex, boolean destroyed) throws GameOverException {
		
		CardImage ci = player.getSlotCards()[slotIndex];
		
		ci.addAction(sequence(fadeOut(2),removeActor(ci)));
		
		player.getSlots()[slotIndex].setOccupied(false);
		player.getSlotCards()[slotIndex] = null;
		
		if (!destroyed) ci.getCreature().onDying();
		
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
		boolean died = ci.decrementLife(this, attack, game);
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
		
		ci1.setFont(Cards.customFont);
		ci1.setFrame(Cards.ramka);
		
		ci1.addListener(game.new TargetedCardListener(owner.getPlayerInfo().getId(), index));
		ci1.addListener(game.sdl);
		
		slot.setOccupied(true);
		owner.getSlotCards()[index] = ci1;
		ci1.setBounds(slot.getX() + 5, slot.getY() + 26, ci1.getFrame().getWidth(), ci1.getFrame().getHeight());
		
		Sounds.play(Sound.SUMMON_DROP);
		
		game.stage.addActor(ci1);
	}
	
	protected void removeRandomCheapestCard(Player player) {
		List<CardImage> cards = null;
		do {
			Dice dice = new Dice(1,5);
			int roll = dice.roll();
			CardType type = Player.TYPES[roll - 1];
			cards = player.getCards(type);
		} while(cards == null || cards.size() < 1) ;
		
		CardImage ci = cards.remove(0);
		ci.remove();
		Sounds.play(Sound.NEGATIVE_EFFECT);
	}
		
	protected void swapCard(String newCardName, CardType type, String oldCardName, PlayerImage pi) {

		CardImage newCard = null;
		try {
			newCard = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, newCardName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		newCard.setFont(Cards.customFont);
		newCard.setFrame(newCard.getCard().isSpell()?Cards.spellramka:Cards.ramka);
		newCard.addListener(game.sdl);
		newCard.addListener(game.li);

		List<CardImage> cards = ownerPlayer.getCards(type);
		CardImage oldCard = null;
		for (CardImage ci : cards) {
			if (ci.getCard().getName().equalsIgnoreCase(oldCardName)) {
				oldCard = ci;
			}
		}
		
		newCard.setBounds(oldCard.getX(), oldCard.getY(), oldCard.getWidth(), oldCard.getHeight());
		
		cards.remove(oldCard);
		oldCard.remove();
		
		cards.add(newCard);
		if (pi.getSlots()[0].isBottomSlots()) {
			game.stage.addActor(newCard);
		}

		//CardImage.sort(cards);
		
	}
	
	protected void scaleImage(CardImage ci) {
		Image img = new Image(new TextureRegion(ci.getImg()));
		img.setBounds(ci.getX(), ci.getY(), ci.getWidth(), ci.getHeight());
		game.stage.addActor(img);
		img.addAction(sequence(scaleTo(1.05f, 1.05f, 0.30f), scaleTo(1.0f, 1.0f, 0.30f), removeActor(img)));
	}
	
	protected void tryMoveToAnotherRandomOpenSlot(PlayerImage player, CardImage ci, int currentSlot) {
		
		List<Integer> emptySlots = new ArrayList<Integer>();
		
		for (int index=0;index<6;index++) {
			SlotImage si = player.getSlots()[index];
			if (!si.isOccupied()) emptySlots.add(index);
		}
		
		if (emptySlots.size() == 0) return;
		
		int targetSlot = 0;
		if (emptySlots.size() == 1) {
			targetSlot = emptySlots.get(0);
		} else {
			Dice dice = new Dice(1, emptySlots.size());
			targetSlot = emptySlots.get(dice.roll() - 1);
		}

		moveCardToAnotherSlot(player, ci, currentSlot, targetSlot);
	}
	
	
	protected void moveCardToAnotherSlot(PlayerImage player, CardImage ci, int srcIndex, int destIndex) {
		
		CardImage[] cards = player.getSlotCards();
		cards[srcIndex] = null;
		cards[destIndex] = ci;
		
		SlotImage[] slots = player.getSlots();
		slots[srcIndex].setOccupied(false);
		slots[destIndex].setOccupied(true);
		
		ci.getCreature().setIndex(destIndex);
		ci.toFront();
		
		Sounds.play(Sound.SUMMONED);
		
		final AtomicBoolean done = new AtomicBoolean(false);
		
		ci.addAction(sequence(moveTo(slots[destIndex].getX() + 5, slots[destIndex].getY() + 26, 1.0f), new Action() {
			public boolean act(float delta) {
				done.set(true);
				return true;
			}
		}));
		
		//wait for action to end
		while(!done.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}
		

}
