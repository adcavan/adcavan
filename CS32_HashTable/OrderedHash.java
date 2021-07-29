import java.util.*;
import java.io.*;


public class OrderedHash{
	int m = 23;
	//PRIMARY HASH FUNCTION
	public int hash(int k){
		int ret = (k % m);
		return ret;
	}

	//SECONDARY HASH FUNCTION
	public int secHash(int k){
		int ret = (1 + (((k/m))%(m-2)));
		return ret;
	}

	public int probe(int k, int i){
		int ret = ((hash(k) - i*secHash(k)) % m);
		return ret;
	}


	public int insert(Record input, Record table[]){
		int j = 1;
		int i = hash(Integer.parseInt(input.student_no));

		String temp;
		System.out.println("\t\t"+input.firstname +" "+ input.surname+" record:");
		System.out.println("\t\t\t\t[try] at index "+i);
		while(true){
			//CASE 1: Duplicate record
			if (table[i].student_no.equals(input.student_no)){
				System.out.println("\t\t\t\tDuplicate key found!");
				break;
			}

			//CASE 2: COLLISION
			else if (table[i].student_no != ""){
				if( Integer.parseInt(table[i].student_no) < Integer.parseInt(input.student_no)){
					//swap
					temp = table[i].student_no;
					table[i].student_no = input.student_no;
					input.student_no = temp;
					System.out.println("\t\t\t\t[swap] at index "+i+"  => ["+input.student_no+" < "+table[i].student_no+"]");
				}
				i = probe(Integer.parseInt(input.student_no), j);
				if (i < 0)
					i = i + m;
				System.out.println("\t\t\t\t[probe] to index "+i);
				j++;

			//CASE 3: VACANT CELL
			}else if (table[i].student_no == ""){
				System.out.println("\t\t\t\t[success] at index " + i);
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
		
		//record fetch
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
		System.out.println("\n\t\t------   ORDERED HASH TABLE    ------");
		for(int i = 0; i < 23; i++){
			System.out.print("\t\t"+i+ "| ["+table[i].student_no + "|" + table[i].firstname +"|"+ table[i].surname+"|"+table[i].doB+"|"+table[i].gender+"] ");
			System.out.println();
		}
	}
}