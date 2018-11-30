package myCheese.main;

import java.util.ArrayList;
import java.util.List;

public class PalletList {

	private static List<TableMain> mpl = new ArrayList<TableMain>();

	public List<TableMain> getPalletList() {
		return mpl;
	}

	public int getSize() {
		return mpl.size();
	}

	public void setPalletList(ArrayList<TableMain> mpl) {
		PalletList.mpl = mpl;
	}
	
	public void clearPalletList() {
		PalletList.mpl.clear();
	}

	public void addLine(String numBox, String weight, String numCook, String dateCook) {
		PalletList.mpl.add(new TableMain(numBox, weight, numCook, dateCook));
	}
	
	void delLastLine() {
		PalletList.mpl.remove(PalletList.mpl.size() - 1);
	}
}
