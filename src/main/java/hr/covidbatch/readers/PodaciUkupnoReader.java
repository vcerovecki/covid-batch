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

import hr.covidbatch.dao.PodaciUkupnoDao;
import hr.covidbatch.model.PodaciUkupno;

public class PodaciUkupnoReader implements ItemReader<List<PodaciUkupno>>{
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@Autowired
	private PodaciUkupnoDao podaciUkupnoDao;

	@Override
	public List<PodaciUkupno> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		ResponseEntity<PodaciUkupno[]> responseEntity = restTemplate.getForEntity("https://www.koronavirus.hr/json/?action=podaci_zadnji", PodaciUkupno[].class);
		PodaciUkupno[] podaciUkupno = responseEntity.getBody();
		List<PodaciUkupno> servicePodaciUkupnoList = Arrays.asList(podaciUkupno);
		PodaciUkupno podaciUkupnoLastInDb = podaciUkupnoDao.getLast();
		Comparator<PodaciUkupno> comparator = (PodaciUkupno p1, PodaciUkupno p2) -> p2.getDatum().compareTo(p1.getDatum());
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
