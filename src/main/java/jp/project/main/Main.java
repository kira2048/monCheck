package jp.project.main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.WebDriver;

import jp.project.config.ChromeDriverFactory;
import jp.project.model.code.Servicese;
import jp.project.servicese.Service;
import jp.project.servicese.impl.rakurakuService;
import jp.project.servicese.impl.recoruService;

public class Main {
	
	static List<Service> services = Arrays.asList(
			new recoruService(),
			new rakurakuService());

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(services.size());

		try {
			services.forEach(service -> executorService.submit(() -> {
				WebDriver driver = null;
				try {
					driver = ChromeDriverFactory.createWebDriver();

					String url = service.getServiceName().equals(Servicese.RECORU.getName())
							? Servicese.RECORU.getLink()
							: Servicese.RAKURAKU.getLink();

					driver.get(url);
					service.executeSteps(driver);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (driver != null) {
						driver.quit();
					}
				}
			}));
		} finally {
			executorService.shutdown();
		}
	}
}
