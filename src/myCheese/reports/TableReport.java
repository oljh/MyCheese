package myCheese.reports;


public class TableReport {

	
	private String numberPallet;
	private String nameItem;
	private String count;
	private String weightNt;
	private String weightBr;
	private String packagingDate;

	public TableReport(String numberPallet, String nameItem, String count, String weightNt, String weightBr, String packagingDate) {
		super();
		this.numberPallet = numberPallet;
		this.nameItem = nameItem;
		this.count = count;
		this.weightNt = weightNt;
		this.weightBr = weightBr;
		this.packagingDate = packagingDate;
	}

	public String getNumberPallet() {
		return numberPallet;
	}

	public String getNameItem() {
		return nameItem;
	}

	public String getCount() {
		return count;
	}

	public String getWeightNt() {
		return weightNt;
	}

	public String getWeightBr() {
		return weightBr;
	}

	public String getPackagingDate() {
		return packagingDate;
	}

	public void setNumberPallet(String numberPallet) {
		this.numberPallet = numberPallet;
	}

	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setWeightNt(String weightNt) {
		this.weightNt = weightNt;
	}

	public void setWeightBr(String weightBr) {
		this.weightBr = weightBr;
	}

	public void setPackagingDate(String packagingDate) {
		this.packagingDate = packagingDate;
	}
	

	
}
