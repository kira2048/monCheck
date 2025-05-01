package notice;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.project.config.AppSettings;
import jp.project.config.ConfigLoader;

public class LineBot {
    public static void makeMessage(Boolean flg) {
    	AppSettings config = ConfigLoader.load();
        String userId = config.getLine().getUserId(); // メッセージを送信するユーザーのID
        String message = new String();
        if (flg) {
        	message = "出勤簿に誤りはありません";
        } else {
        	message = "出勤簿に誤りがあります";
        }

        try {

        	URL url = new URL(config.getLine().getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // リクエストメソッドをPOSTに設定
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + config.getLine().getToken());
            connection.setDoOutput(true);

            // メッセージ内容のJSONを作成
            String jsonPayload = String.format(
                "{\"to\":\"%s\",\"messages\":[{\"type\":\"text\",\"text\":\"%s\"}]}",
                userId, message);

            // リクエストボディにJSONを送信
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // レスポンスコードを取得して確認
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // レスポンス内容を取得して表示
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // エラーが発生した場合のレスポンス内容を表示
                try (var reader = new java.io.InputStreamReader(connection.getErrorStream())) {
                    var errorResponse = new java.io.BufferedReader(reader).lines().reduce("", String::concat);
                    System.out.println("Error Response: " + errorResponse);
                }
            }        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
