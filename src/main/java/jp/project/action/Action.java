package jp.project.action;

import java.util.List;

import org.openqa.selenium.WebDriver;

public interface Action {
	List<String> action(WebDriver driver) throws InterruptedException;
}
