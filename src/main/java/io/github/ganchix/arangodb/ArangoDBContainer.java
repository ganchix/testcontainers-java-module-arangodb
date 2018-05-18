package io.github.ganchix.arangodb;

import com.arangodb.ArangoDB;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static io.github.ganchix.arangodb.ArangoDBConstants.*;

@Slf4j
public class ArangoDBContainer<SELF extends ArangoDBContainer<SELF>> extends GenericContainer<SELF>  {

	private static final Object DRIVER_LOAD_MUTEX = new Object();

	private ArangoDB arangoDB;

	private String password;

	public ArangoDBContainer() {
		this(LATEST_VERSION);
	}

	public ArangoDBContainer(String version) {
		super(IMAGE + ":" + version);
	}

	@Override
	protected void configure() {
		withExposedPorts(PORT);
		withLogConsumer(new Slf4jLogConsumer(log));
	}

	public SELF withPassword(String password) {
		if (getEnvMap().containsKey(ARANGO_NO_AUTH) || getEnvMap().containsKey(ARANGO_RANDOM_ROOT_PASSWORD)) {
			log.error("Random or without authentication is enable, please review your configuration");
			throw new RuntimeException("Random or without authentication is enable, please review your configuration");
		}
		withEnv(ARANGO_ROOT_PASSWORD, password);
		this.password = password;
		return self();
	}

	public SELF withoutAuthentication() {
		if (getEnvMap().containsKey(ARANGO_ROOT_PASSWORD) || getEnvMap().containsKey(ARANGO_RANDOM_ROOT_PASSWORD)) {
			log.error("Random authentication or with password is enable, please review your configuration");
			throw new RuntimeException("Random authentication or with password is enable, please review your configuration");
		}
		withEnv(ARANGO_NO_AUTH, "1");
		return self();
	}

	public SELF withRandomPassword() {
		if (getEnvMap().containsKey(ARANGO_ROOT_PASSWORD) || getEnvMap().containsKey(ARANGO_NO_AUTH)) {
			log.error("Without authentication or with password is enable, please review your configuration");
			throw new RuntimeException("Without authentication or with password is enable, please review your configuration");
		}
		withEnv(ARANGO_RANDOM_ROOT_PASSWORD, "1");
		return self();
	}

	public String getPassword() {
		return password;
	}

	public Integer getPort() {
		return PORT;
	}

	public String getUser() {
		return USER;
	}

	public String getHost() {
		return HOST;
	}

	public String getDatabase() {
		return DATABASE;
	}

	public ArangoDB getArangoDB() {
		synchronized (DRIVER_LOAD_MUTEX) {
			if (arangoDB == null) {
				try {
					arangoDB = new ArangoDB.Builder().host(getContainerIpAddress(), getMappedPort(PORT))
							.user(USER)
							.password(StringUtils.isNotBlank(password) ? password : null)
							.build();
				} catch (Exception e) {
					throw new RuntimeException("Could not get ArangoDB", e);
				}
			}
		}

		return arangoDB;

	}
}
