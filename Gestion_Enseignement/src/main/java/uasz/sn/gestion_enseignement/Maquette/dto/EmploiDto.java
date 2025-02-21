package uasz.sn.gestion_enseignement.Maquette.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uasz.sn.gestion_enseignement.Maquette.model.LigneEmploiDuTemps;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmploiDto {
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LigneEmploiDto> ligneEmploiDtos=new ArrayList<>();
}
