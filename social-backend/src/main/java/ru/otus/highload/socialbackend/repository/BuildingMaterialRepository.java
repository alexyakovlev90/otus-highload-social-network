package ru.otus.highload.socialbackend.repository;

import ru.otus.highload.socialbackend.domain.BuildingMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BuildingMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, Long> {

}
