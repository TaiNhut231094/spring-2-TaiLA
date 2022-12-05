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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @ManyToOne
    @JoinColumn(name = "district_id",referencedColumnName = "id")
    private District district;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<User> user;
}
