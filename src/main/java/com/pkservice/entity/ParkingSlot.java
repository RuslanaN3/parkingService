package com.pkservice.entity;


import com.pkservice.enums.Status;

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
	
	@Column
	private long slotNumber;

	@Column
	@Enumerated(EnumType.STRING)
	private Status slotStatus;
	
	@ManyToOne
	private ParkingLot parkingLot;

}
