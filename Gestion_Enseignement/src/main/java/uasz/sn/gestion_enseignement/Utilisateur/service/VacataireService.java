package uasz.sn.gestion_enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Utilisateur.model.Vacataire;
import uasz.sn.gestion_enseignement.Utilisateur.repository.VacataireRepository;
import java.util.List;

@Service
@Transactional
public class VacataireService {
    @Autowired
    private VacataireRepository vacataireRepository;

    public Vacataire addVacataire(Vacataire vacataire) {
        return vacataireRepository.save(vacataire);
    }

    public List<Vacataire> getAllVacataires() {
        return vacataireRepository.findAll();
    }

    public Vacataire getVacataireById(Long id) {
        return vacataireRepository.findById(id).get();
    }

    public Vacataire updateVacataire(Vacataire vacataire) {
        return vacataireRepository.save(vacataire);
    }

    public void deleteVacataireById(Long id) {
        vacataireRepository.deleteById(id);
    }
}