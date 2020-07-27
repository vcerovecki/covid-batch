package hr.covidbatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import hr.covidbatch.dao.PodaciUkupnoDao;
import hr.covidbatch.model.PodaciUkupno;

@Controller
public class Runner {

	@Autowired
	private PodaciUkupnoDao podaciUkupnoDao;
	
	@GetMapping("/")
	public void run() {
		podaciUkupnoDao.insert(new PodaciUkupno());
	}
}
