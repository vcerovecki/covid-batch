package hr.covidbatch.readers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import hr.covidbatch.dao.PodaciPoZupanijamaDao;
import hr.covidbatch.model.PodaciPoZupanijama;

public class PodaciPoZupanijamaReader implements ItemReader<List<PodaciPoZupanijama>>{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PodaciPoZupanijamaDao podaciPoZupanijamaDao;

	@Override
	public List<PodaciPoZupanijama> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		ResponseEntity<PodaciPoZupanijama[]> responseEntity = restTemplate.getForEntity("https://www.koronavirus.hr/json/?action=po_danima_zupanijama", PodaciPoZupanijama[].class);
		PodaciPoZupanijama[] podaciUkupno = responseEntity.getBody();
		List<PodaciPoZupanijama> servicePodaciUkupnoList = Arrays.asList(podaciUkupno);
		PodaciPoZupanijama podaciUkupnoLastInDb = podaciPoZupanijamaDao.getLast();
		Comparator<PodaciPoZupanijama> comparator = (PodaciPoZupanijama p1, PodaciPoZupanijama p2) -> p2.getDatum().compareTo(p1.getDatum());
		Collections.sort(servicePodaciUkupnoList, comparator);
		if(podaciUkupnoLastInDb != null && servicePodaciUkupnoList != null) {
			if(getDateFromTimestamp(podaciUkupnoLastInDb.getDatum()).equals(getDateFromTimestamp(servicePodaciUkupnoList.get(0).getDatum()))) {
				return null;
			}
		}
		return servicePodaciUkupnoList;
	}
	
	private Timestamp getDateFromTimestamp(Timestamp tstamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tstamp.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

}
