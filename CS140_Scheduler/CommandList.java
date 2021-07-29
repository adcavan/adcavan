import java.util.*;
import java.io.*;

public class CommandList{
	LinkedList<Command> list;
	Command com;
	String line;

/*	public CommandList(String fileName) {
		list = new LinkedList<Command>();
	}*/

	public CommandList(String fileName){
		initialize(fileName);
	}
	
	public void initialize(String fileName){
		try {	
			list = new LinkedList<Command>();
			fileName = fileName.concat(".txt");
			
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				String[] token = line.split(" ");
				com = new Command(token[0], Integer.parseInt(token[1]));
				list.add(com);
			}
			
			bufferedReader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();	// For the meanwhile.
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
}