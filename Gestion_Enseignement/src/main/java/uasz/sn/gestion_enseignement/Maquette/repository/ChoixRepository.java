package uasz.sn.gestion_enseignement.Maquette.repository;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.Choix;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoixRepository extends JpaRepository<Choix,Long> {

    List<Choix> findByEnseignantId(Long id);
}
