package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(
            name = "number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String number;

}
