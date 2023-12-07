# JSF Application for collecting timetable preferences

## Specifications

- Database with users and preferences table
- Authentication mechanism 
- Password encryption with bcrypt
- EntityManager objects with dependency injection
- JPA-only annotations for mappings
- Named queries
- PrimeFaces framework
- Web-flow with navigation-rules

## Configuration
- Payara Server version 6.2023.9
- Database: PostgresSQL version 7.8
- IntelliJ IDEA 2023.2.2

### Setting Up Payara Server
- Download Payara Server : https://www.payara.fish/downloads/payara-platform-community-edition/
- Add the new Configuration in IntelliJ 

### Database Configuration
1. Create a new server in your PostgresSQL GUI(I used pgAdmin)
2. Add a new database 
3. Run the SQL scripts located in `src/main/resources/db/migration.sql`
4. Set up JDB connection pool in Payara Server with your database properties
   - resource type: javax.sql.DataSource
   - datasource classname: org.postgresql.ds.PGSimpleDataSource
   - test the ping
5. Set up JDBC Resource with the connection pool created earlier
   - the name should follow the structure : "jdbc/ResourceName"
6. In the project, make sure that the name of the jta data source (located in `src/main/resources/META-INF/persistence.xml` file ) matches your JDBC Resource

### Building and Running the Application
1. Build the project using Maven: `mvn clean install`.
2. Deploy to Payara Server by clicking `run`.
3. When the artifact is deployed, the page should open automatically