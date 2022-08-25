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

import rppstart.jpa.Obrazovanje;
import rppstart.repository.ObrazovanjeRepository;

@CrossOrigin
@RestController //mora odma anotacija da bi IofCont. Spring odmah naravio bean
public class ObrazovanjeRestController {


	
	@Autowired
	private ObrazovanjeRepository obrazovanjeRepository;
				//tip					//naziv
	
	@Autowired
	private JdbcTemplate jdbcTemplate; //kroz jdbc mozemo raditi direktno izvrsavanje sql upita //npr kad neko obrise id 100 da se automatski vrati nazad
	
	
	@GetMapping("obrazovanje")
	public Collection<Obrazovanje> getObrazovanja() {		//GET METODA ZA OBRAZOVANJA
		return obrazovanjeRepository.findAll();
	}

	@GetMapping("obrazovanje/{id}")
	public Obrazovanje getObrazovanje(@PathVariable("id") Integer id) {		//GET METODA ZA OBRAZOVANJA po ID-u
		return obrazovanjeRepository.getOne(id);
	}
	
	@GetMapping("obrazovanjeNaziv/{naziv}") //ako ne stavimo obrazovanjeNaziv onda se Spring buni koju od ove dve metode da koristi prilikom pozivanja obrazovanja ono sto je u {} ne menja stvari
	public Collection<Obrazovanje> getObrazovanjeByNaziv(@PathVariable("naziv") String naziv) {
		return obrazovanjeRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("obrazovanje")
	public ResponseEntity<Obrazovanje> insertObrazovanje(@RequestBody Obrazovanje obrazovanje) {
														//sql upit		//tip		//naziv
		if(!obrazovanjeRepository.existsById(obrazovanje.getId()))
		{
			obrazovanjeRepository.save(obrazovanje);
			return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
		}
		return new ResponseEntity<Obrazovanje>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("obrazovanje")
	public ResponseEntity<Obrazovanje> updateObrazovanje(@RequestBody Obrazovanje obrazovanje) {
		if(!obrazovanjeRepository.existsById(obrazovanje.getId()))
		{
			return new ResponseEntity<Obrazovanje>(HttpStatus.NO_CONTENT);
		}
		obrazovanjeRepository.save(obrazovanje);
		return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("obrazovanje/{id}")
	public ResponseEntity<Obrazovanje> deleteObrazovanje(@PathVariable("id") Integer id) {
		if(!obrazovanjeRepository.existsById(id))
		{
			return new ResponseEntity<Obrazovanje>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("delete from radnik where obrazovanje = " + id);
		
		obrazovanjeRepository.deleteById(id);
		
		if(id == -100)
		{
			jdbcTemplate.execute(
					"INSERT INTO \"obrazovanje\"(\"id\", \"naziv\", \"stepen_strucne_spreme\")"
					+ "VALUES (-100, 'testnn', 'testss')" 
					);
			
		}
		return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
	}
		
	
	
	
	
	
	
	
	
}


