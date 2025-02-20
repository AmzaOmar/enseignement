package uasz.sn.gestion_enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.Classe;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;

import java.util.List;

@Repository
public interface MaquetteRepository extends JpaRepository<Maquette,Long> {
     List<Maquette> findByClasse(Classe classe);
}
