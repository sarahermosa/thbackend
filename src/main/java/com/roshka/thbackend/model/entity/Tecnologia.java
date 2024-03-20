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
@Table(name = "tecnologia")
public class Tecnologia {
    @Id
    @Column (name = "id_tecnologia")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "El campo no puede estar vacio")
    @Column (name = "nombre")
    private String nombre;

}
