### Add an item (Linux)
	curl -v POST http://localhost:8080/menu -d @item.json \
	--header "Content-Type: application/json"

### Get an item
	curl -v localhost:8080/menu/1

### Delete an item
	curl -vX DELETE localhost:8080/menu/1	