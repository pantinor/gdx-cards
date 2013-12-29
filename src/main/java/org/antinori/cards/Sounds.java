package org.antinori.cards;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sounds {
	
	public static Map<Sound, Music> sounds = new HashMap<Sound, Music>();
	
	public static void play(Sound sound) {
		Music m = sounds.get(sound);
		if (m == null) {
			m = Gdx.audio.newMusic(Gdx.files.classpath(sound.getFile()));
			m.setVolume(0.3f);
			m.setLooping(sound.getLooping());

			sounds.put(sound, m);
		}
		m.play();
	}

}
