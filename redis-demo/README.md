### Start Redis Server From Windows WSL
	sudo service redis-server restart

### Generate Gradle Wrapper
	gradle wrapper --gradle-version 7.6

### Build Application
	gradlew build
	
### Run 
	gradlew run
	
### Browser
	http://localhost:5000/repos/{github_user_id}
	
	Sample response:
	{"gitName":"github_user_id",
	 "gitData":"50",
	 "cached":true,
	 "responseTime":"0.694ms"}

### Stop Redis Server
	sudo service redis-server stop
	