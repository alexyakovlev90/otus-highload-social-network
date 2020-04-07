package ru.otus.highload.socialbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A BuildingObject.
 */
@Entity
@Table(name = "building_object")
public class BuildingObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "buildingObject")
    private Set<BuildingMaterial> buildingMaterials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("buildingObjects")
    private Route route;

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

    public BuildingObject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public BuildingObject location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<BuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public BuildingObject buildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
        return this;
    }

    public BuildingObject addBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.add(buildingMaterial);
        buildingMaterial.setBuildingObject(this);
        return this;
    }

    public BuildingObject removeBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.remove(buildingMaterial);
        buildingMaterial.setBuildingObject(null);
        return this;
    }

    public void setBuildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Route getRoute() {
        return route;
    }

    public BuildingObject route(Route route) {
        this.route = route;
        return this;
    }

    public void setRoute(Route route) {
        this.route = route;
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
        BuildingObject buildingObject = (BuildingObject) o;
        if (buildingObject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingObject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingObject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
