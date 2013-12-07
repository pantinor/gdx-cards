package org.antinori.cards;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SingleDuelChooser {
	
	Stage stage;
	
	TextureRegion background;
	Image bgimg;
	
	TextureRegion chooserBg;
	Image cbgimg;
	
	PlayerImage pi;
	PlayerImage oi;
	
	Cards game;
	AtomicBoolean done = new AtomicBoolean(false);
	
	final AtomicInteger playerIndex = new AtomicInteger(1);
	final AtomicInteger opponentIndex = new AtomicInteger(1);
	
	static int buttonWidth = 13;
	static int buttonHeight = 120;
	static int imgWidth = 120;

	SelectBox classesPlayer;
	SelectBox classesOpponent;

	public void init (Cards game) {
		this.game = game;
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

		background = new TextureRegion(new Texture(Gdx.files.classpath("images/dragonFire.jpg")));
		bgimg = new Image(background);
		stage.addActor(bgimg);

		chooserBg = new TextureRegion(new Texture(Gdx.files.classpath("images/singleduel1.png")));
		cbgimg = new Image(chooserBg);

		Sprite spP = Cards.faceCardAtlas.createSprite("face10");
		spP.flip(false, true);
		pi = new PlayerImage(spP, Cards.portraitramka, new Player());
		
		Sprite spO = Cards.faceCardAtlas.createSprite("face1");
		spO.flip(false, true);
		oi = new PlayerImage(spO, Cards.portraitramka, new Player());
		
		TextButton play = new TextButton("Play", game.skin);
		play.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				pi.getPlayerInfo().setPlayerClass(Specializations.fromString(classesPlayer.getSelection()));
				oi.getPlayerInfo().setPlayerClass(Specializations.fromString(classesOpponent.getSelection()));
				
				//pi.getImg().flip(false, true);
				//oi.getImg().flip(false, true);
				
				stage.clear();
				stage.dispose();
				
				done.set(true);
				
				return true;
			}
		});
		
		
		classesPlayer = new SelectBox(Specializations.names(), game.skin);
		classesOpponent = new SelectBox(Specializations.names(), game.skin);

		int x = 300;
		int y = 253;

		cbgimg.setPosition(x, 0);
		
		Button lpb = createButton(x+=34, y, pi, playerIndex, true);
		pi.setPosition(x+=(buttonWidth+7), y);
		Button rpb = createButton(x+=imgWidth+10, y, pi, playerIndex, false);
		
		Button lob = createButton(x+=43, y, oi, opponentIndex, true);
		oi.setPosition(x+=(buttonWidth+7), y);
		Button rob = createButton(x+=imgWidth+10, y, oi, opponentIndex, false);
		
		Label lbl = new Label("Single Duel", game.skin);
		lbl.setPosition(465, 430);
		
		play.setBounds(430, 133, 150, 25);

				
		stage.addActor(cbgimg);
		
		stage.addActor(lbl);
		
		stage.addActor(lpb);
		stage.addActor(pi);
		stage.addActor(rpb);

		stage.addActor(lob);
		stage.addActor(oi);
		stage.addActor(rob);
		
		stage.addActor(classesPlayer);
		classesPlayer.setPosition(355, 194);
		classesPlayer.setHeight(25);
		classesPlayer.setWidth(123);

		stage.addActor(classesOpponent);
		classesOpponent.setPosition(548, 194);
		classesOpponent.setHeight(25);
		classesOpponent.setWidth(123);

		stage.addActor(play);

				
	}
	
	private Button createButton(int x, int y, PlayerImage img, AtomicInteger index, boolean left) {
		ButtonListener bl = new ButtonListener(img, index, left);

		Button btn = new Button(game.skin);
		btn.addListener(bl);
		
		btn.setBounds(x, y, buttonWidth, buttonHeight);
		
		return btn;	
	}
	
	class ButtonListener extends InputListener {
		PlayerImage pi;
		AtomicInteger index;
		boolean decreasing = false;
		ButtonListener(PlayerImage pi, AtomicInteger index, boolean decreasing) {
			this.pi = pi;
			this.index = index;
			this.decreasing = decreasing;
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			if (decreasing) {
				index.decrementAndGet();
				if (index.get() < 1) index.set(1);
			} else {
				index.incrementAndGet();
				if (index.get() > 77) index.set(77);
			}
			Sprite sp = Cards.faceCardAtlas.createSprite("face"+index);
			sp.flip(false, true);
			pi.setImg(sp);
			pi.getPlayerInfo().setImgName("face"+index);
			return true;
		}
	}
	
	public void draw(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}


}
