package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tecnologia")
public class Tecnologia implements Serializable {
    @Id
    @Column (name = "id_tecnologia")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_tecnologia;

    @Column (name = "nombre")
    private String nombre;

}
