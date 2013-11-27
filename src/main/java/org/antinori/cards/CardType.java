package org.antinori.cards;

public enum CardType {

	FIRE ("Fire"), 
	WATER("Water"), 
	AIR("Air"), 
	EARTH("Earth"), 
	DEATH("Death"), 
	HOLY("Holy"), 
	MECHANICAL("Mechanical"), 
	ILLUSION("Illusion"), 
	CONTROL("Control"), 
	CHAOS("Chaos"), 
	DEMONIC("Demonic"), 
	SORCERY("Sorcery"), 
	BEAST("Beast"), 
	BEASTS_ABILITIES("Beasts Abilities"), 
	GOBLINS("Goblins"), 
	FOREST("Forest"), 
	TIME("Time"), 
	SPIRIT("Spirit"), 
	VAMPIRIC("Blood"), 
	CULT("Cult"), 
	GOLEM("Golem"), 
	OTHER("Other");
	
	String title;
	
	private CardType(String name) {
		this.title = name;
	}
	
	public String getTitle() {
		return this.title;
	}

	public static CardType fromString(String text) {
		if (text != null) {
			for (CardType c : CardType.values()) {
				if (c.toString().equalsIgnoreCase(text)) {
					return c;
				}
			}
		}
		return null;
	}
	
}
