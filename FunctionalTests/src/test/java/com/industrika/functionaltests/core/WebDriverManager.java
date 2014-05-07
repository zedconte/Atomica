package com.industrika.functionaltests.core;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.OperaProfile;

public class WebDriverManager {
	 private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();
	 
	 public static WebDriver getDriverInstance() {
		 WebDriver d = map.get(Thread.currentThread().getId());
		 return d;
	 }
	 
	 public static WebDriver startDriver(String type) {
		 WebDriver d;
		 switch(type.toLowerCase()){
		 case "chrome":
			 System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			 d = new ChromeDriver();
			 break;
		 case "firefox":
			 FirefoxProfile profile = new FirefoxProfile();
			 profile.setPreference("webdriver.log.file", "firefox.log");
			 d = new FirefoxDriver();
			 break;
		 case "ie":
			 System.setProperty("webdriver.ie.driver", "src/test/resources/iedriverserver.exe");
			 File logFile = new File("ie.log");
			 InternetExplorerDriverService service = new InternetExplorerDriverService.Builder().withLogFile(logFile).withLogLevel(InternetExplorerDriverLogLevel.ERROR).build();
			 d = new InternetExplorerDriver(service);
			 break;
		 case "opera":
			 System.setProperty("os.name","windows");
			 OperaProfile operaProfile = new OperaProfile();  // fresh, random profile
			 operaProfile.preferences().set("User Prefs", "Ignore Unrequested Popups", false);

			 DesiredCapabilities capabilities = DesiredCapabilities.opera();
			 capabilities.setCapability("opera.profile", operaProfile);
			 capabilities.setCapability("opera.log.level", "SEVERE");
			 capabilities.setCapability("opera.logging.file", "opera.log");
			 d = new OperaDriver(capabilities);
			 
			 break;
		 default:
			 throw new IllegalArgumentException("Browser type not supported: " + type);
		 }
	 	 map.put(Thread.currentThread().getId(), d);
		 return d;
	 }
	 
	 public static void stopDriver() {
		 WebDriver d = map.get(Thread.currentThread().getId());
		 if (d!=null)
		 {
			 d.close();
			 d.quit();
		 }
	 }
}
