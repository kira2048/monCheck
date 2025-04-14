package jp.project.action.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.project.action.Action;

public class ActionRecoru implements Action {

	@Override
	public void action(WebDriver driver, int site) throws InterruptedException {

		// 明示的な待機を設定（最大10秒待機）
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		LocalDateTime currentTime = LocalDateTime.now();

		DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formatNowDate = dtf3.format(currentTime);

		try {
			// <tr>要素がクリック可能になるまで待機
			WebElement row = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//tr[@onclick=\"loadAttendanceEditDialog('6012','" + formatNowDate
							+ "','1', 'false');\"]")));

			// クリックを実行
			row.click();

			// ドロップダウン要素をクリック可能になるまで待機
			WebElement dropDownElement = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector(".ID\\-attendKbn\\-" + formatNowDate + "\\-1")));
			Select dropDown = new Select(dropDownElement);

			// 表示テキストで完全一致するオプションを選択
			if(site == 0) {
				dropDown.selectByVisibleText("顧客業務(オフサイト)");								
			} else {
				dropDown.selectByVisibleText("顧客業務(オンサイト)");				
			}

			WebElement startDate = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector(".ID\\-worktimeStart\\-" + formatNowDate + "\\-1")));
			WebElement endDate = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector(".ID\\-worktimeEnd\\-" + formatNowDate + "\\-1")));

			// チェックボックスのラベルをクリック
			WebElement label = driver.findElement(By.xpath("//label[@for='chartDto.attendanceDtos[0].approved_1']"));
			label.click();

			startDate.sendKeys("08:30");
			endDate.sendKeys("17:30");

			// <img> 要素を取得してクリック
			WebElement editIcon = driver.findElement(By.cssSelector("img.tip[onclick*='loadBreaktimeEditDialog']"));
			editIcon.click();

			WebElement restStart = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector(".ID\\-breaktimeStart\\-1 ")));
			restStart.sendKeys("11:30");

			WebElement restEnd = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector(".ID\\-breaktimeEnd\\-1 ")));
			restEnd.sendKeys("12:30");

			WebElement updateRestButton = driver
					.findElement(By.cssSelector("input.submit[onclick*='updateBreaktimeEditDialog']"));
			updateRestButton.click();

			Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
			alert1.accept(); // OKボタンをクリック

			Thread.sleep(3000);

			WebElement updateButton = driver
					.findElement(By.cssSelector("input.submit[onclick*='updateAttendanceEditDialog']"));
			updateButton.click();

			Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
			alert2.accept(); // OKボタンをクリック

			Thread.sleep(3000);
			System.out.println("クリック成功！");
		} catch (Exception e) {
			// エラー処理
			System.err.println("要素が見つからない、またはクリックできません: " + e.getMessage());
		} finally {
			// ブラウザを閉じる
			driver.quit();
		}
	}
}