package com.pkservice.entity;

import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="parking_check")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class ParkingState {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="creation_date")
	private ZonedDateTime creationDate;
	
	@OneToMany(mappedBy="parkingState")
	private Set<SlotState> slotStates;
}
