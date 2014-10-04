package com.watabou.pixeldungeon.input;

public enum GameAction {
	BACK, MENU, JOURNAL, RESUME, REST, SEARCH, INFO, BACKPACK, TAG_ATTACK, TAG_DANGER, QUICKSLOT, CATALOGUS, HERO_INFO,
	ZOOM_IN, ZOOM_OUT, ZOOM_DEFAULT,
	MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, MOVE_TOP_LEFT, MOVE_TOP_RIGHT, MOVE_BOTTOM_LEFT, MOVE_BOTTOM_RIGHT, WAIT;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name());
		int idx = 0;
		do {
			sb.replace(idx, idx + 1, sb.substring(idx, idx + 1).toUpperCase());
			idx = sb.indexOf("_", idx) + 1;
		} while (idx > 0 && idx < sb.length());
		return sb.toString();
	}
}
