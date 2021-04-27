package com.pkservice.entity;

import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="parking_lot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class ParkingLot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="lot_type")
	private String lotType;
	
	@OneToMany(mappedBy="parkingLot")
	private Set<ParkingSlot> parkingSlots;
	
	@ManyToMany
	@JoinTable(
			name="parking_lot_building",
			joinColumns=@JoinColumn(name="parking_lot_id"),
			inverseJoinColumns=@JoinColumn(name="building_id"))
	private Set<Building> buildings;

	@ManyToOne
	private DataSource dataSource;

	@Transient
	Long vacantParkingSlotsCount;
}
