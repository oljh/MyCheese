package myCheese.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class CellRenderer extends JTextArea implements TableCellRenderer {
	 private List<List<Integer>> rowColHeight = new ArrayList<>();
	 private static final int MAX_LEN = 150;
	public CellRenderer() {

		super();
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
		setFont(new Font("Verdana", 0, 14));
	}

	 protected String shortener(String str) {
	        if (str.length() < MAX_LEN) {
	            return str;
	        } else {
	          return str.substring(0, MAX_LEN - 10) + "...";
	        }
	    }
	    
	    @Override
	    public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected, boolean hasFocus,
	            int row, int column) {
	        if (isSelected) {
	            setForeground(table.getSelectionForeground());
	            setBackground(table.getSelectionBackground());
	        } else {
	            setForeground(table.getForeground());
	            setBackground(table.getBackground());
	        }
	        setFont(table.getFont());
	        if (hasFocus) {
	            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
	            if (table.isCellEditable(row, column)) {
	                setForeground(UIManager.getColor("Table.focusCellForeground"));
	                setBackground(UIManager.getColor("Table.focusCellBackground"));
	            }
	        } else {
	            setBorder(new EmptyBorder(1, 2, 1, 2));
	        }
	        if (value != null) {
	            setText(shortener(value.toString()));
	        } else {
	            setText("");
	        }
	        adjustRowHeight(table, row, column);
	        return this;
	    }
	    
	    /**
	     * Calculate the new preferred height for a given row, and sets the height on the table.
	     */
	    private void adjustRowHeight(JTable table, int row, int column) {
	        //The trick to get this to work properly is to set the width of the column to the
	        //textarea. The reason for this is that getPreferredSize(), without a width tries
	        //to place all the text in one line. By setting the size with the with of the column,
	        //getPreferredSize() returnes the proper height which the row should have in
	        //order to make room for the text.
	        int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
	        setSize(new Dimension(cWidth, 1000));
	        int prefH = getPreferredSize().height;
	        while (rowColHeight.size() <= row) {
	            rowColHeight.add(new ArrayList<Integer>(column));
	        }
	        List<Integer> colHeights = rowColHeight.get(row);
	        while (colHeights.size() <= column) {
	            colHeights.add(0);
	        }
	        colHeights.set(column, prefH);
	        int maxH = prefH;
	        for (Integer colHeight : colHeights) {
	            if (colHeight > maxH) {
	                maxH = colHeight;
	            }
	        }
	        if (table.getRowHeight(row) != maxH) {
	            table.setRowHeight(row, maxH);
	        }
	    
		
		
		
		
		
		
		
		/* setText((value == null) ? "" : value.toString());
		 setSize(table.getColumnModel().getColumn(column).getWidth(),getPreferredSize().height);
	    if (table.getRowHeight(row) != getPreferredSize().height) {  
	            table.setRowHeight(row, getPreferredSize().height);  
	        }
	   
	        
	    return this; */
		
		/*
		 String data = (String) value.toString();
         int lineWidth = this.getFontMetrics(this.getFont()).stringWidth(data);
         int lineHeight = this.getFontMetrics(this.getFont()).getHeight();
         int cellWidth = table.getCellRect(row, column, true).width;
         int cellHeight = table.getCellRect(row, column, true).height;
         
         int newRowHeight = (int) ((lineWidth / cellWidth) * (lineHeight)) + lineHeight * 2;
         if (table.getRowHeight(row) != newRowHeight) {
             table.setRowHeight(newRowHeight);
         }
         this.setSize(cellWidth, (int) table.getPreferredSize().getHeight());
         this.setText(data);
         return this;*/
	}
}	