package com.industrika.maintenance.dto;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.testng.Assert.*;


import java.io.Serializable;

import org.testng.annotations.*;

public class ResourceTests {
	private Resource resource;
	
	@BeforeMethod
	public void setUp(){
		resource = new Resource();
	}
	
	@Test
	public void ShouldBeInstanceOfBaseDto(){
		assertThat("Should be derived from BaseDto", resource, instanceOf(BaseDto.class));
	}
	
	@Test
	public void ShouldImplementSerializable(){
		assertThat("Should be serializable", resource,  instanceOf(Serializable.class));
	}
	
	@Test
	public void ShouldHaveNameProperty(){
		final String name = "name";
		resource.setName(name);
		
		assertThat("Should set name properly", name, equalTo(resource.getName()));
	}
	
	@Test
	public void ShouldHaveResourceTypeProperty(){
		ResourceType type = new ResourceType();
		
		resource.setResourceType(type);
		
		assertThat("Should set resource type properly",type, sameInstance(resource.getResourceType()));
	}
}
