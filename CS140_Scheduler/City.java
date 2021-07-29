import java.util.*;
import java.io.*;

public class City {
	CommandList comlist;
	String city;
	int order;

	public City(String a, int b){
		city = a;
		order = b;
		comlist = new CommandList(a);
	}

	public Command ReturnCommand(){
		return comlist.list.peek(); //function
	}

	public void DecTime(){ //kapag ginagawa na
		ReturnCommand().duration--;
	}
}