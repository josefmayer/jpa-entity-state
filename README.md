## JPA Entity States
Transitions between the states of a JPA entity  <br />



### Technologies
JPA, Hibernate, Entity, MySQL <br />
JUnit <br /> 


### Project Structure
##### Model
Entity: <br />
Address <br />




### Steps
##### MySQL
Start MySQL server  <br />

Create database: <br />
*mysql –uroot –p –e “source src/main/resources/create_database.sql”*  <br />

Create table:  <br />
*mysql –uroot –p –e “source src/main/resources/create_table_address.sql”*  <br />


##### Run JUnit test
*mvn test*  <br />





