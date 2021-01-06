# WebsiteMonitoringTool
Website Monitoring Tool is a Scheduler based application which provides a complete set of REST APIs to be used by User to check Uptime/Downtime of any Website along with Average response time.

# Reference Documentation of Website Monitoring Tool
	Website Monitoring Tool is the application which continuously monitors the Websites by using Java Sockets to Ping and record response time.
	Application allows the Users to create the Checks, where he/she can mentioned the Website Url and Frequency interval in Minutes or Hours. 
	Application reads the Active Checks and creates Scheduled Jobs for each Websites for specific Interval, and every Ping details going to be saved in table. 
	User can view the Website Status any time along with average response time, and can also disable/enable the checks. 
	
### Instructions to Setup, Build and run the application

## Setup for Database
* Install "MS SQL SERVER 2019" and "MS SQL Server Management Studio"
* Open "MS SQL Server Management Studio" and execute the SQL Scriipt - "website-monitor-db-script.sql", It will create the Database and tables

## Setup for Build and run Application
* Unzip "website-monitoring-tool.zip" file and import as Maven project in IDE - Eclipse or IntelliJ, or any other IDE. 
* Update the project under Maven options and import the dependencies
* Open application.properties file at path - websitemonitor\src\main\resources\application.properties
* application.properties contains configurations parameters for database setup - database name, username and password need to be change
	spring.datasource.url=jdbc:sqlserver://localhost;databaseName=website-monitor	(database name - website-monitor)
	spring.datasource.username=sa				(database username need to be change)
	spring.datasource.password=administrator	(database password need to be change)
* After creation of Database, tables and setup of application.properties time to run the application. 
* Execute the application -  Run as Java Application, by default application will run at http://localhost:8080/
* Test the Website monitor Api's using postman application
* Refer the REST Api's to test the website monitor application using Postman

## REST Api's Details
* Register Check
	URL - http://localhost:8080/check/register
	Method - POST
	Body - {
				"name": "Java-monitor",
				"websiteUrl": "www.google.com",
				"frequency": 30
			}
			
* View all Checks
	URL - http://localhost:8080/check/view
	Method - GET
	
* View filtered Checks by name
	URL - http://localhost:8080/check/view/byname?name=Java-monitor
	Method - GET
	Param - name = "Java-monitor" 
	
* View filtered Checks by Interval
	URL - http://localhost:8080/check/view/byinterval?interval=30
	Method - GET
	Param - interval = 30

* Disable/Deactivate Check
	URL - http://localhost:8080/check/disable
	Method - PUT
	Body - {
				"id": 1,
				"websiteUrl":"www.google.com"
			}

* Enable/Activate Check
	URL - http://localhost:8080/check/enable
	Method - PUT
	Body - {
				"id": 1,
				"websiteUrl":"www.google.com"
			}

* View Status of Website
	URL - http://localhost:8080/check/status?websiteUrl=www.google.com
	Method - GET
	Param - websiteUrl = "www.google.com"
