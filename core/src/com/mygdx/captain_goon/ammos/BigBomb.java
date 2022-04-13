package com.mygdx.captain_goon.ammos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.captain_goon.explosions.EnemyHitExplosion;

public class BigBomb extends Ammo{
    private final static int HEIGHT = 69;
    private final static int WIDTH = 40;

    public BigBomb(float x, float y) {
        super(x, y - HEIGHT, WIDTH, HEIGHT);
        this.setImage(new Texture(Gdx.files.internal("sprites/ammo/aliendropping_small.png")));
        this.setDamage(15);
        this.setExplosion(new EnemyHitExplosion());
    }
}
