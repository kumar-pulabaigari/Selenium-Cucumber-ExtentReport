Feature: Sample feature to validate
	In order to do something
	As someone
	I want something else to happen

	@Test1
	Scenario Outline: Title validation after opening the page using Spreadsheet
		Given Select the Browser "<BrowserName>", "<BrowserVersion>", "<Environment>", "<OperatingSystem>", "<MobileDevieID>"
		And User is on a provided URL page
		Then Title should be matched with expected value.
		
		Examples:
		| BrowserName | BrowserVersion |Environment | OperatingSystem | MobileDevieID |
		|	Chrome	  | 			   |Local		| Windows		  | 			  |
#		|	Firefox	  | 			   |Local		| Windows		  | 			  |
#		|	IE		  | 			   |Local		| Windows		  | 			  |
#		|	Edge	  | 			   |Local		| Windows		  | 			  |
	
	@Test2
	Scenario Outline: Title validation after opening the page 
		Given Select the Browser "<BrowserName>", "<BrowserVersion>", "<Environment>", "<OperatingSystem>", "<MobileDevieID>"
		And User is on a google page
		Then Title should be "Google".
		
		Examples:
		| BrowserName | BrowserVersion |Environment | OperatingSystem | MobileDevieID |
#		|	Chrome	  | 			   |Local		| Windows		  | 			  |
#		|	Firefox	  | 			   |Local		| Windows		  | 			  |