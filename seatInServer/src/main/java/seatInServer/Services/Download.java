package seatInServer.Services;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.Beans.ResourceFile;

public class Download {

	private ExecuteSelect select;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	public Download() {
		this.select = ExecuteSelect.getInstance();
	}
	
	/**
	 * Effettua il download di un file.
	 * @param fileId -- l'id del file.
	 * @return byte[] - contenente il contenuto del file  
	 */
	public byte[] downloadFile(int fileId) {		
		String path = select.selectPathToFile(fileId);		
		byte[] fileContent = null;
		
		try {
			File file = new File(path);
			fileContent = Files.readAllBytes(file.toPath());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
	/**
	 * Crea e manda, all'utente, uno zip contenente tutti i file di una determianta cartella.
	 * @param resourceId -- l'id della cartella in cui si trovano i file da mettere nello zip.
	 * @return byte[] rappresenta il contenuto dello zip che verrà mandato all'user. 
	 */
	public byte[] downloadZip(int resourceId) {
		String zipDirectory = "src/"+resourceId+".zip";
		Collection<ResourceFile> files = select.selectAllResourceFiles(resourceId, true);
		byte[] zipContent = null;		
		File zip = new File(zipDirectory);
		
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
			for(ResourceFile f : files) {
				File srcFile = new File(f.getName());
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				
				byte[] data = Files.readAllBytes(srcFile.toPath());
				zos.write(data);
				
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			
			zipContent = Files.readAllBytes(zip.toPath());
			zip.delete();
		} catch (FileNotFoundException e) {
			logger.debug("Il file non trovato: "+e);
		} catch (IOException e) {
			logger.debug("Errore esecuzione creazione dello zip: "+e);
		}
		return zipContent;
	}
}
