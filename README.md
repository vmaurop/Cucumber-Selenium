# Automated Regression Test Suite

This module, contains the required implementation for the automated regression testing.

<b>&emsp;Prerequisite Software:</b>

- [JAVA JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Apache MAVEN](https://maven.apache.org/download.cgi)

<b>&emsp;Frameworks/Tools that are used:</b>

- Selenium WebDriver
- Cucumber BDD
- TestNG
- REST Assured
- Maven
- Selenium Grid
- Lombok

#### Executing Tests Locally

Local execution can be done via IDE (e.g. IntelliJ IDEA) or via Maven Command. 
In IntelliJ IDEA below plugins are needed: 

- cucumber for java
- gherkin

For execution via Maven, bellow command is used:

    mvn clean install

For partial execution of a specific test case / suite, filtering can be done with the tags that are written in feature files.
If for example, there is a scenario in a feature file with tag "@TC-UI-REFDATA-001", for explicit execution of this scenario, below command is used:

    mvn clean install -Ppartial -Dcucumber.filter.tags=@SMOKE-TEST-001
    mvn clean install -Pparrallel -Dcucumber.filter.tags=@SMOKE-TESTS

-Ppartial is the profile to isolate the proper Cucumber Runners
-Dcucumber.filter.tags is the way to filter specific tags to be executed. 

Regarding <b>environment variables</b>, by default src/main/resources/env.properties is used

    |-- resources
        |-- env.properties




*Note:* For local UI test execution, if you do not want to use the deployed Selenium Grid Hub, on your properties file leave *DRIVER.REMOTE.URL* empty. 
That way a Browser will be spawned on your local machine, that will execute the specified UI tests.

### Project Structure

Automated Regression Test Suite is a maven project consisting of application's module.
This repository contains all the required code, which will automatically perform actions on UI 
and API. An integration with other applications can be also supported.

As far as Design Patterns are concerned, Page Object Pattern is the main design pattern used [Page Object Model](https://www.selenium.dev/documentation/guidelines/page_object_models/).

#### Properties files

```
# Environment URLs Configuration
APP.URL=
APP-API.API.URL=
# Users
ADMIN.USERNAME=
ADMIN.PASSWORD=

###################################
# Timeout and Interval in seconds
TIMEOUT.ELEMENT.FIND=10
TIMEOUT.ENDPOINT.RESPONSE=120
TIMEOUT.DECLARATION.STATUS=180
TIMEOUT=120
INTERVAL=5
###################################
# WebDriver Configuration
BROWSER=chrome
DRIVER.MODE=
DRIVER.REMOTE.URL=
HEADLESS.MODE=false
###################################
#Date/Time Configuration
DATETIME.FORMAT=yyyy-MM-dd HH:mm:ss
###################################
#Debug configuration
DEBUG.MODE=false
###################################
#Elastic
ELASTIC=true
```

Properties files, which can be found under src/main/resources or other alternatives under src/main/resources/draft, contain data (URLs, usernames etc.) for each environment under test.
The default property file "env.properties" that is used/called and defined in pom.xml properties, resides within src/main/resources.

#### Debug Mode
If you set DEBUG.MODE=true to your environment properties the browser does not quit 
and remains open after the test. It is useful for you, if you want  to check the CTA log messages and status.

#### Driver Mode
If you set DRIVER.MODE=manual it means that webdriver files based on the installed browsers version must be downloaded and copied to src/test/resources/drivers path manually.
In other case, webdrivers are downloaded automatically based on the installed browsers version. DRIVER.MODE "manual" is needed when running tests from a machine with no internet access.

#### Feature Files

For Cucumber, Feature files (_.feature_) are the files used to describe your tests in Descriptive Language (like English), using the 
[Gherkin Language](https://cucumber.io/docs/gherkin/reference/). All feature files can be found under [src/test/resources/features]

####  Lombok
To optimize the builder and class management, the Lombok Java Library is used.  

### Test Execution Order

The order in which all tests are executed is:

1. e2e-test-parallel-execution
2. e2e-test-linear-execution

Executions (1), (2) are the two "main" executions and all other executions are basically a retry for any failed tests occurring on "main" executions.

This order is defined on the module's main pom.xml file, using the maven-surefire-plugin. For example:

```
  <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>${maven-surefire-plugin.version}</version>
      <configuration>
          <skipTests>${skipTests}</skipTests>
          <testFailureIgnore>true</testFailureIgnore>
          <runOrder>alphabetical</runOrder>
          <systemProperties>
              <property>
                  <name>env.properties</name>
                  <value>${env.properties}</value>
              </property>
          </systemProperties>
      </configuration>
      <executions>
          <execution>
              <id>e2e-test-parallel-execution</id>
              <phase>test</phase>
              <goals>
                  <goal>test</goal>
              </goals>
              <configuration>
                  <threadCount>${threads}</threadCount>
                  <includes>
                      <include>com/netcompany/intrasoft/runners/parallel/E2ECucumberParallelRunner.java
                      </include>
                  </includes>
                  <properties>
                      <property>
                          <name>suitename</name>
                          <value>E2ECucumberParallelRunner</value>
                      </property>
                      <property>
                          <name>dataproviderthreadcount</name>
                          <value>${threads}</value>
                      </property>
                  </properties>
              </configuration>
          </execution>
```
Execution (1)
```
<threads>6</threads>
```
again within the main pom.xml file _(This property is overwritten from the parameters of the Automated Tests [Jenkins Pipeline](https://jenkins.pdcicdsvc.athens.intrasoft-intl.private:30225/view/E2E/job/e2e-trader-portal-multibranch-standalone/))_.
You can also overwrite this property by using `-Dthreads={enter number of threads}` when running tests locally.
For example
```
mvn clean install -Dthreads=4
```
Execution (2) run linearly.

Partial execution that uses profile "-Ppartial" and specific tag filtering (-Dcucumber.filter.tags='@TC-UI-REFDATA-001') runs only linear.

### Run Tests from a Machine with No Internet Access

Test automation project is a MAVEN project which means that downloads dependencies during build. 
When the tests need to be executed from a machine with no internet access, below steps are proposed: 

- Get the test automation project in a machine with internet access and the prerequisite software (maven, java)


- Create under a folder (e.g. C:\temp) a "settings.xml" file with the path of the repository folder.

        <?xml version="1.0" encoding="UTF-8"?>
        
        <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
            <localRepository>C:\temp\repository</localRepository>
        </settings>

  <i>Update the &lt;localRepository&gt; xml element accordingly with the same path that your "settings.xml" file resides.</i>

  Store "settings.xml" file in the same path with repository (e.g. C:\repository)
  

- Build the test automation project from that machine with internet access skipping tests to get all the needed JARs in the repository folder

         mvn clean install -DskipTests -s "C:\TEMP\settings.xml"

- After successful build transfer the repository folder and the "settings.xml" file to the machine with no internet access. 
  If you copy them to the same folder no update is needed. 
  If you copy them into a different folder, &lt;localRepository&gt; xml element must be updated accordingly.


- Having the repository and settings.xml file now, using argument "-s", you are able to build and run the tests without internet access because all the needed libraries are already downloaded in this "repository" folder. 
  So, to build and execute the tests you need argument "-s" in maven command. An example is displayed below:

        mvn clean install -s C:/settings.xml