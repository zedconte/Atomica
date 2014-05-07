package com.industrika.commons.i18n;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommonsMessagesTest {

	@Test
	public void testGetMessage() {
		assertTrue("El valor obtenido para el nombre es incorrecto",CommonsMessages.getMessage("firstName").equalsIgnoreCase("Nombre"));
	}

}
