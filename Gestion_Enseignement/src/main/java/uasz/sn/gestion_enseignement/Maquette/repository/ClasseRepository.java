package uasz.sn.gestion_enseignement.Maquette.repository;

import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.Classe;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.UE;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe,Long> {
    List<Classe> findByFormation(Formation formation);
    Classe findByNiveau(String niveau);

}
