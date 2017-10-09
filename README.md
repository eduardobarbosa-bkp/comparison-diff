# Comparison diff - Eduardo Barbosa da Costa

***Setup***
* Java 8 and apache-maven 3.3.x;

***Run***
* On the command line on the project root:

1. *mvn package*
2. *mvn wildfly-swarm:run*

The endpoints will be available in: http://localhost:8080

POST

<host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right

*request json format*
```javascript
{
	"content":""
}
```
- content: text data base64 encoded

GET

<host>/v1/diff/<ID>

*response json format*
```javascript
{
  "diffs": ""
}
```

- diffs: insight of the diffs with sign [+] before an addition and [-] before a remove



