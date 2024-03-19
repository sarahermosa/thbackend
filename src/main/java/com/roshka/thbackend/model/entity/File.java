package com.roshka.thbackend.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "files")
public class File implements Serializable {

    @Id
    @Column(name="id_files")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id_files;
    @Column(name="filename")
    private String file_name;
    @Column(name="data")
    @Lob
    private byte[] data;
    @Column(name="file_type")
    private String file_type;

//    @ManyToOne
//    @JoinColumn(name = "id_postulante")
//    private Postulante postulante;


}
