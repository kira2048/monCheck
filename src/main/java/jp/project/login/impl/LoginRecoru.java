package jp.project.login.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jp.project.config.AppSettings;
import jp.project.config.ConfigLoader;
import jp.project.login.Login;

public class LoginRecoru implements Login {

	@Override
	public WebDriver login(WebDriver driver, String value) {
		System.out.println("取得完了" + driver.getTitle());

        AppSettings config = ConfigLoader.load();

		WebElement idElement = driver.findElement(By.name("contractId"));
		WebElement mailElement = driver.findElement(By.name("authId"));
		WebElement passElement = driver.findElement(By.name("password"));
		idElement.sendKeys(config.getRecoru().getId());
		mailElement.sendKeys(config.getEmail());
		passElement.sendKeys(config.getRecoru().getPassword() );

		List<WebElement> webElementList = new ArrayList<>();
		for (WebElement element : webElementList) {
			element.submit();
		}

		WebElement buttonElement = driver.findElement(By.className("submit"));
		buttonElement.click();

		return driver;

	}

}
