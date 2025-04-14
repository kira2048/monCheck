package jp.project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {

	private static final Logger logger = Logger.getLogger(SeleniumUtil.class.getName());

	public static void getLastWindow(WebDriver driver, WebDriverWait wait) throws TimeoutException {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();
			System.out.println("現在のウィンドウハンドル一覧: " + allWindowHandles);

			String lastWindowHandle = null;

			for (String handle : allWindowHandles) {
				driver.switchTo().window(handle);

				// タイトル取得をリトライ
				String title = retryGetTitle(driver, 5, 500);
				System.out.println("取得したウィンドウタイトル: " + title);

				// タイトルが空でない場合、最後のウィンドウとする
				if (!title.isEmpty()) {
					lastWindowHandle = handle;
				}
			}

			// 最後のウィンドウに切り替え
			if (lastWindowHandle != null) {
				driver.switchTo().window(lastWindowHandle);
				System.out.println("最後のウィンドウに切り替えました: " + driver.getTitle());
			} else {
				System.out.println("ウィンドウの切り替えに失敗しました。");
			}
		} catch (Exception e) {
			logger.severe("予期せぬエラーが発生しました: " + e.getMessage());
			throw e;
		}

	}

	// タイトル取得リトライメソッド
	private static String retryGetTitle(WebDriver driver, int maxRetries, int delayMillis) {
		for (int i = 0; i < maxRetries; i++) {
			String title = driver.getTitle();
			if (!title.isEmpty()) {
				return title;
			}
			try {
				Thread.sleep(delayMillis); // 少し待機して再取得
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		return ""; // タイトルが取得できない場合は空文字を返す
	}

	public static void clickElementByXPath(WebDriver driver, WebDriverWait wait, String xpath) {
		try {

			// 指定されたXPathで要素を特定
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

			if (element != null && element.isDisplayed()) {

				// 通常のクリック処理
				element.click();
			} else {
				logger.info("要素が存在していません");
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public static void sendkeyByXPath(WebDriver driver, WebDriverWait wait, String xpath) {
		try {
			// 指定されたXPathで要素を特定
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

			if (element != null && element.isDisplayed()) {

				LocalDateTime currentTime = LocalDateTime.now();

				DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				String formatNowDate = dtf3.format(currentTime);
				// 通常のクリック処理
				element.sendKeys(formatNowDate);
			} else {
				logger.info("要素が存在していません");
			}

		} catch (Exception e) {
			throw e;
		}
	}
}
