package uasz.sn.gestion_enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Maquette.model.EC;
import uasz.sn.gestion_enseignement.Maquette.model.UE;
import uasz.sn.gestion_enseignement.Maquette.repository.ECRepository;
import java.util.List;

@Service
@Transactional
public class ECService {
    @Autowired
    private ECRepository ecRepository;

    public void addEC(EC ec) {
        ecRepository.save(ec);
    }

    public List<EC> findAllEC() {
        return ecRepository.findAll();
    }

    public List<EC> findECByUE(UE ue) {
        return ecRepository.findByUe(ue);
    }

    public EC getEC(Long id) {
        return ecRepository.findById(id).get();
    }

    public void updateEC(EC ec) {
        ecRepository.save(ec);
    }

    public void deleteEC(Long id) {
        ecRepository.deleteById(id);
    }
}