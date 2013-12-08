package org.antinori.cards;

import java.lang.reflect.Constructor;


public class CreatureFactory {
	
	@SuppressWarnings("rawtypes")
	public static Creature getCreatureClass(String className, Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {

		Creature creature = null;
		Constructor constructor = null;
		String packageName = "org.antinori.cards.characters.";
		
		try {
			constructor = Class.forName(packageName + className).getConstructor(Cards.class, Card.class, CardImage.class, Integer.TYPE, PlayerImage.class, PlayerImage.class);
		} catch (Exception e) {
			try {
				constructor = Class.forName(packageName + "BaseCreature").getConstructor(Cards.class, Card.class, CardImage.class, Integer.TYPE, PlayerImage.class, PlayerImage.class);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			creature = (Creature) constructor.newInstance(game, card, cardImage, slotIndex, owner, opponent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return creature;

	}

}
