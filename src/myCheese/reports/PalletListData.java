package myCheese.reports;

public class PalletListData {

	private int countItems;
	private int pallets_key;
	private String itemsName;
	private String itemsLabelsName;
	private String LotNumber;
	private int weightBox;
	private int weightFilm;
	private int commonWeightNt;
	private int commonWeightBr;
	private int sumWeightNt;
	private int sumWeightBr;
	private int countWaights;
	private String grOutputDate;
	private String grProductionDate;
	private String grExpirationDate;
	
	public PalletListData(int countItems, int pallets_key, String itemsName, String itemsLabelsName, String lotNumber,
			int weightBox, int weightFilm, int commonWeightNt, int commonWeightBr, int sumWeightNt, int sumWeightBr, int countWaights, String grOutputDate, String grProductionDate,
			String grExpirationDate) {
		super();
		this.setCountItems(countItems);
		this.setPallets_key(pallets_key);
		this.setItemsName(itemsName);
		this.setItemsLabelsName(itemsLabelsName);
		this.setLotNumber(lotNumber);
		this.setWeightBox(weightBox);
		this.setWeightFilm(weightFilm);
		this.setCommonWeightNt(commonWeightNt);
		this.setCommonWeightBr(commonWeightBr);
		this.setSumWeightNt(sumWeightNt);
		this.setSumWeightBr(sumWeightBr);
		this.setCountWaights(countWaights);
		this.setGrOutputDate(grOutputDate);
		this.setGrProductionDate(grProductionDate);
		this.setGrExpirationDate(grExpirationDate);	
	}
	public int getCountItems() {
		return countItems;
	}
	public void setCountItems(int countItems) {
		this.countItems = countItems;
	}
	public int getPallets_key() {
		return pallets_key;
	}
	public void setPallets_key(int pallets_key) {
		this.pallets_key = pallets_key;
	}
	public String getItemsName() {
		return itemsName;
	}
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	public String getItemsLabelsName() {
		return itemsLabelsName;
	}
	public void setItemsLabelsName(String itemsLabelsName) {
		this.itemsLabelsName = itemsLabelsName;
	}
	public String getLotNumber() {
		return LotNumber;
	}
	public void setLotNumber(String lotNumber) {
		LotNumber = lotNumber;
	}
	public int getWeightBox() {
		return weightBox;
	}
	public void setWeightBox(int weightBox) {
		this.weightBox = weightBox;
	}
	public int getWeightFilm() {
		return weightFilm;
	}
	public void setWeightFilm(int weightFilm) {
		this.weightFilm = weightFilm;
	}
	public int getSumWeightNt() {
		return sumWeightNt;
	}
	public int getCommonWeightNt() {
		return commonWeightNt;
	}
	public void setCommonWeightNt(int commonWeightNt) {
		this.commonWeightNt = commonWeightNt;
	}
	public int getCommonWeightBr() {
		return commonWeightBr;
	}
	public void setCommonWeightBr(int commonWeightBr) {
		this.commonWeightBr = commonWeightBr;
	}
	public void setSumWeightNt(int sumWeightNt) {
		this.sumWeightNt = sumWeightNt;
	}
	public int getSumWeightBr() {
		return sumWeightBr;
	}
	public void setSumWeightBr(int sumWeightBr) {
		this.sumWeightBr = sumWeightBr;
	}
	public int getCountWaights() {
		return countWaights;
	}
	public void setCountWaights(int countWaights) {
		this.countWaights = countWaights;
	}
	public String getGrOutputDate() {
		return grOutputDate;
	}
	public void setGrOutputDate(String grOutputDate) {
		this.grOutputDate = grOutputDate;
	}
	public String getGrProductionDate() {
		return grProductionDate;
	}
	public void setGrProductionDate(String grProductionDate) {
		this.grProductionDate = grProductionDate;
	}
	public String getGrExpirationDate() {
		return grExpirationDate;
	}
	public void setGrExpirationDate(String grExpirationDate) {
		this.grExpirationDate = grExpirationDate;
	}
}
