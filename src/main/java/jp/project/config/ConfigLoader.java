package jp.project.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "/config.properties";

    public static AppSettings load() {
        Properties props = new Properties();
        try (InputStream input = ConfigLoader.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("設定ファイルが見つかりません: " + CONFIG_FILE);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("設定ファイルの読み込み中にエラーが発生しました", e);
        }

        String email = props.getProperty("general.email");

        AppSettings.ServiceCredentials rakuraku = new AppSettings.ServiceCredentials(
            props.getProperty("rakuraku.id"),
            props.getProperty("rakuraku.password")
        );

        AppSettings.ServiceCredentials recoru = new AppSettings.ServiceCredentials(
            props.getProperty("recoru.id"),
            props.getProperty("recoru.password")
        );
        
        AppSettings.WebCredentials line = new AppSettings.WebCredentials(
        		props.getProperty("line.user-id"),
        		props.getProperty("line.token"),
        		props.getProperty("line.url")
        		);

        return new AppSettings(email, rakuraku, recoru, line);
    }
}
