package uasz.sn.gestion_enseignement.Maquette.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle, code, description;
    private int credit, coefficient;


    @OneToMany(mappedBy = "ue", fetch = FetchType.EAGER)
    private List<EC> ecs;

    private String semestre;
    private  int totalCredit;
    private  int totalCoefficient;

    @ToString.Exclude
    @ManyToOne
    private Maquette maquette;

    private int nbrEC;
    private int totalCm;
    private int totalTd;
    private int totalTp;
    private int totalTpe;
    private int totalVht;
    private int totalCoeff;


    @ManyToOne
    @JoinColumn(name = "enseignement_id") // Spécifiez la clé étrangère
    private Enseignement enseignement;
}