package com.mygdx.captain_goon.ammos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.captain_goon.explosions.Explosion;


public class Ammo {
    private Rectangle hitBox;
    private Texture image;

    private int damage;
    private Explosion explosion;
    private boolean used;


    public Ammo(float x, float y, int width, int height){
        this.hitBox = new Rectangle(x, y, width, height);
        used = false;
    }

    public Texture getImage() {
        return image;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        this.used = true;
    }

}
