package com.watabou.pixeldungeon.actors.blobs;


import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.effects.BlobEmitter;
import com.watabou.pixeldungeon.effects.particles.FlameParticle;
import com.watabou.pixeldungeon.effects.particles.GooWarnParticle;
import com.watabou.pixeldungeon.effects.particles.ShadowParticle;
import com.watabou.pixeldungeon.effects.particles.WindParticle;

/**
 * Created by Evan on 29/09/2014.
 */
public class GooWarn extends Blob {

    //cosmetic blob, used to warn noobs that goo's pump up should, infact, be avoided.

    protected int pos;

    @Override
    protected void evolve() {
        for (int i=0; i < LENGTH; i++) {

            int offv = cur[i] > 0 ? cur[i] - 1 : 0;
            off[i] = offv;

            if (offv > 0) {
                volume += offv;
            }
        }


    }

    public void seed( int cell, int amount ) {
        int diff = amount - cur[cell];
        if (diff > 0) {
            cur[cell] = amount;
            volume += diff;
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );
        emitter.start(GooWarnParticle.FACTORY, 0.05f, 0 );
    }

    @Override
    public String tileDesc() {
        return "Dark energy is building here!";
    }
}

