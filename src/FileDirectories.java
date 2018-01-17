import java.io.File;

public class FileDirectories {
	private static String directory;
	
	public FileDirectories() {
		//directory  =s;
	}
	
	public static String getMusicFolderPath() {
		String Path = "";
		File origin = new File("");
		int i =0;
		while(i<=2) {
			String currentFolder = origin.getAbsolutePath();
			//Check if MusicFolder is in currentFolder, which is a Path.
			String returnedPath = returnMusicFolderDirectory(currentFolder);
			//If we don't return '-1', not found,then break the sequence and 
			//Assign the Path.
			if(!returnedPath.equals("-1")) {
				Path = returnedPath;
				break;
			}
			// Else remove last folder from the current Path, then 
			//repeat with the new altered directory.
			String newPath = removeLastFolderFromPath(currentFolder);
			origin = new File(newPath);
			i++;
		}	
		if(i>2)
			Path = "Could Not find MusicFolder, Please create this directory within the scope of MusicTest!";
		return Path;
	}
	
	private static String removeLastFolderFromPath(String currentPath) {
		/*
		 * This function breaks the last folder from the current path.
		 * i.e C:\mohamed\jama turns into  C:\mohamed\
		 */
		
		String revisedPath="";
		//Divide the Path by the '/' and run through each index
		// bar the last one.
		String[] dividedPath = currentPath.split("\\\\");
		for(int i =0; i<dividedPath.length-1; i++)
		{
			revisedPath +=dividedPath[i]+"\\";
		}
		return revisedPath;
	}

	private static String returnMusicFolderDirectory(String Directory) {
		/*
		 * This function checks weather our target Folder is in the current
		 * directory path. 
		 */
		File currentFolder = new File(Directory);
		String target = "MusicFolder";
		for(File files: currentFolder.listFiles()) {
			String file = files.getName();
			if(file.equals(target)) {
				return files.getAbsolutePath();
			}
		}
		return "-1";
	}
}	
