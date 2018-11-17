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
		repository.save(patner);
		repository.save(patner2);
		repository.save(patner3);
	}

}
