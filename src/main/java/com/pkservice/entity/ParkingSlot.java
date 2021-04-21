package com.pkservice.entity;

import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="parking_slot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class ParkingSlot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="slot_number")
	private long slotNumber;
	
	@ManyToOne
	private ParkingLot parkingLot;
	
	@OneToMany(mappedBy="parkingSlot")
	private Set<SlotState> slotStates;

	@ManyToOne
	private DataSource dataSource;
}
