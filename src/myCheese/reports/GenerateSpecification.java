package myCheese.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myCheese.db.QueriesToDB;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class GenerateSpecification {
	private JasperReport jasperReport;
	private String outputFile;
	private FileOutputStream outputStream;
	private String jrxmlFile;
	List<?> list;
	private JasperPrint jasperPrint;
	private String jasperFile;
	
	public GenerateSpecification(List<?> list, String jrxmlFileName) {

		this.list = list;
		this.jrxmlFile = "res\\" + jrxmlFileName + ".jrxml";
		jasperFile = "res\\" + jrxmlFileName + ".jasper";
		
		outputFile = "res\\SpecificationBlank";

	}

	public void generateBlank() {
		try {
	
			File f = new File(jasperFile);
			
			if(!f.exists() && !f.isDirectory()) { 
				JasperCompileManager.compileReportToFile(jrxmlFile, jasperFile);
			}
			

			//	jasperReport = JasperCompileManager.compileReport(jrxmlFile);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, 
				    new HashMap<String, Object>(), new JRBeanCollectionDataSource(list));
			
					
		  	JasperPrintManager.printReport(jasperPrint, true);
			
			//exportPDF(jasperPrint);
			//exportXLSX(jasperPrint);
			
			System.out.println("File Generated");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	void exportPDF(JasperPrint jasperPrint) {
		long start = System.currentTimeMillis();
		JRPdfExporter exporter = new JRPdfExporter();
		//JRXlsxExporter exporter = new JRXlsxExporter();
		//exporter.setParameter(new JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		/* outputStream to create PDF */
		try {
			outputStream = new FileOutputStream(new File(outputFile + ".pdf"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		//exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
		//JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "arial.ttf");
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setCreatingBatchModeBookmarks(true);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}
	
	void exportXLSX(JasperPrint jasperPrint) {
		long start = System.currentTimeMillis();
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		/* outputStream to create XLSX */
		try {
			outputStream = new FileOutputStream(new File(outputFile + ".xlsx"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
		configuration.setDetectCellType(true);//Set configuration as you like it!!
		configuration.setCollapseRowSpan(false);
		exporter.setConfiguration(configuration);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.err.println("XLSX creation time : " + (System.currentTimeMillis() - start));
	}

}