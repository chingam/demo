package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t02064")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T02064 extends CommonEntity {

	private static final long serialVersionUID = 8137814633066212317L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_zone_id")
	private String zoneId;
	
	@NotNull
	@Size(max = 10)
	@Column(name = "t_zone_code")
	private String zoneCode;

	@NotNull
	@Size(max = 15)
	@Column(name = "t_lang1_name")
	private String langName1;

	@NotNull
	@Size(max = 15)
	@Column(name = "t_lang2_name")
	private String langName2;
}
