# Neuronet

This project has been generated by the lagom/lagom-scala.g8 template. 

For instructions on running and testing the project, see https://www.lagomframework.com/get-started-scala.html.



C:\Programs\kafka_2.11-2.1.0 > bin\windows\zookeeper-server-start.bat config\zookeeper.properties
C:\Programs\kafka_2.11-2.1.0 > bin\windows\kafka-server-start.bat config\server.properties

Debug
C:\Programs\kafka_2.11-2.1.0 > bin\windows\kafka-topics.bat
C:\Programs\kafka_2.11-2.1.0\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic greetings --from-beginning
sbt clean runAll