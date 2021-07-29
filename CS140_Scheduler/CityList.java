import java.io.*;
import java.util.*;

public class CityList{
	City ct;
	String read;
	LinkedList<City> cities;
	LinkedList<String> strlist;

	public CityList(String file){
		initialize(file);
	}
	
	//----------INITIALIZE LIST OF CITIES TO CONQUER-------//
	public void initialize(String file){
		try {
			cities = new LinkedList<City>();
			
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			
			while ((read = br.readLine()) != null) {
				String[] wordarr = read.split(" ");
				ct = new City(wordarr[0], Integer.parseInt(wordarr[1]));//count + 1);
				cities.add(ct); //ADD TO THE LINKEDLIST OF CITIES
			}
			br.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();	// For the meanwhile.
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	//----------RETURN CITY------------//  /*REMOVES THE CITY TO BE PROCESSED FROM THE LIST*/
	public LinkedList<City> ReturnCity(int start){
		LinkedList<City> b = new LinkedList<City>();
		City c;
		strlist = new LinkedList<String>();

		for (int i = 0; i<cities.size(); i++) {
			c = cities.get(i);

			if (c.order == start) { //if the city order is equal to the current time
				strlist.add(" We arrived in " + c.city + ". ");
				b.add(cities.remove(i));//remove from the list of cities to be conquered
			}							//should start doing the commands
		}
		return b;
	}
}