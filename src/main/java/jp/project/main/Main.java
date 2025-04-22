package jp.project.main;

import jp.project.config.AppSettings;
import jp.project.config.ConfigLoader;

public class Main {

	public static void main(String[] args) {
        AppSettings config = ConfigLoader.load();

        System.out.println("メール: " + config.getEmail());
        System.out.println("Rakuraku ID: " + config.getRakuraku().getId());
        System.out.println("Recoru パスワード: " + config.getRecoru().getPassword());	}

}
