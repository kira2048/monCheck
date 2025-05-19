package jp.project.model.code;

public enum Payment {

	OFFSITE("在宅チャージ", "0"), 
	ONSITE("通勤費(通常勤務地)", "1"),
	ONSITEIN("社内業務／ハモデイ・会議等", "2");
	
	private String rakuraku;
	private String code;
	
	Payment(String rakuraku, String code){
		this.rakuraku = rakuraku;
		this.code = code;
	}
	
	public String getRakuraku() {
		return rakuraku;
	}
	
	public String getCode() {
		return code;
	}

	public static String fromText(String text) {
		for (Payment payment : Payment.values()) {
			if (payment.getRakuraku().equals(text)) {
				return payment.getCode();
			}
		}
		return null; // 該当なし
	}
}
