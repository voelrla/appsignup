package org.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import com.thoughtworks.selenium.webdriven.commands.GetText;

import io.appium.java_client.android.AndroidDriver;

public class Setup {
	public static AndroidDriver driver;
	public static String setid = "mtest" + getDateTimeStamp() + "@yopmail.com";
	public static String pw = "qwer1234";

	@BeforeTest
	public static void setUp() throws Exception {
		// 테스트 단말 구성 설정
		DesiredCapabilities capabilities = new DesiredCapabilities("appWaitActivity", null, null);
		capabilities.setCapability("appPackage", "com.wemakeprice");
		capabilities.setCapability("appActivity", "com.wemakeprice.intro.Act_Intro");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("app", "C:\\Lee\\apk\\4.31.0\\wemakeprice-4.31.0_pre_3rd.apk");
		capabilities.setCapability("noReset", false);
		capabilities.setCapability("deviceName", "Samsung Galaxy Note5");

		// Appium 연결설정
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// 최초팝업 노출 유무 체크
		System.out.println("테스트 시작");
		int a = 0;
		while (a < 1) {
			a++;
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List clicked = driver.findElementsByAndroidUIAutomator(
					"new UiSelector().resourceId(\"com.wemakeprice:id/textView1\").text(\"닫기\")");
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			if (clicked.isEmpty() == false) {
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshot, new File("C:\\Lee\\shot\\screenshot.jpg"));
				System.out.println("최초팝업 노출");
				driver.findElementByAndroidUIAutomator(
						"new UiSelector().resourceId(\"com.wemakeprice:id/textView1\").text(\"닫기\")").click();
			} else {
				System.out.println("최초팝업 노출 안함");
			}
		}
		// 3.ㅌㅌㅌ
		a = 0;
	}

	// element 클릭 함수
	public static void clickid(String element) throws Exception {
		driver.findElementByAndroidUIAutomator(element).click();
	}

	// 키(문자)입력 함수
	public static void sendkey(String value) throws Exception {
		driver.getKeyboard().sendKeys(value);
	}

	// 현재시간을 구하는 함수
	public static String getDateTimeStamp() {
		// creates a date time stamp that is Windows OS filename compatible
		return new SimpleDateFormat("mmss").format(Calendar.getInstance().getTime());
	}

	// 인증받는 휴대폰번호 중복 체크
	public static String duplicateNumber() throws Exception {
		String strResult = "";
		List dnumber = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"번호인증\")");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if (dnumber.isEmpty() == false) {
			clickid("new UiSelector().text(\"번호인증\")");
			// 앱 팝업의 '확인'버튼을 찾지못해 index값까지 참조로 넣음
			clickid("new UiSelector().index(0).text(\"확인\")");
			strResult = driver
					.findElementByAndroidUIAutomator(
							"new UiSelector().resourceId(\"net.everythingandroid.smspopup:id/messageTextView\")")
					.getText();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			System.out.println(strResult);
		}
		return strResult;
	}

	// 인증번호가 전송된 문자 텍스트를 추출한 후 숫자만 노출되게 필터
	public static String getNumber(String code) {
		return code.replaceAll("[^0-9]", "");
	}

	public static int testResult() throws Exception {
		int testR = 0;
		List checkR = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"쇼핑하기 Link\")");
		if (checkR.isEmpty() == false) {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("C:\\Lee\\shot\\failscreenshot.jpg"));
			testR = 1;
		} else {
			testR = 0;
		}
		return testR;
	}

	String fail_signup = "회원가입 실패";
	// class TestResult
	// {
	// const int TEST_SUCCESS = 1;
	//
	// int m_nCode;
	// string m_strMessage;
	//
	// public TestResult(int nCode, string strMessage = "")
	// {
	// m_nCode = nCode;
	// m_strMessage = strMessage;
	// }
	//
	// int getCode()
	// {
	// return m_nCode;
	// }
	//
	// string getMessage()
	// {
	// return m_strMessage;
	// }
	// }

	@AfterMethod
	public static void tearDown(ITestResult testResult) throws IOException {
		/*
		 * if (testResult.getStatus() == ITestResult.FAILURE){ WebDriver driver1
		 * = new Augmenter().augment(driver); File failshot = ((TakesScreenshot)
		 * driver1).getScreenshotAs(OutputType.FILE);
		 * FileUtils.copyFile(failshot, new
		 * File("C:\\Lee\\shot\\failscreenshot.jpg"));
		 */

		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("C:\\Lee\\shot\\failscreenshot.jpg"));

		} else {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("C:\\Lee\\shot\\passscreenshot.jpg"));
			System.out.println(testResult.getStatus());
		}
		driver.quit();
	}
}