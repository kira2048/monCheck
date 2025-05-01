package jp.project.config;

public class AppSettings {

    private final String email;
    private final ServiceCredentials rakuraku;
    private final ServiceCredentials recoru;
    private final WebCredentials line;

    public AppSettings(String email, ServiceCredentials rakuraku, ServiceCredentials recoru, WebCredentials line) {
        this.email = email;
        this.rakuraku = rakuraku;
        this.recoru = recoru;
        this.line = line;
    }

    public String getEmail() {
        return email;
    }

    public ServiceCredentials getRakuraku() {
        return rakuraku;
    }

    public ServiceCredentials getRecoru() {
        return recoru;
    }
    
    public WebCredentials getLine() {
    	return line;
    }

    // サービス資格情報を保持する小さなクラス
    public static class ServiceCredentials {
        private final String id;
        private final String password;

        public ServiceCredentials(String id, String password) {
            this.id = id;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public String getPassword() {
            return password;
        }
    }
    
    // サービス資格情報を保持する小さなクラス
    public static class WebCredentials {
    	private final String userId;
    	private final String token;
    	private final String url;
    	
    	public WebCredentials(String userId, String token, String url) {
    		this.userId = userId;
    		this.token = token;
    		this.url = url;
    	}
    	
    	public String getUserId() {
    		return userId;
    	}
    	
    	public String getToken() {
    		return token;
    	}
    	
    	public String getUrl() {
    		return url;
    	}
    }
}
