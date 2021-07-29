import java.util.Scanner;

public class NodeAcads{
	Opponent o;
	int hp_enemy;
	NodeAcads next;
	
	public NodeAcads(Opponent pasok, int hp){
		o = pasok;
		hp_enemy = hp;
	}

	public void visualizationNode(int i){
		System.out.print(i+". "+hp_enemy);
	}
	
}