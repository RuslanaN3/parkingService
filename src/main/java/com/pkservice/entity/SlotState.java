package com.pkservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pkservice.enums.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="parking_slot_state")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class SlotState {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private ParkingSlot parkingSlot;
	
	@ManyToOne
	private ParkingCheck parkingCheck;
	
	private State state;
}
