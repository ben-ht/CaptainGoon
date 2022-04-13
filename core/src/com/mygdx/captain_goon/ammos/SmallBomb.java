package com.mygdx.captain_goon.ammos;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.captain_goon.explosions.EnemyHitExplosion;

public class SmallBomb extends Ammo{
    private final static int WIDTH = 15;
    private final static int HEIGHT = 32;

    public SmallBomb(float x, float y) {
        super(x, y - HEIGHT, WIDTH, HEIGHT);
        this.setImage(new Texture("sprites/ammo/wship-4.png"));
        this.setDamage(10);
        this.setExplosion(new EnemyHitExplosion());
    }
}
