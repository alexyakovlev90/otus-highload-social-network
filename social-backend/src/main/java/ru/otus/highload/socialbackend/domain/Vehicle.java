package ru.otus.highload.socialbackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "vehicle")
    private Set<BuildingMaterial> buildingMaterials = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<Route> routes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vehicle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public Vehicle buildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
        return this;
    }

    public Vehicle addBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.add(buildingMaterial);
        buildingMaterial.setVehicle(this);
        return this;
    }

    public Vehicle removeBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.remove(buildingMaterial);
        buildingMaterial.setVehicle(null);
        return this;
    }

    public void setBuildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public Vehicle routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Vehicle addRoutes(Route route) {
        this.routes.add(route);
        route.setVehicle(this);
        return this;
    }

    public Vehicle removeRoutes(Route route) {
        this.routes.remove(route);
        route.setVehicle(null);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
