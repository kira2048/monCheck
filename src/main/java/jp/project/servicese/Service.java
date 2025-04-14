package jp.project.servicese;

import org.openqa.selenium.WebDriver;

public interface Service {

	String getServiceName();

	void executeSteps(WebDriver driver, int site) throws Exception;
}
