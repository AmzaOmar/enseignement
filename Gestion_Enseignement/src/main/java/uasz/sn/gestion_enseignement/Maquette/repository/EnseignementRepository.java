package uasz.sn.gestion_enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.Choix;
import uasz.sn.gestion_enseignement.Maquette.model.Enseignement;

import java.util.Optional;

@Repository
public interface EnseignementRepository extends JpaRepository<Enseignement,Long> {


    Optional<Enseignement> findByChoix(Choix choix);
}
