package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "postulantetecnologia")

public class PostulanteTecnologia {
    @Id
    @Column (name = "id_postulante_tecnologia")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "nivel")
    private String nivel;

    @ManyToOne (targetEntity = Tecnologia.class)
    @JoinColumn (name = "tecnologia_id")
    private Long tecnologia_id;

    @ManyToOne (targetEntity = Postulante.class)
    @JoinColumn (name = "id_postulante")
    private Long id_postulante;

}
