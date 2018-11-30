package myCheese.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

public class ChangeZplCode {

	Doc doc;

	InputStream is;
	private String chData;

	

	public ChangeZplCode(String link) {
		try {
			is = new BufferedInputStream(new FileInputStream(link));
			byte[] content = new byte[is.available()];
			is.read(content);
			chData = new String(content, "UTF-8");
			chData = chData.replace("$weight", DataFields.getWeightKg()); //вес
			chData = chData.replace("$prDateDMY", DataFields.getProductionDateDMY()); // дата изготовления
			chData = chData.replace("$odDateDMY", DataFields.getOutputDateDMY()); // дата выработки
			chData = chData.replace("$exDateDMY", DataFields.getExpirationDateDMY()); //дата годности 
			chData = chData.replace("$paDateDMY", DataFields.getPackagingDateDMY()); //дата упаковывания
			chData = chData.replace("$LN", DataFields.getLotNumberString()); // номер партии
			chData = chData.replace("$BN", DataFields.getBoxNumberString()); // номер ящика
			chData = chData.replace("$NC", DataFields.getLotNumberString()); // номер варки/партии
			chData = chData.replace("$NI", DataFields.getNameItem()); //наименование продукта
			chData = chData.replace("$QIIB",DataFields.getCountUnitsString()); // количество единиц в ящике
			chData = chData.replace("$WB", DataFields.getWeightBoxKg()); // вес ящика 
			chData = chData.replace("$SL", DataFields.getShelfLifeString()); // срок годности
			chData = chData.replace("$NP", DataFields.getNumberPalletString()); // номер паллета
			chData = chData.replace("$WF", DataFields.getWeightFilmString()); // вес пленки
			chData = chData.replace("$NM", DataFields.getNetMassStringKg()); // масса нетто
			chData = chData.replace("$FB", DataFields.getWeightBoxAndFilmsKg()); // масса упаковки без продукта
			chData = chData.replace("$GM", DataFields.getGrossMassStringKg()); // Масса БРУТТО
			
			/* Штрихкод GS-128*/
			
			chData = chData.replace("111111", String.format("%06d", DataFields.getNetMassInt()));  // Вес
			chData = chData.replace("222222", String.format("%06d", DataFields.getQuantityItemsInBoxInt())); // количество единиц в ящике
			chData = chData.replace("333333", DataFields.getProductionDateYMD());  // дата изготовления
			chData = chData.replace("444444", DataFields.getExpirationDateYMD()); //дата годности 
			chData = chData.replace("555555", String.format("%06d", DataFields.getLotNumberInt()));  // номер партии
			chData = chData.replace("666666",  String.format("%06d", DataFields.getBoxNumberInt()));  // количество
			

		} catch (IOException e) {
			System.err.println("Ошибка файла" + e);
		} 
	}
	
	public String getChData() {
		return chData;
	}

	public void printToZebra() {
		try {

			String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
			System.out.println("Default printer: " + defaultPrinter);
			PrintService service = PrintServiceLookup.lookupDefaultPrintService();

			// String s = "^XA^FO100,40^BY3^B3,,30^FD123ABC^XZ";
			// prints the famous hello world! plus a form feed
			InputStream is = new ByteArrayInputStream(chData.getBytes("UTF8"));

			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(new Copies(1));

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			Doc doc = new SimpleDoc(is, flavor, null);
			DocPrintJob job = service.createPrintJob();

			// PrintJobWatcher pjw = new PrintJobWatcher(job);
			job.print(doc, pras);
			// pjw.waitForDone();
			is.close();

		} catch (PrintException | IOException e) {
			e.printStackTrace();
		}
	}

}
