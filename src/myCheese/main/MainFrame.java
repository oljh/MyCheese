package myCheese.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.ButtonGroup;

import myCheese.components.OnlyInteger;
import myCheese.connectionDevice.Weigher;
import myCheese.connectionDevice.weighers.CASADH05;
import myCheese.connectionDevice.weighers.CASADH20;
import myCheese.connectionDevice.weighers.Etalon;
import myCheese.db.QueriesToDB;
import myCheese.io.ChangeZplCode;
import myCheese.io.DataFields;
import myCheese.io.InicialData;
import myCheese.reports.FrameReports;
import myCheese.reports.GenerateSpecification;
import myCheese.reports.PalletListData;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
class MainFrame extends JFrame implements ActionListener, FocusListener, DocumentListener {

	private Font font;
	// private Font fontXL;
	// private Font fontL;
	private Font fontD;

	private JTable table;
	private ModelMainTable model;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu setting;
	private JMenu report;
	private JMenu action;

	private JMenuItem exitItem;
	private JMenuItem menuSet;
	private JMenuItem openLastPallet;
	// private JMenuItem delLastW;
	private JMenuItem reportTime;
	private JMenuItem actionRunWeight;
	private JMenuItem actionDel;
	private JMenuItem printLable;

	private JScrollPane scroll;

	private MatteBorder border;
	private JLabel lotNumberLabel;
	private JLabel quantityBoxesLabel;
	private JLabel quantityItemsInBoxLabel;
	private JLabel boxNumberLabel;
	private JLabel outputDateLabel;
	private JLabel productionDateLabel;
	private JLabel packagingDateLabel;
	private JLabel shelfLifeLabel;
	private JLabel expirationDateLabel;
	private JLabel weightBoxLabel;
	private JLabel weightFilmLable;
	private JLabel nameItemLabel;

	private JTextField weightTextField;

	private static JTextField lotNumberTextField;
	private static JTextField shelfLifeTextField;
	private static JTextField boxNumberTextField;
	private static JTextField quantityBoxesTextField;
	private static JTextField weightFilmTextField;
	private static JTextField weightBoxTextField;

	private JComboBox<String> nameItemComboBox;
	private JComboBox<String> quantityItemsInBoxComboBox;

	private JPanel contentPane;
	private JPanel rightPanel;
	private JPanel buttonPanel;

	private JDateChooser expirationDateChooser;
	private JDateChooser packagingDateChooser;
	private JDateChooser productionDateChooser;
	private JDateChooser outputDateChooser;

	private final GroupLayout gl_buttonPanel;

	private static JButton specificationButton;
	private static JButton palletListButton;
	private static JButton newPalletButton;
	private static JButton getWeightButton;
	private static JButton buttonPalletPannel;

	private Date date = new Date();

	private QueriesToDB qDB;
	private final ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton outputRadioButton;
	private JRadioButton productionRadioButton;
	private JRadioButton packagingDateRadioButton;

	private PalletList pl;
	private Weigher weigher;
	protected JTextField stabilizationTextField;
	private JTextField numberPalletTextField;

	private int counter;
	private boolean printMark;
	private GroupLayout gl_numberPalletPanel;
	private InicialData id;
	private JTextField netMassTextField;
	private JTextField grossMassTextField;
	private Font fontL;
	private JMenuItem printShippingLables;
	private JCheckBox autoWeightCheckBox;
	protected boolean pressedGetWeightButton;
	protected boolean nextPrintMark;
	private Font fontP;

