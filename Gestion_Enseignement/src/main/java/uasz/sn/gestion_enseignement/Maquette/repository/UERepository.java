package uasz.sn.gestion_enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.EC;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.UE;

import java.util.List;

@Repository
public interface UERepository extends JpaRepository<UE, Long> {
    List<UE> findByMaquette(Maquette maquette);
}