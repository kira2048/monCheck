package jp.project.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.openqa.selenium.WebDriver;

import jp.project.config.ChromeDriverFactory;
import jp.project.model.code.Servicese;
import jp.project.notice.LineBot;
import jp.project.servicese.Service;
import jp.project.servicese.impl.rakurakuService;
import jp.project.servicese.impl.recoruService;

public class Main {
	
	static List<Service> services = Arrays.asList(
			new recoruService(),
			new rakurakuService());

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(services.size());
		List<Future<List<String>>> futures = new ArrayList<>();

		try {
			for (Service service : services) {
				Future<List<String>> future = executorService.submit(() -> {
					WebDriver driver = null;
					try {
						driver = ChromeDriverFactory.createWebDriver();

						String url = service.getServiceName().equals(Servicese.RECORU.getName())
								? Servicese.RECORU.getLink()
								: Servicese.RAKURAKU.getLink();

						driver.get(url);
						return service.executeSteps(driver);  // ← sitesを返す
					} catch (Exception e) {
						e.printStackTrace();
						return Collections.emptyList();       // ← エラー時は空リスト
					} finally {
						if (driver != null) {
							driver.quit();
						}
					}
				});
				futures.add(future);
			}
		} finally {
			executorService.shutdown();
		}

		// === sites の比較 ===
		try {
			if (futures.size() >= 2) {
				List<String> sites1 = futures.get(0).get();
				List<String> sites2 = futures.get(1).get();
				System.out.println(sites1.toString());
				System.out.println(sites2.toString());
				
				System.out.println(sites1.size() == sites2.size());
				
				boolean result = sites1.equals(sites2);
				LineBot.makeMessage(result);

				System.out.println(result);

				// 共通のサイト
				List<String> common = sites1.stream()
						.filter(sites2::contains)
						.toList();

				System.out.println("共通: " + common);
			} else {
				System.out.println("比較対象が2つ未満です。");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
