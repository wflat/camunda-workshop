# Camunda Workshop

Trainingsprojekt für den Camunda-Workshop

## Links zu Camunda Web-Apps

* _Tasklist_: [http://localhost:8080/app/tasklist/](http://localhost:8080/app/tasklist/)
* _Cockpit_: [http://localhost:8080/app/cockpit/](http://localhost:8080/app/cockpit/)
* _Admin_: [http://localhost:8080/app/admin/](http://localhost:8080/app/admin/)


## Arbeiten mit JMS Queues

Link zu [_hawtio Console_](http://localhost:8080/actuator/hawtio/activemq/) 

### Neuen Umsatz erzeugen

Eine neue JMS Message an die 
[_neuer-umsatz-queue_](http://localhost:8080/actuator/hawtio/activemq/sendMessage?pref=Connect&nid=root-org.apache.activemq-Broker-localhost-Queue-neuer-umsatz-queue) 
Queue senden.

#### Headers
* cfdContentType = Kontoumsatz

#### Nachricht

`{"iban":"DE 1234 5678 1234 5678 90","kontentyp":"CFD","betrag":10.0}`


### Bestätigungsnachricht erzeugen

Eine neue JMS Message an die 
[_partnerkonto-bestaetigung-queue_](http://localhost:8080/actuator/hawtio/activemq/sendMessage?pref=Connect&nid=root-org.apache.activemq-Broker-localhost-Queue-partnerkonto-bestaetigung-queue) 
Queue senden.

#### Headers
* cfdContentType = KontoumsatzBestaetigung
* JMSCorrelationID = `<BusinessKey von der Prozessinstanz>`

#### Nachricht

`{"erfolgreich": true}`
