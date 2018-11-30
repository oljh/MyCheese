package myCheese.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class JoinToDB {

	public Connection connection;
	public Statement st;
	public ResultSet rs;

	// Connecting to DataBase
	public JoinToDB() throws ClassNotFoundException {
		connection = null;
		Class.forName("org.sqlite.JDBC");
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:myCheeseDB.s3db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection to database \"myCheeseDB.s3db\" successfully!");
	}
	
	// Creating DB
	public void CreateDB() throws ClassNotFoundException, SQLException {
		st = connection.createStatement();
		st.execute("CREATE TABLE weights (" + 
				"    id_w           INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"    boxNumber      INTEGER," + 
				"    weight         INTEGER DEFAULT NULL," + 
				"    pallets_key    INTEGER DEFAULT NULL" + 
				"                           REFERENCES pallets (id_p) ON DELETE NO ACTION" + 
				"                                                     ON UPDATE CASCADE," + 
				"    numberCooking  INTEGER DEFAULT NULL," + 
				"    lotNumber      INTEGER," + 
				"    productionDate DATE    DEFAULT NULL," + 
				"    outputDate     DATE    DEFAULT NULL," + 
				"    packagingDate  DATE," + 
				"    shelfLife      INTEGER," + 
				"    expirationDate DATE" + 
				");");

		st.execute("CREATE TABLE items (" + 
				"    id_i            INTEGER       PRIMARY KEY AUTOINCREMENT," + 
				"    itemsName       VARCHAR (255) DEFAULT NULL," + 
				"    consumerLabels  VARCHAR (255) DEFAULT NULL," + 
				"    shippingLabels  VARCHAR (255) DEFAULT NULL," + 
				"    additionalIinfo TEXT," + 
				"    isEnable        BOOLEAN       DEFAULT true" + 
				");");

		st.execute("CREATE TABLE pallets (" + 
				"    id_p               INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"    items_key          INTEGER DEFAULT NULL" + 
				"                               REFERENCES items (id_i) ON DELETE NO ACTION" + 
				"                                                       ON UPDATE CASCADE," + 
				"    quantityBoxes      INTEGER DEFAULT NULL," + 
				"    quantityItemsInBox INTEGER DEFAULT NULL," + 
				"    weightBox          INTEGER DEFAULT NULL," + 
				"    weightFilm         INTEGER DEFAULT NULL," + 
				"    employees_key      INTEGER DEFAULT NULL" + 
				"                               REFERENCES employees (id_e) ON DELETE NO ACTION" + 
				"                                                           ON UPDATE CASCADE," + 
				"    customers_key      INTEGER DEFAULT NULL" + 
				"                               REFERENCES customers (id_c) ON DELETE NO ACTION" + 
				"                                                           ON UPDATE CASCADE," + 
				"    radioButtonChoser  INT     DEFAULT (0)," + 
				"    palletCondition    BOOLEAN DEFAULT true," + 
				"    creatingDate       DATE" + 
				");");

		st.execute("CREATE TABLE employees (" + 
				"    id_e          INTEGER       PRIMARY KEY AUTOINCREMENT," + 
				"    employeesName VARCHAR (255) DEFAULT NULL" + 
				");");

		st.execute("CREATE TABLE customers (" + 
				"    id_c          INTEGER       PRIMARY KEY AUTOINCREMENT," + 
				"    customersName VARCHAR (255) DEFAULT NULL" + 
				");");

	}
}
