import java.util.*;
import java.io.*;


//FOR CHAINED TABLES, 
public class RecordList{
	Record head;
	
	public void addRecord(Record input){
		Record bago = input;
		if (head == null){
				head = bago;
		} else {
				bago.next = head;
				head = bago;
		}
	}

	public int search(String key){
		Record alpha = head;
		while(alpha != null){
			if(alpha.student_no.equals(key)){
				return 1;
			}
			else{
				alpha = alpha.next; 
			}
		}
		return 0;
	}

	public void printList(){
		Record alpha = head;
		while(alpha != null){
			System.out.print("["+alpha.student_no + "|" + alpha.firstname +"|"+ alpha.surname+"|"+alpha.doB+"|"+alpha.gender+"]");
			if(alpha.next != null){
				System.out.print(" -> ");
			}
			alpha = alpha.next;
		}
	}
}