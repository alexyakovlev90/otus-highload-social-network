package ru.otus.highload.socialbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "route")
    private Set<BuildingObject> buildingObjects = new HashSet<>();

    @OneToMany(mappedBy = "route")
    private Set<BuildingMaterial> buildingMaterials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Factory factory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Route vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Set<BuildingObject> getBuildingObjects() {
        return buildingObjects;
    }

    public Route buildingObjects(Set<BuildingObject> buildingObjects) {
        this.buildingObjects = buildingObjects;
        return this;
    }

    public Route addBuildingObjects(BuildingObject buildingObject) {
        this.buildingObjects.add(buildingObject);
        buildingObject.setRoute(this);
        return this;
    }

    public Route removeBuildingObjects(BuildingObject buildingObject) {
        this.buildingObjects.remove(buildingObject);
        buildingObject.setRoute(null);
        return this;
    }

    public void setBuildingObjects(Set<BuildingObject> buildingObjects) {
        this.buildingObjects = buildingObjects;
    }

    public Set<BuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public Route buildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
        return this;
    }

    public Route addBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.add(buildingMaterial);
        buildingMaterial.setRoute(this);
        return this;
    }

    public Route removeBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.remove(buildingMaterial);
        buildingMaterial.setRoute(null);
        return this;
    }

    public void setBuildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Factory getFactory() {
        return factory;
    }

    public Route factory(Factory factory) {
        this.factory = factory;
        return this;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        if (route.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), route.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            "}";
    }
}
