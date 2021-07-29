import java.util.*;
import java.io.*;


public class LinearProbe{
	//PRIMARY HASH FUNCTION
	public int hash(int k){
		int m = 23;
		int ret = (k % m);
		return ret;
	}
	
	public int insert(Record input, Record table[]){
		int m = 23;
		int i = hash(Integer.parseInt(input.student_no));
		int s = 1;
		System.out.println("\t\t"+input.firstname +" "+ input.surname+" record:");
		System.out.println("\t\t\t\t[try] at index "+i);
		while(true){
			//CASE 1: DUPLICATE RECORD
			if (table[i].student_no.equals(input.student_no)){
				System.out.println("\t\t\t\tDuplicate key found!");
				break;
			}

			//CASE 2: COLLISION
			else if (table[i].student_no != ""){
				i = i - s;
				if(i < 0)
					i = i + m;
				System.out.println("\t\t\t\t[probe] to Index "+i);

			//CASE 3: VACANT CELL
			}else if (table[i].student_no == ""){
				System.out.println("\t\t\t\t[success] at Index " + i);
				table[i].student_no	= input.student_no;
				table[i].firstname = input.firstname;
				table[i].surname = input.surname;
				table[i].doB = input.doB;
				table[i].gender	= input.gender;
				break;
			}
		}
		return 0;		
	}

	//-----------------  MAIN  -----------------//
	public void run() throws Exception{
		Record record[] = new Record[20];
		Record table[] = new Record[23];
		File file = new File("test.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		

		String st; 
		for (int i = 0; i < 20; i++){
			record[i] = new Record();
			st = br.readLine();
			record[i].student_no = st;
			st = br.readLine();
			record[i].firstname = st;
			st = br.readLine();
			record[i].surname = st;
			st = br.readLine();
			record[i].doB = st;
			st = br.readLine();
			record[i].gender = st;
		}

		for(int i = 0; i < 23; i++){
			table[i] = new Record();
		}

		for(int i = 0; i < 20; i++){
			insert(record[i], table);
		}
		System.out.println("\n\t\t------   OPEN ADDRESS TABLE (LINEAR PROBING)   ------");
		for(int i = 0; i < 23; i++){
			System.out.print("\t\t"+i+ "| ["+table[i].student_no + "|" + table[i].firstname +"|"+ table[i].surname+"|"+table[i].doB+"|"+table[i].gender+"] ");
			System.out.println();
		}
	}
}