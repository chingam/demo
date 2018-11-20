package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t91009")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Association extends CommonEntity implements Serializable {

	private static final long serialVersionUID = -4724127004747426884L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_association_id")
	private String associationId;
	@Column
	private String patientNo;

	@Column
	private String targetUuid;
	@Column
	private String sourceUuid;
	@Column
	private String associationType;// APPEND,REPLACE,TRANSFORM,TRANSFORM_AND_REPLACE, HAS_MEMBER,SIGNS,
									// IS_SNAPSHOT_OF, UPDATE_AVAILABILITY_STATUS, SUBMIT_ASSOCIATION
	@Column
	private String label; // ORIGINAL,REFERENCE
	@Column
	private String entryUuid;
	@Column
	private String docCode;
	@Column
	private String previousVersion;
	@Column
	private String originalStatus; // APPROVED,DEPRECATED, SUBMITTED
	@Column
	private Boolean associationPropagation;

}
