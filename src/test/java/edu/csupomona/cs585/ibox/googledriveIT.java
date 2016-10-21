package edu.csupomona.cs585.ibox;

import org.junit.*;

import java.io.*;

import java.util.Date;

import static org.junit.Assert.*;

import edu.csupomona.cs585.ibox.sync.*;

public class googledriveIT {
	private GoogleDriveFileSyncManager g;
	private java.io.File myFile;
	private String pwd = "/Users/danielacevedo/Documents/iBox/test.txt";
	
	@Before
	public void setup() {
		g = new GoogleDriveFileSyncManager(GoogleDriveServiceProvider.get().getGoogleDriveClient());
		myFile = new java.io.File(pwd);
	}
	
	@Test
	public void test_Add_File() {
		try {
			g.addFile(myFile);
			assertNotNull(g.getFileId(myFile.getName()));
			g.deleteFile(myFile);
			
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	@Test
	public void test_Delete_File() {
		try {
			if (g.getFileId(myFile.getName()) == null) {
				g.addFile(myFile);
			}
			g.deleteFile(myFile);
			assertNull(g.getFileId(myFile.getName()));
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	@Test
	public void test_Update_file() {
		try {
			g.addFile(myFile);
			Date beforeUpdate = new Date(myFile.lastModified());
			modifyFile(myFile);
			g.updateFile(myFile);
			Date afterUpdate = new Date(myFile.lastModified());
			assertTrue(beforeUpdate.compareTo(afterUpdate) < 0);
			g.deleteFile(myFile);
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public void modifyFile(java.io.File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Words have been added to this file!");
			bw.flush();
			bw.close();
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
}