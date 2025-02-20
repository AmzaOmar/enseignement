package uasz.sn.gestion_enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.gestion_enseignement.Maquette.model.EmploiDuTemps;
import uasz.sn.gestion_enseignement.Maquette.repository.EmploiDuTempsRepository;

import java.util.List;

@Service
public class EmploiDuTempsService {
    @Autowired
    private EmploiDuTempsRepository EmploiDuTempsRepository;

    public void addEmploiDuTemps(EmploiDuTemps EmploiDuTemps) {
        EmploiDuTempsRepository.save(EmploiDuTemps);
    }

    public List<EmploiDuTemps> findAllEmploiDuTemps() {
        return EmploiDuTempsRepository.findAll();
    }

    public EmploiDuTemps getEmploiDuTemps(Long id) {
        return EmploiDuTempsRepository.findById(id).get();
    }

    public void updateEmploiDuTemps(EmploiDuTemps ue) {
        EmploiDuTempsRepository.save(ue);
    }

    public void deleteEmploiDuTemps(Long id) {
        EmploiDuTempsRepository.deleteById(id);
    }


    public EmploiDuTemps findById(Long id) {
        return EmploiDuTempsRepository.findById(id).get();}
}
