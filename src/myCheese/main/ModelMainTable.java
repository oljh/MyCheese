package myCheese.main;


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

final class ModelMainTable implements TableModel  {
	int count = 1;
	private PalletList pl = new PalletList();

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {

	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Class<?> getColumnClass(int col) {
		switch (col) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			return String.class;
		}

		throw new AssertionError("invalid column");
	}

	public int getRowCount() {
		return pl.getSize();
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "№ п.п";
		case 1:
			return "№ ящика";
		case 2:
			return "Масса НЕТТО";
		case 3:
			return "Номер партии";
		case 4:
			return "Дата изготовления";
		}

		throw new AssertionError("invalid column");
	}

	public Object getValueAt(int row, int col) {
		TableMain mpl = pl.getPalletList().get(row);

		switch (col) {
		case 0:
			 return Integer.valueOf(row + 1);
		case 1:
			return mpl.getNumBox();
		case 2:
			return mpl.getWeight();
		case 3:
			return mpl.getNumCook();
		case 4:
			return mpl.getDateCook();

		}

		throw new AssertionError("invalid column");
	}
	
  

}
