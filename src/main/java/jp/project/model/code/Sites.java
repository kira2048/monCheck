package jp.project.model.code;

public enum Sites {
	OFFSITE("顧客業務(オフサイト)", "在宅チャージ", "0"), 
	ONSITE("顧客業務(オンサイト)", "通勤費(通常勤務地)", "1"),
	OFFSITEIN("社内業務(オフサイト)", "社内業務／ハモデイ・会議等", "2"),
	ONSITEIN("社内業務(オンサイト)", "社内業務／ハモデイ・会議等", "3");
	
	private String recoru;
	private String rakuraku;
	private String code;
	
	Sites(String recoru, String rakuraku, String code){
		this.recoru = recoru;
		this.rakuraku = rakuraku;
		this.code = code;
	}
	
	public String getRecoru() {
		return recoru;
	}
	
	public String getRakuraku() {
		return rakuraku;
	}
	
	public String getCode() {
		return code;
	}

	public static String fromText(String text) {
		for (Sites site : Sites.values()) {
			if (site.getRecoru().equals(text) || site.getRakuraku().equals(text)) {
				return site.getCode();
			}
		}
		return null; // 該当なし
	}

}
