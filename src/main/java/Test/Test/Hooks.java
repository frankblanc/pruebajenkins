package Test.Test;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	@Before
    public void beforeScenarioLine(){
        System.out.println("----------Start of Scenario----------");
    }	
	
	@After
    public void afterScenarioLine(){
        System.out.println("----------End of Scenario----------");
        System.out.println("");
    }
	
}
