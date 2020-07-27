package hr.covidbatch.writers;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import hr.covidbatch.dao.PodaciUkupnoDao;
import hr.covidbatch.model.PodaciUkupno;

public class PodaciUkupnoWriter implements ItemWriter<List<PodaciUkupno>>{
	
	@Autowired
	private PodaciUkupnoDao podaciUkupnoDao;

	@Override
	public void write(List<? extends List<PodaciUkupno>> items) throws Exception {
		List<PodaciUkupno> podaciUkupnoItems = items.get(0);
		podaciUkupnoItems.forEach(e -> podaciUkupnoDao.insert(e));
	}


}
