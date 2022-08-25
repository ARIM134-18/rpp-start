package rppstart.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rppstart.jpa.Preduzece;
import rppstart.jpa.Radnik;
import rppstart.repository.RadnikRepository;

@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController
public class RadnikRestController {
	
	@Autowired
	private RadnikRepository radnikRepository;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@GetMapping("radnik")
	public Collection<Radnik> getRadnici() {
		return radnikRepository.findAll();
	}
	

	@GetMapping("radnik/{id}")
	public Radnik getRadnik(@PathVariable("id") Integer id) {
		return radnikRepository.getOne(id);
	}
	
	@GetMapping("radnikPrezime/{prezime}")
	public Collection<Radnik> getRadnikByPrezime(@PathVariable("prezime") String prezime) {  //VRACA PREKO NAZIVA
		return radnikRepository.findByPrezimeContainingIgnoreCase(prezime);
	}
	
	@PostMapping("radnik")
	public ResponseEntity<Radnik> insertRadnik(@RequestBody Radnik radnik) {
		if(!radnikRepository.existsById(radnik.getId()))
		{
			radnikRepository.save(radnik);
			return new ResponseEntity<Radnik>(HttpStatus.OK);
		}
		return new ResponseEntity<Radnik>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("radnik")
	public ResponseEntity<Radnik> updateRadnik(@RequestBody Radnik radnik) {
		if(!radnikRepository.existsById(radnik.getId()))
		{
			return new ResponseEntity<Radnik>(HttpStatus.NO_CONTENT);
		}
		radnikRepository.save(radnik);
		return new ResponseEntity<Radnik>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("radnik/{id}")
	public ResponseEntity<Radnik> deleteRadnik(@PathVariable("id") Integer id) {
		if(!radnikRepository.existsById(id))
		{
			return new ResponseEntity<Radnik>(HttpStatus.NO_CONTENT);
		}
		radnikRepository.deleteById(id);
		if(id == -100)
		{
			jdbcTemplate.execute(
					"INSERT INTO \"radnik\"(\"id\", \"ime\", \"prezime\", \"broj_lk\", \"obrazovanje\", \"sektor\")"
					+ "VALUES (-100, 'testime', 'testrpr', 131222, 5, 5)" 
					);
		}
		return new ResponseEntity<Radnik>(HttpStatus.OK);
	}
}
