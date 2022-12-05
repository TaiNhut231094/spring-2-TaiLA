package com.codegym.book_store.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Address> addressSet;

    @ManyToOne
    @JoinColumn(name = "province_city_id", referencedColumnName = "id")
    private ProvinceCity provinceCity;
}
