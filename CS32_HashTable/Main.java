//Antonio D. Cavan 2015-65395
//Language: Java. openjdk version 11.0.2
//may be compiled in Linux or Windows
//for best results, maximize console
//data record from "test.txt" file, make sure there are no excess spaces at the end of each record

import java.util.*;
import java.io.*;


public class Main{
	public static void clearConsole(){
	    final String os = System.getProperty("os.name");
	    try{
	        if (os.contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else{
	        	System.out.print("\033\143");
	        }
	    }
	    catch (final Exception e){
	    	System.out.println(os);
	    }
	}
	
	//MAIN METHOD 
	public static void main (String args[]) throws Exception{
		Scanner in = new Scanner(System.in);
		
		Chained chain = new Chained();
		LinearProbe openAdd = new LinearProbe();
		OrderedHash ordered = new OrderedHash();
		

		System.out.println("\n\t\t\t\t\t\t\t\t*for best display, maximize console");
		String choice;
		while(true){
			System.out.println("\n\t\t\t\t\t\t\t\t>> - - - - - HASH TABLE (MP) - - - - - <<");
			System.out.println("\t\t\t\t\t\t\t\t\tA.  Chained Table \n\t\t\t\t\t\t\t\t\tB.  Linear Probing\n\t\t\t\t\t\t\t\t\tC.  Ordered Hash\n\t\t\t\t\t\t\t\t\tD.  Exit");

			System.out.print("\n\t\t\t\t\t\t\t\tEnter choice: ");
			choice = in.nextLine();

			if (choice.equals("A"))
				chain.run(); 
			else if (choice.equals("B"))
				openAdd.run();
			else if(choice.equals("C"))
				ordered.run();
			else if(choice.equals("D")){
				System.out.println("\n\t\t\t\t\t\t\t\texiting...");
				break;
			}
			else{
				System.out.println("\t\t\t\t\t\t\t\tChoose between A, B or C");
				continue;
			}
			System.out.print("\t\t\t\t\t\t\t\t[System paused] Press enter to continue...");
			choice = in.nextLine();
			clearConsole();
		}
	}
}