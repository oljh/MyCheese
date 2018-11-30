package myCheese.reports;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

class ModelReport implements TableModel  {
	ArrayList<TableReport> lp;


	
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ArrayList<TableReport> getLp() {
		return lp;
	}
	public void setLp(ArrayList<TableReport> lp) {
		this.lp = lp;
	}
	
	public Class<?> getColumnClass(int col) {
        switch(col) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            	return String.class; 
        }

        throw new AssertionError("invalid column");
    }
    
    public int getRowCount() {
        return lp.size();
    }
    
    public int getColumnCount() {
        return 6;
    }
    
    public String getColumnName(int col) {
        switch(col) {
            case 0: return "№ паллета";
            case 1: return "Наименование";
            case 2: return "Количество ед.";
            case 3: return "Масса НЕТТО";
            case 4: return "Масса БРУТТО";
            case 5: return "Дата упаковки";
        }
        
        throw new AssertionError("invalid column"); 
    }
    
    public Object getValueAt(int row, int col) {
    	TableReport mpl = lp.get(row);
    
        switch(col) {
            case 0:
            	return mpl.getNumberPallet();
            case 1:
            	return mpl.getNameItem();
            case 2:
            	return mpl.getCount();
            case 3:
            	return mpl.getWeightNt();
            case 4:
            	return mpl.getWeightBr();
            case 5:
            	return mpl.getPackagingDate();
        }

        throw new AssertionError("invalid column");
    }

}
