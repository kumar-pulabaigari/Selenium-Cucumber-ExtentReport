package com.hom.stepDefinitions;

import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions( features = {".\\src\\test\\java\\com\\hom\\featureFile\\feature1.feature"},
		   format = {"pretty", "html:target/results1.html",  "json:target/result1.json"}
//		,tags = {"~@Test1"}
) 
public class RunCukes {

	@Test
	public void runCukes() {
        new TestNGCucumberRunner(getClass()).runCukes();
    }
}
