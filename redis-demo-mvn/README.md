### Start Redis Server From Windows WSL
	sudo service redis-server restart

### Build Application
	mvn clean package
	
### Run 
	mvn exec:java
	
### Browser
	http://localhost:5000/repos/{github_user_id}
	
	Sample response:
	{"gitName":"github_user_id",
	 "gitData":"50",
	 "cached":true,
	 "responseTime":"0.694ms"}

	where github user id
### Stop Redis Server
	sudo service redis-server stop

Version 15 
