package io.github.ganchix.arangodb;

import com.arangodb.ArangoDB;
import org.junit.Rule;
import org.junit.Test;

import static io.github.ganchix.arangodb.ArangoDBConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ArangoDBContainerTest {


	@Rule
	public ArangoDBContainer arangoDBContainer = new ArangoDBContainer().withoutAuthentication();

	@Test
	public void simpleTestWithClientCreation() {
		ArangoDB arangoDB = arangoDBContainer.getArangoDB();
		assertNotNull(arangoDB.getVersion().getServer());
	}

	@Test(expected = Exception.class)
	public void testErrorTryRunWithoutAuthenticationAndRandomPassword() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
		arangoDBContainer.withRandomPassword();
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}

	@Test(expected = Exception.class)
	public void testErrorTryRunWithoutAuthenticationAndSetPassword() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
		arangoDBContainer.withPassword("Pass");
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}

	@Test(expected = Exception.class)
	public void testErrorTryWithPasswordAndRunWithoutAuthentication() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withPassword("Pass");
		arangoDBContainer.withoutAuthentication();
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}

	@Test(expected = Exception.class)
	public void testErrorTryWithRandomPasswordAndRunWithoutAuthentication() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withRandomPassword();
		arangoDBContainer.withoutAuthentication();
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}

	@Test
	public void testGetter() {

		assertEquals(arangoDBContainer.getDatabase(),DATABASE);
		assertEquals(arangoDBContainer.getHost(), HOST);
		assertNull(arangoDBContainer.getPassword());
		assertEquals(arangoDBContainer.getUser(), USER);
		assertEquals(arangoDBContainer.getPort(), PORT);


	}

	@Test(expected = Exception.class)
	public void testGetClientFail(){
		arangoDBContainer.stop();
		arangoDBContainer.getArangoDB();
	}
}
