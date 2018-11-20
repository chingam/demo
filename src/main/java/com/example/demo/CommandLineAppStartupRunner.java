package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{

	@Autowired private T130961Repository repository;
	
	@Override
	public void run(String... args) throws Exception {
		T130961 patner = new T130961();
		patner.setPatnerName("SpiritXDSRegistry");
		patner.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner.setUrl("217.175.200.182:8081/SpiritXDSRegistry/Reg1");
		T130961 patner2 = new T130961();
		patner2.setPatnerName("My server");
		patner2.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner2.setUrl("localhost:8080/xdstools/sim/default__reg/reg/sq");
		T130961 patner3 = new T130961();
		patner3.setPatnerName("ehealthsuisse");
		patner3.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner3.setUrl("ehealthsuisse.ihe-europe.net:8481/xdstools4/sim/default__rep_test_support/reg/sq");
		
		T130961 patner4 = new T130961();
		patner4.setPatnerName("SprintXDSRepository");
		patner4.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner4.setUrl("217.175.200.182:8081/SpiritXDSRepository/Rep1");
		patner4.setHomeCommunityId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.123788");
		patner4.setRepositoryUniqueId("1.3.6.1.4.1.21367.2011.2.3.248");
		
		T130961 patner5 = new T130961();
		patner5.setPatnerName("Ehealthsuisse repository");
		patner5.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner5.setUrl("ehealthsuisse.ihe-europe.net:8481/xdstools4/sim/default__rr/rep/ret");
		patner5.setHomeCommunityId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.123788");
		patner5.setRepositoryUniqueId("1.3.6.1.4.1.21367.13.71.101.1");
		
		T130961 patner6 = new T130961();//1.2.42.20180913113513.116
		patner6.setPatnerName("RDP repository");
		patner6.setAssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000");
		patner6.setUrl("rdp.wbc.co.in:7474/xdstools/sim/meenal__meenal_drr/rep/ret");
		patner6.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner6.setRepositoryUniqueId("1.1.4567332.1.4");
		
		T130961 patner7 = new T130961();
		patner7.setPatnerName("RDP regitry");
		patner7.setAssigningAuthority("1.3.6.1.4.1.21367.13.20.2000");
		patner7.setUrl("rdp.wbc.co.in:7474/xdstools/sim/meenal__meenal_drr/reg/sq");
		patner7.setHomeCommunityId("1.3.6.1.4.1.21367.2012.2.1.1");
		patner7.setRepositoryUniqueId("1.1.4567332.1.4");
		
		repository.save(patner);
		repository.save(patner2);
		repository.save(patner3);
		repository.save(patner4);
		repository.save(patner5);
		repository.save(patner6);
		repository.save(patner7);
	}

}
