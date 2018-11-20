package com.example.demo.model;

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

@Entity
@Table(name = "t91006")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSet extends CommonEntity {

	private static final long serialVersionUID = 6303178046434049831L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_id")
	private String id;
	
	@Column(name = "t_document_id")
	private String documentId;
	
	@Column(name = "t_repositoryid")
	private String repositoryId;
	
	@Column(name = "t_home_id")
	private String homeCommunityId;
	
	@Column(name = "t_new_doc")
	private String newDocumentId;
	
	@Column(name = "t_new_repository")
	private String newRepository;
	
	@Column(name = "t_path")
	private String path;
}
