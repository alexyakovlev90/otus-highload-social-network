package ru.otus.highload.socialbackend.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Factory entity.
 */
public class FactoryDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactoryDTO factoryDTO = (FactoryDTO) o;
        if (factoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
