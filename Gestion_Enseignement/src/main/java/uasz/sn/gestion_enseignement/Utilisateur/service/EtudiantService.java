package uasz.sn.gestion_enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import uasz.sn.gestion_enseignement.Utilisateur.model.Etudiant;
import uasz.sn.gestion_enseignement.Utilisateur.repository.EtudiantRepository;
import java.util.List;

public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).get();
    }

    public Etudiant updateEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiantById(Long id) {
        etudiantRepository.deleteById(id);
    }
}