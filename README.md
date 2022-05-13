# api-test

**Tech Stack Used**
1. Apache Maven 3.8.5
2. openjdk 11.0.15
3. REST Assured 5.0.1
4. TestNG 7.4.0

**Setup and Run tests**
1. Download / Install Apache Maven 3.8.5 or latest version.
2. Download / Install openjdk >=11 version.
3. Clone the git repository to your local system.
4. Open command line terminal.
5. Change directory to cloned repository folder in the terminal.
6. Execute the following command to trigger the tests: `mvn clean test -DxmlFile=api_tests`


**Framework Improvements:**

1. Maintain base details like URI, api version, authentication details in a config file and read as config object.
2. Maintain resource names in a properties file or as an enum for easy maintenance and updates.
3. Introduce logging and reporting for debug and tests analysis.
4. Maintain POJO objects for all endpoints request and response validations.
5. Maintain JSON schema in JSON file for schema validations.
6. Write efficient re-usable API validation methods to avoid duplicating the code.
7. Introduce Base API test class for maintaining before and after tests status and logging.
8. Implement common methods for serialization and deserialization request and response.
9. Implement concurrency and parallelism to improve tests execution time etc.