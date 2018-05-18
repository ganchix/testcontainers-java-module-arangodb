# TestContainers ArangoDB testing module [![Build Status](https://travis-ci.org/ganchix/testcontainers-java-module-arangodb.svg?branch=master)](https://travis-ci.org/ganchix/testcontainers-java-module-arangodb) [![codecov](https://codecov.io/gh/ganchix/testcontainers-java-module-arangodb/branch/master/graph/badge.svg)](https://codecov.io/gh/ganchix/testcontainers-java-module-arangodb) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/arangodb-spring-boot-parent/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/arangodb-spring-boot-parent) [![GitHub stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star)](https://github.com/ganchix/testcontainers-java-module-arangodb)

Testcontainers module for [ArangoDB](https://www.arangodb.com/).

# Table of Contents
 
- [Overview](#overview)
- [Getting started](#getting-started)
- [License](#license)


### Overview

See [testcontainers.org](https://www.testcontainers.org) for more information about Testcontainers.

### Getting started

#### Add dependency

```
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers-java-module-arangodb</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Gradle

```
compile group: 'org.testcontainers', name: 'testcontainers-java-module-arangodb', version: '0.0.1'
```

#### Code example

Running ArangoDB during a test:

```java
public class SomeTest {


	@Rule
	public ArangoDBContainer arangoDBContainer = new ArangoDBContainer().withoutAuthentication();

    
	@Test
	public void simpleTestWithClientCreation() {
		ArangoDB arangoDB = arangoDBContainer.getArangoDB();
		assertNotNull(arangoDB.getVersion().getServer());
	}
}
```


### License

Testcontainers module for ArangoDB is licensed under the MIT License. See [LICENSE](LICENSE.md) for details.

Copyright (c) 2018 Rafael Ríos Moya


