package com.pkservice.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="building")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class Building {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String buildingName;
	
	@OneToMany(mappedBy = "building")
	private Set<Section> sections;
	
	@ManyToMany(mappedBy="buildings")
	private Set<ParkingLot> parkingLot;

}
