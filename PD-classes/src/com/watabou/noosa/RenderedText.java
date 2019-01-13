/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.watabou.noosa;

//FIXME this class is currently just wrapping bitmaptext, need to get proper rendered text working ASAP
//see android project for an example of how this class is supposed to work.
public class RenderedText extends BitmapText {

	public RenderedText( ) {
		text = null;
	}

	public RenderedText( Font font ) {
		super( font );
	}

	public RenderedText( String text, Font font ) {
		super( text, font );

		measure();
	}

	public void text( String text ){
		super.text( text );

		measure();
	}

	public String text(){
		return text;
	}

	public void size( int size ){
		//does nothing at the moment
	}

	public float baseLine(){
		return super.baseLine();
	}

	@Override
	protected void updateMatrix() {
		super.updateMatrix();
		//the y value is set at the top of the character, not at the top of accents.
		//Matrix.translate( matrix, 0, -Math.round((baseLine()*0.15f)/scale.y) );
	}

	public static void clearCache(){
		//do nothing atm
	}

	public static void reloadCache(){
		//nothing atm
	}

	public static void setFont(String asset){
		//nothing atm
	}
}
