package uasz.sn.gestion_enseignement.Maquette.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uasz.sn.gestion_enseignement.Maquette.model.Enseignement;
import uasz.sn.gestion_enseignement.Maquette.repository.EnseignementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EnseignementService {


    private final EnseignementRepository enseignementRepository;

    public List<Enseignement> findAll() {
        return enseignementRepository.findAll();
    }

    public Enseignement save(Enseignement enseignement) {
        return enseignementRepository.save(enseignement);
    }

    public void delete(Long id) {
        enseignementRepository.deleteById(id);
    }
}
