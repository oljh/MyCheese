package myCheese.db;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import myCheese.io.DataFields;
import myCheese.main.PalletList;
import myCheese.main.TableMain;
import myCheese.reports.TableReport;
import myCheese.reports.PalletListData;
import myCheese.reports.SpecificationPalletData;

public class QueriesToDB extends JoinToDB {
	String[] arrayAnsw;
	private final SimpleDateFormat dMy = new SimpleDateFormat("dd.MM.yyyy");
	private final SimpleDateFormat iso8601DateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	// private final SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");
	DataFields df = new DataFields();
	PalletList pl = new PalletList();

	public QueriesToDB() throws ClassNotFoundException, SQLException {
		st = connection.createStatement();
	}

	public String[] getItemsList() throws SQLException {
		List<String> list = new ArrayList<String>();
		rs = st.executeQuery("SELECT id_i, itemsName FROM items WHERE isEnable = 'true'");
		while (rs.next()) {
			list.add(rs.getString("itemsName"));
		}
		closeRsSt();
		return getCurrentArray(list);
	}

	public String[] getEmployeesList() throws SQLException {
		List<String> list = new ArrayList<String>();
		rs = st.executeQuery("SELECT employeesName FROM employees");
		while (rs.next()) {
			list.add(rs.getString("employeesName"));
		}
		closeRsSt();
		return getCurrentArray(list);
	}

	public String[] getCustomersList() throws SQLException {
		List<String> list = new ArrayList<String>();
		rs = st.executeQuery("SELECT customersName FROM customers");
		while (rs.next()) {
			list.add(rs.getString("customersName"));
		}
		closeRsSt();
		return getCurrentArray(list);
	}

	public void getPalletData(String np) throws SQLException {
		rs = st.executeQuery(
				"SELECT * FROM pallets LEFT JOIN items ON pallets.items_key = items.id_i  LEFT JOIN customers ON pallets.customers_key = customers.id_c LEFT JOIN employees ON pallets.employees_key = employees.id_e WHERE id_p ='"
						+ np + "'");

		// df.setNumberPallet(rs.getString("id_p"));
		DataFields.setNameItem(rs.getString("itemsName"));

		DataFields.setQuantityBoxes(rs.getString("quantityBoxes"));
		DataFields.setQuantityItemsInBox(rs.getInt("quantityItemsInBox"));
		DataFields.setWeightFilm(rs.getString("weightFilm"));
		DataFields.setWeightBox(rs.getString("weightBox"));
		DataFields.setRadioButtonChoser(rs.getString("radioButtonChoser"));
		DataFields.setShippingLabels(rs.getString("shippingLabels"));
		DataFields.setConsumerLabels(rs.getString("consumerLabels"));
		DataFields.setCustomer(rs.getString("customersName"));
		DataFields.setEmployee(rs.getString("employeesName"));

		rs = st.executeQuery("SELECT * FROM weights WHERE pallets_key ='" + np + "' ORDER BY id_w DESC LIMIT 1");

		DataFields.setLotNumber(rs.getString("lotNumber"));
		DataFields.setBoxNumber(rs.getString("boxNumber"));
		DataFields.setShelfLife(rs.getString("shelfLife"));

		DataFields.setProductionDate(rs.getString("productionDate"));
		DataFields.setPackagingDate(rs.getString("packagingDate"));
		DataFields.setOutputDate(rs.getString("outputDate"));
		DataFields.setExpirationDate(rs.getString("expirationDate"));

		closeRsSt();

	}

	public ArrayList<TableMain> getPalletList(String np) throws SQLException, ParseException {
		ArrayList<TableMain> rList = new ArrayList<TableMain>();
		rs = st.executeQuery("SELECT * FROM weights WHERE pallets_key ='" + np + "'");
		while (rs.next()) {
			rList.add(new TableMain(rs.getString("boxNumber"), rs.getString("weight"), rs.getString("lotNumber"),
					dMy.format(iso8601DateTime.parse(rs.getString("productionDate")))));
		}
		closeRsSt();
		return rList;
	}

	public ArrayList<TableReport> getReportList(String dateS, String datePo) throws SQLException, ParseException {
		ArrayList<TableReport> rList = new ArrayList<TableReport>();
		rs = st.executeQuery(
				"SELECT id_p, COUNT(weight) AS count, itemsName, SUM(weight) AS SumWeight,(SUM(weight+weightFilm)+weightBox*(SELECT COUNT(*) FROM (SELECT SUM(weight) AS SumWeightNt FROM weights WHERE pallets_key = id_p GROUP BY boxNumber))) AS SumWeightBr, creatingDate FROM weights LEFT JOIN pallets ON pallets_key = id_p LEFT JOIN items ON items_key = id_i WHERE creatingDate BETWEEN '"
						+ dateS + " 00:00:00' AND '" + datePo + " 23:59:59' GROUP BY id_p");
		while (rs.next()) {
			rList.add(new TableReport(rs.getString("id_p"), rs.getString("itemsName"), rs.getString("count"),
					rs.getString("SumWeight"), rs.getString("SumWeightBr"),
					dMy.format(iso8601DateTime.parse(rs.getString("creatingDate")))));
		}
		closeRsSt();
		return rList;
	}

