import java.util.*;
import java.io.*;


public class Chained{
	public int hash(int k){
		//h(K) = ⌊m(Kθ mod 1)⌋
		int m = 16;
		double theta = 0.6180339887;
		int ret = (int) (m *((k*theta) % 1));
		return ret;
	}

	public int insert(Record input, RecordList chainedTable[]){
		int i = hash(Integer.parseInt(input.student_no));

		//CASE 1: DUPLICATE KEY
		if (chainedTable[i].search(input.student_no) == 1){
			System.out.println("Duplicate Found!");
			return 0;
		}

		//CASE 2:INSERTION PROPER, see RecordList -> addRecord()
		System.out.print("\t\t"+input.firstname +" "+ input.surname+" record:");
		System.out.println("\t[insert] at index "+i);
		chainedTable[i].addRecord(input);
		return 1;
	}

	public void printTable(RecordList chainedTable[]){
		System.out.println("\n\t\t------   CHAINED HASH TABLE    ------");
		for(int i = 0; i < 16; i++){
			System.out.print("\t\t"+i+"."); chainedTable[i].printList();
			System.out.println();
		}
	}

	//-----------------  MAIN  -----------------//
	public void run() throws Exception{
		Record record[] = new Record[20];
		RecordList chainedTable[] = new RecordList[16];
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
		for(int i = 0; i < 16; i++){
			chainedTable[i] = new RecordList();
		}

		for(int i = 0; i < 20; i++){
			insert(record[i], chainedTable);
		}

		printTable(chainedTable);
	}
}