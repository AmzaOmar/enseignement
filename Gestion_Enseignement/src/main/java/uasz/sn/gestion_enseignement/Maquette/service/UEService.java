package uasz.sn.gestion_enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Maquette.model.EC;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.UE;
import uasz.sn.gestion_enseignement.Maquette.repository.UERepository;
import java.util.List;

@Service
@Transactional
public class UEService {
    @Autowired
    private UERepository ueRepository;

    public void addUE(UE ue) {
        ueRepository.save(ue);
    }

    public List<UE> findAllUE() {
        return ueRepository.findAll();
    }

    public UE getUE(Long id) {
        return ueRepository.findById(id).get();
    }

    public void updateUE(UE ue) {
        ueRepository.save(ue);
    }

    public void deleteUE(Long id) {
        ueRepository.deleteById(id);
    }
    public List<UE> findUEByMaquette(Maquette maquette) {

        return ueRepository.findByMaquette(maquette);
    }

    public UE findById(Long id) {
        return ueRepository.findById(id).get();
    }
}