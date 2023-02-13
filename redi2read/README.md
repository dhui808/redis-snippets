Cloned from https://github.com/redis-developer/redi2read

### Set up
	git submodule add https://github.com/redis-developer/redismod-docker-compose docker
	git submodule add https://github.com/redis-developer/redi2read-data src/main/resources/data

### Quick Start
	git clone --branch course/milestone-1 https://github.com/redis-developer/redi2read.git --recurse-submodule

### Modify docker-compose.yml
	--loadmodule /usr/lib/redis/modules/redisgears.so

### Install Redis
	sudo apt-add-repository ppa:redislabs/redis
	sudo apt-get update
	sudo apt-get upgrade
	sudo apt-get install redis-server
 
### Run
	docker-compose up
	docker container ls
	docker exec -it docker-redis-1 bash
	redis-cli
	MODULE list

### Course 2
	git clone --branch course/milestone-2 https://github.com/redis-developer/redi2read.git
	
	Ubuntu:
	docker-compose up
	
	mvn clean package exec:java -DskipTests=true
	
	Git Bash:
	curl -X POST http://localhost:8080/api/redis/strings \
	-H 'Content-Type: application/json' \
	-d '{ "database:redis:creator": "Salvatore Sanfilippo" }'
	
	curl localhost:8080/api/redis/strings/database:redis:creator
	
### Course 3
	Ubuntu:
	redis-cli
	keys com.redislabs.edu.redi2read.models.Role*
	type com.redislabs.edu.redi2read.models.Role
	type com.redislabs.edu.redi2read.models.Role:5934c949-961d-4ff9-8ea7-abb647174ab5
	hgetall com.redislabs.edu.redi2read.models.Role:5934c949-961d-4ff9-8ea7-abb647174ab5
	
### Course 4
	KEYS "com.redislabs.edu.redi2read.models.User"
	SCARD "com.redislabs.edu.redi2read.models.User"
	SRANDMEMBER "com.redislabs.edu.redi2read.models.User"
	HGETALL "com.redislabs.edu.redi2read.models.User:6154022699401839193"
	
	curl localhost:8080/api/users/?email=donald.gibson@example.com
	curl localhost:8080/api/users

### Course 5	
	curl localhost:8080/api/books/all
	curl localhost:8080/api/books/1783989114
	curl localhost:8080/api/books/categories
	curl localhost:8080/api/books/categories/434caa63-b996-4561-9bcb-130770f06579
	curl localhost:8080/api/books
	curl 'localhost:8080/api/books/?size=25&page=2'

### Course 6
	curl 'localhost:8080/api/carts/fd49f685-9265-4fd1-8ca4-482ea472dc73'
	
	FT.INFO "books-idx"
	FT.SEARCH books-idx "networking" RETURN 1 title
	FT.SEARCH books-idx "@title:networking" RETURN 1 title
	FT.SEARCH books-idx "clo*" RETURN 4 title subtitle authors.[0] authors.[1]
	FT.SEARCH books-idx "%scal%" RETURN 2 title subtitle
	FT.SEARCH books-idx "rust | %scal%" RETURN 3 title subtitle authors.[0]
	curl 'localhost:8080/api/books/search/?q=%25java%25'
	curl 'localhost:8080/api/books/search/?q=%25scal%25'
	curl 'localhost:8080/api/books/authors/?q=brian%20w'
	curl 'localhost:8080/api/books/authors/?q=brian%20wi'

	GRAPH.QUERY imdb-grf "CREATE (:Actor {name: 'Kathryn Hahn', nick: 'ka'})"
	GRAPH.QUERY imdb-grf "CREATE (:Show {name: 'WandaVision', nick: 'wv'})"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'ka'}), (s:Show {nick: 'wv'}) CREATE (a)-[:ACTS]->(s)"
	GRAPH.QUERY "imdb-grf" "match (n) return distinct labels(n)"
	
	CreateGraph does not work - java.lang.NoClassDefFoundError: redis/clients/jedis/commands/MultiKeyBinaryRedisPipeline
	
### Course 8
	redis-cli
	GRAPH.QUERY imdb-grf "CREATE (:Actor {name: 'Kathryn Hahn', nick: 'ka'})"
	GRAPH.QUERY imdb-grf "CREATE (:Show {name: 'WandaVision', nick: 'wv'})"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'ka'}), (s:Show {nick: 'wv'}) CREATE (a)-[:ACTS]->(s)"
	GRAPH.QUERY "imdb-grf" "match (n) return distinct labels(n)"
	
	GRAPH.QUERY imdb-grf "CREATE (:Actor {name: 'Paul Bettany', nick: 'pb'})"
	GRAPH.QUERY imdb-grf "CREATE (:Actor {name: 'Paul Rudd', nick: 'pr'})"
	
	GRAPH.QUERY imdb-grf "CREATE (:Show {name: 'The Shrink Next Door', nick: 'tsnd'})"
	GRAPH.QUERY imdb-grf "CREATE (:Movie {name: 'Iron Man', nick: 'im'})"
	GRAPH.QUERY imdb-grf "CREATE (:Movie {name: 'Our Idiot Brother', nick: 'oib'})"
	GRAPH.QUERY imdb-grf "CREATE (:Movie {name: 'Captain America: Civil War', nick: 'cacw'})"
	
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'pb'}), (s:Show {nick: 'wv'}) CREATE (a)-[:ACTS]->(s)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'pb'}), (m:Movie {nick: 'im'}) CREATE (a)-[:ACTS]->(m)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'ka'}), (m:Movie {nick: 'oib'}) CREATE (a)-[:ACTS]->(m)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'pr'}), (m:Movie {nick: 'oib'}) CREATE (a)-[:ACTS]->(m)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'pr'}), (m:Movie {nick: 'cacw'}) CREATE (a)-[:ACTS]->(m)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'pr'}), (s:Show {nick: 'tsnd'}) CREATE (a)-[:ACTS]->(s)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor {nick: 'ka'}), (s:Show {nick: 'tsnd'}) CREATE (a)-[:ACTS]->(s)"

	GRAPH.QUERY "imdb-grf" "MATCH ()-[e]->() RETURN distinct type(e)"
	GRAPH.QUERY "imdb-grf" "MATCH (n) RETURN distinct labels(n), count(n)"
	GRAPH.QUERY imdb-grf "MATCH (a:Actor)-[:ACTS]->(:Show {name:'The Shrink Next Door'}) RETURN a.name"
	GRAPH.QUERY "imdb-grf" "MATCH (a1:Actor)-[:ACTS]->(m:Movie)<-[:ACTS]-(a2:Actor) WHERE a1 <> a2 AND id(a1) > id(a2) RETURN m.name, a1.name, a2.name"

	curl 'http://localhost:8080/api/recommendations/user/55214615117483454'
	java.lang.NoClassDefFoundError: redis/clients/jedis/commands/MultiKeyBinaryRedisPipeline

### Course 9
	Remove graph related class CreateGraph.
	
	Enable application-wide caching with the @EnableCaching annotation
	Using the @Cacheable annotation at method level
	
	curl 'http://localhost:8080/api/books/search?q=java'
	
