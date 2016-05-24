package visitor;


public class MercuryAI extends SupremeAI{

	public MercuryAI() {
		
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


