package org.antinori.cards;

import java.lang.reflect.Constructor;


public class SpellFactory {
	
	@SuppressWarnings("rawtypes")
	public static Spell getSpellClass(String className, Cards game, Card card, CardImage cardImage, boolean isComputer) {

		Spell spell = null;
		Constructor constructor = null;
		String packageName = "org.antinori.cards.spells.";
		
		try {
			constructor = Class.forName(packageName + className).getConstructor(Cards.class, Card.class, CardImage.class, Boolean.TYPE);
		} catch (Exception e) {
			try {
				constructor = Class.forName(packageName + "BaseSpell").getConstructor(Cards.class, Card.class, CardImage.class, Boolean.TYPE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			spell = (Spell) constructor.newInstance(game, card, cardImage, isComputer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return spell;

	}

}
