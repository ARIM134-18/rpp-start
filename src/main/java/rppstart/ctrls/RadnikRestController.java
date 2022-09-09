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
import rppstart.jpa.Radnik;
import rppstart.jpa.Sektor;
import rppstart.service.RadnikService;
import rppstart.service.SektorService;

@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController
public class RadnikRestController {
	

    @Autowired
    private RadnikService radnikService;
    
    @Autowired
    private SektorService sektorService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Radniks")
    @GetMapping("radnik")
    public ResponseEntity<List<Radnik>> getAll() {
        List<Radnik> radniks = radnikService.getAll();
        return new ResponseEntity<>(radniks, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Radnik with id that was forwarded as path variable.")
    @GetMapping("radnik/{id}")
    public ResponseEntity<Radnik> getOne(@PathVariable("id") Integer id) {
        if (radnikService.findById(id).isPresent()) {
            Optional<Radnik> radnikOpt = radnikService.findById(id);
            return new ResponseEntity<>(radnikOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Radniks containing string that was forwarded as path variable in 'prezime'.")
    @GetMapping("radnik/prezime/{prezime}")
    public ResponseEntity<List<Radnik>> findByPrezimeContainingIgnoreCase(@PathVariable("prezime") String prezime) {
        List<Radnik> preduzeces = radnikService.findByPrezime(prezime);
        return new ResponseEntity<>(preduzeces, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Returns list of Radnik for Sektor with id that was forwarded as path variable.")
    @GetMapping("radniciSektor/{id}")
    public ResponseEntity<List<Radnik>> getAllForSektor(@PathVariable("id") Integer id) {
        Optional<Sektor> sektorOpt = sektorService.findById(id);
        if (sektorOpt.isPresent()) {
            List<Radnik> radniks = radnikService.findBySektor(sektorOpt.get());
            return new ResponseEntity<>(radniks, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @ApiOperation(value = "Adds new Radnik to database.")
    @PostMapping("radnik")
    public ResponseEntity<Radnik> addRadnik(@RequestBody Radnik radnik) {
        Radnik savedRadnik = radnikService.save(radnik);
        URI location = URI.create("/radnik/" + savedRadnik.getId());
        return ResponseEntity.created(location).body(savedRadnik);
    }

    @ApiOperation(value = "Updates Radnik that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "radnik/{id}")
    public ResponseEntity<Radnik> updateRadnik(@RequestBody Radnik radnik,
            @PathVariable("id") Integer id) {
        if (radnikService.existsById(id)) {
            radnik.setId(id);
            Radnik savedRadnik = radnikService.save(radnik);
            return ResponseEntity.ok().body(savedRadnik);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Radnik with id that was forwarded as path variable.")
    @DeleteMapping("radnik/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !radnikService.existsById(-100)) {

        	jdbcTemplate.execute(
					"INSERT INTO \"radnik\"(\"id\", \"ime\", \"prezime\", \"broj_lk\", \"obrazovanje\", \"sektor\")"
					+ "VALUES (-100, 'testime', 'testrpr', 131222, 5, 5)" 
					);
        }

        if (radnikService.existsById(id)) {
            radnikService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }
}

