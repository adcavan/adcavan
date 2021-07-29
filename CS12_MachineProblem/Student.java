import java.awt.*;
import java.awt.image.*;
public class Student extends GameObject { 
	String choice = ""; 
	int hp = 100;
	int kopicount = 6;
	int skipturn = 0;
	boolean isEffective = true;
	String effect;

	public int cram(int acads_hp){
		acads_hp = acads_hp - 10; 
		return acads_hp;
	}

	public int kopiko78(int acads_hp){
		acads_hp = acads_hp - 40;
		kopicount = kopicount - 1;
		return acads_hp; 
	}

	public int prepare(int acads_hp){
		acads_hp = acads_hp - 70;
		return acads_hp; 
	}

	public void sanitybreak(){
		hp = hp + 30;
	}

	public void buycoffee(){
		kopicount = kopicount + 1;
	}

	
	public void paint(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.GREEN);

		
		if(isEffective == true)
			effect = "Effective!";
		else if(isEffective == false)
			effect = "Miss! Too bad";

		g.drawString("Student's move: ", 50, 200);
		if (choice.equals("A")) {
			g.drawString("CRAM", 100, 250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(effect, 100, 275);
		} else if (choice.equals("B")) {
			g.drawString("CHUG KOPIKO78", 100,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(effect, 100, 275);
		} else if (choice.equals("C")) {
			g.drawString("Group Study 3 days before LE", 100,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(effect, 100, 275);
		} else if (choice.equals("D")) {
			g.drawString("SOCIAL MEDIA BREAK", 100,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
		} else if (choice.equals("E")) {
			g.drawString("BUY Kopiko78", 100,250);
			g.setFont(new Font("Arial", Font.BOLD, 20));
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Health: "+hp, 100, 300);
		g.drawString("Number of Kopiko78 remaining: "+kopicount, 100, 350);
		
	}
}