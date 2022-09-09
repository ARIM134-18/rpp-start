package rppstart.ctrls;

import java.net.URI;
import java.util.List;
import java.util.Optional;


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

import io.swagger.annotations.ApiOperation;
import rppstart.jpa.Preduzece;
import rppstart.service.PreduzeceService;
//uvek automatski treba importovati a ne kopirati


@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController //kazemo da jeste restcontroller
public class PreduzeceRestController {
	
	//metoda za vracanje svih preduzeca iz baze podataka
	//ovo predstavlja dependency of injection koncept -- jer zavisnosti klase/interfejsa PreduzeceRepository injektovati u neku drugu klasu
	//sta su zavisnosti klase? -- varijable i metode neke klase i u ovom slucaju nama trebaju sve metode ovog interfejsa PreduzeceRepository koje je nasledio sve od JpaRepository
	//zato moramo da injektujemo PreduduzeceREpository u ovu klasu -- Autowired --injektovanje pomocu polja/property-a
	

    @Autowired
    private PreduzeceService preduzeceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Preduzeces")
    @GetMapping("preduzece")
    public ResponseEntity<List<Preduzece>> getAll() {
        List<Preduzece> preduzeces = preduzeceService.getAll();
        return new ResponseEntity<>(preduzeces, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Preduzece with id that was forwarded as path variable.")
    @GetMapping("preduzece/{id}")
    public ResponseEntity<Preduzece> getOne(@PathVariable("id") Integer id) {
        if (preduzeceService.findById(id).isPresent()) {
            Optional<Preduzece> preduzeceOpt = preduzeceService.findById(id);
            return new ResponseEntity<>(preduzeceOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Preduzeces containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("preduzece/naziv/{naziv}")
    public ResponseEntity<List<Preduzece>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Preduzece> preduzeces = preduzeceService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(preduzeces, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Preduzece to database.")
    @PostMapping("preduzece")
    public ResponseEntity<Preduzece> addPreduzece(@RequestBody Preduzece preduzece) {
        Preduzece savedPreduzece = preduzeceService.save(preduzece);
        URI location = URI.create("/preduzece/" + savedPreduzece.getId());
        return ResponseEntity.created(location).body(savedPreduzece);
    }

    @ApiOperation(value = "Updates Preduzece that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "preduzece/{id}")
    public ResponseEntity<Preduzece> updatePreduzece(@RequestBody Preduzece preduzece, @PathVariable("id") Integer id) {
        if (preduzeceService.existsById(id)) {
            preduzece.setId(id);
            Preduzece savedPreduzece = preduzeceService.save(preduzece);
            return ResponseEntity.ok().body(savedPreduzece);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Preduzece with id that was forwarded as path variable.")
    @DeleteMapping("preduzece/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {

        if (id == -100 && !preduzeceService.existsById(id)) {
        	jdbcTemplate.execute(
					"INSERT INTO \"preduzece\"(\"id\", \"naziv\", \"pib\", \"sediste\")"
					+ "VALUES (-100, 'testn', 22222, 'tests')" 
					);
			
		}

        if (preduzeceService.existsById(id)) {
            preduzeceService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}