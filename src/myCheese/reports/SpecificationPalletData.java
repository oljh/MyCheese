package myCheese.reports;

public class SpecificationPalletData {

	private String boxNumber;
	private String countWaights;
	private String weights;
	private int weightNt;
	private int weightBr;
	private String numberCooking;
	private String outputDate;
	private String productionDate;
	private String expirationDate;
	private String palletNumber;
	private String itemsName;

	public SpecificationPalletData(String palletNumber, String itemsName, String boxNumber, String countWaights, String weights, int weightNt, int weightBr,
			String numberCooking, String outputDate, String productionDate, String expirationDate) {
		super();
		this.setPalletNumber(palletNumber);
		this.setItemsName(itemsName);
		this.setBoxNumber(boxNumber);
		this.setCountWaights(countWaights);
		this.setWeights(weights);
		this.setWeightNt(weightNt);
		this.setWeightBr(weightBr);
		this.setNumberCooking(numberCooking);
		this.setOutputDate(outputDate);
		this.setProductionDate(productionDate);
		this.setExpirationDate(expirationDate);
	}

	public String getPalletNumber() {
		return palletNumber;
	}

	public void setPalletNumber(String palletNumber) {
		this.palletNumber = palletNumber;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public int getWeightNt() {
		return weightNt;
	}

	public void setWeightNt(int weightNt) {
		this.weightNt = weightNt;
	}

	public int getWeightBr() {
		return weightBr;
	}

	public void setWeightBr(int weightBr) {
		this.weightBr = weightBr;
	}

	public String getNumberCooking() {
		return numberCooking;
	}

	public void setNumberCooking(String numberCooking) {
		this.numberCooking = numberCooking;
	}

	public String getOutputDate() {
		return outputDate;
	}

	public void setOutputDate(String outputDate) {
		this.outputDate = outputDate;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

	public String getCountWaights() {
		return countWaights;
	}

	public void setCountWaights(String countWaights) {
		this.countWaights = countWaights;
	}

}
