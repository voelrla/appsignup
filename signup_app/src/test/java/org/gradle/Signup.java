package org.gradle;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class Signup extends Setup {

	@Test
	public void signup1() throws Exception {
		System.out.println(setid);
		clickid("new UiSelector().resourceId(\"com.wemakeprice:id/mypage_button\")");
		clickid("new UiSelector().resourceId(\"com.wemakeprice:id/tv_mypage_main_menu_cell_title\")");
		clickid("new UiSelector().text(\"개인 구매회원\")");

		// 회원가입 페이지
		clickid("new UiSelector().text(\"이메일(예) example@example.com\")");
		sendkey(setid);

		clickid("new UiSelector().text(\"비밀번호(영문/숫자 조합 6~15자 이내)\")");
		sendkey(pw);

		clickid("new UiSelector().text(\"비밀번호 확인\")");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		sendkey(pw);

		clickid("new UiSelector().text(\"이름\")");
		sendkey("Lee test");

		// 생년월일, 휴대폰
		clickid("new UiSelector().text(\"생년\")");
		// driver.findElement(By.name("2003")).click();
		clickid("new UiSelector().text(\"2002\")");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		clickid("new UiSelector().text(\"월\")");
		driver.findElement(By.name("5")).click();

		clickid("new UiSelector().text(\"일\")");
		driver.findElement(By.name("5")).click();

		// 전화번호 인증
		clickid("new UiSelector().text(\"휴대폰번호(예) 01012345678\")");
		sendkey("01084695633");
		clickid("new UiSelector().text(\"성별(선택)\")");

		// 인증번호 입력
		String strCode = duplicateNumber();

		if (strCode.isEmpty() == false) {
			String code = getNumber(strCode);
			System.out.println(code);
			clickid("new UiSelector().resourceId(\"net.everythingandroid.smspopup:id/button1\")");
			clickid("new UiSelector().text(\"수신된 인증번호 입력(예) 12345\")");
			sendkey(code);
			clickid("new UiSelector().resourceId(\"_btnMobileVerifyCode\")");
			clickid("new UiSelector().index(0).text(\"확인\")");

		}

		// 성별, 마케팅
		clickid("new UiSelector().text(\"남자\")");
		clickid("new UiSelector().text(\"전체동의\")");

		// 회원가입 버튼
		clickid("new UiSelector().text(\"가입하기\")");

		if (testResult() == 1) {
			System.out.print(fail_signup);
		}
		//
		// TestResult r = testResult(a);
		// if (r.getCode() == TestCode.TEST_SUCCESS) {
		// pring(r.getMessage())
		// }

	}
}