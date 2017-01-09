package com.hom.stepDefinitions;

import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions( features = {".\\src\\test\\resources\\features\\feature3.feature"},
		   format = {"pretty", "html:target/results1.html",  "json:target/result.json"} ) 
public class RunCukes3 {

	@Test
	public void runCukes() {
        new TestNGCucumberRunner(getClass()).runCukes();
    }
}
