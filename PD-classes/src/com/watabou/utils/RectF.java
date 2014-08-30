package com.watabou.utils;

public class RectF {
	public final float left;
	public final float right;
	public final float top;
	public final float bottom;

	public RectF(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public RectF(RectF other) {
		this.left = other.left;
		this.right = other.right;
		this.top = other.top;
		this.bottom = other.bottom;
	}

	public float width() {
		return right - left;
	}

	public float height() {
		return bottom - top;
	}

	public RectF offset(float dx, float dy) {
		return new RectF(left + dx, top + dy, right + dx, bottom + dy);
	}
}
