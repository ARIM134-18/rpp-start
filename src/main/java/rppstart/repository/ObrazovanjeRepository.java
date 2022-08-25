package rppstart.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Obrazovanje;

public interface ObrazovanjeRepository extends JpaRepository<Obrazovanje, Integer> {
	
	Collection<Obrazovanje> findByNazivContainingIgnoreCase(String naziv);  //metoda za pronalazenje po nazivu

}
