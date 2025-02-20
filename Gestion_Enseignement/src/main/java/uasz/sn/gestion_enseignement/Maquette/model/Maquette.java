package uasz.sn.gestion_enseignement.Maquette.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Maquette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String intitule;
    private int annee;

    private int nbCreditsTotal;
    private int nbUEs;
    private int nbHeuresTotal;
    private String description;


    @ToString.Exclude
    @OneToMany(mappedBy = "maquette",fetch = FetchType.EAGER)
    private List<UE> ues;


    @OneToOne
    @JoinColumn(name = "classe_id", unique = true)
    private Classe classe;

}
