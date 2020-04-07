package ru.otus.highload.socialbackend.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BuildingObject entity.
 */
public class BuildingObjectDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

    private Long routeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingObjectDTO buildingObjectDTO = (BuildingObjectDTO) o;
        if (buildingObjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingObjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingObjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", route=" + getRouteId() +
            "}";
    }
}
