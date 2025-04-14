package jp.project.config;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

public class ChromeDriverFactory {

	public static WebDriver createWebDriver() {
		// ChromeOptionsを作成
		ChromeOptions options = new ChromeOptions();

		// ログの設定（ブラウザのログを全て取得）
		options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));

		// 必要に応じて他のオプションも設定（例: ヘッドレスモード、プロキシ設定など）
		// options.addArguments("--headless");

		// ChromeDriverを作成し、オプションを適用
		ChromeDriver driver = new ChromeDriver(options);

		// ログを出力する（必要に応じて）
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(entry.getMessage());
		}

		return driver;
	}
}
