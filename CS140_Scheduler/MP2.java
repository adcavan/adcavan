import java.awt.*;

public class MP2{
	public static void main (String args[]){
	final SharedVars sv = new SharedVars();
	final MainScheduler m = new MainScheduler(sv);
	final Visualizer vis = new Visualizer(sv);
	final MarioWindow w1 = new MarioWindow();
	w1.add(m); w1.add(vis); 


							//making a thread object and defining it. 
							//creates and starts the thread without assigning it to variables
							//a.k.a Anonymous Inner Class
	(new Thread(){			//requirement: all variables inside must be constant, hence, "final"
		public void run(){
			w1.startGame();	//outside the anonymous inner class, w1.startGame hangs the main. 
		}
	}).start();

	}
}