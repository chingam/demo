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
@Table(name = "t91001")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssigningAuthority implements Serializable {

	private static final long serialVersionUID = 6567357535491034240L;
	
	@Id
	@Column(name = "t_universalid")
	private String universalId;
	
	@Column(name = "t_universalidtype")
	private String universalIdType;
}
