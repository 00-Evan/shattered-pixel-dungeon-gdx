package com.watabou.pixeldungeon.input;

public enum GameAction {
	BACK, MENU, JOURNAL, RESUME, REST, SEARCH, INFO, BACKPACK, TAG_ATTACK, TAG_DANGER, QUICKSLOT, CATALOGUS;

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
