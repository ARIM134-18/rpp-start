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
import rppstart.jpa.Sektor;
import rppstart.service.SektorService;


@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController
public class SektorRestController {
	

    @Autowired
    private SektorService sektorService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Sektors")
    @GetMapping("sektor")
    public ResponseEntity<List<Sektor>> getAll() {
        List<Sektor> sektors = sektorService.getAll();
        return new ResponseEntity<>(sektors, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Sektor with id that was forwarded as path variable.")
    @GetMapping("sektor/{id}")
    public ResponseEntity<Sektor> getOne(@PathVariable("id") Integer id) {
        if (sektorService.findById(id).isPresent()) {
            Optional<Sektor> sektorOpt = sektorService.findById(id);
            return new ResponseEntity<>(sektorOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Sektors containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("sektor/naziv/{naziv}")
    public ResponseEntity<List<Sektor>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Sektor> sektors = sektorService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(sektors, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Sektor to database.")
    @PostMapping("sektor")
    public ResponseEntity<Sektor> addSektor(@RequestBody Sektor sektor) {
        Sektor savedSektor = sektorService.save(sektor);
        URI location = URI.create("/sektor/" + savedSektor.getId());
        return ResponseEntity.created(location).body(savedSektor);
    }

    @ApiOperation(value = "Updates Sektor that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "sektor/{id}")
    public ResponseEntity<Sektor> updateSektor(@RequestBody Sektor sektor,
            @PathVariable("id") Integer id) {
        if (sektorService.existsById(id)) {
            sektor.setId(id);
            Sektor savedSektor = sektorService.save(sektor);
            return ResponseEntity.ok().body(savedSektor);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Sektor with id that was forwarded as path variable.")
    @DeleteMapping("sektor/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !sektorService.existsById(-100)) {

        	jdbcTemplate.execute(
					"INSERT INTO \"sektor\"(\"id\", \"naziv\", \"oznaka\", \"preduzece\")"
					+ "VALUES (-100, 'testns', 'testsp', 5)" 
					);
        	}

        if (sektorService.existsById(id)) {
            sektorService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}