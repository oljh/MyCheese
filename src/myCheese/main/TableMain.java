package myCheese.main;

public class TableMain {

	private String numBox;
	private String weight;
	private String lotNum;
	private String dateCook;

	public TableMain(String numBox, String weight, String lotNum, String dateCook) {
		this.setNumBox(numBox);
		this.setWeight(weight);
		this.setLotNum(lotNum);
		this.setDateCook(dateCook);
	}

	public void setNumBox(String numBox) {
		this.numBox = numBox;
	}

	public String getNumBox() {
		return numBox;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeight() {
		return weight;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setDateCook(String dateCook) {
		this.dateCook = dateCook;
	}

	public String getDateCook() {
		return dateCook;
	}
}
