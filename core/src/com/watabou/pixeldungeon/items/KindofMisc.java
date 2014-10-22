package com.watabou.pixeldungeon.items;

import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.items.EquipableItem;

/**
 * Created by Evan on 24/08/2014.
 */
public abstract class KindofMisc extends EquipableItem {

    public abstract void activate(Char ch);
}
