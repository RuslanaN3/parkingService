package com.pkservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="vehicle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "license_plate")
	private String licensePlate;
	
	@ManyToOne
	private Section section;		

}
