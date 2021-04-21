package com.pkservice.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@Column
	private String type;
	
	@OneToMany(mappedBy="parkingLot")
	private Set<ParkingSlot> parkingSlots;
	
	@ManyToMany
	@JoinTable(
			name="parking_lot_building",
			joinColumns=@JoinColumn(name="parking_lot_id"),
			inverseJoinColumns=@JoinColumn(name="building_id"))
	private Set<Building> buildings;

}
