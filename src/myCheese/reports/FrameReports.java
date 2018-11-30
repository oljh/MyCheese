package myCheese.reports;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import myCheese.db.QueriesToDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JCheckBox;

public class FrameReports extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QueriesToDB qDB;
	JComboBox<String> itemsComboBox;
	private JLabel r;
	private JDateChooser startDate;
	private JDateChooser endDate;
	private JLabel s;
	private JCheckBox NameCheckBox;
	private JCheckBox SumCheckBox;
	private JButton btnNewButton;
	//private final SimpleDateFormat dMy = new SimpleDateFormat("dd.MM.yyyy");
	private final SimpleDateFormat iso8601Date = new SimpleDateFormat("yyyy-MM-dd");
	//private final SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");

	public FrameReports() throws SQLException {

		setTitle("Отчёт");

		try {
			qDB = new QueriesToDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "За период:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(11, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE)));

		r = new JLabel("c:");

		startDate = new JDateChooser();

		endDate = new JDateChooser();

		s = new JLabel("по:");

		itemsComboBox = new JComboBox<String>(qDB.getItemsList()); // qDB.getItemsList()
		// itemsComboBox.setSelectedIndex(0);

		NameCheckBox = new JCheckBox("По наименованию:");

		SumCheckBox = new JCheckBox("Суммировать по видам");

		btnNewButton = new JButton("Подготовить");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
								gl_panel.createSequentialGroup().addComponent(SumCheckBox)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING,
										gl_panel.createSequentialGroup().addComponent(r)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(startDate, GroupLayout.PREFERRED_SIZE, 109,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(s, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(endDate,
														GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING,
										gl_panel.createSequentialGroup().addComponent(NameCheckBox)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(itemsComboBox, 0, 148, Short.MAX_VALUE)))
						.addGap(24)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel.createSequentialGroup().addContainerGap()
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addComponent(endDate, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(s)
												.addComponent(startDate, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(r))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(itemsComboBox, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(NameCheckBox))
										.addGap(5)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(SumCheckBox, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addContainerGap(29, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		setAlwaysOnTop(true);
		setResizable(false);
		setSize(315, 163);
		setLocationRelativeTo(null);

		listeners();

	}

	void listeners() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (startDate.getDate() != null & endDate.getDate() != null) {
					System.out.println(iso8601Date.format(startDate.getDate()));
					try {
						ModelReport ms = new ModelReport();
						ms.setLp(qDB.getReportList(iso8601Date.format(startDate.getDate()),
								iso8601Date.format(endDate.getDate())));
						FramePrintSpecification ftp = new FramePrintSpecification(ms, "", "");
						ftp.setVisible(true);
					} catch (SQLException | ParseException e1) {
						e1.printStackTrace();
					}
					if (SumCheckBox.isSelected() & NameCheckBox.isSelected()) {
						System.out.println("vibor 1");
					} else if (SumCheckBox.isSelected()) {
						System.out.println("vibor 2");
					} else if (NameCheckBox.isSelected()) {
						System.out.println("vibor 3");
					}
				}
			}
		});

	}

}
