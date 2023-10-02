PORT := lsof -t -i:5001

all: 
	echo "All"
	mvn test
	mvn compile
	mvn clean 
	mvn package 
	sudo docker build -t quote-server
	sudo docker run quote-server 

test: 
	mvn test 
	
build: 
	mvn compile 
	mvn verify 
	mvn package 

docker:
	sudo docker build -t quote-server .
	sudo docker run -p 5000:5000 quote-server