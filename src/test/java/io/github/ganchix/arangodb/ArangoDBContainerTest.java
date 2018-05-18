package io.github.ganchix.arangodb;

import com.arangodb.ArangoDB;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ArangoDBContainerTest {

	@Rule
	public ArangoDBContainer arangoDBContainer = new ArangoDBContainer().withoutAuthentication();

	@Test
	public void simpleTestWithClientCreation() {
		ArangoDB arangoDB = arangoDBContainer.getArangoDB();
		assertNotNull(arangoDB.getVersion().getServer());
	}

	@Test(expected = Exception.class)
	public void simpleTestErrorTryRunWithoutAuthenticationAndRandomPassword() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
		arangoDBContainer.withRandomPassword();
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}

	@Test(expected = Exception.class)
	public void simpleTestErrorTryRunWithoutAuthenticationAndSetPassword() {
		arangoDBContainer.stop();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
		arangoDBContainer.withPassword("Pass");
		arangoDBContainer.start();
		arangoDBContainer = arangoDBContainer.withoutAuthentication();
	}



}
