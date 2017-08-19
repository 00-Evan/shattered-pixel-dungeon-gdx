/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.shatteredpixeldungeon.input;

public enum GameAction {
	BACK( null ),

	HERO_INFO("Hero Info"),
	JOURNAL("Journal"),

	REST("Wait"),
	SEARCH("Search"),
	RESUME("Resume motion"),

	BACKPACK("Backpack"),
	QUICKSLOT_1("Quickslot 1"),
	QUICKSLOT_2("Quickslot 2"),
	QUICKSLOT_3("Quickslot 3"),
	QUICKSLOT_4("Quickslot 4"),

	TAG_ATTACK("Attack"),
	TAG_DANGER("Visible Enemies"),

	ZOOM_IN("Zoom In"),
	ZOOM_OUT("Zoom Out"),
	ZOOM_DEFAULT("Default Zoom"),

	MOVE_UP("Move North"), MOVE_DOWN("Move South"), MOVE_LEFT("Move West"), MOVE_RIGHT("Move East"),
	MOVE_TOP_LEFT("Move NW"), MOVE_TOP_RIGHT("Move NE"), MOVE_BOTTOM_LEFT("Move SW"), MOVE_BOTTOM_RIGHT("Move SE"),
	OPERATE("Current Cell"),

	UNKNOWN(null);

	private final String description;

	GameAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
