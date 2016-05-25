package visitor;

public class VenusAI extends SupremeAI{
	
	public VenusAI() {
		
	}
	
	public void runAlgorithm() {
		super.performCalculations();
		doSpecificAIStuff();
	}
	
	
	private void doSpecificAIStuff() {
		//algorithm specific to the execution of
		//this artificial intelligence player;
	}

	
}
