package hr.covidbatch.enums;

public enum ZupanijeEnum {

	BJELOVARSKO_BILOGORSKA(1, "Bjelovarsko-bilogorska"),
	BRODSKO_POSAVSKA(2, "Brodsko-posavska"),
	DUBROVACKO_NERETVANSKA(3, "Dubrovačko-neretvanska"),
	GRAD_ZAGREB(4, "Grad Zagreb"),
	ISTARSKA(5, "Istarska"),
	KARLOVACKA(6, "Karlovačka"),
	LICKO_SENJSKA(7, "Ličko-senjska"),
	KRAPINSKO_ZAGORSKA(8, "Krapinsko-zagorska županija"),
	KOPRIVNICKO_KRIZEVACKA(9, "Koprivničko-križevačka"),
	MEDIMUSKA(10, "Međimurska"),
	OSJECKO_BARANJSKA(11, "Osječko-baranjska"),
	POZESKO_SLAVONSKA(12, "Požeško-slavonska"),
	PRIMORSKO_GORANSKA(13, "Primorsko-goranska"),
	SIBENSKO_KNINSKA(14, "Šibensko-kninska"),
	SISACKO_MOSLAVACKA(15, "Sisačko-moslavačka"),
	SPLITSKO_DALMATINSKA(16, "Splitsko-dalmatinska"),
	VARAZDNISKA(17, "Varaždinska"),
	VIROVITIVKO_PODRAVSKA(18, "Virovitičko-podravska"),
	VUKOVARSKO_SRJEMSKA(19, "Vukovarsko-srijemska"),
	ZADARSKA(20, "Zadarska"),
	ZAGREBACKA(21, "Zagrebačka");
	
	private int id;
	private String naziv;
	
	
	
	public int getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	private ZupanijeEnum(int id, String naziv) {
		this.id = id;
		this.naziv = naziv;
	}
	
	public static ZupanijeEnum getByNaziv(String naziv) {
		for(ZupanijeEnum ze : ZupanijeEnum.values()) {
			if(ze.getNaziv().equalsIgnoreCase(naziv)) {
				return ze;
			}
		}
		return null;
	}
	
}
