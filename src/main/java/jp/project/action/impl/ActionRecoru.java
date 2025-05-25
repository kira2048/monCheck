package jp.project.action.impl;

import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.project.action.Action;
import jp.project.model.code.Sites;
import jp.project.util.SeleniumUtil;

public class ActionRecoru implements Action {

	@Override
	public List<String> action(WebDriver driver) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		List<String> sites = new ArrayList<>(); 

		try {
			
			YearMonth thisMonth = YearMonth.now();
			int daysInMonth = thisMonth.lengthOfMonth();

			int day = 1;
			int rowIndex = 1;

			while (day <= daysInMonth) {
			    String xpath = "//*[@id='ID-attendanceChartGadgetTable']/tbody/tr[" + rowIndex + "]/td[2]";
			    String text = SeleniumUtil.getTextByXPath(wait, xpath);

			    String code = Sites.fromText(text);
			    if (code != null && !Sites.OFFSITEIN.getCode().equals(code) && !Sites.FULLREST.getCode().equals(code)) {
			        sites.add(code); // 有効なコードなら追加
			        day++; // 日付のカウントを進める
			    }
			    // 条件を満たさない（関係ない行）なら day は進めず、rowIndex だけ進めて次の行をチェック
			    rowIndex++;
			}


			// アラートを待ってOK
			Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
			alert1.accept();

			Thread.sleep(3000);
		} catch (Exception e) {
			System.err.println("要素が見つからない、またはクリックできません: " + e.getMessage());
		} finally {
			driver.quit();
		}
		return sites;
	}
}
