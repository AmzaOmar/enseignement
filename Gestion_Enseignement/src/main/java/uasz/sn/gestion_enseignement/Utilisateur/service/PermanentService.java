package uasz.sn.gestion_enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;
import uasz.sn.gestion_enseignement.Utilisateur.repository.PermanentRepository;
import java.util.List;

@Service
@Transactional
public class PermanentService {
    @Autowired
    private PermanentRepository permanentRepository;

    public Permanent addPermanent(Permanent permanent) {
        return permanentRepository.save(permanent);
    }

    public List<Permanent> getAllPermanents() {
        return permanentRepository.findAll();
    }

    public Permanent getPermanentById(Long id) {
        return permanentRepository.findById(id).get();
    }

    public Permanent updatePermanent(Permanent permanent) {
        return permanentRepository.save(permanent);
    }

    public void deletePermanentById(Long id) {
        permanentRepository.deleteById(id);
    }
}