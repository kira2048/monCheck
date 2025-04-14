package jp.project.model.code;

public enum Servicese {
	RECORU("https://app.recoru.in/ap/", "Recoru"), RAKURAKU("https://rsclef.rakurakuseisan.jp/CSR9KsE9qUa/", "楽楽精算");

	private String link;
	private String name;

	Servicese(String link, String name) {
		this.name = name;
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

}
