package com.hom.automationDigital.commonFunctions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonBehaviour {

	public boolean isExist(final WebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element.isDisplayed();
	}

	public boolean isExist(final RemoteWebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element.isDisplayed();
	}

	public boolean isClickable(final WebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element.isEnabled();
	}

	public boolean isClickable(final RemoteWebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element.isEnabled();
	}

	public boolean isSelectable(final WebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.elementToBeSelected(locator));
	}

	public boolean isSelectable(final RemoteWebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.elementToBeSelected(locator));
	}

	public Date stringToDate(String formatter, String value) {
		DateFormat df = new SimpleDateFormat(formatter);
		Date startDate = null;
		try {
			startDate = df.parse(value);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}

	public boolean click(By by, RemoteWebDriver driver) {
		try {
			if (isClickable(driver, by)) {
				driver.findElement(by).click();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean jClick(By by, RemoteWebDriver driver) {
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean type(By by, RemoteWebDriver driver, String message) {
		try {
			if (isClickable(driver, by)) {
				driver.findElement(by).sendKeys(message);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean jType(By by, RemoteWebDriver driver, String message) {
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('value', '" + message + "')", element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectValue(By by, RemoteWebDriver driver, String message) {
		try {
			if (isSelectable(driver, by)) {
				Select dropdown = new Select(driver.findElement(by));
				try {
					dropdown.selectByValue(message);
				} catch (Exception e) {
					e.printStackTrace();
					for (int i = 0; i < dropdown.getOptions().size(); i++) {
						if (dropdown.getOptions().get(i).getText().trim().equalsIgnoreCase(message.trim())) {
							dropdown.selectByIndex(i);
							break;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectByIndex(By by, RemoteWebDriver driver, int index) {
		try {
			if (isSelectable(driver, by)) {
				Select dropdown = new Select(driver.findElement(by));
				dropdown.selectByIndex(index);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean click(By by, WebDriver driver) {
		try {
			if (isClickable(driver, by)) {
				driver.findElement(by).click();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean jClick(By by, WebDriver driver) {
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean type(By by, WebDriver driver, String message) {
		try {
			if (isClickable(driver, by)) {
				driver.findElement(by).sendKeys(message);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean jType(By by, WebDriver driver, String message) {
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('value', '" + message + "')", element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectValue(By by, WebDriver driver, String message) {
		try {
			if (isSelectable(driver, by)) {
				Select dropdown = new Select(driver.findElement(by));
				try {
					dropdown.selectByValue(message);
				} catch (Exception e) {
					e.printStackTrace();
					for (int i = 0; i < dropdown.getOptions().size(); i++) {
						if (dropdown.getOptions().get(i).getText().trim().equalsIgnoreCase(message.trim())) {
							dropdown.selectByIndex(i);
							break;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean selectByIndex(By by, WebDriver driver, int index) {
		try {
			if (isSelectable(driver, by)) {
				Select dropdown = new Select(driver.findElement(by));
				dropdown.selectByIndex(index);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getAttribute(By by, WebDriver driver, String attribute) {
		String returnValue = "";
		try {
			if (isSelectable(driver, by)) {
				returnValue = driver.findElement(by).getAttribute(attribute);
				return returnValue;
			} else {
				return returnValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnValue;
		}
	}

	public String getAttribute(By by, RemoteWebDriver driver, String attribute) {
		String returnValue = "";
		try {
			if (isSelectable(driver, by)) {
				returnValue = driver.findElement(by).getAttribute(attribute);
				return returnValue;
			} else {
				return returnValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnValue;
		}
	}

	public boolean getscreenshot(WebDriver driver, String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with name
		// "screenshot.png"
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean getscreenshot(RemoteWebDriver driver, String fileName) {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean navigateURL(WebDriver driver, String url) {
		driver.get(url);
		return true;
	}
	
	public boolean navigateURL(RemoteWebDriver driver, String url) {
		driver.get(url);
		return true;
	}
	
	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getTitle(RemoteWebDriver driver) {
		return driver.getTitle();
	}
}
