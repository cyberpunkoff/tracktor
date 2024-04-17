/*
 * This file is generated by jOOQ.
 */
package edu.java.repository.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private OffsetDateTime checkedAt;
    private OffsetDateTime updatedAt;

    public Links() {}

    public Links(Links value) {
        this.id = value.id;
        this.url = value.url;
        this.checkedAt = value.checkedAt;
        this.updatedAt = value.updatedAt;
    }

    @ConstructorProperties({ "id", "url", "checkedAt", "updatedAt" })
    public Links(
        @Nullable Long id,
        @NotNull String url,
        @Nullable OffsetDateTime checkedAt,
        @Nullable OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.url = url;
        this.checkedAt = checkedAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINKS.CHECKED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getCheckedAt() {
        return this.checkedAt;
    }

    /**
     * Setter for <code>LINKS.CHECKED_AT</code>.
     */
    public void setCheckedAt(@Nullable OffsetDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    /**
     * Getter for <code>LINKS.UPDATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>LINKS.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Links other = (Links) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.checkedAt == null) {
            if (other.checkedAt != null)
                return false;
        }
        else if (!this.checkedAt.equals(other.checkedAt))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.checkedAt == null) ? 0 : this.checkedAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(checkedAt);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}
