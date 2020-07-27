package hr.covidbatch.writers;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import hr.covidbatch.dao.PodaciPoZupanijamaDao;
import hr.covidbatch.model.PodaciPoZupanijama;

public class PodaciPoZupanijamaWriter implements ItemWriter<List<PodaciPoZupanijama>>{

	@Autowired
	private PodaciPoZupanijamaDao podaciPoZupanijamaDao;
	
	@Override
	public void write(List<? extends List<PodaciPoZupanijama>> items) throws Exception {
		List<PodaciPoZupanijama> itemList = items.get(0);
		for(PodaciPoZupanijama podaciPoZupanijama : itemList) {
			podaciPoZupanijamaDao.insert(podaciPoZupanijama);
		}
	}

}
