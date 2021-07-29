import java.awt.*;
import java.lang.*;
import java.util.*;

//Discussion on multi threaded programming
public class Visualizer extends GameObject{
	int x, y, width, height;
	SharedVars sv;

	String temp;
	String scheme = " ";
	String action = " ";
	String readyQueue = " ";
	String scq = " ";
	String remarks = " ";
	String commander = " ";
	int subcommanders = 0;
	int day = 0;

	//mario window, 800x600
	public Visualizer(SharedVars sv){
		this.sv = sv;
	}
	//MarioWindow calls paint every 10ms
	public void paint(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(315, 25, 160, 60);
		g.setColor(Color.BLACK);
		g.fillRect(320, 30, 150, 50);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN, 15));
		g.drawString("Battle Scheduler Version 2.0", 300, 15);

		g.setFont(new Font("Calibri", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("DAY " + day, 350, 60);
		
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("SCHEDULER: " + scheme, 315, 110);
		g.drawString(action + " GENERAL", 315, 160);
		g.drawLine(300, 170, 500, 170);
		g.drawString("Ready Queue: ", 25, 260);
		g.drawString("Remarks:", 25, 300);
		g.drawString("Subcommander Queue:", 25, 470);
		g.drawString("SUBCOMMANDERS", 320, 360);
		g.drawLine(300, 370, 500, 370);

		g.setFont(new Font("Calibri", Font.PLAIN, 17));
		g.drawString(" "+readyQueue, 30, 280);
		g.drawString(" " + remarks, 30, 320);
		g.setFont(new Font("Calibri", Font.PLAIN, 15));
		g.drawString(" " + scq, 30, 490);

		g.setFont(new Font("Calibri", Font.BOLD, 30));
		g.drawString(" "+commander, 275, 210);
		g.setFont(new Font("Calibri", Font.BOLD, 50));
		g.drawString(Integer.toString(subcommanders), 390, 430);
//		g.setFont(new Font("Calibri", Font.PLAIN, 20));
//		g.drawString("Subcommanders", 100, 350);
//		g.drawString(action + " General", 100, 100);
//		g.drawString("Ready Queue", 350, 130);
//		g.drawString("Remarks", 100, 400);
//		
//		g.setFont(new Font("Calibri", Font.PLAIN, 19));
//		g.drawString(" "+commander, 120, 130);
//		g.drawString(Integer.toString(subcommanders), 130, 380);
//		g.drawString(" " + remarks, 130, 420);
//		g.setFont(new Font("Calibri", Font.PLAIN, 20));
//		g.drawString(" "+readyQueue, 350, 150);

		//g.drawString("Subcommdaner Queue", 350, 350);
		//g.fillRect(20, 270, kuha, 10);
	}

	public void run(){
		while(true){
			MarioWindow.delay(1000);  
			synchronized(sv){
				remarks = sv.remarks;
				commander = sv.commander;
				day = sv.day;
				action = sv.action;
				remarks = sv.remarks;
				subcommanders = sv.scCount;
				readyQueue = sv.readyQueue;
				scheme = sv.scheme;
				scq = sv.scq;
			}

		}
	}								
}