package uasz.sn.gestion_enseignement.Maquette.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.repository.MaquetteRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.MaquetteRepository;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MaquetteService {
    
    private MaquetteRepository maquetteRepository;
    


    public void addMaquette(Maquette maquette) {
        maquetteRepository.save(maquette);
    }

    public List<Maquette> findAllMaquette() {
        return maquetteRepository.findAll();
    }

    public Maquette getMaquette(Long id) {
        return maquetteRepository.findById(id).get();
    }

    public void updateMaquette(Maquette maquette) {
        maquetteRepository.save(maquette);
    }

    public void deleteMaquette(Long id) {
        maquetteRepository.deleteById(id);
    }

    public Maquette findById(Long id) {
        return maquetteRepository.findById(id).get();
    }
}
