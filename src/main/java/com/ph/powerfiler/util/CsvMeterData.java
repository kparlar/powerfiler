package com.ph.powerfiler.util;

public enum CsvMeterData {
	CONNECTION_ID(0), PROFILE(1), MONTH(2), METER_READING(
			3);

	private int cellOrder;

	CsvMeterData(int cellOrder) {
		this.cellOrder = cellOrder;
	}

	public int cellOrder() {
		return cellOrder;
	}
}
