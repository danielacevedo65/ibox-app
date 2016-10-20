package edu.csupomona.cs585.ibox;

// Import JUnit
import org.junit.*;

// Import Mockito
import static org.mockito.Mockito.*;

// Import Java classes
import java.io.IOException;
import java.util.ArrayList;

// Import Google Drive File Sync Manager
import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;

// Import Google API tools
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;


public class unitTest {
	
	// Private variables
	private GoogleDriveFileSyncManager g;
	private Drive mockDrive;
	private File mockFile;
	private Files mockFiles;
	private List request;
	private Files.Insert insert;
	private Files.Update update;
	private Files.Delete delete;
	private java.io.File File;
	private FileList fileList;
	private ArrayList<File> array;
	
	// Set up all mocks, arrays, and files
	@Before
	public void setup() throws IOException {		
		// Set up Mockito mocks 
		mockDrive = mock(Drive.class);
		mockFiles = mock(Files.class);
		request = mock(List.class);
		// Mockito mocks - Insert, Update, Delete
		insert = mock(Files.Insert.class);
		update = mock(Files.Update.class);
		delete = mock(Files.Delete.class);
		
		// Set up Google Drive File Manager
		g = new GoogleDriveFileSyncManager(mockDrive);
		
		// Set up local file
		String path = System.getProperty("user.dir") + java.io.File.separator.toString() + "testFile";
		File = new java.io.File(path);
	
		// Set up mock file
		mockFile = new File();
		mockFile.setId("testFile");
		mockFile.setTitle("testFile");
		
		// Set up list of files
		fileList = new FileList();
		
		// Set up array of files
		array = new ArrayList<File>();
		
		// Fill array of files
		array.add(mockFile);
		
		// Set list of files
		fileList.setItems(array);
		
	}
	
	// Test if file was added
	@Test
	public void test_File_Added() throws IOException {
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.insert(any(File.class), any(FileContent.class))).thenReturn(insert);
		when(insert.execute()).thenReturn(mockFile);
		
		g.addFile(File);
		
		verify(mockFiles).insert(any(File.class), any(FileContent.class));
		verify(insert).execute();	
	}

	
	// Test if file was updated
	@Test
	public void test_File_Updated() throws IOException {		
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.list()).thenReturn(request);
		when(request.execute()).thenReturn(fileList);
		when(mockFiles.update(any(String.class), any(File.class), any(FileContent.class))).thenReturn(update);
		when(update.execute()).thenReturn(mockFile);
		
		// This portion checks the "else" statement
		g.updateFile(File);		
		
		// This portion checks the "if" statement
		java.io.File mock = new java.io.File("mock");
		
		try {
			g.updateFile(mock);
		}
		catch (java.lang.NullPointerException e) {
			;
		}
		
		verify(mockFiles).update(any(String.class), any(File.class), any(FileContent.class));
		verify(update).execute();
	}
	
	// Test if file gets deleted
	@Test
	public void test_File_Deleted() throws IOException {						
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.list()).thenReturn(request);
		when(request.execute()).thenReturn(fileList);
		when(mockFiles.delete(any(String.class))).thenReturn(delete);
		
		//This tests the "else" statement
		g.deleteFile(File);
		
		// This tests the "if" statement
		java.io.File mock = new java.io.File("mock");
		
		try {
			g.deleteFile(mock);
		}
		catch (java.io.FileNotFoundException e) {
			;
		}
		
		verify(mockFiles).delete(any(String.class));
		verify(delete).execute();
		
	}
	
	// Test if file id successfully grabbed
	@Test
	public void test_Get_File_ID() throws IOException {
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.list()).thenReturn(request);
		when(request.execute()).thenThrow(new IOException());
		
		g.getFileId("testFile");
		
		verify(request).execute();
	}
	
}
	
	
	/*
	// Initial tests
	// These were simple tests to get myself started
	// After using Cobertura, realized these tests weren't necessary for testing purposes.
	@Test
	public void test_App_Exists() throws IOException {
		// System.out.print("STARTING APP EXISTS\n");
		App a = new App();
		assertTrue(!a.equals(null));
		// System.out.print("ENDING APP EXISTS\n");
	}
	
	@Test
	public void test_File_Exists() throws IOException {
		// System.out.print("STARTING FILE EXIST\n");
		assertTrue(!File.equals(null));
		// System.out.print("ENDING FILE EXIST\n");
	}
	
	@Test
	public void test_Array_Not_Empty() throws IOException {
		// System.out.print("STARTING ARRAY NOT EMPTY\n");
		assertTrue(array.size() != 0);
		// System.out.print("ENDING ARRAY NOT EMPTY\n");
	}
	
	@Test
	public void test_Google_Drive_Exists() throws IOException {
		// System.out.print("STARTING GOOGLE DRIVE EXISTS\n");
		assertTrue(!g.equals(null));
		// System.out.print("ENDING GOOGLE DRIVE EXISTS\n");
	}
	
	@Test
	public void test_Mock_Drive_Exists() throws IOException {
		// System.out.print("STARTING MOCK DRIVE EXISTS\n");
		assertTrue(!mockDrive.equals(null));
		// System.out.print("ENDING MOCK DRIVE EXISTS\n");
	}
	
	@Test
	public void test_Mock_File_Exists() throws IOException {
		// System.out.print("STARTING MOCK FILE EXISTS\n");
		assertTrue(!mockFile.equals(null));
		// System.out.print("ENDING MOCK FILE EXISTS\n");
	}
	
	@Test
	public void test_Mock_File_ID() throws IOException {
		// System.out.print("STARTING MOCK ID IS CORRECT\n");
		assertTrue(mockFile.getId() == "testFile");
		// System.out.print("ENDING MOCK ID IS CORRECT\n");
	}
	
	@Test
	public void test_Mock_Files_Exists() throws IOException {
		// System.out.print("STARTING MOCK FILES EXISTS\n");
		assertTrue(!mockFiles.equals(null));
		// System.out.print("ENDING MOCK FILES EXISTS\n");
	}
	
	@Test
	public void test_Mock_Files_Not_Empty() throws IOException {
		// System.out.print("STARTING MOCK FILES NOT EMPTY\n");
		assertTrue(!mockFiles.equals(null));
		//System.out.print("ENDING MOCK FILES NOT EMPTY\n");
	}
	
	@Test
	public void test_Request_Exists() throws IOException {
		// System.out.print("STARTING MOCK LIST EXISTS\n");
		assertTrue(!request.equals(null));
		// System.out.print("ENDING MOCK LIST EXISTS\n");
	}
	
	@Test
	public void test_File_List_Exists() throws IOException {
		// System.out.print("STARTING FILE LIST EXISTS\n");
		assertTrue(!fileList.equals(null));
		// System.out.print("ENDING FILE LIST EXISTS\n");
	}
	
	@Test
	public void test_File_List_Not_Empty() throws IOException {
		// System.out.print("STARTING FILE LIST NOT EMPTY\n");
		assertTrue(fileList.size() != 0);
		// System.out.print("ENDING FILE LIST NOT EMPTY\n");
	}
	
	*/