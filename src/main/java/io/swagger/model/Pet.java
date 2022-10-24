package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Pet
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T12:36:17.472Z[GMT]")

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("category")
    private Category category = null;

    @ElementCollection
    @JsonProperty("photoUrls")
    @Valid
    private List<String> photoUrls = new ArrayList<String>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonProperty("tags")
    @Valid
    private Set<Tag> tags = null;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private StatusEnum status = null;

    public Pet id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @Schema(example = "10", description = "")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @Schema(example = "doggie", required = true, description = "")
    @NotNull

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pet category(Category category) {
        this.category = category;
        return this;
    }

    /**
     * Get category
     *
     * @return category
     **/
    @Schema(description = "")

    @Valid
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Pet photoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public Pet addPhotoUrlsItem(String photoUrlsItem) {
        this.photoUrls.add(photoUrlsItem);
        return this;
    }

    /**
     * Get photoUrls
     *
     * @return photoUrls
     **/
    @Schema(required = true, description = "")
    @NotNull

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public Pet tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Pet addTagsItem(Tag tagsItem) {
        if (this.tags == null) {
            this.tags = new HashSet<Tag>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * Get tags
     *
     * @return tags
     **/
    @Schema(description = "")
    @Valid
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Pet status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * pet status in the store
     *
     * @return status
     **/
    @Schema(description = "pet status in the store")

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet pet = (Pet) o;
        return Objects.equals(this.id, pet.id) &&
                Objects.equals(this.name, pet.name) &&
                Objects.equals(this.category, pet.category) &&
                Objects.equals(this.photoUrls, pet.photoUrls) &&
                Objects.equals(this.tags, pet.tags) &&
                Objects.equals(this.status, pet.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, photoUrls, tags, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Pet {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    photoUrls: ").append(toIndentedString(photoUrls)).append("\n");
        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * pet status in the store
     */
    public enum StatusEnum {
        AVAILABLE("available"),

        PENDING("pending"),

        SOLD("sold");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
