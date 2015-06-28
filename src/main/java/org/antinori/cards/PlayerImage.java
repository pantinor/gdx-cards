package org.antinori.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerImage extends Actor {

    Sprite img;
    Texture frame;
    Player playerInfo;
    BitmapFont font;

    private SlotImage[] slots = new SlotImage[6];
    private CardImage[] slotCards = new CardImage[6];

    public boolean mustSkipNexAttack = false;
    private static Texture stunned;

    public PlayerImage(Sprite img, Texture frame, Player info) {
        this.img = img;
        this.frame = frame;
        this.playerInfo = info;
    }

    public PlayerImage(Sprite img, Texture frame, BitmapFont font, Player info, float x, float y) {
        this.img = img;
        this.frame = frame;
        this.playerInfo = info;
        this.font = font;

        setX(x);
        setY(y);
    }

    private static void initTextures() {
        stunned = new Texture(Gdx.files.classpath("images/stunned.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (stunned == null) {
            initTextures();
        }

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX();
        float y = getY();
        batch.draw(img, x, y);

        if (this.mustSkipNexAttack) {
            batch.draw(stunned, x + 10, y - 10);
        }

        batch.draw(frame, x - 6, y - 6);

    }

    public void decrementLife(int value, Cards game) {
        playerInfo.decrementLife(value);
        game.animateDamageText(value, this);
    }

    public void incrementLife(int value, Cards game) {
        playerInfo.incrementLife(value);
        game.animateHealingText(value, this);
    }

    public Sprite getImg() {
        return img;
    }

    public Texture getFrame() {
        return frame;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setImg(Sprite img) {
        this.img = img;
    }

    public void setFrame(Texture frame) {
        this.frame = frame;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public Player getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(Player playerInfo) {
        this.playerInfo = playerInfo;
    }

    public SlotImage[] getSlots() {
        return slots;
    }

    public void setSlots(SlotImage[] slots) {
        this.slots = slots;
    }

    public CardImage[] getSlotCards() {
        return slotCards;
    }

}
