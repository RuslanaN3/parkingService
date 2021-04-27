package com.pkservice.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="sections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter	
public class Section {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String sectionNumber;
	
	@ManyToOne
	private Building building;

	@OneToMany(mappedBy = "section")
	private Set<Vehicle> vehicles;
}
