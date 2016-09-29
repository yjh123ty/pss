package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Depot;

public class DepotServiceTest extends BaseServiceTest{
	@Autowired
	private IDepotService depotService;

	@Test
	public void all() throws Exception {
		System.out.println(depotService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		depotService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Depot depot = new Depot();
		depot.setName("xxx");
		depotService.save(depot);
	}
	
	@Test
	public void update() throws Exception {
		Depot depot = depotService.get(103L);
		depot.setName("song");
		depotService.update(depot);
	}
	
}
