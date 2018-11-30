package myCheese.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class ZipUtil {
	
	private static final int BUFFER_SIZE = 1024;
	private List<String> files = new ArrayList<>();;
	private String dstFile;
	private String src;
	
	
	protected void unZip(String dstFile, String tempFile) {
		this.dstFile = dstFile;
		this.src = tempFile;
		byte[] buffer = new byte[BUFFER_SIZE];
		File dstDir = new File(tempFile);
		
		if (!dstDir.exists()) {
			dstDir.mkdir();
		}

		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(this.dstFile));
			ZipEntry ze = zis.getNextEntry();
			String nextFileName;
			while (ze != null) {
				nextFileName = ze.getName();
				File nextFile = new File(src + File.separator + nextFileName);
				System.out.println("Распаковываем: " + nextFile.getAbsolutePath());
				if (ze.isDirectory()) {
					nextFile.mkdir();
				} else {
					new File(nextFile.getParent()).mkdirs();
					try (FileOutputStream fos = new FileOutputStream(nextFile)) {
						int length;
						while ((length = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, length);
						}
					}
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			System.out.println("Операция выполнена успешно");
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	/**
	 * Сформировать список всех файлов в каталоге (рекурсивно).
	 *
	 * @param file
	 */
	void generateFilesList(File file) {
		
		if (file.isFile()) {
			files.add(generateZipEntry(file.getAbsolutePath()));
			
		}
		if (file.isDirectory()) {
			String dir = file.getAbsolutePath();
			
			if (!dir.equalsIgnoreCase(src)) {
				files.add(dir.substring(src.length()) + File.separator);
			}
			for (String nextFile : file.list()) {
				generateFilesList(new File(file, nextFile));
			}
		}
	}

	/**
	 * Сжать файлы в архив.
	 */
	protected void zipFiles(final String zipFile) {
		generateFilesList(new File(zipFile));
		System.out.println(files);
		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			FileOutputStream fos = new FileOutputStream("labels\\"+zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			System.out.println("Записываем в архив: " + zipFile);
			for (String nextFile : files) {
				System.out.println("Обрабатываем файл: " + nextFile);
				ZipEntry ze = new ZipEntry(nextFile);
				zos.putNextEntry(ze);
				if ((nextFile.substring(nextFile.length()-1)).equals(File.separator)) {
					continue;
				}
				try (FileInputStream in = new FileInputStream(src + File.separator + nextFile)) {
					int len;
					while ((len = in.read(buffer)) >= 0) {
						zos.write(buffer, 0, len);
					}
				}
			}
			zos.closeEntry();
			zos.close();
			System.out.println("Все файлы добавлены");
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	private String generateZipEntry(final String filename) {
		return filename.substring(src.length() + 1);
	}

}
