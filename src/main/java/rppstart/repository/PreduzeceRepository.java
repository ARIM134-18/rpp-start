 package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Preduzece;

public interface PreduzeceRepository extends JpaRepository<Preduzece, Integer>{
//sta mora da sadrzi svaki repository?		//interfejs //tabela/klasa //tip podatka kljuca
	//repository je skladiste, u ovom slucaju preduzeca
	//na osnovu njega mozemo zvati metode CRUD
	//mozemo da koristimo predefinisane metode
	
	//apstraktna metoda -- ne treba public apstract jer su sve metode apstraktne
	List<Preduzece> findByNazivContainingIgnoreCase(String naziv);
					//get metoda - Naziv obelezja - Zanemari mala i velika slova
}
