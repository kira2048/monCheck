package jp.project.action.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.project.action.Action;
import jp.project.util.SeleniumUtil;

public class ActionRecoru implements Action {

	@Override
	public void action(WebDriver driver) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formatNowDate = dtf3.format(currentTime);

		try {
			// <tr>要素クリック
			//            String rowXpath = "//tr[@onclick=\"loadAttendanceEditDialog('6012','" + formatNowDate + "','1', 'false');\"]";
			//            SeleniumUtil.clickElementByXPath(driver, wait, rowXpath);

			YearMonth thisMonth = YearMonth.now(); // 今の年月（例：2025-04）
			int daysInMonth = thisMonth.lengthOfMonth(); // その月の日数（30とか31）

			for (int day = 1; day <= daysInMonth; day++) {
				int trNumber = day + 3; // tr[4]が1日だから、+3する
				String xpath = "//*[@id='ID-attendanceChartGadgetTable']/tbody/tr[" + trNumber + "]/td[2]";
				String text = SeleniumUtil.getTextByXPath(wait, xpath);
				System.out.println(day + "日のデータ: " + text);
			}

			// アラートを待ってOK
			Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
			alert1.accept();

			Thread.sleep(3000);

			// 再度アラートを待ってOK
			Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
			alert2.accept();

			Thread.sleep(3000);
			System.out.println("クリック成功！");
		} catch (Exception e) {
			System.err.println("要素が見つからない、またはクリックできません: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}
}
