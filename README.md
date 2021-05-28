# Parking Assistant

###  Back-end part of parking assistant application.


Web App using Spring Boot, Spring MVC, Spring Data JPA.

MySQL database locally, data.sql is provided for initializing database.


`GET /api/parking-lot`

The response is a list of parking lots.

`GET /api/parking-lot/{id}`

The response is a parking lot with respective id.

`POST /api/parking-lot`

Request param - image. Response is suitable parking lot and slot.

Data sources provide data through methods:

`PATCH /api/parking-slot/sensors-data/{parkingLotId}`

`PATCH /api/parking-slot/cam-data/{parkingLotId}`

The response is updated parking slots statuses.

To test api you can send image from camera 5 with various weather conditions and occupancy of parking lot.
Images are presented [here].

CNRPark+EXT is a dataset for visual occupancy detection of parking lots of roughly 150,000 labeled images 
(patches) of vacant and occupied parking spaces, built on a parking lot of 164 parking spaces.

> Parking occupancy prediction API must running. 


[here]: http://cnrpark.it 