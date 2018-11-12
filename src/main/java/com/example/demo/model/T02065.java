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
@Table(name = "t02065")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class T02065 extends CommonEntity {

	private static final long serialVersionUID = 2115091577736284049L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_site_id")
	private String siteId;
	
	@Column(name = "t_site_code")
	private String siteCode;
	
	@Column(name = "t_zone_code")
	private String zoneCode;
	
	@Column(name = "t_lang1_name")
	private String langName1;
	
	@Column(name = "t_lang2_name")
	private String langName2;
	
	@Column(name = "t_contact_name")
	private String contactName;
	
	@Column(name = "t_contact_mobile")
	private String contactMobile;
	
	@Column(name = "t_site_url")
	private String siteUrl;
	
	@Column(name = "t_latitude")
	private Integer latitude;
	
	@Column(name = "t_longitude")
	private Integer longitude;
	
	@Column(name = "t_mudiria")
	private String mudiria;
	
	@Column(name = "t_site_status")
	private String siteStatus;

}
