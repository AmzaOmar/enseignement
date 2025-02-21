package uasz.sn.gestion_enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.Choix;
import uasz.sn.gestion_enseignement.Maquette.model.Enseignement;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignementRepository extends JpaRepository<Enseignement,Long> {


    Optional<Enseignement> findByChoix(Choix choix);
    @Query("SELECT e FROM Enseignement e LEFT JOIN LigneEmploiDuTemps l ON e.id = l.enseignement.id WHERE l.id IS NULL")
    List<Enseignement> findEnseignementsNonUtilises();

}
