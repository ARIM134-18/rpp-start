package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Sektor;

public interface SektorRepository extends JpaRepository<Sektor, Integer>{
	
	List<Sektor> findByNazivContainingIgnoreCase(String naziv); 
}
