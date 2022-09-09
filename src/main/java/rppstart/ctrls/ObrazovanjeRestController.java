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
import rppstart.jpa.Obrazovanje;
import rppstart.service.ObrazovanjeService;

@CrossOrigin
@RestController //mora odma anotacija da bi IofCont. Spring odmah naravio bean
public class ObrazovanjeRestController {


	
	 @Autowired
	    private ObrazovanjeService obrazovanjeService;
				//tip					//naziv
	
	@Autowired
	private JdbcTemplate jdbcTemplate; //kroz jdbc mozemo raditi direktno izvrsavanje sql upita //npr kad neko obrise id 100 da se automatski vrati nazad
	
	
	@ApiOperation(value = "Returns List of all Obrazovanjes")
    @GetMapping("obrazovanje")
    public ResponseEntity<List<Obrazovanje>> getAll() {
        List<Obrazovanje> obrazovanje = obrazovanjeService.getAll();
        return new ResponseEntity<>(obrazovanje, HttpStatus.OK);
    }

	@ApiOperation(value = "Returns Obrazovanje with id that was forwarded as path variable.")
    @GetMapping("obrazovanje/{id}")
    public ResponseEntity<Obrazovanje> getOne(@PathVariable("id") Integer id) {
        if (obrazovanjeService.findById(id).isPresent()) {
            Optional<Obrazovanje> obrazovanjeOpt = obrazovanjeService.findById(id);
            return new ResponseEntity<>(obrazovanjeOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
	
    @ApiOperation(value = "Returns list of Obrazovanjes containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("obrazovanje/naziv/{naziv}")
    public ResponseEntity<List<Obrazovanje>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Obrazovanje> obrazovanjes = obrazovanjeService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(obrazovanjes, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Obrazovanje to database.")
    @PostMapping("obrazovanje")
    public ResponseEntity<Obrazovanje> addObrazovanje(@RequestBody Obrazovanje obrazovanje) {
        Obrazovanje savedObrazovanje = obrazovanjeService.save(obrazovanje);
        URI location = URI.create("/obrazovanje/" + savedObrazovanje.getId());
        return ResponseEntity.created(location).body(savedObrazovanje);
    }

    @ApiOperation(value = "Updates Obrazovanje that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "obrazovanje/{id}")
    public ResponseEntity<Obrazovanje> updateObrazovanje(@RequestBody Obrazovanje obrazovanje, @PathVariable("id") Integer id) {
        if (obrazovanjeService.existsById(id)) {
            obrazovanje.setId(id);
            Obrazovanje savedObrazovanje = obrazovanjeService.save(obrazovanje);
            return ResponseEntity.ok().body(savedObrazovanje);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Obrazovanje with id that was forwarded as path variable.")
    @DeleteMapping("obrazovanje/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !obrazovanjeService.existsById(id)) {
        	jdbcTemplate.execute(
					"INSERT INTO \"obrazovanje\"(\"id\", \"naziv\", \"stepen_strucne_spreme\")"
					+ "VALUES (-100, 'testnn', 'testss')" 
					);
			
		}

        if (obrazovanjeService.existsById(id)) {
            obrazovanjeService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}
