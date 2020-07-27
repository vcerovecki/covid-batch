package hr.covidbatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PodaciDetaljno {

	@JsonProperty("broj_zarazenih")
	private int brojZarazenih;
	@JsonProperty("broj_umrlih")
	private int brojUmrlih;
	@JsonProperty("Zupanija")
	private String zupanija;
	
}
