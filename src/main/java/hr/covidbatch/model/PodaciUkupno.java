package hr.covidbatch.model;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PodaciUkupno implements Entity{
	
	private Long idPodaciUkupno;
	@JsonProperty("SlucajeviSvijet")
	private Long slucajeviSvijet;
	@JsonProperty("SlucajeviHrvatska")
	private Long slucajeviHrvatska;
	@JsonProperty("UmrliSvijet")
	private Long umrliSvijet;
	@JsonProperty("UmrliHrvatska")
	private Long umrliHrvatska;
	@JsonProperty("IzlijeceniSvijet")
	private Long izlijeceniSvijet;
	@JsonProperty("IzlijeceniHrvatska")
	private Long izlijeceniHrvatska;
	@JsonProperty("Datum")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+02:00")
	private Timestamp datum;

}
