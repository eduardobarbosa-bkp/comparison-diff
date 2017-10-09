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


ex. Comparison of Developer/Tester and Developer/Leader
```
curl -X POST \
  http://localhost:8080/v1/diff/1/left \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"content": "RGV2ZWxvcGVyL0xlYWRlcg=="
}'
```
```
curl -X POST \
  http://localhost:8080/v1/diff/1/right \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"content": "RGV2ZWxvcGVyL1Rlc3Rlcg=="
}'
```
```
curl -X GET \
  http://localhost:8080/v1/diff/1 \
  -H 'cache-control: no-cache'
```
```javascript
{
    "diffs": "Developer/[+]T[-]Le[+]s[+]t[-]a[-]der"
}
```