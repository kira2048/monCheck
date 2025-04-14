package jp.project.action;

import org.openqa.selenium.WebDriver;

public interface Action {
	void action(WebDriver driver, int site) throws InterruptedException;
}
