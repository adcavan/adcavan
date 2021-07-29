import java.util.*;

public class Command {
	String command;
	int duration;
	boolean isfinished;

	public Command(String name, int time){
		command = name;
		duration = time;
		isfinished = false;
	}

}