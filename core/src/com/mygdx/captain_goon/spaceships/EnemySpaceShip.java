package com.mygdx.captain_goon.spaceships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.captain_goon.ammos.Ammo;
import com.mygdx.captain_goon.ammos.BigBomb;
import com.mygdx.captain_goon.explosions.EnemyShipDestroyedExplosion;

public class EnemySpaceShip extends SpaceShip{
    private final static int SIZE = 150;
    private float increment = 0.03f;

    public EnemySpaceShip() {
        this.setHitBox(new Rectangle(0, Gdx.graphics.getHeight()-SIZE, SIZE, SIZE));
        this.setImage(new Texture(Gdx.files.internal("sprites/ships/spco_small.png")));
        this.setExplosion(new EnemyShipDestroyedExplosion());
    }

    /**
     * @see SpaceShip#spawnAmmo()
     */
    public void spawnAmmo() {
        if (!isDestroyed()) {
            if (TimeUtils.nanoTime() - this.getLastDropTime() > 1000000000) {
                this.createAmmo();
            }
        }
    }

    /**
     * Creates a new ammunition and adds it to the ammunition array
     */
    public void createAmmo(){
        Ammo newAmmo = new BigBomb(this.getHitBox().getX() + this.getHitBox().getWidth()/2, this.getHitBox().getY());
        this.getAmmunition().add(newAmmo);
        setLastDropTime(TimeUtils.nanoTime());
    }

    /**
     * @see SpaceShip#move()
     */
    public void move(){
        if (!isDestroyed()) {
            /*if (this.getHitBox().getX() > Gdx.graphics.getWidth() - this.getHitBox().getWidth()) {
                this.setSpeed(-SpaceShip.getInitialSpeed());
            }
            if (this.getHitBox().getX() < 0) {
                this.setSpeed(SpaceShip.getInitialSpeed());
            }*/
            increment *= (1+Gdx.graphics.getDeltaTime());
            this.getHitBox().setX((float) (Math.sin(400) + 400));
            //this.getHitBox().setX(this.getHitBox().getX() + this.getSpeed() * Gdx.graphics.getDeltaTime());
        }
    }

}
