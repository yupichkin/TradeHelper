# TradeHelper
Program that describes situation about changes for a cuncurency in last day by showing gif image with specific tag  
For a using, fork project and run main class (TradeHelperApplication.java) that located in src/main/java/com/example/demo/TradeHelperApplication.java  
Then open a browser and follow a link of this kind - (http://localhost:8080/getSituation/{param})  
Where param - currency letter code by ISO 4217. For example dollar-USD, russian ruble - RUB, korean won - KRW and other.  
If your param is correct, you will be redirected to a random GIF with a special tag
describing the change in the specified currency in relation to the US dollar over the last 24 hours.
## GIF tags
### rich - currency rate has grown
### broke - currency rate has decreased
### hold - currency rate hasn't changed
## Examples (for 29.11.21 7:00 GMT+3)
### rich
http://localhost:8080/getSituation/czk  
result GIF: https://giphy.com/gifs/ghoststarz-power-starz-powerghost-d9SySV9FPO6Zk22L38
### broke
http://localhost:8080/getSituation/rub  
result GIF: https://giphy.com/gifs/callmesaygrace-shrug-little-girl-no-money-j3ykSDXZ5ST1RUOdQ5
### hold
http://localhost:8080/getSituation/usd  
result GIF: https://giphy.com/gifs/cbs-tough-as-nails-toughasnails-season-2-KMkgkVdDtTmsOQEiDG
