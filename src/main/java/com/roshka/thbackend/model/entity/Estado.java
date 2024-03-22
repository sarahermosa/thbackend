package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data //CREA AUTOMATICAMENTE GETTERS-SETTERS,ETC
@AllArgsConstructor //CREA CONSTRUCTORES CON ARGUMENTOS
@NoArgsConstructor //CONSTRUCTORES VACIOS
@ToString //TO STRING
@Builder
@Entity
@Table(name="estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id_estado;

    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "estado")
    public String estado;
}
