package uasz.sn.gestion_enseignement.Authentification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Authentification.model.Role;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.repository.RoleRepository;
import uasz.sn.gestion_enseignement.Authentification.repository.UtilisateurRepository;

@Service
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Role addRole(Role role) {
        Role r = roleRepository.findByRole(role.getRole());
        if (r == null) {
            roleRepository.save(role);
        }
        return role;
    }

    public void addUtilisateurRole(Utilisateur utilisateur, Role role) {
        Utilisateur u = utilisateurRepository.findByUsername(utilisateur.getUsername());
        Role profil = roleRepository.findByRole(role.getRole());
        u.getRoles().add(profil);
    }

    public Utilisateur findUtilisateur(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findByUsername(utilisateur.getUsername());
        utilisateur.setDateCreation(user.getDateCreation());
        utilisateur.setActive(user.isActive());
        utilisateur.setRoles(user.getRoles());
        utilisateurRepository.save(utilisateur);
    }
}