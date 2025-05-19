package jp.project.model.code;

public enum Sites {
	OFFSITE("顧客業務(オフサイト)", "0"), 
	ONSITE("顧客業務(オンサイト)","1"),
	ONSITEIN("社内業務(オンサイト)","2"),
	OFFSITEIN("社内業務(オフサイト)", "3"),
	FULLREST("有給休暇(全休)", "4");
	
	private String recoru;
	private String code;
	
	Sites(String recoru, String code){
		this.recoru = recoru;
		this.code = code;
	}
	
	public String getRecoru() {
		return recoru;
	}
	
	public String getCode() {
		return code;
	}

	public static String fromText(String text) {
		for (Sites site : Sites.values()) {
			if (site.getRecoru().equals(text)) {
				return site.getCode();
			}
		}
		return null; // 該当なし
	}

}
