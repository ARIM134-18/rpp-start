package rppstart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rppstart.jpa.Radnik;
import rppstart.jpa.Sektor;
import rppstart.repository.RadnikRepository;


@Service
public class RadnikService {
	
	@Autowired
	private RadnikRepository radnikRepository;
	
	public List<Radnik> getAll(){
		return radnikRepository.findAll();
	}
	
	public Optional<Radnik> findById(Integer id) {
		return radnikRepository.findById(id);
	}
	
	public List<Radnik> findBySektor(Sektor sektor) {
		return radnikRepository.findBySektor(sektor);
	}
	
	public List<Radnik> findByPrezime(String prezime) {
        return radnikRepository.findByPrezimeContainingIgnoreCase(prezime);
    }
	
	 
	public Radnik save(Radnik radnik) {
		return radnikRepository.save(radnik);
	}
	
	public boolean existsById(Integer id) {
		return radnikRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		radnikRepository.deleteById(id);
	}

}