package hr.covidbatch.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PodaciPoZupanijama implements Entity{

	@JsonProperty("Datum")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+02:00")
	private Timestamp datum;
	
	@JsonProperty("PodaciDetaljno")
	private List<PodaciDetaljno> podaciDetaljno;
	
}
