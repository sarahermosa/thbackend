package com.roshka.thbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Tecnologia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tecnologia;
    private String nombre;

//
//    @ManyToMany(mappedBy = "tecnologiasasignadas")
//    @JsonIgnore
//    private Set<Postulante> postulanteSet = new HashSet<>();

}
