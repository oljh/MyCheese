package myCheese.main;

public class TableMain {

	private String numBox;
	private String weight;
	private String numCook;
	private String dateCook;

	public TableMain(String numBox, String weight, String numCook, String dateCook) {
		this.setNumBox(numBox);
		this.setWeight(weight);
		this.setNumCook(numCook);
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

	public void setNumCook(String numCook) {
		this.numCook = numCook;
	}

	public String getNumCook() {
		return numCook;
	}

	public void setDateCook(String dateCook) {
		this.dateCook = dateCook;
	}

	public String getDateCook() {
		return dateCook;
	}
}
