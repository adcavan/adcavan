
public class Opponent{ 
	String choice2 = ""; 
	int longExams = 3;
	int skipturn = 0;
	boolean isEffective = true;
	String effect = "";
	

	public int quiz(int student_hp){
		student_hp = student_hp - 10; //10 pts damage
		return student_hp;
	}

	public int exam(int student_hp, int longExams){
		student_hp = student_hp - 20; //20 pts damage
		longExams = longExams - 1;
		return student_hp; 
	}

	public int makeupclasses(int student_hp){
		student_hp = student_hp - 30; //30 pts damage
		return student_hp; 
	}


	public void prepareLE(){
		longExams = longExams + 1;
	}

	

}