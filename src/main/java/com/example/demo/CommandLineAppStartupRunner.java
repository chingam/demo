package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.Code;
import com.example.demo.model.T01199;
import com.example.demo.model.T130961;
import com.example.demo.repo.CodeRepository;
import com.example.demo.repo.T01199Repository;
import com.example.demo.repo.T130961Repository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{

	@Autowired private T130961Repository repository;
	@Autowired private T01199Repository repo;
	@Autowired private CodeRepository codeRepo;
	
	@Override
	public void run(String... args) throws Exception {
		T130961 patner = new T130961();
		patner.setPatnerName("SpiritEHR Query (Tiani)");
		patner.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner.setUrl("217.175.200.182:8081/SpiritXDSRegistry/Reg1");
		T130961 patner2 = new T130961();
		patner2.setPatnerName("My server");
		patner2.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner2.setUrl("localhost:8080/xdstools/sim/default__reg/reg/sq");
		T130961 patner3 = new T130961();
		patner3.setPatnerName("Ehealthsuisse Query (ihe-europe)");
		patner3.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner3.setUrl("ehealthsuisse.ihe-europe.net:8481/xdstools4/sim/default__rep_test_support/reg/sq");
		
		T130961 patner4 = new T130961();
		patner4.setPatnerName("SpiritEHR Repository (Tiani)");
		patner4.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner4.setUrl("217.175.200.182:8081/SpiritXDSRepository/Rep1");
		patner4.setHomeCommunityId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.123788");
		patner4.setRepositoryUniqueId("1.3.6.1.4.1.21367.2011.2.3.248");
		
		T130961 patner5 = new T130961();
		patner5.setPatnerName("Ehealthsuisse Repository (ihe-europe)");
		patner5.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner5.setUrl("ehealthsuisse.ihe-europe.net:8481/xdstools4/sim/default__rr/rep/ret");
		patner5.setHomeCommunityId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.123788");
		patner5.setRepositoryUniqueId("1.3.6.1.4.1.21367.13.71.101.1");
		
		T130961 patner6 = new T130961();//1.2.42.20180913113513.116
		patner6.setPatnerName("RDP Repository");
		patner6.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner6.setUrl("rdp.wbc.co.in:7474/xdstools/sim/meenal__meenal_drr/rep/ret");
		patner6.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner6.setRepositoryUniqueId("1.1.4567332.1.4");
		
		T130961 patner7 = new T130961();
		patner7.setPatnerName("RDP Query");
		patner7.setAssigningAuthority("1.3.6.1.4.1.21367.13.20.2000");
		patner7.setUrl("rdp.wbc.co.in:7474/xdstools/sim/meenal__meenal_drr/reg/sq");
		patner7.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner7.setRepositoryUniqueId("1.1.4567332.1.4");
		
		T130961 patner8 = new T130961();
		patner8.setPatnerName("RDP Query (IHE-TEST)");
		patner8.setAssigningAuthority("1.3.6.1.4.1.21367.13.20.1000");
		patner8.setUrl("rdp.wbc.co.in:7474/xdstools/sim/ihetest__ihe_drg/reg/sq");
		patner8.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner8.setRepositoryUniqueId("1.1.4567332.1.4");
		
		T130961 patner9 = new T130961();
		patner9.setPatnerName("RDP Repository (IHE-TEST)");
		patner9.setAssigningAuthority("1.3.6.1.4.1.21367.13.20.1000");
		patner9.setUrl("rdp.wbc.co.in:7474/xdstools/sim/ihetest__ihe_drg/rep/ret");
		patner9.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner9.setRepositoryUniqueId("1.1.4567332.1.4");
		
		repository.save(patner);
		repository.save(patner2);
		repository.save(patner3);
		repository.save(patner4);
		repository.save(patner5);
		repository.save(patner6);
		repository.save(patner7);
		repository.save(patner8);
		repository.save(patner9);
		
		//###################################
		T01199 menuLink = new T01199(1, "Search medical record", "Search medical record", "/query/registryquery", 2, "003", null);
		T01199 menuLink2 = new T01199(2, "Download report", "Download report", "/query/retrieve", 3, "003", null);
		T01199 menuLink3 = new T01199(3, "EHR vendor setup", "EHR vendor setup", "/setup/patnerconfig", 4, "003", null);
		T01199 menuLink4 = new T01199(4, "Zone registration", "Zone registration", "/setup/zone", 4, "003", null);
		T01199 menuLink5 = new T01199(5, "Role registration", "Role registration", "/setup/role", 4, "003", null);
		T01199 menuLink6 = new T01199(6, "Job registration", "Job registration", "/setup/job", 4, "003", null);
		T01199 menuLink7 = new T01199(7, "Site registration", "Site registration", "/setup/site", 4, "003", null);
		T01199 menuLink8 = new T01199(8, "Role permssion", "Role permssion", "/setup/rolepermission", 4, "003", null);
		T01199 menuLink9 = new T01199(9, "User registration", "User registration", "/setup/user", 4, "003", null);
		T01199 menuLink10 = new T01199(10, "Patient registration", "Patient registration", "/transaction/patientregistration", 1, "003", null);
		T01199 menuLink11 = new T01199(11, "Publish the document", "Publish the document", "/transaction/documententry", 1, "003", null);
		T01199 menuLink12 = new T01199(12, "Practice setting", "Practice setting", "/transaction/practicesetting", 1, "003", null);
		T01199 menuLink13 = new T01199(13, "EHR Code setup", "EHR Code setup", "/transaction/code", 1, "003", null);
		T01199 menuLink14 = new T01199(14, "Message log", "Message log", "/query/messagelog", 2, "003", null);
		repo.save(menuLink);
		repo.save(menuLink2);
		repo.save(menuLink3);
		repo.save(menuLink4);
		repo.save(menuLink5);
		repo.save(menuLink6);
		repo.save(menuLink7);repo.save(menuLink8);repo.save(menuLink9);repo.save(menuLink10);repo.save(menuLink11);repo.save(menuLink12);
		repo.save(menuLink13);
		repo.save(menuLink14);
		
		//######################
		Code code = new Code(null, "cl-01", "Report", "1.2.3.4.5.6.7.8");
		Code code1 = new Code(null, "cl-02", "Summary", "1.2.3.4.5.6.7.9");
		Code code2 = new Code(null, "cl-03", "Note", "1.2.3.4.5.6.7.10"); // classCode
		Code code3 = new Code(null, "fr-01", "Anatomi pathology report", "1.2.3.44.25.6.7.10");// format code
		Code code4 = new Code(null, "he-01", "Nursing home", "1.22.3.44.25.6.7.10");// health
		Code code5 = new Code(null, "pr-01", "Family practice", "1.22.3.4.925.6.7.10");// Family
		codeRepo.save(code);codeRepo.save(code1);codeRepo.save(code2);codeRepo.save(code3);codeRepo.save(code4);codeRepo.save(code5);
		
	}

}
