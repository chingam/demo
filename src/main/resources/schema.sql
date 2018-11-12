DROP TABLE IF EXISTS t01003;
CREATE TABLE t01003 (
  T_ENTRY_USER varchar(12) default NULL,
  T_ENTRY_DATE date default NULL,
  T_UPD_USER varchar(12) default NULL,
  T_UPD_DATE date default NULL,
  T_FORM_CODE varchar(6) default NULL,
  T_LANG1_NAME varchar(50) default NULL,
  T_LANG2_NAME varchar(50) default NULL,
  T_VERSION_NO varchar(20) default NULL,
  T_REPORT_FLAG varchar(1) default NULL
) ;

DROP TABLE IF EXISTS t01008;
CREATE TABLE t01008 (
  T_ENTRY_USER varchar(12) default NULL,
  T_ENTRY_DATE date default NULL,
  T_UPD_USER varchar(12) default NULL,
  T_UPD_DATE date default NULL,
  T_ROLE_CODE varchar(4) NOT NULL,
  T_FORM_CODE varchar(6) NOT NULL,
  T_CAN_OPN varchar(1) default NULL,
  T_CAN_INS varchar(1) default NULL,
  T_CAN_UPD varchar(1) default NULL,
  T_CAN_DEL varchar(1) default NULL,
  T_CAN_QRY varchar(1) default NULL,
  PRIMARY KEY  (T_FORM_CODE,T_ROLE_CODE)
) ;

DROP TABLE IF EXISTS t01199;
CREATE TABLE t01199 (
  T_ENTRY_DATE date default NULL,
  T_ENTRY_USER varchar(12) default NULL,
  T_UPD_DATE date default NULL,
  T_UPD_USER varchar(12) default NULL,
  T_LINK_LABEL_ID decimal(10,0) NOT NULL,
  T_LANG2_NAME varchar(100) default NULL,
  T_LANG1_NAME varchar(100) default NULL,
  T_LINK_TEXT varchar(100) default NULL,
  T_LINK_SEPERATION decimal(10,0) NOT NULL,
  T_ROLE_CODE varchar(4) NOT NULL,
  T_INACTIVE_FLAG varchar(1) default NULL
) ;
