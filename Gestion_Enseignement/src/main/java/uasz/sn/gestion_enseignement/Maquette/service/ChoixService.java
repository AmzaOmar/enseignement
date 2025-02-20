package uasz.sn.gestion_enseignement.Maquette.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import uasz.sn.gestion_enseignement.Maquette.model.Choix;
import uasz.sn.gestion_enseignement.Maquette.repository.ChoixRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ChoixService {


    private final ChoixRepository choixRepository;

    public List<Choix> findAllByEnseignant(Long enseignantId) {
        return choixRepository.findByEnseignantId(enseignantId);
    }

    public Choix save(Choix choix) {
        return choixRepository.save(choix);
    }

    public void delete(Long id) {
        choixRepository.deleteById(id);
    }
}
