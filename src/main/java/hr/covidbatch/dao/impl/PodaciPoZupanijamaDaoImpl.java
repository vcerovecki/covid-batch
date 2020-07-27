package hr.covidbatch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import hr.covidbatch.dao.PodaciPoZupanijamaDao;
import hr.covidbatch.enums.ZupanijeEnum;
import hr.covidbatch.model.PodaciDetaljno;
import hr.covidbatch.model.PodaciPoZupanijama;
import hr.covidbatch.model.PodaciUkupno;

@Repository
public class PodaciPoZupanijamaDaoImpl implements PodaciPoZupanijamaDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insert(PodaciPoZupanijama podaciPoZupanijama) {
		String sql = "INSERT INTO defaultdb.PODACI_PO_ZUPANIJAMA(BROJ_ZARAZENIH, BROJ_UMRLIH, ID_ZUPANIJE, DATUM) VALUES (?, ?, ?, ?)";
		for(PodaciDetaljno podaciDetaljno : podaciPoZupanijama.getPodaciDetaljno()) {
			PodaciDetaljno pd = podaciDetaljno;
			if(pd != null && StringUtils.hasLength(pd.getZupanija())) {
				ZupanijeEnum zupanija = ZupanijeEnum.getByNaziv(StringUtils.trimWhitespace(pd.getZupanija()));
				if(zupanija != null) {
					final Object[] params = new Object[] {pd.getBrojZarazenih(), pd.getBrojUmrlih(), zupanija.getId(), podaciPoZupanijama.getDatum()};
					jdbcTemplate.update(sql, params);
				}
			}
		}
	}

	@Override
	public PodaciPoZupanijama getLast() {
		String sql = "SELECT PPZ.ID_PODACI_PO_ZUPANIJAMA, PPZ.ID_ZUPANIJE, PPZ.DATUM FROM defaultdb.PODACI_PO_ZUPANIJAMA PPZ ORDER BY PPZ.DATUM DESC LIMIT 1";
		try {
			PodaciPoZupanijama pu = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PodaciPoZupanijama>(PodaciPoZupanijama.class));
			return pu;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
