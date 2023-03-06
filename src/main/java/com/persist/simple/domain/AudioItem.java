package com.persist.simple.domain;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A AudioItem.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("audioitem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AudioItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String slug;

    private String transaction;

    private String itemDescription;

    private String categoryName;

    private String price;

    private String location;

    private String dateofadd;

    private String link;

    private String image;

    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public AudioItem id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSlug() {
        return this.slug;
    }

    public AudioItem slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTransaction() {
        return this.transaction;
    }

    public AudioItem transaction(String transaction) {
        this.setTransaction(transaction);
        return this;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public AudioItem itemDescription(String itemDescription) {
        this.setItemDescription(itemDescription);
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public AudioItem categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPrice() {
        return this.price;
    }

    public AudioItem price(String price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return this.location;
    }

    public AudioItem location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateofadd() {
        return this.dateofadd;
    }

    public AudioItem dateofadd(String dateofadd) {
        this.setDateofadd(dateofadd);
        return this;
    }

    public void setDateofadd(String dateofadd) {
        this.dateofadd = dateofadd;
    }

    public String getLink() {
        return this.link;
    }

    public AudioItem link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return this.image;
    }

    public AudioItem image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AudioItem category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AudioItem)) {
            return false;
        }
        return id != null && id.equals(((AudioItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AudioItem{" +
            "id=" + getId() +
            ", slug='" + getSlug() + "'" +
            ", transaction='" + getTransaction() + "'" +
            ", itemDescription='" + getItemDescription() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", price='" + getPrice() + "'" +
            ", location='" + getLocation() + "'" +
            ", dateofadd='" + getDateofadd() + "'" +
            ", link='" + getLink() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
