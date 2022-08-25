package rppstart.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Radnik;

public interface RadnikRepository extends JpaRepository<Radnik, Integer>{
	
	Collection<Radnik> findByPrezimeContainingIgnoreCase(String prezime);
	//get metoda - Naziv obelezja - Zanemari mala i velika slova
}
