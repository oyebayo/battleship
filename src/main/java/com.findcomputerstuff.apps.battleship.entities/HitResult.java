package com.findcomputerstuff.apps.battleship.entities;

import com.findcomputerstuff.apps.battleship.entities.HitType;

public class HitResult {
	private int cellCount;
	private final HitType hitType;

	public HitResult(HitType hitType) {
		this.cellCount = 0;
		this.hitType = hitType;
	}
	public HitResult(HitType hitType, int cellCount) {
		this(hitType);
		this.cellCount = cellCount;
	}

	public int getCellCount() {
		return cellCount;
	}

	public HitType getHitType() {
		return hitType;
	}
}
