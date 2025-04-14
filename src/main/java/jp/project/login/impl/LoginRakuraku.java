package jp.project.login.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jp.project.login.Login;

public class LoginRakuraku implements Login {

	@Override
	public WebDriver login(WebDriver driver, String value) {
		System.out.println("取得完了" + driver.getTitle());

		WebElement idElement = driver.findElement(By.name("loginId"));
		WebElement passElement = driver.findElement(By.name("password"));
		idElement.sendKeys("2024035");
		passElement.sendKeys("Kira-010728");

		List<WebElement> webElementList = new ArrayList<>();
		for (WebElement element : webElementList) {
			element.submit();
		}

		WebElement buttonElement = driver.findElement(By.id("submitBtn"));
		buttonElement.click();

		return driver;
	}

}
