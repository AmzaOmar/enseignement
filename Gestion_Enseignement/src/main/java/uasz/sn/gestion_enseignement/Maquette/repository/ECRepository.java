package uasz.sn.gestion_enseignement.Maquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Maquette.model.EC;
import uasz.sn.gestion_enseignement.Maquette.model.UE;
import java.util.List;

public interface ECRepository extends JpaRepository<EC, Long> {
    List<EC> findByUe(UE ue);
    List<EC> findByUeMaquetteId(Long id);
}