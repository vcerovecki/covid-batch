package hr.covidbatch.dao;

import hr.covidbatch.model.PodaciPoZupanijama;

public interface PodaciPoZupanijamaDao {

	public void insert(PodaciPoZupanijama podaciPoZupanijama);

	public PodaciPoZupanijama getLast();
	
}
