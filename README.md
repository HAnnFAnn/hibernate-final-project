We have a relational MySQL database with a schema (country-city, language per country). 
There's a frequent query for cities that is causing performance issues. We've come up with 
a solution - to offload all frequently queried data into Redis (an in-memory key-value storage).
My result: Redis:  ms MySQL:  ms