package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Radnik;
import rppstart.jpa.Sektor;

public interface RadnikRepository extends JpaRepository<Radnik, Integer>{
	
	List<Radnik> findBySektor(Sektor sektor);
	List<Radnik> findByPrezimeContainingIgnoreCase(String prezime);
	//get metoda - Naziv obelezja - Zanemari mala i velika slova
}
