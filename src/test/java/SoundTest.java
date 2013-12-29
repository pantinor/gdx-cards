import java.net.URL;

import org.antinori.cards.Card;
import org.antinori.cards.CardSetup;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.Spell;
import org.antinori.cards.SpellFactory;


public class SoundTest {

	public static void main(String[] args) {
		
		CardSetup cs = new CardSetup();
		cs.parseCards();
		
		ClassLoader cldr = CardSetup.class.getClassLoader();
		
		for (Card card : cs.getSpellCards()) {
			String name = card.getName();
			Spell spell = SpellFactory.getSpellClass(name, null, null, null, new PlayerImage(null, null, new Player()), new PlayerImage(null, null, new Player()));	
			//Sounds.play(spell);
			
			String file = "audio/" + spell.getClass().getSimpleName() + ".ogg";

			URL url = cldr.getResource(file);
			
			System.out.println(file + "\t\t\t\t" + url);
		}
		


	}

}
