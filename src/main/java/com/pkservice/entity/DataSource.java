package com.pkservice.entity;

import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="data_source")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String sourceName;

    @OneToMany(mappedBy = "dataSource")
    private Set<ParkingSlot> parkingSlots;

}
