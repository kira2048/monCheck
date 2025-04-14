package jp.project.login;

import org.openqa.selenium.WebDriver;

public interface Login {
	// ログイン処理を実行する
	public WebDriver login(WebDriver driver, String value);
}
