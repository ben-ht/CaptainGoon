package com.mygdx.captain_goon.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.captain_goon.ammos.Ammo;
import com.mygdx.captain_goon.ammos.Bullet;
import com.mygdx.captain_goon.explosions.FriendlyShipDestroyedExplosion;

import java.util.Iterator;

public class FriendlySpaceShip extends SpaceShip{
    private final int WIDTH = 100;
    private final int HEIGHT = 136;

    public FriendlySpaceShip() {
        this.setHitBox(new Rectangle(Gdx.graphics.getWidth()/2 - WIDTH/2, 20, WIDTH, HEIGHT));
        this.setImage(new Texture(Gdx.files.internal("sprites/ships/blueships1_small.png")));
        setExplosion(new FriendlyShipDestroyedExplosion());
    }

    /**
     * @see SpaceShip#move()
     */
    public void move(){
        if (!isDestroyed()) {
            if (Gdx.input.getX() > this.getImage().getWidth() / 2 && Gdx.input.getX() < Gdx.graphics.getWidth() - WIDTH / 2)
                this.getHitBox().setX(Gdx.input.getX() - WIDTH / 2);
            if (Gdx.input.getY() > 600 && Gdx.input.getY() < Gdx.graphics.getHeight() - HEIGHT / 2)
                this.getHitBox().setY(Gdx.graphics.getHeight() - Gdx.input.getY() - HEIGHT / 2);
        }
    }

    /**
     * Creates a new ammunition and adds it to the ammunition array whenever the user clicks
     */
    public void createAmmo(){
        if (Gdx.input.justTouched()){
            Bullet bullet = new Bullet(this.getHitBox().getX() + WIDTH/2,  this.getHitBox().getY() + HEIGHT);
            this.getAmmunition().add(bullet);
        }
    }

    /**
     * @see SpaceShip#spawnAmmo()
     */
    public void spawnAmmo() {
        if (!isDestroyed()) {
            createAmmo();
        }
        Iterator<Ammo> iter = this.getAmmunition().iterator();
        while (iter.hasNext()) {
            Ammo ammo = iter.next();
            if (!ammo.isUsed()) {
                ammo.getHitBox().setY(ammo.getHitBox().getY() + 500 * Gdx.graphics.getDeltaTime());
            }
            if (ammo.getHitBox().getY() > Gdx.graphics.getHeight()) {
                iter.remove();
            }
        }
    }
    
}
