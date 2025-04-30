package jp.project.servicese.impl;

import java.util.List;

import org.openqa.selenium.WebDriver;

import jp.project.action.impl.ActionRecoru;
import jp.project.login.impl.LoginRecoru;
import jp.project.model.code.Servicese;
import jp.project.servicese.Service;

public class recoruService implements Service {

	LoginRecoru login = new LoginRecoru();
	ActionRecoru action = new ActionRecoru();

	@Override
	public String getServiceName() {
		return Servicese.RECORU.getName();

	}

	@Override
	public List<String> executeSteps(WebDriver driver) throws Exception {
		System.out.println("Executing Service A steps...");

		// ステップ1: ログイン
		System.out.println("Logging in to Service A");
		login.login(driver, getServiceName());
		// ステップ2: データ取得
		System.out.println("Retrieving data from Service A");
		List<String> sites = action.action(driver);

		// データ取得処理

		// ステップ3: ログアウト
		System.out.println("Logging out from Service A");
		// ログアウト処理
		return sites;
	}

}
