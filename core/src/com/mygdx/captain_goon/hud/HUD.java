package com.mygdx.captain_goon.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.captain_goon.spaceships.EnemySpaceShip;
import com.mygdx.captain_goon.spaceships.FriendlySpaceShip;

public class HUD {
    private BitmapFont font;
    private final int Y = 50;
    public static final int IMAGESIZE = 50;

    private int friendlyLifeX;;
    private int friendlyImageX;

    private int enemyLifeX;
    private int enemyImageX;

    public HUD(){
        font =  new BitmapFont(Gdx.files.internal("fonts/font-export.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.5f);

        this.friendlyLifeX = 100;
        this.friendlyImageX = 20;

        this.enemyLifeX = Gdx.graphics.getWidth()-165;
        this.enemyImageX = Gdx.graphics.getWidth()-60;
    }

    /**
     * Displays two ships and their lives in the bottom
     */
    public void draw(Batch batch, FriendlySpaceShip friendlySpaceShip, EnemySpaceShip enemySpaceShip){
        font.draw(batch, friendlySpaceShip.getLife() + " %", this.getFriendlyLifeX(), this.getY());
        font.draw(batch, enemySpaceShip.getLife() + " %", this.getEnemyLifeX(), this.getY());
        batch.draw(friendlySpaceShip.getImage(), this.getFriendlyImageX(), this.getY()-HUD.IMAGESIZE/2, HUD.IMAGESIZE, HUD.IMAGESIZE);
        batch.draw(enemySpaceShip.getImage(), this.getEnemyImageX(), this.getY()-HUD.IMAGESIZE/2, HUD.IMAGESIZE, HUD.IMAGESIZE);
    }

    public void displayLoseMessage(Batch batch){
        GlyphLayout layout = new GlyphLayout(font,"YOU LOSE !");
        font.draw(batch,layout, (Gdx.graphics.getWidth()-layout.width)/2, (Gdx.graphics.getHeight()-layout.height)/2);
    }

    public void displayWinMessage(Batch batch){
        GlyphLayout layout = new GlyphLayout(font,"YOU SAVED THE WORLD !");
        font.draw(batch,layout, (Gdx.graphics.getWidth()-layout.width)/2, (Gdx.graphics.getHeight()-layout.height)/2);
    }




    public int getY() {
        return Y;
    }

    public int getFriendlyLifeX() {
        return friendlyLifeX;
    }

    public int getFriendlyImageX() {
        return friendlyImageX;
    }

    public int getEnemyLifeX() {
        return enemyLifeX;
    }

    public int getEnemyImageX() {
        return enemyImageX;
    }
}
