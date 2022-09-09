package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Obrazovanje;

public interface ObrazovanjeRepository extends JpaRepository<Obrazovanje, Integer> {
	
	List<Obrazovanje> findByNazivContainingIgnoreCase(String naziv);  //metoda za pronalazenje po nazivu

}
