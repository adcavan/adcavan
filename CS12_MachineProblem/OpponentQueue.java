import java.util.Scanner;
import java.awt.*;
import java.awt.image.*;

public class OpponentQueue extends GameObject{
	NodeAcads head;
	NodeAcads tail;

	public void addOpponent(Opponent o, int hp){
		NodeAcads bago = new NodeAcads(o, hp);
		if (head == null){
				head = bago;
				tail = bago;
		} else {
				tail.next = bago;
				tail = bago;
		}
	}


	public int dequeue(){
		if (head!=null){
			int result = head.hp_enemy;
			head = head.next;
			if (head == null){
				tail = null;
			}
			return result;
		} else {
			return -1;
		}
	}
	

	public void printLine(){
		NodeAcads rover = head;
		int i = 0;
		if (rover == null)
			System.out.println("null");
		while (rover!=null){
			i++;
			if (rover == tail){
				rover.visualizationNode(i);
				System.out.println("<--tail\n");
			}else if(rover == head){
				System.out.println("Current Acads Queue:");
				rover.visualizationNode(i);
				System.out.println("<--head");
			}else{ 
				rover.visualizationNode(i);
				System.out.print("\n");
			}
			rover = rover.next;
		}
	}

	public int removeOpponent(){
		if (head!=null){
			int result = head.hp_enemy;
			head = head.next;
			if (head == null){
				tail = null;
			}
			return result;
		} else {
			return -1;
		}
	}

	public int basicmove(int student_hp){
		if (head!=null){
			if (head.hp_enemy <= 0){
				removeOpponent();
				System.out.println("Opponent has been eliminated.");
				printLine();
			}
			else student_hp = head.o.quiz(student_hp);
		} else {
			System.out.println("There are no Opponents out there.");
		}
		return student_hp;
	}

	public int secondarySkill(int student_hp){
		if (head!=null){
			if (head.hp_enemy <= 0){
				removeOpponent();
				System.out.println("Opponent has been eliminated.");
				printLine();
			}
			else student_hp = head.o.exam(student_hp, head.o.longExams);
		} else {
			System.out.println("There are no Opponents out there.");
		}
		return student_hp;
	}

	public int superskill(int student_hp){
		if (head!=null){
			if (head.hp_enemy <= 0){
				removeOpponent();
				System.out.println("Opponent has been eliminated.");
				printLine();
			}
			else student_hp = head.o.makeupclasses(student_hp);
		} else {
			System.out.println("There are no Opponents out there.");
		}
		return student_hp;
	}

	public void regen(){
		head.hp_enemy = head.hp_enemy + 10;
	}

	public void restorebullets(){
		head.o.prepareLE();
	}

 	public void paint(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.RED);

		if(head.o.isEffective == true)
			head.o.effect = "Effective!";
		else if(head.o.isEffective == false)
			head.o.effect = "Miss! Too bad";

		g.drawString("Acads' move: ", 600, 200);
		if (head.o.choice2.equals("A")) {
			g.drawString("SURPRISE QUIZ", 650, 250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(head.o.effect, 650, 275);
		} else if (head.o.choice2.equals("B")) {
			g.drawString("LONG EXAM", 650,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(head.o.effect, 650, 275);
		} else if (head.o.choice2.equals("C")) {
			g.drawString("MONDAY MAKE UP CLASS", 650,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(head.o.effect, 650, 275);
		} else if (head.o.choice2.equals("D")) {
			g.drawString("GAIN KNOWLEDGE", 650,250);
		} else if (head.o.choice2.equals("E")) {
			g.drawString("PREPARE LONG EXAM", 650,250);
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Health: "+head.hp_enemy, 650, 300);
		g.drawString("Number of LEs: "+head.o.longExams, 650, 350);
		
		NodeAcads rover = head;
		int i = 0, y = 450;
		g.drawString("Current Opponent Queue:", 650, 425);
		while (rover!=null){
			i++;
			if (rover == tail){
				g.drawString(i+". "+rover.hp_enemy, 670, y);
				g.drawString("<--tail", 750, y);
			}else if(rover == head){
				g.drawString(i+". "+rover.hp_enemy, 670, y);
				g.drawString("<--head", 750, y);
			}else{ 
				g.drawString(i+". "+rover.hp_enemy, 670, y);
			}
			rover = rover.next;
			y = y + 25;
		}
	}
}