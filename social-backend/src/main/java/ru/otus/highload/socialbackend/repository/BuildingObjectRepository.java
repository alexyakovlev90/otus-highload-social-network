package ru.otus.highload.socialbackend.repository;

import ru.otus.highload.socialbackend.domain.BuildingObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BuildingObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingObjectRepository extends JpaRepository<BuildingObject, Long> {

}
