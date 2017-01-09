$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("./src/test/java/com/hom/featureFile/feature1.feature");
formatter.feature({
  "line": 1,
  "name": "Sample feature to validate",
  "description": "In order to do something\r\nAs someone\r\nI want something else to happen",
  "id": "sample-feature-to-validate",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 7,
  "name": "Title validation after opening the page using Spreadsheet",
  "description": "",
  "id": "sample-feature-to-validate;title-validation-after-opening-the-page-using-spreadsheet",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 6,
      "name": "@Test1"
    }
  ]
});
formatter.step({
  "line": 8,
  "name": "Select the Browser \"\u003cBrowserName\u003e\", \"\u003cBrowserVersion\u003e\", \"\u003cEnvironment\u003e\", \"\u003cOperatingSystem\u003e\", \"\u003cMobileDevieID\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "User is on a provided URL page",
  "keyword": "And "
});
formatter.step({
  "line": 10,
  "name": "Title should be matched with expected value.",
  "keyword": "Then "
});
formatter.examples({
  "line": 12,
  "name": "",
  "description": "",
  "id": "sample-feature-to-validate;title-validation-after-opening-the-page-using-spreadsheet;",
  "rows": [
    {
      "cells": [
        "BrowserName",
        "BrowserVersion",
        "Environment",
        "OperatingSystem",
        "MobileDevieID"
      ],
      "line": 13,
      "id": "sample-feature-to-validate;title-validation-after-opening-the-page-using-spreadsheet;;1"
    },
    {
      "cells": [
        "Chrome",
        "",
        "Local",
        "Windows",
        ""
      ],
      "line": 14,
      "id": "sample-feature-to-validate;title-validation-after-opening-the-page-using-spreadsheet;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 669964168,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "Title validation after opening the page using Spreadsheet",
  "description": "",
  "id": "sample-feature-to-validate;title-validation-after-opening-the-page-using-spreadsheet;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 6,
      "name": "@Test1"
    }
  ]
});
formatter.step({
  "line": 8,
  "name": "Select the Browser \"Chrome\", \"\", \"Local\", \"Windows\", \"\"",
  "matchedColumns": [
    0,
    1,
    2,
    3,
    4
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "User is on a provided URL page",
  "keyword": "And "
});
formatter.step({
  "line": 10,
  "name": "Title should be matched with expected value.",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "Chrome",
      "offset": 20
    },
    {
      "val": "",
      "offset": 30
    },
    {
      "val": "Local",
      "offset": 34
    },
    {
      "val": "Windows",
      "offset": 43
    },
    {
      "val": "",
      "offset": 54
    }
  ],
  "location": "StepDefinitionFeature1.select_the_Browser(String,String,String,String,String)"
});
formatter.write("Chrome Browser has been initiated in local");
formatter.write("Select the browser method");
formatter.write("Project specific test data file :\\\\testData\\\\TestDataInputFile1.xls:  , and sheet name is TestData");
formatter.result({
  "duration": 8607824371,
  "status": "passed"
});
formatter.match({
  "location": "StepDefinitionFeature1.user_is_on_a_provided_URL_page()"
});
formatter.write("User is navigated to given URL:http://www.google.com");
formatter.result({
  "duration": 19524549517,
  "status": "passed"
});
formatter.match({
  "location": "StepDefinitionFeature1.title_should_be_matched_with_expected_value()"
});
formatter.write("Browse title is:Google");
formatter.write("Title has been mateched with expected titleGoogle");
formatter.result({
  "duration": 440141856,
  "status": "passed"
});
formatter.write("Test passed");
formatter.after({
  "duration": 136685167486,
  "status": "passed"
});
formatter.scenarioOutline({
  "comments": [
    {
      "line": 15,
      "value": "#\t\t|\tFirefox\t  | \t\t\t   |Local\t\t| Windows\t\t  | \t\t\t  |"
    },
    {
      "line": 16,
      "value": "#\t\t|\tIE\t\t  | \t\t\t   |Local\t\t| Windows\t\t  | \t\t\t  |"
    },
    {
      "line": 17,
      "value": "#\t\t|\tEdge\t  | \t\t\t   |Local\t\t| Windows\t\t  | \t\t\t  |"
    }
  ],
  "line": 20,
  "name": "Title validation after opening the page",
  "description": "",
  "id": "sample-feature-to-validate;title-validation-after-opening-the-page",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 19,
      "name": "@Test2"
    }
  ]
});
formatter.step({
  "line": 21,
  "name": "Select the Browser \"\u003cBrowserName\u003e\", \"\u003cBrowserVersion\u003e\", \"\u003cEnvironment\u003e\", \"\u003cOperatingSystem\u003e\", \"\u003cMobileDevieID\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 22,
  "name": "User is on a google page",
  "keyword": "And "
});
formatter.step({
  "line": 23,
  "name": "Title should be \"Google\".",
  "keyword": "Then "
});
formatter.examples({
  "line": 25,
  "name": "",
  "description": "",
  "id": "sample-feature-to-validate;title-validation-after-opening-the-page;",
  "rows": [
    {
      "cells": [
        "BrowserName",
        "BrowserVersion",
        "Environment",
        "OperatingSystem",
        "MobileDevieID"
      ],
      "line": 26,
      "id": "sample-feature-to-validate;title-validation-after-opening-the-page;;1"
    }
  ],
  "keyword": "Examples"
});
});