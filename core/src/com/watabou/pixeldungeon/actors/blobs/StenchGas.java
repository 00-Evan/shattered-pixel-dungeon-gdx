package com.watabou.pixeldungeon.actors.blobs;

import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.effects.BlobEmitter;
import com.watabou.pixeldungeon.effects.Speck;

/**
 * Created by debenhame on 08/10/2014.
 */
public class StenchGas extends Blob {

    @Override
    protected void evolve() {
        super.evolve();

        Char ch;
        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
                if (!(ch instanceof Ghost.FetidRat))
                    Buff.prolong(ch, Paralysis.class, Paralysis.duration(ch)/5);
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );

        emitter.pour( Speck.factory(Speck.STENCH), 0.6f );
    }

    @Override
    public String tileDesc() {
        return "A cloud of fetid stench is swirling here.";
    }
}
