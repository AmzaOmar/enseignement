package uasz.sn.gestion_enseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;

@Repository
public interface PermanentRepository extends JpaRepository<Permanent, Long> {
}