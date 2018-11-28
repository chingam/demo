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
import lombok.ToString;

@Entity
@Table(name = "t130961")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T130961 extends CommonEntity {
	private static final long serialVersionUID = 7068035032567872373L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_patner_id")
	private String patnerId;

	@Column(name = "t_patner_name")
	private String patnerName;
	
	@Column(name = "t_type")
	private String type;

	@Column(name = "t_url")
	private String url;
	
	@Column(name = "t_assigningAuthority")
	private String assigningAuthority;
	
	@Column(name = "t_homecommunity_id")
	private String homeCommunityId;
	
	@Column(name = "t_repo_uniqueid")
	private String repositoryUniqueId;
}
