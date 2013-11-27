package org.antinori.cards;

public enum Specializations {
	
	Cleric ("Cleric", CardType.HOLY),
	Mechanician ("Mechanician", CardType.MECHANICAL), 
	Necromancer ("Necromancer", CardType.DEATH), 
	Chaosmaster ("Chaosmaster", CardType.CHAOS), 
	Dominator ("Dominator", CardType.CONTROL),
	Illusionist ("Illusionist", CardType.ILLUSION),
	Demonologist ("Demonologist", CardType.DEMONIC), 
	Sorcerer ("Sorcerer", CardType.SORCERY), 
	Beastmaster ("Beastmaster", CardType.BEAST), 
	Goblin_Chieftan ("Goblin Chieftan", CardType.GOBLINS), 
	Mad_Hermit ("Mad Hermit", CardType.FOREST), 
	Chronomancer ("Chronomancer", CardType.TIME), 
	Warror_Priest ("Warror Priest", CardType.SPIRIT),
	Vampire_Lord ("Vampire Lord", CardType.VAMPIRIC),
	Cultist	("Cultist", CardType.CULT),
	Golem_Master ("Golem Master", CardType.GOLEM),
	Random ("Random", CardType.HOLY);

	String title;
	CardType type;
	
	private Specializations(String name, CardType type) {
		this.title = name;
		this.type = type;
	}
	
	public static String[] names() {
		Specializations[] states = values();
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].title;
		}

		return names;
	}
	
	public static Specializations fromString(String title) {
		if (title != null) {
			for (Specializations b : Specializations.values()) {
				if (title.equalsIgnoreCase(b.getTitle())) {
					return b;
				}
			}
		}
		return null;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public CardType getType() {
		return this.type;
	}


}
