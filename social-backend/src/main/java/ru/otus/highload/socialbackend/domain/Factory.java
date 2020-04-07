package ru.otus.highload.socialbackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Factory.
 */
@Entity
@Table(name = "factory")
public class Factory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "factory")
    private Set<BuildingMaterial> buildingMaterials = new HashSet<>();

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

    public Factory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public Factory location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<BuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public Factory buildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
        return this;
    }

    public Factory addBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.add(buildingMaterial);
        buildingMaterial.setFactory(this);
        return this;
    }

    public Factory removeBuildingMaterials(BuildingMaterial buildingMaterial) {
        this.buildingMaterials.remove(buildingMaterial);
        buildingMaterial.setFactory(null);
        return this;
    }

    public void setBuildingMaterials(Set<BuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
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
        Factory factory = (Factory) o;
        if (factory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Factory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
