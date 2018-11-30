package myCheese.reports;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import myCheese.components.CellRenderer;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;




public class FramePrintSpecification extends JFrame{
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	/* UI Components */
    private JPanel contentPane;
    private JLabel formLabel;
    private JTable table;
    private JScrollPane scroll;
    private JCheckBox showPrintDialogBox;
    private JCheckBox interactiveBox;
    private JCheckBox fitWidthBox;
    private JButton printButton;
    
    /* Protected so that they can be modified/disabled by subclasses */
    protected JCheckBox headerBox;
    protected JCheckBox footerBox;
    protected JTextField headerField;
    protected JTextField footerField;
    
    String tooltipText;
    private JLabel label;
    private JLabel lblNewLabel;
    private TableModel model;

    
	public FramePrintSpecification(TableModel model, String name, String numPal){
		super("Печать отчета");
		this.model = model;
		
		formLabel = new JLabel("Спецификация\t № " + numPal + " \t  " + name);
	        formLabel.setFont(new Font("Dialog", Font.BOLD, 16));
	        
	table = createTable(this.model);
	

	table.setDefaultRenderer(String.class, new CellRenderer());
	table.getColumnModel().getColumn(0).setPreferredWidth(20);
	table.getColumnModel().getColumn(1).setPreferredWidth(30);
	table.getColumnModel().getColumn(2).setPreferredWidth(135);
	
	table.getColumnModel().getColumn(3).setPreferredWidth(50);
	table.getColumnModel().getColumn(4).setPreferredWidth(55);

	headerField = new JTextField("Спецификация\t № " + numPal + " \t  " + name);
	MatteBorder border = new MatteBorder(0, 0, 1, 1, Color.BLACK);
	table.getTableHeader().setBorder(border);
    table.setFillsViewportHeight(true);
    
    scroll = new JScrollPane(table);
    
    headerBox = new JCheckBox("Верхний колонтитул:", true);
    headerBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            headerField.setEnabled(headerBox.isSelected());
        }
    });
    headerField.setToolTipText(tooltipText);
  
    tooltipText = "Включить нижний колонтитул";
    footerBox = new JCheckBox("Нижний колонтитул:", true);
    footerBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            footerField.setEnabled(footerBox.isSelected());
        }
    });
    footerBox.setToolTipText(tooltipText);
    tooltipText = "Нижний колонтитул (Используйте {0} для нумерации страниц)";
    footerField = new JTextField("Лист {0}");
    footerField.setToolTipText(tooltipText);

    tooltipText = "Окно печати";
    showPrintDialogBox = new JCheckBox("Открыть окно печати", true);
    showPrintDialogBox.setToolTipText(tooltipText);
    showPrintDialogBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            if (!showPrintDialogBox.isSelected()) {
                JOptionPane.showMessageDialog(
                    FramePrintSpecification.this,
                    "Если отключено диалоговое окно печати,"
                        + "используется принтер по умолчанию",
                    "Сообщение о печати",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
    
    
    tooltipText = "Показывать прогресс печати";
    interactiveBox = new JCheckBox("Показывать состаяние печати", false);
    interactiveBox.setToolTipText(tooltipText);
    interactiveBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            if (!interactiveBox.isSelected()) {
                JOptionPane.showMessageDialog(
                    FramePrintSpecification.this,
                    "Если отключено диалоговое окно печати,"
                            + "используется принтер по умолчанию",
                        "Сообщение о печати",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });

    tooltipText = "Вписать печатаемую таблицу по ширине листа";
    fitWidthBox = new JCheckBox("Вписать по ширине", true);
    fitWidthBox.setToolTipText(tooltipText);

    tooltipText = "Отправить на печать таблицу";
    printButton = new JButton("Печать");
    printButton.setToolTipText(tooltipText);
    
    printButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            printTable();
        }
    });

    contentPane = new JPanel();
    addComponentsToContentPane();
    setContentPane(contentPane);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setSize(900, 600);
    setPreferredSize(new Dimension(900, 480));
    setLocationRelativeTo(null);
	}
	
    /**
     * Adds to and lays out all GUI components on the {@code contentPane} panel.
     * <p>
     * It is recommended that you <b>NOT</b> try to understand this code. It was
     * automatically generated by the NetBeans GUI builder.
     * 
     */
    private void addComponentsToContentPane() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Печать"));

        GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
        bottomPanelLayout.setHorizontalGroup(
        	bottomPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(bottomPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(headerBox)
        				.addComponent(footerBox))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
        					.addComponent(footerField, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(fitWidthBox)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(interactiveBox)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(showPrintDialogBox))
        				.addComponent(headerField, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
        			.addGap(18)
        			.addComponent(printButton, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
        );
        bottomPanelLayout.setVerticalGroup(
        	bottomPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(bottomPanelLayout.createSequentialGroup()
        			.addGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(bottomPanelLayout.createSequentialGroup()
        					.addGroup(bottomPanelLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(headerBox)
        						.addComponent(headerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(bottomPanelLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(footerBox)
        						.addComponent(footerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(showPrintDialogBox)
        						.addComponent(interactiveBox)
        						.addComponent(fitWidthBox)))
        				.addComponent(printButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(24, Short.MAX_VALUE))
        );
        bottomPanel.setLayout(bottomPanelLayout);
        
        label = new JLabel("<html>Изготовитель: ОАО «Милкавита» Полесский производственный участок, \n ул. Жукова, 1, 247618 г.Хойники, Гомельская обл., Республика Беларусь, тел./факс +375 2346 30430, \r\n" + 
        		"e-mail: rmp@tut.by Юридический адрес: ОАО «Милкавита», ул. Бр. Лизюковых, 1, 246029 г. Гомель, Республика Беларусь, тел./факс: +375(232) 23-72-29, е-mail: mellug@mail.gomel.by, www.gomelmilk.by\r\n" + 
        		"</html>");
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Verdana", Font.ITALIC, 10));
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setIcon(new ImageIcon("D:\\JAVA\\classes\\ToWeigh\\img\\logo2.png"));

        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
        				.addComponent(bottomPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(formLabel, GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
        						.addComponent(label, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblNewLabel)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(label, GroupLayout.PREFERRED_SIZE, 73, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(formLabel))
        				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
        			.addGap(9)
        			.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        contentPane.setLayout(layout);
    }

    protected JTable createTable(TableModel model) {
        return new JTable(model);
    }

    /**
     * Create and return a cell renderer for rendering the pass/fail column.
     * This is protected so that a subclass can further customize it.
     */
    protected TableCellRenderer createPassedColumnRenderer() {
        return new PassedColumnRenderer();
    }

    /**
     * Print the table.
     */
    private void printTable() {
        /* Fetch printing properties from the GUI components */
        MessageFormat header = null;
        /* if we should print a header */
        if (headerBox.isSelected()) {
            /* create a MessageFormat around the header text */
            header = new MessageFormat(headerField.getText());
        	
        }

        MessageFormat footer = null;
        
        /* if we should print a footer */
        if (footerBox.isSelected()) {
            /* create a MessageFormat around the footer text */
            footer = new MessageFormat(footerField.getText());
        }

        boolean fitWidth = fitWidthBox.isSelected();
        boolean showPrintDialog = showPrintDialogBox.isSelected();
        boolean interactive = interactiveBox.isSelected();

        /* determine the print mode */
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH
                                         : JTable.PrintMode.NORMAL;

        try {
            /* print the table */
        	PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
        	set.add(OrientationRequested.LANDSCAPE);
        	  set.add(Chromaticity.MONOCHROME);
        	  set.add(PrintQuality.HIGH);
        	 set.add(new MediaPrintableArea(6,6,199,285,MediaPrintableArea.MM));

        	
            table.print(mode, header, footer, showPrintDialog, set, interactive, null);

            
        } catch (PrinterException pe) {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(this,
                                          "Ошибка печати: " + pe.getMessage(),
                                          "Результат печати",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * A custom cell renderer for rendering the "Passed" column.
     */
    @SuppressWarnings("serial")
	protected static class PassedColumnRenderer extends DefaultTableCellRenderer {


			public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {

            super.getTableCellRendererComponent(table, value, isSelected,
                                                hasFocus, row, column);

            setText("");
            setHorizontalAlignment(SwingConstants.CENTER);

            /* set the icon based on the passed status */
           // boolean status = (Boolean)value;
           // setIcon(status ? passedIcon : failedIcon);

            return this;
        }
    }
    
    private static void scrollToNewRow(JTable table, int row, int col) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();
        Rectangle rect = table.getCellRect(row, col, true);
        Point pt = viewport.getViewPosition();
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
     
        viewport.scrollRectToVisible(rect);
    }
}
