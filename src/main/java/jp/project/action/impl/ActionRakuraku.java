package jp.project.action.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
	public void action(WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_DURATION);
		List<String> sites = new ArrayList<>();

		try {

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

//			List<WebElement> meisaiList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
//				    By.xpath("//tbody[starts-with(@id, 'meisai')]")));			
//			System.out.println(meisaiList.toString());
			
			// 1. tbodyを表示待ちしてから取得
			List<WebElement> tbodies = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//tbody[starts-with(@id, 'meisai')]")
			));
			int tbodyCount = tbodies.size();

			// 2. 各inputを安全に取得
			for (int i = 0; i < tbodyCount; i++) {
			    List<WebElement> inputs = driver.findElements(By.xpath("//*[@id='meisai" + i + "']/tr[2]/td/table/tbody/tr/td[2]/input"));
			    if (!inputs.isEmpty()) {
			        sites.add(inputs.get(0).getAttribute("value"));
			    } else {
			        logger.warning("meisai" + i + " の input が見つかりませんでした。");
			    }
			}
			
			System.out.println(sites.toString());
			System.out.println("tbodyの数: " + tbodyCount);

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

}