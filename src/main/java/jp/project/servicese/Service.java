package jp.project.servicese;

import java.util.List;

import org.openqa.selenium.WebDriver;

public interface Service {

	String getServiceName();

	List<String> executeSteps(WebDriver driver) throws Exception;
}
