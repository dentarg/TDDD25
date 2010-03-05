lab3
----

	$ cd lab3
	$ javac database/*.java
	$ java database/Client -ORBInitRef NameService=corbaloc::styr3:10000/NameService
	Enter a number or 'x' for exit:
	  1: getCardsNumber()
	  2: getCardName()
	  3: getCardFile()
	  4: getCardURL()
	  5: getCardThumb()
	  6: getCardArea()
	  7: writeCard()
	> 1
	getCardsNumber(): 27
	> 2
	cardNumber? 1
	getCardName: Three Bulls and Peasant
	> 3
	cardNumber? 2
	getCardFile: cave.jpg
	> 4
	cardNumber? 3
	getCardURL: http://www.ida.liu.se/~trapo/TDDB37/cards/field_sunset.jpg
	> 5
	cardNumber? 4
	getCardThumb: http://www.ida.liu.se/~trapo/TDDB37/cards/thumbs/fishermen_t.jpg
	> 6
	cardNumber? 5
	getCardArea: true
	> 7
	cardNumber? 8
	Message? tut
	writeCard(): http://www.ida.liu.se/~TDDD25/cards/foo

lab4
----	
	
	$ cd lab4
	$ javac database/*.java
	$ java database/Server -ORBInitRef NameService=corbaloc::styr3:10000/NameService
	$ cd lab3
	$ javac database/*.java
	$ java database/Client -ORBInitRef NameService=corbaloc::styr3:10000/NameService
	Enter a number or 'x' for exit:
	  1: getCardsNumber()
	  2: getCardName()
	  3: getCardFile()
	  4: getCardURL()
	  5: getCardThumb()
	  6: getCardArea()
	  7: writeCard()
	> 1
	getCardsNumber(): 27
	> 2
	cardNumber? 2
	getCardName: Cave
	> 3
	cardNumber? 3
	getCardFile: field_sunset.jpg
	> 4
	cardNumber? 4
	getCardURL: http://domain.tld/cards/fishermen.jpg
	> 5
	cardNumber? 5
	getCardThumb: forest_t.jpg
	> 6
	cardNumber? 6
	getCardArea: false
	> 7
	cardNumber? 7
	Message? hello
	writeCard(): http://www.ida.liu.se/~TDDD25/cards/foo
	
