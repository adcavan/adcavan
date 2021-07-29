import java.util.*;

public class Scheduler{
	LinkedList<City> rq, scq, tempq;
	CityList clist;
	City commander, subcommander, currentcity;
	String remark = "", subcomremark = "";
	String scheme;
	LinkedList<String> tempstr;
	int time = 0, cs_time = 0; 
	boolean cs = false, cs_initial = false;

	public Scheduler(String file){
		clist = new CityList(file);
		tempq = new LinkedList<City>();
		rq = new LinkedList<City>();
		scq = new LinkedList<City>();
	}

	public void initialize(int start, String beatrice){
		tempq = clist.ReturnCity(start);
		for (int i = 0; i < clist.strlist.size(); i++) { //go through the entire cities
			remark = remark + clist.strlist.get(i); //get the remarks from the list
		}
		clist.strlist.clear();
		
		while (tempq.peek()!=null){
			if(tempq.peek().ReturnCommand().command.equals(beatrice)) //depends on input
				rq.add(tempq.remove());
			else 

				scq.add(tempq.remove());
		}
	}

	//------COMMANDER PROCESS AND READY QUEUE FETCH-----//
	public String MainCommand(String a){
		String str = " ";
		City shortest; City temp; City priority;
		if(scheme.equals("FCFS") || scheme.equals("SJF") || scheme.equals("Priority")){
			if (commander != null && commander.ReturnCommand().duration == 0) { //DONE PROCESSING 
				remark = remark + commander.city + " (" + commander.ReturnCommand().command + " is done). ";
				commander.comlist.list.remove();
	
				//Check if there are still cities to be conquered
				if (commander.comlist.list.size() > 0) {
					if (commander.ReturnCommand().command.equals(a)){ rq.add(commander); }
					else scq.add(commander); //else, add to subcommander queue
				}
				else remark = remark + commander.city + " has been conquered. ";
				commander = null;
			}
	
			if (rq.size() > 0 || commander != null) {
				if (commander == null){
					switch(scheme){
						case "FCFS": commander = rq.remove(); break;
						case "SJF":  shortest = rq.get(0);
									 for(int i = 0; i<rq.size(); i++){
									 	temp = rq.get(i);
									 	if (temp.ReturnCommand().duration < shortest.ReturnCommand().duration)
									 		shortest = temp;
									 }
									 commander = shortest;
									 rq.remove(commander);
									 break;
						case "Priority": priority = rq.get(0);
										 for(int i = 0; i<rq.size(); i++){
										 	temp = rq.get(i);
										 	if(temp.city.charAt(0) < priority.city.charAt(0))
										 		priority = temp;
										 }
										 commander = priority;									
										 rq.remove(commander);
										 break;
						default: break;
					}				
					remark = remark.concat(commander.city + " is chosen. ");
				}
				str = commander.city + " (" + commander.ReturnCommand().command + " : " + commander.ReturnCommand().duration + ") ";
				commander.DecTime();
			}

		} else if (scheme.equals("Round Robin")){
			if (commander != null && (commander.ReturnCommand().duration == 0 || time >= 4)){
				if (rq.size() != 0){
					cs_initial = true;
					cs = true;
				}
			} else if (cs_time != 2 && cs_initial){
				cs = true;
				cs_initial = false;
			} else {
					cs = false;
			}

			if(cs_initial && cs){
				time = 0;
				cs_time = 0;				
				
				if(commander.ReturnCommand().duration > 0){
					rq.add(commander);
					commander = null;
				} else {
					remark = remark + commander.city + " (" + commander.ReturnCommand().command + " is done). ";
					commander.comlist.list.remove();
					//Check if there are still cities to be conquered
					if (commander.comlist.list.size() > 0) {
						if (commander.ReturnCommand().command.equals(a)){ rq.add(commander); }
						else { scq.add(commander); } 
					} else remark = remark + commander.city + " has been conquered. ";
					commander = null;
				}
				if (rq.size() > 0)
					remark = remark.concat(rq.get(0).city + " is chosen. ");
			}

			if(cs){
				cs_time++;
				if(rq.size() > 0){
					str = "    IN TRANSIT : " + cs_time + "";
					remark = remark + " General in transit (CS). ";
				} 
				if(cs_time == 2){
					cs = false;
				}
			} else {
				time++;
				if (rq.size() > 0 && commander == null)
					commander = rq.remove(); 
				if (commander != null){
					if(commander.ReturnCommand().duration == 0){
						remark = remark + commander.city + " (" + commander.ReturnCommand().command + " is done). ";
						commander.comlist.list.remove();
	
						//Check if there are still cities to be conquered
						if (commander.comlist.list.size() > 0) {
							if (commander.ReturnCommand().command.equals(a)){ rq.add(commander); }
							else scq.add(commander); //else, add to subcommander queue
						}
						else remark = remark + commander.city + " has been conquered. ";
						commander = null;
					} else {
						str = commander.city + " (" + commander.ReturnCommand().command + " : " + commander.ReturnCommand().duration + ") ";
						commander.DecTime();
					}
				}
			}
		}
		return str;
	} 			
	
	//------SUBCOMMANDER PROCESS AND SUBCOMMANDER QUEUE FETCH-----//
	public void Subcommand(String a) {
		tempstr = new LinkedList<String>();
		subcomremark = "";
		if (scq.size() > 0) {
			for (int i = 0; i < scq.size(); i++) {	
				subcommander = scq.get(i);
				tempstr.add(subcommander.city + " (" + subcommander.ReturnCommand().command + " : " + subcommander.ReturnCommand().duration + ") ");
				subcommander.DecTime();
					
				if (subcommander != null && subcommander.ReturnCommand().duration == 0) {
					subcomremark = subcomremark + subcommander.city + " (" + subcommander.ReturnCommand().command + " is done). ";
					subcommander.comlist.list.remove();
					if (subcommander.comlist.list.size() > 0) {
						if (subcommander.ReturnCommand().command.equals(a)) 
							rq.add(scq.remove(i));
					}
					else {
						subcomremark = subcomremark + subcommander.city + " has been conquered. ";
						scq.remove(i);
					}
				} 
			}
		}
		else
			tempstr.add(" ");
	}
	//-----UPDATING STRING FOR HTML OUTPUT----//
	public void update(String a) {
		tempstr = new LinkedList<String>();
		if (rq.size() > 0) {
			for (int i = 0; i < rq.size(); i++) {
				tempstr.add(rq.get(i).city + " (" + a + " : " + rq.get(i).ReturnCommand().duration + ") " );
			}
		}
		else
			tempstr.add(" ");
	}

	//----DETERMINING IF ALL CITIES ARE CONQUERED-----//
	public boolean isFinished() {
		if (commander == null && rq.size() == 0 && scq.size() == 0)
			return true;
		else
			return false;
	}

}