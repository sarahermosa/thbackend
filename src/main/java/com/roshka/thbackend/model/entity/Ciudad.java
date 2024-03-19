package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data //CREA AUTOMATICAMENTE GETTERS-SETTERS,ETC
@AllArgsConstructor //CREA CONSTRUCTORES CON ARGUMENTOS
@NoArgsConstructor //CONSTRUCTORES VACIOS
@ToString //TO STRING
@Builder
@Entity
@Table(name="ciudad")
public class Ciudad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nombre")
    public String nombre;
}
