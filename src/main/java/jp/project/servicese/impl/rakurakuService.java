package jp.project.servicese.impl;

import org.openqa.selenium.WebDriver;

import jp.project.action.impl.ActionRakuraku;
import jp.project.login.impl.LoginRakuraku;
import jp.project.model.code.Servicese;
import jp.project.servicese.Service;

public class rakurakuService implements Service {

	LoginRakuraku login = new LoginRakuraku();

	ActionRakuraku action = new ActionRakuraku();

	@Override
	public String getServiceName() {
		return Servicese.RAKURAKU.getName();

	}

	@Override
	public void executeSteps(WebDriver driver) throws Exception {
		System.out.println("Executing " + getServiceName() + " steps...");

		// ステップ1: ログイン
		System.out.println("Logging in to " + getServiceName());
		// ログイン処理
		login.login(driver, getServiceName());

		// ステップ2: データ取得
		System.out.println("Retrieving data from " + getServiceName());
		// データ取得処理
//		action.action(driver);

		// ステップ3: ログアウト
		System.out.println("Logging out from " + getServiceName());
		// ログアウト処理
	}

}
