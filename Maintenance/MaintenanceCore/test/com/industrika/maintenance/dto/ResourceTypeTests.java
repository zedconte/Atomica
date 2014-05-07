package com.industrika.maintenance.dto;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


import java.io.Serializable;

import org.testng.annotations.*;



public class ResourceTypeTests {

	private ResourceType resourceType;
	
	@BeforeMethod
	public void setUp() throws Exception {
		resourceType = new ResourceType();
	}

	@Test
	public void ShouldImplementSerializable() {
		assertThat("Should be serializable", resourceType, instanceOf(Serializable.class));
	}
	
	@Test
	public void ShouldBeInstanceOfBaseDto(){
		assertThat("Derives from BaseDto", resourceType, instanceOf(BaseDto.class));
	}
	
	@Test
	public void ShouldHaveNameProperty(){
		final String name = "name";
		resourceType.setName(name);
		
		assertThat("Should set name properly", name, equalTo(resourceType.getName()));
	}

}
