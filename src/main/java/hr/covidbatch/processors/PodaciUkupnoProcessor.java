package hr.covidbatch.processors;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import hr.covidbatch.model.PodaciUkupno;

public class PodaciUkupnoProcessor implements ItemProcessor<List<PodaciUkupno>, List<PodaciUkupno>>{

	@Override
	public List<PodaciUkupno> process(List<PodaciUkupno> item) throws Exception {
		return item;
	}

}
