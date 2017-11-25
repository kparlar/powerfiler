package com.ph.powerfiler.util;

public enum CsvFractionData {
	MONTH(0), PROFILE(1), FRACTION(2);

	private int cellOrder;

	CsvFractionData(int cellOrder) {
		this.cellOrder = cellOrder;
	}

	public int cellOrder() {
		return cellOrder;
	}
}
