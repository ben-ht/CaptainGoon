package com.mygdx.captain_goon.explosions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private final static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas"));
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private int size;
    private Color color;

    public Explosion(){
        animation = new Animation<TextureRegion>(1f / 30f, getAtlas().getRegions());
        this.setElapsedTime();
    }

    /**
     * Draws the explosion on the batch and update its stateTime
     */
    public void playExplosion(Batch batch, float x, float y){
        batch.setColor(this.getColor());
        batch.draw(this.getAnimation().getKeyFrame(this.getElapsedTime()), x - this.getSize()/2, y- this.getSize()/2, size, size);
        batch.setColor(Color.WHITE);
        this.setElapsedTime();
    }

    public boolean isComplete(){
        return getAnimation().isAnimationFinished(elapsedTime);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setElapsedTime(){
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }


    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

