# Monitoring
Service for URL availability monitoring

## How to build:
It's a maven project
```
mvn clean install
```
Deploy in docker container
```
docker build -t monitoring .

docker run -p 8080:8080 monitoring
```

## How to use:

**/start/{url}**  
Start monitoring on url 
Request type: POST  
Params: string url
Returns: void

```
/start/jnetworks.by
```

**/stop/{url}**  
Stop monitoring on url
Request type: POST  
Params: string url

```
/stop/jnetworks.by
```

**/last-result**  
Get last result monitoring
Request type: GET  
Params: string url 
Returns: monitoringDto - object with two field, for example:
```
{
    "url": "jnetworks.by",
    "available": "Available"
}
```



