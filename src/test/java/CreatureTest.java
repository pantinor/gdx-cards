import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.antinori.cards.Card;
import org.antinori.cards.CardSetup;
import org.apache.commons.io.FileUtils;

public class CreatureTest {

	public static void main(String[] args) {

		CardSetup cs = new CardSetup();
		cs.parseCards();
		
		Set<Card> creatures = cs.getCreatureCards();

		Set<Card> spells = cs.getSpellCards();
		
		String template1 = 
				"package org.antinori.cards.characters;\n\n"+
				"import org.antinori.cards.Card;\n"+
				"import org.antinori.cards.CardImage;\n"+
				"import org.antinori.cards.Cards;\n\n"+
				"public class %s extends BaseCreature {\n"+
				"public %s(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {\n"+
				"super(game, card, cardImage, isComputer, slotIndex);\n}\n"+
				"public void onSummoned() {\nsuper.onSummoned();\n}\n"+
				"public void onAttack() {\nsuper.onAttack();\n}\n}\n";


		String template2 = 
				"package org.antinori.cards.spells;\n\n"+
				"import org.antinori.cards.Card;\n"+
				"import org.antinori.cards.CardImage;\n"+
				"import org.antinori.cards.Cards;\n\n"+
				"public class %s extends BaseSpell {\n"+
				"public %s(Cards game, Card card, CardImage cardImage, boolean isComputer) {\n"+
				"super(game, card, cardImage, isComputer);\n}\n"+
				"public void onCast() {\nsuper.onCast();\n}\n}\n";
		
		for (Card c : creatures) {
			String clazz = String.format(template1, c.getName(), c.getName());
			try {
				FileUtils.writeStringToFile(new File("d:/temp/cr/"+c.getName() + ".java"), clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (Card c : spells) {
			String clazz = String.format(template2, c.getName(), c.getName());
			try {
				FileUtils.writeStringToFile(new File("d:/temp/sp/"+c.getName() + ".java"), clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
