package myCheese.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class PrintLables {
	private File[] files;
	InputStream is;

	public void print(String dirName) {
		// получение списка имен файлов в директории
		File dir = new File(dirName);
		if (!dir.exists() || !dir.isDirectory()) {
			try {
				throw new FileNotFoundException(dir + " not found");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		files = dir.listFiles();
		
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		for ( File f : files) {
			
				try {
					is = new BufferedInputStream(new FileInputStream(f));

			
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(new Copies(1));

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			Doc doc = new SimpleDoc(is, flavor, null);
			DocPrintJob job = service.createPrintJob();

			
				job.print(doc, pras);
			
			
			
				is.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (PrintException e) {
				e.printStackTrace();
		}
			

		}
	}
	
	
}