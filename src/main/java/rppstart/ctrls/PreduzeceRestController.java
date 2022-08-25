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
import rppstart.repository.PreduzeceRepository;
//uvek automatski treba importovati a ne kopirati


@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController //kazemo da jeste restcontroller
public class PreduzeceRestController {
	
	//metoda za vracanje svih preduzeca iz baze podataka
	//ovo predstavlja dependency of injection koncept -- jer zavisnosti klase/interfejsa PreduzeceRepository injektovati u neku drugu klasu
	//sta su zavisnosti klase? -- varijable i metode neke klase i u ovom slucaju nama trebaju sve metode ovog interfejsa PreduzeceRepository koje je nasledio sve od JpaRepository
	//zato moramo da injektujemo PreduduzeceREpository u ovu klasu -- Autowired --injektovanje pomocu polja/property-a
	
	@Autowired //prilikom pokretanja projkta iz svih bean-ova koji se kreiraju na nivou app -- Nama ce trebati preduzece repository bean
	private PreduzeceRepository preduzeceRepository; //jedno polje/property
	
	@Autowired
	private JdbcTemplate jdbcTemplate; //kroz jdbc mozemo raditi direktno izvrsavanje sql upita //npr kad neko obrise id 100 da se automatski vrati nazad
	
	@GetMapping("preduzece") //ovo je za url localhost/preduzece
	public Collection<Preduzece> getPreduzeca() { 			//VRACA SVE
		return preduzeceRepository.findAll();	
	}
	
	@GetMapping("preduzece/{id}") //mapiranje do id-a 
	public Preduzece getPreduzece(@PathVariable("id") Integer id) { //VRACA PREKO ID-A
								//ovo je parametar i sama pronalazi sta je na ID-u
		return preduzeceRepository.getOne(id);
	}
	
	@GetMapping("preduzeceNaziv/{naziv}")
	public Collection<Preduzece> getPreduzecelByNaziv(@PathVariable("naziv") String naziv) {  //VRACA PREKO NAZIVA
		return preduzeceRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("preduzece") //-- kad pustimo POST zahtev na zadatoj putanji (preduzece) metoda insert preduzece bice pozvana i izvrsice metodu
	//dobra praksa je da se vrati klijentu neki odgovor bez obzira sto je set metoda -- zato treba odrediti povratnu vrednost Response
	public ResponseEntity<Preduzece> insertPreduzece(@RequestBody Preduzece preduzece) {
	//responseEntity vraca http status kod -- poruku
		if(!preduzeceRepository.existsById(preduzece.getId())) //proveravamo da li preduzece sa prosledjenim IDem vec postoji 
		{
			preduzeceRepository.save(preduzece);
			return new ResponseEntity<Preduzece>(HttpStatus.OK);
		}
		return new ResponseEntity<Preduzece>(HttpStatus.CONFLICT); //zasto mi prilikom inserta negativnog ID-a konvertuje u prvi sledeci???????
		
	}
	
	@PutMapping("preduzece")
	public ResponseEntity<Preduzece> updatePreduzece(@RequestBody Preduzece preduzece) {
		if(!preduzeceRepository.existsById(preduzece.getId())) 
		{
			return new ResponseEntity<Preduzece>(HttpStatus.NO_CONTENT); 
		}
		preduzeceRepository.save(preduzece);
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
	}
	
	@Transactional //ovo govori ili ce izvrsiti sve ili nista -- odnosi se na ovo kaskadno brisanje
	@DeleteMapping("preduzece/{id}")
	public ResponseEntity<Preduzece> deletePreduzece(@PathVariable("id") Integer id) {
		if(!preduzeceRepository.existsById(id)) 
		{
			return new ResponseEntity<Preduzece>(HttpStatus.NO_CONTENT); 
		}
		jdbcTemplate.execute("delete from sektor where preduzece = " + id);
		
		preduzeceRepository.deleteById(id);
		
		if(id == -100)
		{
			jdbcTemplate.execute(
					"INSERT INTO \"preduzece\"(\"id\", \"naziv\", \"pib\", \"sediste\")"
					+ "VALUES (-100, 'testn', 22222, 'tests')" 
					);
			
		}
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
	}
}
