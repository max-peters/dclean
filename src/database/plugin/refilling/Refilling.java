package database.plugin.refilling;

import database.main.date.Date;
import database.plugin.Instance;

public class Refilling extends Instance {
	public double	refuelAmount;
	public double	distance;
	private double	value;
	private Date	date;
	private int		count;

	public Refilling(String[] parameter, RefillingList list) {
		super(new Date(parameter[3]).toString(), list);
		this.refuelAmount = Double.parseDouble(parameter[0]);
		this.value = Double.parseDouble(parameter[1]);
		this.distance = Double.parseDouble(parameter[2]);
		this.date = new Date(parameter[3]);
		this.count = list.getSize() + 1;
		createExpense();
	}

	public String toString() {
		return "refilling" + " : " + refuelAmount + " / " + distance + " / " + date.toString() + " / " + value;
	}

	private void createExpense() {
		String[] parameter = { "Tankstelle", "Auto", String.valueOf(value), date.toString() };
		list.add(parameter);
	}

	private double calcAverageConsumption() {
		return Math.round(refuelAmount / distance * 1000) / 10.0;
	}

	protected String output() {
		return "[" + String.format("%" + String.valueOf(list.getSize()).length() + "s", count).replace(' ', '0') + "] distance: ["
				+ String.format("%" + String.valueOf(((RefillingList) list).getHighestDistance()).length() + "s", distance) + " km] refuelAmount: ["
				+ String.format("%" + String.valueOf(String.format("%.1f", ((RefillingList) list).getHighestRefuelAmount())).length() + "s", String.format("%.1f", refuelAmount)).replace(",", ".")
				+ " l] averageConsumption: [" + String.format("%" + String.valueOf(((RefillingList) list).getHighestAverageConsumption()).length() + "s", calcAverageConsumption()) + " l/km]";
	}
}