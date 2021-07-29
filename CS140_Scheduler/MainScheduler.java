import java.util.*;
import java.io.*;


public class MainScheduler extends GameObject{
	SharedVars sv;

	public MainScheduler(SharedVars sv){
		this.sv = sv;			
	}

	public void run(){
		while(true){
			MarioWindow.delay(100);
			Boolean end = false;
			Scanner input = new Scanner(System.in);
			String action = null; String scheme = " ";
	
			System.out.println("\n\nIt has begun, your excellency. Here are the phases of battle:");
			System.out.println("\t| A. Attack  |\n\t| B. Siege   |\n\t| C. Defend  |\n\t| D. Parley  |");
			System.out.print("I would like to lead choice: ");
			String choice = input.nextLine(), phase = "";
			     if (choice.equals("A")){ phase = "attack"; action = "ATTACKING";} 
			else if (choice.equals("B")){ phase = "seige"; action = "SEIGING";} 
			else if (choice.equals("C")){ phase = "defend"; action = "DEFENDING";} 
			else if (choice.equals("D")){ phase = "parley"; action = "PARLEYING";}
	
			System.out.println("\nWhat type of scheduling would you like to use, your exellency?");
			System.out.println("\t| A. FCFS        |\n\t| B. SJF         |\n\t| C. Priority    |\n\t| D. Round Robin |");
			System.out.print("Enter choice: ");
			choice = input.nextLine();
			     if (choice.equals("A")){ scheme = "FCFS";} 
			else if (choice.equals("B")){ scheme = "SJF";} 
			else if (choice.equals("C")){ scheme = "Priority";} 
			else if (choice.equals("D")){ scheme = "Round Robin";}
	
	
			Scheduler scheduler = new Scheduler("WarStrategy.txt");
			scheduler.scheme = scheme;
			String subcom_done = "";
			String rq = "", scq = " ";

			sv.scheme = scheme;
			sv.action = action;
			for (int i = 1; ;i++) {
				synchronized(sv){
					
		//-------------------------TIME-------------------------//
					scheduler.initialize(i, phase);
					sv.day = i;
		            
		//--------------------COMMANDER-------------------------//            
		            sv.commander = scheduler.MainCommand(phase);
		
		//-----------------------READY QUEUE--------------------------//
					scheduler.update(phase);
					rq = " ";
		            for(int x = 0; x < scheduler.tempstr.size(); x++) {
		            	rq = rq + " " + scheduler.tempstr.get(x);
		            }
		            sv.readyQueue = rq;
		            scheduler.tempstr.clear();
		            
		//--------------------SUBCOMMANDER------------------------//            
					scheduler.Subcommand(phase);
					scq = " ";
		            for(int x = 0; x < scheduler.tempstr.size(); x++) {
		            	scq = scq + " " + scheduler.tempstr.get(x);
		            }
		            sv.scq = scq;
		            if (scheduler.tempstr.get(0).equals(" ")) sv.scCount = 0;
		            else sv.scCount = scheduler.tempstr.size();
		            //System.out.println("1. " + scq);
		            scheduler.tempstr.clear();
		            
		//-----------------------REMARKS------------------------//
		            
		            if (scheduler.remark == "" && subcom_done == "") { sv.remarks = "";} 
		            else  { sv.remarks = scheduler.remark + subcom_done; }
		            scheduler.remark = "";
		            subcom_done = scheduler.subcomremark;
		            
		            if (end) break;
		            if (scheduler.isFinished()) end = true;
		            MarioWindow.delay(2000);
		 		}
		 		MarioWindow.delay(2000);
			}
		}		
	}
}