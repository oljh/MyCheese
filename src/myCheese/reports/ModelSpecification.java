package myCheese.reports;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ModelSpecification implements TableModel  {
	ArrayList<SpecificationPalletData> lp;


	
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public ArrayList<SpecificationPalletData> getLp() {
		return lp;
	}
	public void setLp(ArrayList<SpecificationPalletData> lp) {
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
            case 6:
            case 7:
            case 8:
            	return String.class; 
        }

        throw new AssertionError("invalid column");
    }
    
    public int getRowCount() {
        return lp.size();
    }
    
    public int getColumnCount() {
        return 9;
    }
    
    public String getColumnName(int col) {
        switch(col) {
        	case 0: return "№ п/п";
            case 1: return "№ ящика";
            case 2: return "Веса";
            case 3: return "Масса НЕТТО";
            case 4: return "Масса БРУТТО";
            case 5: return "Номер варки";
            case 6: return "Дата выработки";
            case 7: return "Дата изготовления";
            case 8: return "Годен до";
        }
        
        throw new AssertionError("invalid column"); 
    }
    
    public Object getValueAt(int row, int col) {
    	SpecificationPalletData mpl = lp.get(row);
    
        switch(col) {
        	case 0:
        		 //return mpl.getItemsName();
            case 1:
            	return mpl.getBoxNumber();
            case 2:
            	return mpl.getWeights();
            case 3:
            	return mpl.getWeightNt();
            case 4:
            	return mpl.getWeightBr();
            case 5:
            	return mpl.getNumberCooking();
            case 6:
            	return mpl.getOutputDate();
            case 7:
            	return mpl.getProductionDate();
            case 8:
            	return mpl.getExpirationDate();
        }

        throw new AssertionError("invalid column");
    }

}