	public MainFrame() {
		super("MyCheese");
		model = new ModelMainTable();
		id = new InicialData();
		pl = new PalletList();
		// _____________________
		try {
			qDB = new QueriesToDB();
		} catch (ClassNotFoundException | SQLException e2) {
			e2.printStackTrace();
		}

		// _____________________
		font = new Font("Verdana", Font.PLAIN, 12);
		fontL = new Font("Verdana", Font.PLAIN, 16);

		try {
			fontD = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\7segment.ttf"));
		} catch (Exception e) {
			System.out.println("Шрифт 7segment.ttf в каталоге fonts не найден!");
		}

		try {
			fontP = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\HelveticaBold.ttf"));
		} catch (Exception e) {
			System.out.println("Шрифт HelveticaBold.ttf в каталоге fonts не найден!");
		}

		// ______________________

		String[] arrNum = new String[40];
		for (int i = 0; i < 40; i++) {
			arrNum[i] = String.valueOf(i + 1);
		}

		// ______________________

		menuBar = new JMenuBar();

		fileMenu = new JMenu("Файл");
		fileMenu.setFont(font);
		menuBar.add(fileMenu);

		exitItem = new JMenuItem("Выход");
		exitItem.setFont(font);
		fileMenu.add(exitItem);

		setting = new JMenu("Настройки");
		setting.setFont(font);
		menuBar.add(setting);

		menuSet = new JMenuItem("Изменить настройки");
		menuSet.setFont(font);
		setting.add(menuSet);

		action = new JMenu("Действия");
		action.setFont(font);
		menuBar.add(action);

		actionRunWeight = new JMenuItem("Восстановить связь с весами");
		actionRunWeight.setFont(font);
		action.add(actionRunWeight);

		openLastPallet = new JMenuItem("Открыть последний паллет");
		openLastPallet.setFont(font);
		action.add(openLastPallet);

		printLable = new JMenuItem("Печать текущей тр. этикетки");
		printLable.setFont(font);
		printLable.setEnabled(false);
		action.add(printLable);

		printShippingLables = new JMenuItem("Печать тр. этикеток паллета");
		printShippingLables.setFont(font);
		printShippingLables.setEnabled(false);
		action.add(printShippingLables);

		actionDel = new JMenuItem("Удалить последнюю запись");
		actionDel.setFont(font);
		actionDel.setEnabled(false);
		action.add(actionDel);

		report = new JMenu("Отчеты");
		report.setFont(font);
		menuBar.add(report);

		reportTime = new JMenuItem("Подготовить отчет");
		reportTime.setFont(font);
		report.add(reportTime);

		// ______________________

		table = createTable(model);
		// table.getColumnModel().getColumn(0).setPreferredWidth(40);
		// table.getColumnModel().getColumn(1).setResizable(isResizable());
		// table.getColumnModel().getColumn(2).setPreferredWidth(40);
		// table.getColumnModel().getColumn(3).setPreferredWidth(100);

		border = new MatteBorder(0, 0, 1, 1, Color.GRAY);
		table.getTableHeader().setBorder(border);
		table.setFillsViewportHeight(true);
		// table.setRowHeight(24);

		scroll = new JScrollPane(table);

		contentPane = new JPanel();

		rightPanel = new JPanel();
		rightPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		buttonPanel = new JPanel();
		buttonPanel
				.setBorder(new TitledBorder(
						new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
								TitledBorder.TOP, null, new Color(0, 0, 0)),
						"", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Print table
		GroupLayout layout = new GroupLayout(contentPane);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
						.addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scroll, 0, 0, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
							.addGap(9)
							.addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(15, Short.MAX_VALUE))
		);

		specificationButton = new JButton("Спецификация");
		specificationButton.setEnabled(true);
		specificationButton.setFont(fontL);

		palletListButton = new JButton("Паллетный лист");
		palletListButton.setEnabled(true);
		palletListButton.setFont(fontL);

		newPalletButton = new JButton("Новый паллет");
		newPalletButton.setEnabled(false);
		newPalletButton.setFont(fontL);

		getWeightButton = new JButton("Вес");
		getWeightButton.setEnabled(false);
		getWeightButton.setFont(fontL);

		gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(specificationButton, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
								.addComponent(palletListButton, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(newPalletButton, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
								.addComponent(getWeightButton, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addContainerGap()));
		gl_buttonPanel.setVerticalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(newPalletButton, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE)
										.addGap(6)
										.addComponent(getWeightButton, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE))
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(specificationButton, GroupLayout.PREFERRED_SIZE, 26,
												Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(palletListButton,
												GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE)))
						.addGap(15)));
		buttonPanel.setLayout(gl_buttonPanel);

		weightTextField = new JTextField();
		weightTextField.setBounds(79, 11, 177, 37);
		weightTextField.setBackground(SystemColor.controlHighlight);
		weightTextField.setHorizontalAlignment(SwingConstants.CENTER);
		weightTextField.setText("0000");
		weightTextField.setFont(fontD.deriveFont(36.0f));
		weightTextField.setColumns(10);

		JPanel numberPalletPanel = new JPanel();
		numberPalletPanel.setBounds(266, 11, 180, 87);
		numberPalletPanel.setBorder(
				new TitledBorder(null, "Номер паллета:", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		numberPalletTextField = new JTextField();
		numberPalletTextField.setHorizontalAlignment(SwingConstants.CENTER);
		numberPalletTextField.setFont(fontP.deriveFont(26.0f));
		numberPalletTextField.setBackground(SystemColor.controlHighlight);
		numberPalletTextField.setDocument(new OnlyInteger(null));
		numberPalletTextField.setColumns(10);

		nameItemLabel = new JLabel("Наименование:");
		nameItemLabel.setBounds(10, 105, 77, 14);

		try {
			nameItemComboBox = new JComboBox<String>(qDB.getItemsList());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}//qDB.getItemsList()

		nameItemComboBox.setBounds(92, 102, 354, 20);

		nameItemComboBox.setSelectedIndex(-1);

		lotNumberLabel = new JLabel("№ партии:");
		lotNumberLabel.setBounds(10, 131, 56, 14);

		lotNumberTextField = new JTextField();
		lotNumberTextField.setBounds(92, 128, 96, 21);
		lotNumberTextField.setDocument(new OnlyInteger(null));
		lotNumberTextField.setText("1");
		lotNumberTextField.setColumns(10);

		outputDateLabel = new JLabel("Дата выработки:");
		outputDateLabel.setBounds(211, 131, 89, 14);

		outputDateChooser = new JDateChooser();
		outputDateChooser.setBounds(330, 128, 91, 21);
		outputDateChooser.setDate(date);

		outputRadioButton = new JRadioButton("");
		outputRadioButton.setBounds(423, 128, 21, 21);

		radioButtonGroup.add(outputRadioButton);

		productionDateLabel = new JLabel("Дата изготовления:");
		productionDateLabel.setBounds(211, 158, 103, 14);

		productionDateChooser = new JDateChooser();              
		productionDateChooser.setBounds(330, 155, 91, 21);
		productionDateChooser.setDate(date);

		productionRadioButton = new JRadioButton("");
		productionRadioButton.setBounds(423, 155, 21, 21);
		productionRadioButton.setSelected(true);
		radioButtonGroup.add(productionRadioButton);

		quantityBoxesLabel = new JLabel("Кол. ящиков:");
		quantityBoxesLabel.setBounds(10, 158, 69, 14);

		quantityBoxesTextField = new JTextField();
		quantityBoxesTextField.setBounds(92, 155, 96, 21);
		quantityBoxesTextField.setDocument(new OnlyInteger(null));
		quantityBoxesTextField.setText("42");
		quantityBoxesTextField.setColumns(10);

		packagingDateLabel = new JLabel("Дата упаковывания:");
		packagingDateLabel.setBounds(211, 185, 109, 14);

		packagingDateChooser = new JDateChooser();
		packagingDateChooser.setBounds(330, 182, 91, 21);
		packagingDateChooser.setDate(date);

		packagingDateRadioButton = new JRadioButton("");
		packagingDateRadioButton.setBounds(423, 182, 21, 21);
		radioButtonGroup.add(packagingDateRadioButton);

		quantityItemsInBoxLabel = new JLabel("Кол. ед. в ящ.:");
		quantityItemsInBoxLabel.setBounds(10, 185, 78, 14);

		quantityItemsInBoxComboBox = new JComboBox<String>(arrNum); // arrNum
		quantityItemsInBoxComboBox.setBounds(92, 182, 96, 20);
		quantityItemsInBoxComboBox.setSelectedIndex(1);
		shelfLifeLabel = new JLabel("Срок годности:");
		shelfLifeLabel.setBounds(211, 211, 79, 14);

		shelfLifeTextField = new JTextField();
		shelfLifeTextField.setBounds(330, 208, 91, 20);
		shelfLifeTextField.setColumns(10);
		shelfLifeTextField.setDocument(new OnlyInteger(null));

		boxNumberLabel = new JLabel("№ п.п ящика:");
		boxNumberLabel.setBounds(10, 211, 72, 14);

		boxNumberTextField = new JTextField();
		boxNumberTextField.setBounds(92, 208, 96, 20);
		boxNumberTextField.setDocument(new OnlyInteger(null));
		boxNumberTextField.setText("1");
		boxNumberTextField.setColumns(10);

		expirationDateLabel = new JLabel("Годен до:");
		expirationDateLabel.setBounds(211, 236, 51, 14);

		expirationDateChooser = new JDateChooser();
		expirationDateChooser.setBounds(330, 233, 91, 20);
		expirationDateChooser.setDate(date);
		expirationDateChooser.setEnabled(false);

		weightBoxLabel = new JLabel("Масса ящика (г):");
		weightBoxLabel.setBounds(10, 236, 96, 14);

		weightBoxTextField = new JTextField();
		weightBoxTextField.setBounds(116, 233, 72, 20);
		weightBoxTextField.setColumns(10);
		weightBoxTextField.setDocument(new OnlyInteger(null));
		weightFilmLable = new JLabel("Масса упак. (г):");
		weightFilmLable.setBounds(10, 261, 103, 14);

		weightFilmTextField = new JTextField();
		weightFilmTextField.setBounds(116, 258, 72, 20);
		weightFilmTextField.setColumns(10);
		weightFilmTextField.setDocument(new OnlyInteger(null));

		grossMassTextField = new JTextField();
		grossMassTextField.setText("0");
		grossMassTextField.setHorizontalAlignment(SwingConstants.CENTER);
		grossMassTextField.setFont(null);
		grossMassTextField.setColumns(10);
		grossMassTextField.setBackground(SystemColor.controlHighlight);
		grossMassTextField.setBounds(160, 77, 96, 21);

		netMassTextField = new JTextField();
		netMassTextField.setBounds(160, 52, 96, 21);
		netMassTextField.setText("0");
		netMassTextField.setHorizontalAlignment(SwingConstants.CENTER);
		netMassTextField.setColumns(10);
		netMassTextField.setBackground(SystemColor.controlHighlight);

		buttonPalletPannel = new JButton("Открыть");

		gl_numberPalletPanel = new GroupLayout(numberPalletPanel);
		gl_numberPalletPanel.setHorizontalGroup(gl_numberPalletPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_numberPalletPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_numberPalletPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(numberPalletTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 148,
										Short.MAX_VALUE)
								.addComponent(buttonPalletPannel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 148,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_numberPalletPanel.setVerticalGroup(gl_numberPalletPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_numberPalletPanel.createSequentialGroup()
						.addComponent(numberPalletTextField, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(buttonPalletPannel)));
		numberPalletPanel.setLayout(gl_numberPalletPanel);
		rightPanel.setLayout(null);
		rightPanel.add(productionDateLabel);
		rightPanel.add(productionDateChooser);
		rightPanel.add(productionRadioButton);
		rightPanel.add(quantityItemsInBoxLabel);
		rightPanel.add(quantityItemsInBoxComboBox);
		rightPanel.add(shelfLifeLabel);
		rightPanel.add(shelfLifeTextField);
		rightPanel.add(boxNumberLabel);
		rightPanel.add(boxNumberTextField);
		rightPanel.add(expirationDateLabel);
		rightPanel.add(expirationDateChooser);
		rightPanel.add(quantityBoxesLabel);
		rightPanel.add(quantityBoxesTextField);
		rightPanel.add(packagingDateLabel);
		rightPanel.add(lotNumberLabel);
		rightPanel.add(lotNumberTextField);
		rightPanel.add(outputDateLabel);
		rightPanel.add(outputDateChooser);
		rightPanel.add(outputRadioButton);
		rightPanel.add(packagingDateChooser);
		rightPanel.add(packagingDateRadioButton);
		rightPanel.add(weightBoxLabel);
		rightPanel.add(weightBoxTextField);
		rightPanel.add(weightFilmLable);
		rightPanel.add(weightFilmTextField);
		rightPanel.add(nameItemLabel);
		rightPanel.add(nameItemComboBox);
		rightPanel.add(weightTextField);
		rightPanel.add(netMassTextField);
		rightPanel.add(numberPalletPanel);
		rightPanel.add(grossMassTextField);

		JLabel labelNetMass = new JLabel("\u041C\u0430\u0441\u0441\u0430 \u041D\u0415\u0422\u0422\u041E:");
		labelNetMass.setBounds(79, 55, 77, 14);
		rightPanel.add(labelNetMass);

		JLabel labelGrossMass = new JLabel("\u041C\u0430\u0441\u0441\u0430 \u0411\u0420\u0423\u0422\u0422\u041E:");
		labelGrossMass.setBounds(79, 80, 77, 14);
		rightPanel.add(labelGrossMass);

		stabilizationTextField = new JTextField();
		stabilizationTextField.setBounds(10, 11, 56, 37);
		rightPanel.add(stabilizationTextField);
		stabilizationTextField.setText("S");
		stabilizationTextField.setFont(new Font("Verdana", Font.BOLD, 32));
		stabilizationTextField.setHorizontalAlignment(SwingConstants.CENTER);
		stabilizationTextField.setColumns(10);

		autoWeightCheckBox = new JCheckBox("авто.");
		autoWeightCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		autoWeightCheckBox.setBounds(10, 51, 56, 22);
		autoWeightCheckBox.setEnabled(false);
		rightPanel.add(autoWeightCheckBox);
		
	

		contentPane.setLayout(layout);

		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (counter <= 0) {
					qDB.delPallet(DataFields.getNumberPalletString());
					unlockPanel();
				}
				System.exit(0);
			}
		});
		setIconImage(new ImageIcon("img\\mich.png").getImage());

		setJMenuBar(menuBar);
		setPreferredSize(new Dimension(900, 480));
		setResizable(false);
		pack();
		setLocationRelativeTo(null);

		if (id.getWeigherType().equals("CASADH20")) {
			weigher = new CASADH20();
			
		}else if (id.getWeigherType().equals("CASADH05")) {
			weigher = new CASADH05();
		}
		else if (id.getWeigherType().equals("Etalon")) {
			weigher = new Etalon();
		} 
		
		loop();

	}

	protected JTable createTable(TableModel model) {
		return new JTable(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == actionRunWeight) {
			weigher.toWeigh();
		}

		if (e.getSource() == reportTime) {
			FrameReports fr;
			try {
				fr = new FrameReports();
				fr.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource() == specificationButton) {
			try {
				new GenerateSpecification(qDB.specificationReport(numberPalletTextField.getText()),"Specification").generateBlank();
			} catch (ClassNotFoundException e1) {
				message("Внимание №5", "Ошибка");
				e1.printStackTrace();
			} catch (SQLException e1) {
				message("Ошибка связи с базой данных", "Ошибка SQL");
				e1.printStackTrace();
			} catch (ParseException e1) {
				message("Внимание №4", "Ошибка");
				e1.printStackTrace();
			}
		}

		if (e.getSource() == palletListButton) {
			try {
				new GenerateSpecification(qDB.getInfoPallet(numberPalletTextField.getText()),"PalletList").generateBlank();
			} catch (SQLException e1) {
				message("Ошибка связи с базой данных", "Ошибка SQL");
				e1.printStackTrace();
			}
		}

		if (e.getSource() == newPalletButton) {
			if (getWeightButton.isEnabled() == false) {
				try {
					if ((nameItemComboBox.getSelectedIndex() < 0)
							|| expirationDateChooser.getDate().equals(null)
							|| packagingDateChooser.getDate().equals(null)
							|| productionDateChooser.getDate().equals(null)
							|| outputDateChooser.getDate().equals(null)) {
						message("Не все поля заполнены! Проверьте выпадающие поля!", "Внимание! #1");
					} else {

						DataFields.setNameItem(nameItemComboBox.getSelectedItem().toString());
						DataFields.setQuantityItemsInBox(quantityItemsInBoxComboBox.getSelectedIndex() + 1);

						DataFields.setExpirationDate(expirationDateChooser.getDate());
						DataFields.setPackagingDate(packagingDateChooser.getDate());
						DataFields.setOutputDate(outputDateChooser.getDate());
						DataFields.setProductionDate(productionDateChooser.getDate());

						DataFields.setLotNumber(lotNumberTextField.getText());
						DataFields.setQuantityBoxes(quantityBoxesTextField.getText());
						DataFields.setBoxNumber(boxNumberTextField.getText());
						DataFields.setWeightBox(weightBoxTextField.getText());
						DataFields.setShelfLife(shelfLifeTextField.getText());
						DataFields.setWeightFilm(weightFilmTextField.getText());

						if (outputRadioButton.isSelected()) {
							DataFields.setRadioButtonChoser(0);
						} else if (productionRadioButton.isSelected()) {
							DataFields.setRadioButtonChoser(1);
						} else if (packagingDateRadioButton.isSelected()) {
							DataFields.setRadioButtonChoser(2);
						}

						numberPalletTextField.setText(qDB.getNumLastInseredPallet());
						DataFields.setNumberPallet(numberPalletTextField.getText());

						counter = 0;
						printLable.setEnabled(true);
						printShippingLables.setEnabled(true);
						actionDel.setEnabled(true);
						disableOrEnableFormComponent(false);
						newPalletButton.setText("Закрыть паллет");
						autoWeightCheckBox.setEnabled(true);
						getWeightButton.setEnabled(true);
						getWeightButton.requestFocusInWindow();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					message("Не удалось связаться с базой данных!", "Внимание! #3");
				} catch (NullPointerException n) {
					n.printStackTrace();
					message("Не все поля заполнены, либо введены неверные значения!", "Внимание! #2");
				}

			} else {
				Toolkit.getDefaultToolkit().beep();
				int dialogResult = JOptionPane.showOptionDialog(null, "Вы действительно хотите закрыть паллет!?",
						"Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						new Object[] { "Да", "Нет" }, "Да");

				if (dialogResult == JOptionPane.YES_OPTION) {
					shelfLifeTextField.setText("");
					boxNumberTextField.setText("1");
					printLable.setEnabled(false);
					printShippingLables.setEnabled(false);
					actionDel.setEnabled(false);
					if (counter <= 0) {
						qDB.delPallet(DataFields.getNumberPalletString());
						unlockPanel();
					} else {
						unlockPanel();
					}
				}

			}
		}

		if (e.getSource() == getWeightButton) {
			pressedGetWeightButton = true; // запаминаем, что кнопка нажималась
			getWeightButton.requestFocusInWindow();
		}

		if (e.getSource() == buttonPalletPannel) {
			palletOpen();
			getWeightButton.requestFocusInWindow();
		}

		if (e.getSource() == autoWeightCheckBox) {
			getWeightButton.requestFocusInWindow();
		}

		if (e.getSource() == numberPalletTextField) {
			buttonPalletPannel.requestFocusInWindow();
		}

		if (e.getSource() == printLable) {
			if (numberPalletTextField.getText().trim().length() > 0
					& boxNumberTextField.getText().trim().length() > 0) {
				if (counter % DataFields.getQuantityItemsInBoxInt() != 0) {
					try {
						// System.out.println(DataFields.getNumberPalletString() +
						// DataFields.getBoxNumberString());
						qDB.getWeightsInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());
						qDB.getWeightsBoxAndFilmsWithoutWeights(DataFields.getNumberPalletString(),
								DataFields.getBoxNumberString());
						qDB.getGrossMassInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());

						new ChangeZplCode("labels\\" + DataFields.getShippingLabels() + ".prn").printToZebra();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else {
				message("Проверьте номер паллетного листа", "Нет открытого паллета");
			}
		}

		if (e.getSource() == printShippingLables) {
			if (numberPalletTextField.getText().trim().length() > 0) {
				try {
					qDB.getWeightsInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());
					qDB.getWeightsBoxAndFilmsWithoutWeights(DataFields.getNumberPalletString(),
							DataFields.getBoxNumberString());
					qDB.getGrossMassInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());

					new ChangeZplCode("labels\\" + DataFields.getShippingLabels() + ".prn").printToZebra();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else {
				message("Проверьте номер паллетного листа", "Нет открытого паллета");
			}
		}

		if (e.getSource() == openLastPallet) {
			try {
				numberPalletTextField.setText(qDB.getNumLastPallet());
				palletOpen();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource() == actionDel) {
			if (numberPalletTextField.getText().trim().length() > 0) {
				Toolkit.getDefaultToolkit().beep();
				int dialogResult = JOptionPane.showOptionDialog(null,
						"Вы действительно хотите удалить последнее взвешивание!?", "Внимание!",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Да", "Нет" },
						"Да");

				if (dialogResult == JOptionPane.YES_OPTION) {

					if (counter > 0) {
						qDB.delLastWeight(DataFields.getNumberPalletString());

						if (counter % DataFields.getQuantityItemsInBoxInt() == 0) {
							DataFields.setBoxNumber(DataFields.getBoxNumberInt() - 1);
							boxNumberTextField.setText(DataFields.getBoxNumberString());

						}

						counter--;
					} else {
						qDB.delPallet(DataFields.getNumberPalletString());
						unlockPanel();

						message("Паллет был удален в сввязи с отстутствием записей!", "Удаление последней записи");
					}

					try {
						pl.setPalletList(qDB.getPalletList(DataFields.getNumberPalletString()));
					} catch (SQLException | ParseException e1) {
						e1.printStackTrace();
					}
					refrashMainTableModel();

				}
			} else {
				message("Поле \"Номер паллета:\" должно быть заполненно, \nа табличные данные активны!\n", "Ошибка!");
			}
		}

		if (e.getSource() == outputRadioButton) {
			shelfLifeConrol();
		}

		if (e.getSource() == productionRadioButton) {
			shelfLifeConrol();
		}

		if (e.getSource() == packagingDateRadioButton) {
			shelfLifeConrol();
		}

		if (e.getSource() == exitItem) {
			setVisible(false);
			System.exit(0);
		}

	}

	@Override
	public void focusGained(FocusEvent e2) {
		if (e2.getSource() == specificationButton & getWeightButton.isEnabled()) {
			getWeightButton.requestFocusInWindow();
		}
	}

	@Override
	public void focusLost(FocusEvent e2) {

		if (e2.getSource() == lotNumberTextField) {
			DataFields.setLotNumber(lotNumberTextField.getText());
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		check();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		check();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		check();
	}

	public void check() {

		if (lotNumberTextField.getText().equals("") || quantityBoxesTextField.getText().equals("")
				|| boxNumberTextField.getText().equals("") || weightBoxTextField.getText().equals("")
				|| shelfLifeTextField.getText().equals("") || weightFilmTextField.getText().equals("")) {

			newPalletButton.setEnabled(false);
			if (numberPalletTextField.isEnabled() == false) {
				getWeightButton.setEnabled(false);
				autoWeightCheckBox.setEnabled(false);
			}

		} else {
			newPalletButton.setEnabled(true);
			if (numberPalletTextField.isEnabled() == false) {
				getWeightButton.setEnabled(true);
				autoWeightCheckBox.setEnabled(true);
			}
		}

	}

	public void listeners() {

		exitItem.addActionListener(this);
		actionRunWeight.addActionListener(this);
		openLastPallet.addActionListener(this);
		
		printLable.addActionListener(this);
		printShippingLables.addActionListener(this);
		actionDel.addActionListener(this);
		reportTime.addActionListener(this);
		specificationButton.addActionListener(this);
		palletListButton.addActionListener(this);
		newPalletButton.addActionListener(this);
		getWeightButton.addActionListener(this);
		numberPalletTextField.addActionListener(this);
		outputRadioButton.addActionListener(this);
		productionRadioButton.addActionListener(this);
		packagingDateRadioButton.addActionListener(this);
		buttonPalletPannel.addActionListener(this);
		autoWeightCheckBox.addActionListener(this);

		specificationButton.addFocusListener(this);
		lotNumberTextField.addFocusListener(this);

		lotNumberTextField.getDocument().addDocumentListener(this);
		quantityBoxesTextField.getDocument().addDocumentListener(this);
		boxNumberTextField.getDocument().addDocumentListener(this);
		weightBoxTextField.getDocument().addDocumentListener(this);
		shelfLifeTextField.getDocument().addDocumentListener(this);
		weightFilmTextField.getDocument().addDocumentListener(this);

		outputDateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					shelfLifeConrol();
				}
			}
		});

		productionDateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					shelfLifeConrol();
				}
			}
		});

		packagingDateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					shelfLifeConrol();
				}
			}
		});
		
		ListSelectionListener lsl = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {
					return;
				}

				int row = table.getSelectionModel().getLeadSelectionIndex();
				int col = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
				if (row>0) {
				System.out.println(table.getValueAt(row, col));}
			}

		};

		table.getSelectionModel().addListSelectionListener(lsl);
		table.getColumnModel().getSelectionModel().addListSelectionListener(lsl);

		DataFields.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("stab".equals(e.getPropertyName())) {

					if (e.getNewValue().equals(true)) {

						if (DataFields.getWeightInt() > 0 & getWeightButton.isEnabled() & autoWeightCheckBox.isSelected()
								 & nextPrintMark & printMark == false) {

							nextPrintMark = false;
							pressedGetWeightButton = false;
							printMark = true;
							
						}
						
						
						
						stabilizationTextField.setText("Ok");
						stabilizationTextField.setBackground(Color.GREEN);
					} else {
						stabilizationTextField.setText("Er");
						stabilizationTextField.setBackground(Color.RED);
					}

				}

				if ("weight".equals(e.getPropertyName())) {

					weightTextField.setText(DataFields.getWeightString());

					if (DataFields.getWeightInt() <= 0 & nextPrintMark == false) {
						nextPrintMark = true; // печать после обнуления весов
					}
					



				}
				System.out.println("Property \"" + e.getPropertyName() + "\" has new value: " + e.getNewValue());
			}
		});

		/*
		 * 
		 * calculation expiration Date
		 */

		DocumentListener dl = new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				ch();
			}

			public void insertUpdate(DocumentEvent e) {
				ch();
			}

			public void removeUpdate(DocumentEvent e) {
				ch();
			}

			public void ch() {

				shelfLifeConrol();
			}
		};
		shelfLifeTextField.getDocument().addDocumentListener(dl);
	}

	private void shelfLifeConrol() {

		try {
			if (shelfLifeTextField.getText().trim().length() > 0) {
				int k = Integer.valueOf(shelfLifeTextField.getText());
				Calendar c = new GregorianCalendar();

				if (outputRadioButton.isSelected()) {
					c.setTime(outputDateChooser.getDate());
				} else if (productionRadioButton.isSelected()) {
					c.setTime(productionDateChooser.getDate());
				} else if (packagingDateRadioButton.isSelected()) {
					c.setTime(packagingDateChooser.getDate());
				}

				c.add(Calendar.DAY_OF_MONTH, k);
				expirationDateChooser.setDate(c.getTime());

			}
		} catch (NumberFormatException n) {
			message("Введите срок годности", "Ошибка!");
		} catch (NullPointerException n) {
			message("Не верный формат даты", "Ошибка!");
		}

	}

	private void palletOpen() {
		if (numberPalletTextField.getText().trim().length() > 0) {

			try {
				// DataFields.setWeight(weightTextField.getText());
				DataFields.setNumberPallet(numberPalletTextField.getText());
				qDB.getPalletData(numberPalletTextField.getText());

				pl.setPalletList(qDB.getPalletList(numberPalletTextField.getText()));

				nameItemComboBox.setSelectedItem(DataFields.getNameItem());
				
				quantityItemsInBoxComboBox.setSelectedIndex(DataFields.getQuantityItemsInBoxInt() - 1);

				counter = pl.getSize();
				boxNumberTextField.setText(DataFields.getBoxNumberString());

				if (counter % DataFields.getQuantityItemsInBoxInt() == 0) {
					DataFields.setBoxNumber(DataFields.getBoxNumberInt() + 1);
					boxNumberTextField.setText(DataFields.getBoxNumberString());
				}
				expirationDateChooser.setDate(DataFields.getExpirationDate());
				packagingDateChooser.setDate(DataFields.getPackagingDate());
				productionDateChooser.setDate(DataFields.getProductionDate());
				outputDateChooser.setDate(DataFields.getOutputDate());

				lotNumberTextField.setText(DataFields.getLotNumberString());
				quantityBoxesTextField.setText(DataFields.getQuantityBoxesString());

				weightBoxTextField.setText(DataFields.getWeightBoxString());
				shelfLifeTextField.setText(DataFields.getShelfLifeString());
				weightFilmTextField.setText(DataFields.getWeightFilmString());

				switch (DataFields.getRadioButtonChoser()) {
				case 0:
					outputRadioButton.setSelected(true);
					break;
				case 1:
					productionRadioButton.setSelected(true);
					break;
				case 2:
					packagingDateRadioButton.setSelected(true);
					break;
				}

				refrashMainTableModel();

				printLable.setEnabled(true);
				printShippingLables.setEnabled(true);
				actionDel.setEnabled(true);
				disableOrEnableFormComponent(false);
				newPalletButton.setText("Закрыть паллет");
				autoWeightCheckBox.setEnabled(true);
				getWeightButton.setEnabled(true);
				getWeightButton.requestFocusInWindow();

			} catch (SQLException | ParseException e1) {
				message("Не удалось выполнить запрос!", "Ошибка!");
				e1.printStackTrace();
			}

		} else {
			message("Поле \"Номер паллета:\" должно быть заполненно,\nа табличные данные активны!",
					"Нет активного паллета!");
		}
	}

	public String removeLastChar(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
		return s.substring(0, s.length() - 1);
	}

	private void disableOrEnableFormComponent(boolean b) {

		openLastPallet.setEnabled(b);
		buttonPalletPannel.setEnabled(b);
		// lotNumberLabel.setEnabled(b);
		// numberCookingLabel.setEnabled(b);
		quantityBoxesLabel.setEnabled(b);
		quantityItemsInBoxLabel.setEnabled(b);
		boxNumberLabel.setEnabled(b);
		// productionDateLabel.setEnabled(b);
		// outputDateLabel.setEnabled(b);
		// packagingDateLabel.setEnabled(b);
		shelfLifeLabel.setEnabled(b);
		expirationDateLabel.setEnabled(b);
		weightBoxLabel.setEnabled(b);
		weightFilmLable.setEnabled(b);

		nameItemLabel.setEnabled(b);

		// weightTextField.setEnabled(b);
		numberPalletTextField.setEnabled(b);


		// lotNumberTextField.setEnabled(b);
		shelfLifeTextField.setEnabled(b);
		boxNumberTextField.setEnabled(b);
		quantityBoxesTextField.setEnabled(b);
		weightFilmTextField.setEnabled(b);
		weightBoxTextField.setEnabled(b);

		nameItemComboBox.setEnabled(b);
		// numberCookingComboBox.setEnabled(b);
		quantityItemsInBoxComboBox.setEnabled(b);

		// expirationDateChooser.setEnabled(b);
		// packagingDateChooser.setEnabled(b);
		// outputDateChooser.setEnabled(b);
		// productionDateChooser.setEnabled(b);

		outputRadioButton.setEnabled(b);
		productionRadioButton.setEnabled(b);
		packagingDateRadioButton.setEnabled(b);

	}

	boolean isValidDate(SimpleDateFormat pattern, Date date) {
		try {
			pattern.parse(String.valueOf(date));
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	void disableRightPanel() {
		for (Component cp : rightPanel.getComponents()) {
			System.out.println(cp.getName());
			cp.setEnabled(false);
		}
	}

	void unlockPanel() {

		pl.clearPalletList();
		DataFields.setWeightFilm(0);
		refrashMainTableModel();
		disableOrEnableFormComponent(true);
		newPalletButton.setText("Новый паллет");
		getWeightButton.setEnabled(false);
		autoWeightCheckBox.setSelected(false);
		autoWeightCheckBox.setEnabled(false);

	}

	void printingZPL() {

		printMark = false;

		DataFields.setPackagingDate(packagingDateChooser.getDate());
		DataFields.setOutputDate(outputDateChooser.getDate());
		DataFields.setProductionDate(productionDateChooser.getDate());
		DataFields.setExpirationDate(expirationDateChooser.getDate());

		try {
			qDB.incertWeightToTheDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// System.out.println("До " + counter);
		counter++;

		pl.addLine(DataFields.getBoxNumberString(), DataFields.getWeightString(), DataFields.getLotNumberString(),
				DataFields.getProductionDateDMY());
		refrashMainTableModel();

		new ChangeZplCode("labels\\" + DataFields.getConsumerLabels() + ".prn").printToZebra();

		if (counter % DataFields.getQuantityItemsInBoxInt() == 0) {
			try {
				qDB.getWeightsInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());
				qDB.getWeightsBoxAndFilmsWithoutWeights(DataFields.getNumberPalletString(),
						DataFields.getBoxNumberString());
				qDB.getGrossMassInBox(DataFields.getNumberPalletString(), DataFields.getBoxNumberString());

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// количество печатаемых этикеток

			for (int i = 0; i < id.getQuantityPrintShippingLabel(); i++) {
				new ChangeZplCode("labels\\" + DataFields.getShippingLabels() + ".prn").printToZebra();
			}
			DataFields.setBoxNumber(DataFields.getBoxNumberInt() + 1);
			boxNumberTextField.setText(DataFields.getBoxNumberString());
			getWeightButton.requestFocusInWindow();
		}

		if (counter % (DataFields.getQuantityBoxesInt() * DataFields.getQuantityItemsInBoxInt()) == 0) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Внимание! паллет сложен!", "Внимание!",
					JOptionPane.INFORMATION_MESSAGE);
			newPalletButton.requestFocusInWindow();
		}

		getNetMass();
		// System.out.println(" и После " + counter);
	}

	void refrashMainTableModel() {
		table.setModel(model);
		table.revalidate();
		table.changeSelection(model.getRowCount() - 1, 0, false, false);
		table.repaint();
		table.changeSelection(model.getRowCount() - 1, 0, false, false);
		table.repaint();
	}

	void getNetMass() {
		int netMass = 0;
		for (TableMain a : pl.getPalletList()) {

			netMass += Integer.parseInt(a.getWeight());
		}
		try {
			
			for (PalletListData a : qDB.getInfoPallet(DataFields.getNumberPalletString())) {

				netMassTextField.setText(String.valueOf(a.getSumWeightNt()));
				grossMassTextField.setText(String.valueOf(a.getSumWeightNt()+a.getCountWaights()*a.getWeightFilm()+a.getWeightBox()));;
			}
			
			
			
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		grossMassTextField.setText(String.valueOf(netMass + DataFields.getWeightBoxInt() * counter / DataFields.getQuantityItemsInBoxInt()
						+ DataFields.getWeightFilmInt() * counter));
	}

	void loop() {

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					weigher.toWeigh();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (printMark) {
						printingZPL();
					}
					
					if (DataFields.getWeightInt() > 0 & !autoWeightCheckBox.isSelected() & getWeightButton.isEnabled()
							& pressedGetWeightButton & printMark == false & DataFields.getStabCondition()) {
						
						nextPrintMark = false;
						pressedGetWeightButton = false;
						printMark = true;
					}
				}
			}
		}).start();
	}

	private static void scrollToNewRow(JTable table, int row, int col) {
		if (!(table.getParent() instanceof JViewport)) {
			return;
		}
		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(row, col, true);
		Point pt = viewport.getViewPosition();
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);

		viewport.scrollRectToVisible(rect);
	}

	private void scrollDown(JScrollPane scrollPane) {
		JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

		int currentScrollValue = verticalBar.getValue();
		int previousScrollValue = -1;

		while (currentScrollValue != previousScrollValue) {
			// Scroll down a bit
			int downDirection = 1;
			int amountToScroll = verticalBar.getUnitIncrement(downDirection);
			verticalBar.setValue(currentScrollValue + amountToScroll);

			previousScrollValue = currentScrollValue;
			currentScrollValue = verticalBar.getValue();
		}
	}

	void message(String messageText, String title) {
		new Thread(new Runnable() {
			public void run() {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, messageText, title, JOptionPane.ERROR_MESSAGE);
			}
		}).start();
	}
}