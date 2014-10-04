package com.watabou.pixeldungeon.input;

public enum GameAction {
	BACK("Go back"), MENU("Open menu"), JOURNAL("Open journal"), RESUME("Resume game"),
	REST("Rest"), SEARCH("Search"), CELL_INFO("Get cell information"), BACKPACK("Open backpack"),
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
}
