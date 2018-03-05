DOCKER_IP=$(shell docker-machine ip default)
PROJECT=beat

launch_sonar:
	docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube

sonar:
	mvn -f $(PROJECT) sonar:sonar \
		-Dsonar.host.url=http://$(DOCKER_IP):9000 \
		-Dsonar.jdbc.url="jdbc:h2:tcp://$(DOCKER_IP)/sonar"

launch_rmi:
	rmiregistry \
		-J-Djava.rmi.server.useCodebaseOnly=false \
		-J-Djava.rmi.server.codebase=file:///$(shell pwd)/$(PROJECT)/target/classes/
