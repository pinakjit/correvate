package com.correvate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUploadUtils {
	/**
	 * The method zips the file and saves it in the provided filepath with a prefixed static name
	 * To Do:-need to add error handling code.
	 */
	public static void zipFiles(List<Path>files, Path filePath) throws IOException
	{
		 FileOutputStream fos = new FileOutputStream(filePath+"/multiCompressed.zip");
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		for(Path file:files)
		{
			File fileToZip = new File(file.toString());
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
		}
		 zipOut.close();
	        fos.close();
	}
	
}
