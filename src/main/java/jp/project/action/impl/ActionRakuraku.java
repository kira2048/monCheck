package jp.project.action.impl;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.project.action.Action;
import jp.project.util.SeleniumUtil;

public class ActionRakuraku implements Action {

	// Loggerのインスタンスを作成
	private static final Logger logger = Logger.getLogger(ActionRakuraku.class.getName());
	private static final Duration WAIT_DURATION = Duration.ofSeconds(10); // 待機時間の共通定義

	@Override
	public void action(WebDriver driver, int site) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_DURATION);

		try {
			logger.info("アクションを開始します。");

			// <frame>に切り替え
			logger.info("フレーム切り替えを開始します。");
			waitUntilFrameAvailableAndSwitchToIt(driver, wait);
			logger.info("フレーム切り替えが完了しました。");

			// 交通費精算画面を開く
			logger.info("交通費精算を開く処理を開始します。");
			openNewTabAndSwitch(driver, wait);
			logger.info("交通費精算を開きました。");

			// 修正画面を開く
			logger.info("交通費精算画面を開く処理を開始します。");
			//			openEditPage(driver, wait);
			SeleniumUtil.clickElementByXPath(driver, wait,
					"//a[contains(@href, 'detailView') and contains(@class, 'w_denpyo_l')]");
			SeleniumUtil.getLastWindow(driver, wait);
			logger.info("交通費精算画面を開きました。");

			// 「マイパターン」ボタンをクリック
			logger.info("「マイパターン」ボタンのクリック処理を開始します。");
			SeleniumUtil.clickElementByXPath(driver, wait,
					"//p[contains(@class, 'denpyo-quick-mypattern')]/ancestor::button");
			//			clickMyPatternButton(driver, wait);
			logger.info("「マイパターン」ボタンを正常にクリックしました。");

			// 「マイパターン」ボタンをクリック
			logger.info("「マイパターン一覧」画面を開く処理を実行します。");
			SeleniumUtil.getLastWindow(driver, wait);
			//			checkMyPatternButton(driver, wait);
			logger.info("「マイパターン」ボタンを正常にクリックしました。");

			if(site == 0) {
				SeleniumUtil.clickElementByXPath(driver, wait, "//*[@id=\"kakutei_0_8591\"]");				
			} else {
				SeleniumUtil.clickElementByXPath(driver, wait, "//*[@id=\"kakutei_0_8913\"]");
			}
			SeleniumUtil.clickElementByXPath(driver, wait, "//*[@id=\"d_footer\"]/div[1]/button");
			SeleniumUtil.getLastWindow(driver, wait);
			WebElement element = driver.findElement(By.cssSelector(".meisaiGyo.d_meisai_gyo"));

			// 要素のID属性を取得
			String elementId = element.getDomProperty("id");

			// 結果を出力
			System.out.println("要素のID: " + elementId);
			SeleniumUtil.sendkeyByXPath(driver, wait,
					"//*[@id=\"" + elementId + "\"]/tr[1]/td[2]/table/tbody/tr/td[1]/div/div/input[1]");

			SeleniumUtil.clickElementByXPath(driver, wait, "//*[@id=\"d_denpyo\"]/form/div/div[5]/div/button[3]");
			Thread.sleep(5000);

			// 操作が終わったら元のページに戻す
			logger.info("処理が完了しました");

		} catch (Exception e) {
			// 例外が発生した場合はエラーログを記録
			logger.severe("エラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void waitUntilFrameAvailableAndSwitchToIt(WebDriver driver, WebDriverWait wait) {
		try {
			logger.info("フレームが利用可能になるのを待機中...");
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("main")));
			logger.info("フレームへの切り替えに成功しました。");
		} catch (Exception e) {
			logger.severe("フレームへの切り替え中にエラーが発生しました: " + e.getMessage());
			throw e;
		}
	}

	private void openNewTabAndSwitch(WebDriver driver, WebDriverWait wait) {
		try {
			logger.info("リンク要素を取得中...");
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//a[@href='/CSR9KsE9qUa/sapKotsuhiKensaku/tmpView' and contains(@class, 'd_title')]")));
			link.click();
			logger.info("リンクを正常にクリックしました。");

			String currentWindowHandle = driver.getWindowHandle();
			logger.info("現在のウィンドウハンドル: " + currentWindowHandle);

			// 新しいウィンドウまたはタブに切り替える処理
			switchToNewWindow(driver, currentWindowHandle);

			// 新しい画面が完全に読み込まれるまで待機
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
			logger.info("新しいタブに切り替え、ページが完全に読み込まれました。");
		} catch (Exception e) {
			logger.severe("交通費精算画面のオープン中にエラーが発生しました: " + e.getMessage());
			throw e;
		}
	}

	private void switchToNewWindow(WebDriver driver, String currentWindowHandle) {
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(currentWindowHandle)) {
				logger.info("新しいウィンドウに切り替えます: " + windowHandle);
				driver.switchTo().window(windowHandle);
				logger.info("新しいウィンドウに切り替えました。");
				break;
			}
		}
	}

	private void switchBackToOriginalWindow(WebDriver driver) {
		try {
			String originalWindowHandle = driver.getWindowHandle();
			logger.info("元のウィンドウに戻ります: " + originalWindowHandle);
			driver.switchTo().window(originalWindowHandle);
			logger.info("元のウィンドウに戻りました。");
		} catch (Exception e) {
			logger.severe("元のウィンドウに戻る中にエラーが発生しました: " + e.getMessage());
			throw e;
		}
	}
}