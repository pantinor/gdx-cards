package org.antinori.cards.network;

import java.io.Serializable;

import org.antinori.cards.CardType;
import org.antinori.cards.Player;

public class NetworkEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private Event event;
	private int slot;
	
	private int healthAffectedAmount;
	private int attackAffectedAmount;
	private String spellName;
	private String cardName;
	private boolean damageViaSpell;

	private String id;
	private String playerIcon;
	private String playerClass;
	private CardType typeStrengthAffected;
	private int strengthAffected;
	private Player player;
	
	public NetworkEvent(Event event, String id) {
		this.event = event;
		this.id = id;
	}
	
	public NetworkEvent(Event event, int slot, String cardName, String id) {
		this.event = event;
		this.slot = slot;
		this.cardName = cardName;
		this.id = id;
	}
	
	public NetworkEvent(Event event, int slot, String cardName, String id, int attackAffected, int healthAffected) {
		this.event = event;
		this.slot = slot;
		this.cardName = cardName;
		this.id = id;
		this.attackAffectedAmount = attackAffected;
		this.healthAffectedAmount = healthAffected;
	}
	
	public NetworkEvent(Event event, String id, CardType typeStrengthAffected, int strengthAffected) {
		this.event = event;
		this.id = id;
		this.typeStrengthAffected = typeStrengthAffected;
		this.strengthAffected = strengthAffected;
	}
	
	public NetworkEvent(Event event, Player player) {
		this.event = event;
		this.player = player;
		this.id = player.getId();
		this.playerIcon = player.getImgName();
		this.playerClass = player.getPlayerClass().getTitle();
	}
	
	public Event getEvent() {
		return event;
	}
	public int getSlot() {
		return slot;
	}
	public int getHealthAffectedAmount() {
		return healthAffectedAmount;
	}
	public int getAttackAffectedAmount() {
		return attackAffectedAmount;
	}
	public String getSpellName() {
		return spellName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public void setHealthAffectedAmount(int healthAffectedAmount) {
		this.healthAffectedAmount = healthAffectedAmount;
	}
	public void setAttackAffectedAmount(int attackAffectedAmount) {
		this.attackAffectedAmount = attackAffectedAmount;
	}
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public boolean isDamageViaSpell() {
		return damageViaSpell;
	}
	public void setDamageViaSpell(boolean damageViaSpell) {
		this.damageViaSpell = damageViaSpell;
	}
	public CardType getTypeStrengthAffected() {
		return typeStrengthAffected;
	}
	public void setTypeStrengthAffected(CardType typeStrengthAffected) {
		this.typeStrengthAffected = typeStrengthAffected;
	}
	public int getStrengthAffected() {
		return strengthAffected;
	}
	public void setStrengthAffected(int strengthAffected) {
		this.strengthAffected = strengthAffected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(String playerIcon) {
		this.playerIcon = playerIcon;
	}

	public String getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(String playerClass) {
		this.playerClass = playerClass;
	}

	@Override
	public String toString() {
		return String.format("NetworkEvent [event=%s, slot=%s, healthAffectedAmount=%s, attackAffectedAmount=%s, spellName=%s, cardName=%s, damageViaSpell=%s, id=%s, playerIcon=%s, playerClass=%s, typeStrengthAffected=%s, strengthAffected=%s]",
				event, slot, healthAffectedAmount, attackAffectedAmount, spellName, cardName, damageViaSpell, id, playerIcon, playerClass, typeStrengthAffected, strengthAffected);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	

}
