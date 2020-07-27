package hr.covidbatch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import hr.covidbatch.dao.PodaciUkupnoDao;
import hr.covidbatch.model.PodaciUkupno;

@Service
public class PodaciUkupnoDaoImpl implements PodaciUkupnoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(PodaciUkupno pu) {
		String sql = "INSERT INTO defaultdb.PODACI_UKUPNO(SLUCAJEVI_SVIJET, SLUCAJEVI_HRVATSKA, UMRLI_SVIJET, UMRLI_HRVATSKA, IZLIJECENI_SVIJET, IZLIJECENI_HRVATSKA, DATUM) VALUES (?, ?, ?, ?, ?, ?, ?)";
		final Object[] params = new Object[] {pu.getSlucajeviSvijet(), pu.getSlucajeviHrvatska(), pu.getUmrliSvijet(), pu.getUmrliHrvatska(), pu.getIzlijeceniSvijet(), pu.getIzlijeceniHrvatska(), pu.getDatum()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public PodaciUkupno getLast() {
		String sql = "SELECT PU.ID_PODACI_UKUPNO, PU.SLUCAJEVI_SVIJET, PU.SLUCAJEVI_HRVATSKA, PU.UMRLI_SVIJET, PU.UMRLI_HRVATSKA, PU.IZLIJECENI_SVIJET, PU.IZLIJECENI_HRVATSKA, PU.DATUM FROM defaultdb.PODACI_UKUPNO PU ORDER BY PU.DATUM DESC LIMIT 1";
		try {
			PodaciUkupno pu = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PodaciUkupno>(PodaciUkupno.class));
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
