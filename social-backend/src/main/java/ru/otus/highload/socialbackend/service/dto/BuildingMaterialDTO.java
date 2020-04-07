package ru.otus.highload.socialbackend.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BuildingMaterial entity.
 */
public class BuildingMaterialDTO implements Serializable {

    private Long id;

    private String name;

    private Long quantity;

    private Long buildingObjectId;

    private Long vehicleId;

    private Long routeId;

    private Long factoryId;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getBuildingObjectId() {
        return buildingObjectId;
    }

    public void setBuildingObjectId(Long buildingObjectId) {
        this.buildingObjectId = buildingObjectId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingMaterialDTO buildingMaterialDTO = (BuildingMaterialDTO) o;
        if (buildingMaterialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingMaterialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingMaterialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", buildingObject=" + getBuildingObjectId() +
            ", vehicle=" + getVehicleId() +
            ", route=" + getRouteId() +
            ", factory=" + getFactoryId() +
            "}";
    }
}
