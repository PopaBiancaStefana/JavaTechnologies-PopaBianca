# JSF Web Application for managing Students and their Projects

# Specifications  
- PostgresSQL database with students and projects tables
- Defined Entity Classes and Beans
- PrimeFaces framework
- Web-flow with navigation-rules 
- Internationalization

## Updates:
  - Switched to Payara server
  - DatSource (configured a connection pool and a JDBC resource using Payara Server)
  - Used Templates:
    - page.xhtml - containing header, content, footer (from separate .xhtml files); 
    - the header includes a menu bar - uses navigation rules
    - the footer displays a copyright notice and the current version of the application
    - dataView.xhtml: a generic page for displaying data as a dataTable 
    - dataEdit.xhtml: a generic page for editing data, with a dialog
  
  - Used EntityManager objects with dependency injection
  - Defined the mappings using JPA-only annotations
  - Used named queries using JPA-Q
  - Implemented the Repository classes
  - Created a complete test unit for the CRUD operations for one entity class with JUnit
  - Implemented the repository classes as Enterprise Java Beans


# Configuration
- Payara Server version 6.2023.9
- Database: PostgresSQL version 7.8
- IntelliJ IDEA 2023.2.2

### Setting Up Payara Server
- Download Payara Server : https://www.payara.fish/downloads/payara-platform-community-edition/
- Add the new Configuration in IntelliJ

### Database Configuration
1. Create a new server in your PostgresSQL GUI(I used pgAdmin) and add a new database
2. Run the SQL scripts located in `src/main/resources/db/migration.sql`
3. Set up a JDBC connection pool in Payara Server with your database properties
    - resource type: javax.sql.DataSource
    - datasource classname: org.postgresql.ds.PGSimpleDataSource
    - test the ping
4. Set up a JDBC Resource with the connection pool created earlier
    - the name should follow the structure : "jdbc/ResourceName"
5. In the project, make sure that the name of the jta data source (located in `src/main/resources/META-INF/persistence.xml` file ) matches your JDBC Resource

### Building and Running the Application
1. Build the project using Maven: `mvn clean install`.
2. Deploy to Payara Server by clicking `run`.
3. When the artifact is deployed, the page should open automatically