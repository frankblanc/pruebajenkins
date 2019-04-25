package Test.Test;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="Feature"
		,glue="Test.Test"
		,monochrome=true,
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:report/report.html"}
		)

public class AppTest {

}
