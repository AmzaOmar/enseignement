package uasz.sn.gestion_enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.repository.FormationRepository;


import java.util.List;

@Service
@Transactional
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;

    public void addFormation(Formation formation) {
        formationRepository.save(formation);
    }

    public List<Formation> findAllFormation() {
        return formationRepository.findAll();
    }

    public Formation getFormation(Long id) {
        return formationRepository.findById(id).get();
    }

    public void updateFormation(Formation ue) {
        formationRepository.save(ue);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }


    public Formation findById(Long id) {
        return formationRepository.findById(id).get();
    }
}
