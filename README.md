# Test

REpofor testing from b1

## Prerequisites:
* Java 8.
* Maven 3.3.9
* Mysql 5.7 (MariaDB 10.3.7)
* Git

## Build
```bash
$ git clone https://github.com/periket2000/Test.git
$ cd Test
$ mvn clean package
```

## Run the application
```bash
# After doing mvn clean package
$ java -cp "target/parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250
```
By default the application takes the configuration from the packaged jar as well as the logs to parse, see https://github.com/periket2000/Test/blob/master/src/main/resources/META-INF/config.properties and https://github.com/periket2000/Test/blob/master/src/main/resources/META-INF/access.log

This can be changed by defining the following environment variable before running the tool:
```bash
$ export WALLETHUB_PARSER_PROPERTIES_FILE=/tmp/config.properties
```
And setting this properties accordingly.

## Application properties
The following properties can be set in the config.properties file:
* database.persistence.unit: The persistence unit used by JPA (default wallethub_banned)
* database.truncate.table: true/false value indicating if truncate the tables in every run
* parser.input.file: Path to the access.log file (or the file we want to parse)
* logging.file: Path to the log file where we want to write the application logs.

## DB structure
See https://github.com/periket2000/Test/blob/master/dev/create_schema.sql
### Table for the aggregated results => banned
* id: The row identifier (autogenerated)
* run: The parameters passed in the command line
* banned_ip: The banned ip due to exceed requests rate
* requests: Number of request done by the ip in the period
* comment: Comment or reason why the ip is banned
* start_date: Start date (we take into account requests made between start_date and start_date+duration)
* end_date: Command execution date (This is because we allow have several executions of the command in the table although by default the tables are truncated on each execution)
### Table for the raw logs => requests
* id: The row identifier (autogenerated)
* ip: The ip that made the request
* request_date: The date at which the request was made
* request: The request path
* request_status: The request status code
* request_agent: The client agent that made the request

## SQL queries:
See https://github.com/periket2000/Test/blob/master/dev/queries.txt
### Query 1
* IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00
```sql
select ip, count(*) hits from requests
where request_date BETWEEN '2017-01-01.13:00:00' AND '2017-01-01.14:00:00'
GROUP BY ip HAVING hits > 100;
```
### Query 2
* Requests made by a given IP (for instance 192.168.77.101)
```sql
select * from requests where ip = '192.168.77.101';
```
