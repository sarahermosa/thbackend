package com.roshka.thbackend.model.dao;


import com.roshka.thbackend.model.entity.ImageDataConvocatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface imageDataConvocatoriaDao extends CrudRepository<ImageDataConvocatoria, Long> {
}
