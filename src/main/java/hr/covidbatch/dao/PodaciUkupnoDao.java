package hr.covidbatch.dao;

import hr.covidbatch.model.PodaciUkupno;

public interface PodaciUkupnoDao {

	public void insert(PodaciUkupno pu);
	
	public PodaciUkupno getLast();
	
}