	public ArrayList<SpecificationPalletData> specificationReport(String np)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<SpecificationPalletData> rList = new ArrayList<SpecificationPalletData>();
		rs = st.executeQuery(
				"SELECT pallets_key, itemsLabelsName, boxNumber, COUNT(weight) AS countWaights, (GROUP_CONCAT(weight, '; ')) AS GroupedWaights, SUM(weight) AS SumWeightNt, (SUM(weight+weightFilm)+weightBox) AS SumWeightBr, GROUP_CONCAT(DISTINCT lotNumber) AS LotNumber, outputDate, productionDate, expirationDate FROM  'pallets' LEFT JOIN 'weights' ON pallets_key = id_p LEFT JOIN 'items' ON items_key = id_i WHERE id_p ='"
						+ np + "' GROUP BY boxNumber");
		while (rs.next()) {
			rList.add(new SpecificationPalletData(rs.getString("pallets_key"), rs.getString("itemsLabelsName"),
					rs.getString("boxNumber"), rs.getString("countWaights"), rs.getString("GroupedWaights"),
					rs.getInt("SumWeightNt"), rs.getInt("SumWeightBr"), rs.getString("LotNumber"),
					dMy.format(iso8601DateTime.parse(rs.getString("outputDate"))),
					dMy.format(iso8601DateTime.parse(rs.getString("productionDate"))),
					dMy.format(iso8601DateTime.parse(rs.getString("expirationDate")))));
		}
		closeRsSt();
		return rList;
	}
	


	public String getNumLastInseredPallet() throws SQLException {
		rs = st.executeQuery("SELECT id_i,consumerLabels,shippingLabels FROM items WHERE itemsName ='"
				+ DataFields.getNameItem() + "';");
		String i = rs.getString("id_i");
		DataFields.setConsumerLabels(rs.getString("consumerLabels"));
		DataFields.setShippingLabels(rs.getString("shippingLabels"));
		st.execute(
				"INSERT INTO pallets (items_key,quantityBoxes,quantityItemsInBox,weightBox,weightFilm,employees_key,customers_key,radioButtonChoser,creatingDate) VALUES ('"
						+ i + "','" + DataFields.getQuantityBoxesString() + "','"
						+ DataFields.getQuantityItemsInBoxInt() + "','" + DataFields.getWeightBoxString() + "','"
						+ DataFields.getWeightFilmString() + "',(SELECT id_e FROM employees WHERE employeesName ='"
						+ DataFields.getResponsible() + "'),(SELECT id_c FROM customers WHERE customersName ='"
						+ DataFields.getCustomer() + "'),'" + DataFields.getRadioButtonChoser() + "','"
						+ iso8601DateTime.format(new Date()) + "');");
		rs = st.executeQuery("SELECT id_p FROM pallets ORDER BY id_p DESC LIMIT 1");
		String p = rs.getString("id_p");
		closeRsSt();
		return p;
	}

	public void getWeightsInBox(String numberPallet, String numberBox) throws SQLException {
		rs = st.executeQuery("SELECT SUM(weight) AS SumWeight, COUNT(*) AS CountUnit FROM weights WHERE pallets_key = '"
				+ numberPallet + "' AND boxNumber = '" + numberBox + "' GROUP BY boxNumber");
		DataFields.setNetMass(rs.getString("SumWeight"));
		DataFields.setCountUnit(rs.getString("CountUnit"));
		closeRsSt();
	}

	public void getWeightsBoxAndFilmsWithoutWeights(String numberPallet, String numberBox) throws SQLException {
		rs = st.executeQuery(
				"SELECT (SUM(weightFilm)+weightBox*(SELECT COUNT(*) FROM (SELECT SUM(weight) AS SumWeightNt FROM weights WHERE pallets_key = '"
						+ numberPallet + "' AND boxNumber = '" + numberBox
						+ "' GROUP BY boxNumber))) AS SumWeightBoxAndFilms FROM weights LEFT JOIN pallets ON pallets_key = id_p WHERE id_p = '"
						+ numberPallet + "' AND boxNumber = '" + numberBox + "' GROUP BY pallets_key");
		DataFields.setWeightBoxAndFilms(rs.getString("SumWeightBoxAndFilms"));
		closeRsSt();
	}

	public void getGrossMassInBox(String numberPallet, String numberBox) throws SQLException {
		rs = st.executeQuery(
				"SELECT (SUM(weight+weightFilm)+weightBox*(SELECT COUNT(*) FROM (SELECT SUM(weight) AS SumWeightNt FROM weights WHERE pallets_key = '"
						+ numberPallet + "' AND boxNumber = '" + numberBox
						+ "' GROUP BY boxNumber))) AS SumWeightBr FROM weights LEFT JOIN pallets ON pallets_key = id_p WHERE id_p = '"
						+ numberPallet + "' AND boxNumber = '" + numberBox + "' GROUP BY pallets_key");
		DataFields.setGrossMass(rs.getString("SumWeightBr"));
		closeRsSt();
	}

	public ArrayList<PalletListData> getInfoPallet(String numberPallet) throws SQLException {
		ArrayList<PalletListData> rList = new ArrayList<PalletListData>();
		rs = st.executeQuery(
				"SELECT\r\n" + 
				"    COUNT(weight) AS countItems,\r\n" + 
				"    pallets_key,\r\n" + 
				"    itemsName,\r\n" + 
				"    itemsLabelsName,\r\n" + 
				"    lotNumber,\r\n" + 
				"    weightBox,\r\n" + 
				"    weightFilm, \r\n" + 
				"    (SELECT SUM(weight) FROM weights WHERE pallets_key = '"+ numberPallet +"') AS CommonWeightNt,\r\n" + 
				"    (SELECT SUM(weight+weightFilm)+ weightBox*COUNT(DISTINCT boxNumber) FROM weights WHERE pallets_key = '"+ numberPallet +"') AS CommonWeightBr,\r\n" + 
				"    SUM(weight) AS SumWeightNt,\r\n" + 
				"    (SUM(weight+weightFilm)+weightBox*COUNT(DISTINCT boxNumber)) AS SumWeightBr,\r\n" + 
				"    COUNT(weight) AS countWaights,\r\n" + 
				"    GROUP_CONCAT(DISTINCT outputDate) AS GroupedOutputDate,\r\n" + 
				"    GROUP_CONCAT(DISTINCT productionDate) AS GroupedProductionDate,\r\n" + 
				"    GROUP_CONCAT(DISTINCT expirationDate) AS GroupedExpirationDate\r\n" + 
				"FROM 'pallets' LEFT JOIN 'weights' ON pallets_key = id_p LEFT JOIN 'items' ON items_key = id_i  WHERE id_p ='"+ numberPallet +"' GROUP BY lotNumber");
		while (rs.next()) {
			try {
				rList.add(new PalletListData(rs.getInt("countItems"), rs.getInt("pallets_key"),
						rs.getString("itemsName"), rs.getString("itemsLabelsName"), rs.getString("LotNumber"),
						rs.getInt("weightBox"), rs.getInt("weightFilm"), rs.getInt("CommonWeightNt"), rs.getInt("CommonWeightBr"),
						rs.getInt("SumWeightNt"), rs.getInt("SumWeightBr"), rs.getInt("countWaights"),
						dMy.format(iso8601DateTime.parse(rs.getString("GroupedOutputDate"))),
						dMy.format(iso8601DateTime.parse(rs.getString("GroupedProductionDate"))),
						dMy.format(iso8601DateTime.parse(rs.getString("GroupedExpirationDate")))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		closeRsSt();
		return rList;
	}
	
	public String getNumLastPallet() throws SQLException {
		rs = st.executeQuery("SELECT id_p FROM pallets ORDER BY id_p DESC LIMIT 1");
		String p = rs.getString("id_p");
		closeRsSt();
		return p;
	}

	public String getNamePallet(String np) throws SQLException {
		rs = st.executeQuery("SELECT * FROM 'items' LEFT JOIN 'pallets' ON id_i = items_key WHERE id_p = '" + np
				+ "' GROUP BY '" + np + "'");
		String p = rs.getString("itemsName");
		closeRsSt();
		return p;
	}

	public void delLastWeight(String numberPallet) {
		try {
			st.execute("DELETE FROM 'weights' WHERE id_w = (SELECT id_w FROM 'weights' WHERE pallets_key = '"
					+ numberPallet + "' ORDER BY id_w DESC LIMIT 1)");
			closeRsSt();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void delPallet(String numberPallet) {
		try {
			st.execute("DELETE FROM 'pallets' WHERE id_p ='" + numberPallet + "'");
			closeRsSt();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void incertWeightToTheDB() throws SQLException {
		st.execute(
				"INSERT INTO weights (weight,boxNumber,pallets_key,lotNumber,productionDate,outputDate,packagingDate,shelfLife,expirationDate) VALUES ('"
						+ DataFields.getWeightString() + "','" + DataFields.getBoxNumberString() + "','"
						+ DataFields.getNumberPalletString() + "','"
						+ DataFields.getLotNumberString() + "','" + DataFields.getProductionDateISO8601() + "','"
						+ DataFields.getOutputDateISO8601() + "','" + DataFields.getPackagingDateISO8601() + "','"
						+ DataFields.getShelfLifeString() + "','" + DataFields.getExpirationDateISO8601() + "')");
		closeRsSt();
	}

	// convert current List to Array
	String[] getCurrentArray(List<String> list) {
		if (list.size() != 0) {
			arrayAnsw = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arrayAnsw[i] = list.get(i);
			}
		}
		list.clear();
		return arrayAnsw;
	}

	void closeRsSt() {
		try {
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
