package com.watabou.pixeldungeon.input;

public enum GameAction {
	BACK("Go back"), MENU("Open menu"), JOURNAL("Open journal"), RESUME("Resume game"),
	REST("Rest"), SEARCH("Search"), INFO("Get cell information"), BACKPACK("Open backpack"),
	TAG_ATTACK("Open attack screen"), TAG_DANGER("Open danger screen"), QUICKSLOT("Quickslot"),
	CATALOGUS("Open catalogus"), HERO_INFO("Open hero info"),
	ZOOM_IN("Zoom in"), ZOOM_OUT("Zoom out"), ZOOM_DEFAULT("Reset zoom"),
	MOVE_UP("Move up"), MOVE_DOWN("Move down"), MOVE_LEFT("Move left"), MOVE_RIGHT("Move right"),
	MOVE_TOP_LEFT("Move top left"), MOVE_TOP_RIGHT("Move top right"), MOVE_BOTTOM_LEFT("Move bottom left"), MOVE_BOTTOM_RIGHT("Move bottom right"),
	WAIT("Wait"), UNKNOWN(null);

	private final String description;

	GameAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

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
