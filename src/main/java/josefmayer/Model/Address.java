package josefmayer.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;


@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private String city;
    private String country;
    private String postcode;

    public Integer getId() {
        return id;
    }

//    public Address setId(Integer id) {
//        this.id = id;
//        return this;
//    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public Address setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }
}