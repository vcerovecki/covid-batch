package hr.covidbatch.processors;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import hr.covidbatch.model.PodaciPoZupanijama;

public class PodaciPoZupanijamaProcessor implements ItemProcessor<List<PodaciPoZupanijama>, List<PodaciPoZupanijama>>{

	@Override
	public List<PodaciPoZupanijama> process(List<PodaciPoZupanijama> item) throws Exception {
		return item;
	}

}
