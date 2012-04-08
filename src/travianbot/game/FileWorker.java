package travianbot.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Esr
 */
public class FileWorker {
    
      public static BufferedReader getBReader(String fileName){
		FileReader fReader;
		try {
			fReader = new FileReader(Game.class.getResource(fileName).getFile());
			return (new BufferedReader(fReader));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	public static String get_file(String name){
		
		BufferedReader file = getBReader(name);
		String content="",line="";
		
		try {
			while((line=file.readLine())!= null)
				content+=line;
		} catch (IOException e) {
			
			//e.printStackTrace();
		}
		return content;
		
	}
    
}
