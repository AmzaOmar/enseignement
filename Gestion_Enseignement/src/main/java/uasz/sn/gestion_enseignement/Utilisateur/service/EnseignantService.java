package uasz.sn.gestion_enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Utilisateur.model.Enseignant;
import uasz.sn.gestion_enseignement.Utilisateur.repository.EnseignantRepository;
import java.util.List;

@Service
@Transactional
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;

    public Enseignant addEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).get();
    }

    public Enseignant updateEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public void deleteEnseignantById(Long id) {
        enseignantRepository.deleteById(id);
    }

    public void activer(Long id) {
        Enseignant enseignant = getEnseignantById(id);
        enseignant.setActive(!enseignant.isActive());
        enseignantRepository.save(enseignant);
    }

    public void archive(Long id) {
        Enseignant enseignant = getEnseignantById(id);
        enseignant.setArchive(!enseignant.isArchive());
        enseignantRepository.save(enseignant);
    }
}