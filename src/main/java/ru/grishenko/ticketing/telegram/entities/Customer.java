package ru.grishenko.ticketing.telegram.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustCode")
    private Long id;

    @Column(name = "FullNameR")
    private String fullNameR;

    @Column(name = "fStopList")
    private Integer stopList;

    @Column(name = "Type")
    private Integer type;

}
