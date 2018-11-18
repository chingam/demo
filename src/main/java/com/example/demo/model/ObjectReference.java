package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t91000")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectReference implements Serializable{
	
	private static final long serialVersionUID = 4054665961401353824L;

	@Id
	@Column(name = "t_id")
	private String id;
	
	@Column(name = "t_home")
	private String home;
}
