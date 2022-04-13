package com.mygdx.captain_goon.ammos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.captain_goon.explosions.HeroHitExplosion;

public class Bullet extends Ammo{
    private final static int WIDTH = 10;
    private final static int HEIGHT = 25;

    public Bullet(float x, float y){
        super(x - WIDTH/2, y-HEIGHT, WIDTH, HEIGHT);
        this.setImage(new Texture(Gdx.files.internal("sprites/ammo/bullets/glowtube_small.png")));
        this.setDamage(10);
        setExplosion(new HeroHitExplosion());
    }
}
 