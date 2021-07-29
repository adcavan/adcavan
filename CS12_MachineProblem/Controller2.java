import java.awt.*;
import java.awt.image.*;
import java.util.Scanner;
import java.util.Random;


public class Controller extends GameObject { 
	Student humanptr; // this is a pointer to the player1 object
	OpponentQueue acadsQueue;
	
	
	public void run() {
		Opponent o = new Opponent();
		Scanner mp = new Scanner(System.in);
		Random rand = new Random();

		//Create Queue with 7 Opponents
		for(int i = 1; i<8; i++){
			acadsQueue.addOpponent(o, 100);
		}
		System.out.println("\n\nInstantiated 7 Opponents.");
		
		int dice;
		while(true) {
			//Human's turn
			if (humanptr.skipturn == 0){
				System.out.println("Student's turn. Choose");
				System.out.println("A. Cram (10 pts damage)");
				System.out.println("B. Drink Kopiko78.(40 pts damage, -1 Kopiko78)");
				System.out.println("C. Study. (70 pts damage, skip 1 turn)");
				System.out.println("D. Social Media Break. (+30 hp)");
				System.out.println("E. Buy Kopiko 78. (+1 Kopiko78)\n");
			
				String choice = mp.nextLine();
				humanptr.choice = choice;
					if (choice.equals("A")){
						System.out.println("Student used cram against acads.");
						dice = rand.nextInt(5)+1;
						if (dice == 1){
							System.out.println("Cramming not effective. 0 damage dealt.\n");
							humanptr.isEffective = false;
						} else{
							acadsQueue.head.hp_enemy = humanptr.cram(acadsQueue.head.hp_enemy);
							System.out.println("Succesfully crammed you way through acads! 10 pts damage dealt.");
							humanptr.isEffective = true;
						}
					}

					else if (choice.equals("B")){
						System.out.println("Student drank Kopiko 78 while studying!");
						dice = rand.nextInt(10)+1;
						if (dice == 4){
							System.out.println("'Di ka gumaling. Nag palpitate ka lang. 0 damage dealt.");
							humanptr.isEffective = false;
						} else {
							acadsQueue.head.hp_enemy = humanptr.kopiko78(acadsQueue.head.hp_enemy);
							System.out.println("You got more efficient at studying! 40 pts damage dealt. "+humanptr.kopicount+" Kopiko 78 remaining.");
							humanptr.isEffective = true;
						}
					}
					else if (choice.equals("C")){
						System.out.println("Student studied 1 week before exam at Diligence cafe!");
						dice = rand.nextInt(20)+1;
						if (dice == 8){
							System.out.println("Wala boi gumastos ka lang. Bagsak pa rin. 0 damage dealt.");
							humanptr.isEffective = false;
						} else {
							acadsQueue.head.hp_enemy = humanptr.prepare(acadsQueue.head.hp_enemy);
							System.out.println("GG EZ! 70 pts damage dealt. Bawi ka muna ng tulog (skip 1 turn).");
							humanptr.skipturn = 2;
							humanptr.isEffective = true;
						}
					}
					else if (choice.equals("D")){
						humanptr.sanitybreak();
						System.out.println("Sanity break muna! Bring 'em all doggo pics, memes and chika minute. Gained 30 health points.");
					}
					else if (choice.equals("E")){
						humanptr.buycoffee();
						if (humanptr.kopicount < 7){
							System.out.println("Succesfully bought 1 bottle of Kopiko 78!");	
						} else
							System.out.println("You can't carry anymore.");
					}
					else if (choice.equals("F")){
						System.out.println("Special Cheat: Screw Acads!");
						acadsQueue.dequeue();	
					}
			}
			if (humanptr.skipturn == 2)
				humanptr.skipturn = humanptr.skipturn - 1;
			else if (humanptr.skipturn == 1){ 
				humanptr.skipturn = humanptr.skipturn - 1;
				System.out.println("(Skipped a turn)");	
			}
			if (acadsQueue.head == null && acadsQueue.tail == null){
					System.out.println("Game Over. Student aced all of his subjects.");	
					System.exit(0);
				}
			System.out.println("\nStudent's HP: "+humanptr.hp+"\nAcad's HP: "+acadsQueue.head.hp_enemy+"\n");



			//Checking Victory status and changing of front opponent.
			if (acadsQueue.head.hp_enemy <= 0){
				if (acadsQueue.head == null && acadsQueue.tail == null){
					System.out.println("Game Over. Student aced all of his subjects.");	
					System.exit(0);
				}
				System.out.println("Opponent lost. You are now facing another opponent. Laban lang.");
				acadsQueue.dequeue();
				acadsQueue.printLine();
				 //change front opponent
			}
			
			//acad's turn
			if (humanptr.skipturn == 0){
				System.out.println("Acad's turn. Choose");
				System.out.println("A. Surprise Quiz (10 pts damage)");
				System.out.println("B. Long exam (20 pts damage, -1 Long Exam)");
				System.out.println("C. Monday make up Long Exam (30 pts damage, skip 1 turn)");
				System.out.println("D. Add new references (+10 HP)");
				System.out.println("E. Prepare LE (+1 LE)\n");
				
				String option;
				int choice2 = rand.nextInt(5)+1;
				if (choice2 == 1){
					option = "A";
				} else if (choice2 ==2){
					option = "B";
				} else if (choice2 ==3){
					option = "C";
				} else if (choice2 ==4){
					option = "D";
				} else
					option = "E";
				System.out.println(option);
				acadsQueue.head.o.choice2 = option;

					if (choice2 == 1){
						System.out.println("Acads used suprise quiz!");
						dice = rand.nextInt(5)+1;
						if (dice == 1){
							System.out.println("Not effective. Students was listening during discussion. 0 damage dealt.");
							acadsQueue.head.o.isEffective = false;
						} else{
							humanptr.hp = acadsQueue.basicmove(humanptr.hp);
							System.out.println("Very effective! 10 pts damage dealt. Nakatulog si student eh.");
							acadsQueue.head.o.isEffective = true;
						}	
					}
					else if (choice2 == 2){
						System.out.println("Acads used Long Exam!");
						dice = rand.nextInt(10)+1;
						if (dice == 4){
							System.out.println("Not effective. Student had a high score. 0 damage dealt.");
							acadsQueue.head.o.isEffective = false;
						} else{
							humanptr.hp = acadsQueue.secondarySkill(humanptr.hp);
							System.out.println("Very effective! Bagsak si student. "+acadsQueue.head.o.longExams+" Long exams left. 20 pts damage dealt.");
							acadsQueue.head.o.isEffective = true;
						}
					}
					else if (choice2 == 3){
						System.out.println("Acads scheduled a Long exam on a Monday!");
						dice = rand.nextInt(20)+1;
						if (dice == 8){
							System.out.println("Not effective. Taas pa rin ng score ni student. 0 damage dealt. Pahinga ka muna (skip 1 turn).");
							acadsQueue.head.o.isEffective = false;
						} else{
							humanptr.hp = acadsQueue.superskill(humanptr.hp);
							System.out.println("Very effective! Nakalimutan ni student yung schedule. 30 pts damage dealt. Pahinga ka muna (skip 1 turn).");
							acadsQueue.head.o.skipturn = 2;
							acadsQueue.head.o.isEffective = true;
						}
					}
					else if (choice2 == 4){
						System.out.println("Acads did some research and found new, complicated knowledge!");		
							acadsQueue.regen();
							//System.out.println("HP: "+acadsQueue.head.hp_enemy);
					}
					else if (choice2 == 5){
						acadsQueue.restorebullets();
						if (acadsQueue.head.o.longExams < 3){
							System.out.println("Succesfully prepared a nosebleeding exam!");	
						} else
							System.out.println("You already have enough exams prepared.");
					}		
			}
			if (acadsQueue.head.o.skipturn == 2)
				acadsQueue.head.o.skipturn = acadsQueue.head.o.skipturn - 1;
			else if (acadsQueue.head.o.skipturn == 1){ 
				acadsQueue.head.o.skipturn = acadsQueue.head.o.skipturn - 1;
				System.out.println("(Skipped a turn)");	
			}
			

			dice = rand.nextInt(20)+1;
			if (dice == 8){
				acadsQueue.addOpponent(o, 100);
				System.out.println("A Zombie has spawned!");
				acadsQueue.printLine();
			}
			System.out.println("\nAcads' HP: "+acadsQueue.head.hp_enemy+"\nStudent's HP: "+humanptr.hp+"\n");

			//Checking Victory status.
			if (humanptr.hp <= 0){
				System.out.println("You died. Game Over. Academics wins.");
				System.exit(0);	
			}
		}		
	}

	/*public void paint(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.RED);

		g.drawString("Acads' move: ", 600, 200);
		if (head.o.choice2.equals("A")) {
			g.drawString("Surprise Quiz", 600, 250);
		} else if (head.o.choice2.equals("B")) {
			g.drawString("Long Exam", 600,250);
		} else if (head.o.choice2.equals("C")) {
			g.drawString("Monday Make up Class", 600,250);
		} else if (head.o.choice2.equals("D")) {
			g.drawString("Add References", 600,250);
		} else if (head.o.choice2.equals("E")) {
			g.drawString("Prepare Long Exams", 600,250);
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Health: "+head.hp_enemy, 700, 300);
		g.drawString("Number of LEs: "+head.o.longExams, 100, 350);
		
	}*/
}
