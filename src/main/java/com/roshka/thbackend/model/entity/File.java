package com.roshka.thbackend.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
//    @Column(name="data")
//    @Lob
//    private byte[] data;
    @Column(name="file_type")
    private String file_type;



}
