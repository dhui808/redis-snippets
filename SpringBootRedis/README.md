### Add an item (Linux)
	curl -vX POST http://localhost:8080/menu -d @item.json \
	--header "Content-Type: application/json"

### Get an item
	curl -v localhost:8080/menu/1
	