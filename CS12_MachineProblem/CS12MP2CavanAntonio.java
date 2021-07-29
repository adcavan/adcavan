public class CS12MP2CavanAntonio {
	public static void main(String args[]) {
		Student s = new Student();
		OpponentQueue a = new OpponentQueue();

		Controller c = new Controller();
		c.humanptr = s;
		c.acadsQueue = a;

		MarioWindow w1 = new MarioWindow();
		w1.add(s); w1.add(a); w1.add(c);
		w1.startGame();
		/*Player1 p1 = new Player1();
		Player2 p2 = new Player2();
		
		Controller c = new Controller();
		c.player1ptr = p1;  
		c.player2ptr = p2;
		
		
		MarioWindow w1 = new MarioWindow();
		w1.add(p1); w1.add(p2); w1.add(c);
		w1.startGame();*/
	}
}