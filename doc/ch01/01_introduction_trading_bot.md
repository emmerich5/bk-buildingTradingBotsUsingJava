# CHAPTER 01 - INTRODUCTION TO TRADING BOT

* Some mundane trading tasks are provided via API by OANDA, LMAX, and others which make trading manageable.

# BOT SUMMARY (APP)

## App Features
* Account management
* Integrate with realtime market data feed
* Disseminate of market data
* Place orders
* Handle order/trade and account events
* Analyze of historic prices
* Integrate with Twitter
* Develop strategies

## Specification

| Design Goal | Benefit |
| ----------- | ------- | 	
| Services should solve a single problem or collection of problems. | Easy unit testing, and code reuse |
| Services should be loosely couple | Reduces dependencies, and becomes more maintainable |
| Services must have high unit test coverage | Prevents bug manifestation in production (financial transactions) |

## Code Organization

Three JARS (3 projects)

* **tradingbot-core** : Generic trading exchange implementation with generic services/interfaces.
* **oanda-restapi** : Specific trading exchange implementation of API
* **tradingbot-app** : Main application which utilizes trading implementation (Spring Injection), provides strategies, and future integrations (Social Media) 

Main Book Package `com.precioustech.fxtrading`
Main Local Package `com.fx`

## Coding Stack

* Java SDK 1.7+
* Spring Framework 4.1, Spring Social 1.1 (dependency in the tradingbot-app project only)
* Guava 18.0
* HttpClient 4.3
* Maven 3.2.5
* Eclipse IDE
* OANDA REST API

## OANDA

* Using API v20 REST API
* Using fxTrade Practice/Test URL: https://api-fxpractice.oanda.com
* Must Generate API Token for demo account: Manage API Access > Revoke OR Generate (depending which do you need) Only 1 per account. 
* Must use testing account: MyFunds > fxTrade Practice Accounts > v20 Account Number: nnn-nnn-nnnnmmmm-nnn

Request Example:
```http
GET /v3/accounts/101-001-11608487-001/instruments/ HTTP/1.1
Host: api-fxpractice.oanda.com
Authorization: Bearer hereisalargetoken-code
Cache-Control: no-cache
```

Answer Example:
```http
{
    "instruments": [
        {
            "name": "EUR_ZAR",
            "type": "CURRENCY",
            "displayName": "EUR/ZAR",
            "pipLocation": -4,
            "displayPrecision": 5,
            "tradeUnitsPrecision": 0,
            "minimumTradeSize": "1",
            "maximumTrailingStopDistance": "1.00000",
            "minimumTrailingStopDistance": "0.00050",
            "maximumPositionSize": "0",
            "maximumOrderUnits": "100000000",
            "marginRate": "0.07",
            "tags": [
                {
                    "type": "ASSET_CLASS",
                    "name": "CURRENCY"
                }
            ]
        },
        {
            "name": "EUR_PLN",
            "type": "CURRENCY",
            "displayName": "EUR/PLN",
            "pipLocation": -4,
            "displayPrecision": 5,
            "tradeUnitsPrecision": 0,
            "minimumTradeSize": "1",
            "maximumTrailingStopDistance": "1.00000",
            "minimumTrailingStopDistance": "0.00050",
            "maximumPositionSize": "0",
            "maximumOrderUnits": "100000000",
            "marginRate": "0.05",
            "tags": [
                {
                    "type": "ASSET_CLASS",
                    "name": "CURRENCY"
                }
            ]
        }
    ]
}  
```

# APP ARCHITECTURE

* Several type of keys exists. They will be captured in the `OandaJsonKeys` class
* Spring will be utilized for setting up the environment (test/production/etc) for Oanda connection.

## Event-Driven Architecture
* App must react to market events, social media events, user events, and more.
* Goal is for the state transitions to be thread-safe.
* The heart of an event-driven system consist of infinite loops.

### Typical Event Chain
1. Event is received from the stream.
2. Event is parsed and sent to Event Bus
3. Suscribers(Strategy,etc) are called back by the Event Bus
4. Suscribers may generate a signal(trading sent to a order queue) in response to the event processing.
5. A service pick signals from suscribers to generate a payload for the external platform.
6. The platform sends a new event to the stream.

### Critical Design
* Components will be performing tasks at the same time (Multithreading)
* State change in business objects must always be consistent:
   * Synchronization and Locking is critical.

### Implementation
* Google EventBus from Guava Library will be utilized as broker for publisher/subscriber (Apache Kafka/Flink would be a better implementation)
* An interface which provides transformation of notations (currency pairs, etc)
* A Config class will setup the configuration.

# REFERENCES

* https://github.com/shekharvarshney/book-code.git
* https://developer.oanda.com/rest-live-v20/introduction/