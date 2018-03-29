
CREATE TABLE AirplaneTypes(
  AirplaneType VARCHAR,
  PRIMARY KEY (AirplaneType)
);


CREATE TABLE FlightSeats(
  AirplaneType VARCHAR,
  Row INT,
  Column VARCHAR(1),
  IsFirstClass BIT,
  PRIMARY KEY (AirplaneType, Row, Column)
  FOREIGN KEY (AirplaneType) REFERENCES AirplaneTypes(AirplaneType)
);

CREATE TABLE Locations(
  City VARCHAR,
  PRIMARY KEY (City)
);

CREATE TABLE Airlines(
  Airline VARCHAR,
  PRIMARY KEY (Airline)
);

CREATE TABLE Flights(
  FlightNumber VARCHAR,
  Airline VARCHAR,
  AirplaneType VARCHAR,
  DepartureLocation VARCHAR,
  ArrivalLocation VARCHAR,
  DepartureTime DATETIME,
  ArrivalTime DATETIME,
  PriceCoach INT,
  PriceFirstClass INT,
  HasMeal BIT,
  HasVegeterianMeal BIT,
  HasEntertainment BIT,
  PRIMARY KEY (FlightNumber, DepartureTime),
  FOREIGN KEY (Airline) REFERENCES Airlines(Airline)
  FOREIGN KEY (DepartureLocation) REFERENCES  Locations(City),
  FOREIGN KEY (ArrivalLocation) REFERENCES  Locations(City),
  FOREIGN KEY (AirplaneType) REFERENCES AirplaneTypes(AirplaneType)
);

CREATE TABLE Reviews(
  FlightNumber VARCHAR,
  Airline VARCHAR,
  DepartureTime VARCHAR,
  Name VARCHAR,
  PassportNumber VARCHAR,
  Rating INT,
  Comment VARCHAR,
  PRIMARY KEY (FlightNumber, DepartureTime, Name, PassportNumber),
  FOREIGN KEY (FlightNumber, DepartureTime) REFERENCES Flights(FlightNumber, DepartureTime),
  FOREIGN KEY (Name, PassportNumber) REFERENCES Users(Name, PassportNumber)
);


CREATE TABLE Users(
  Name VARCHAR,
  IsMinor BIT,
  PassportNumber VARCHAR,
  PRIMARY KEY (Name, PassportNumber)
);

CREATE TABLE Reservations(
  FlightNumber VARCHAR,
  DepartureTime DATETIME,
  Name VARCHAR,
  PassportNumber VARCHAR,
  AirplaneType VARCHAR,
  SeatRow INT,
  SeatColumn VARCHAR(1),
  Bags INT,
  HasVegeterianMeal BIT,
  PRIMARY KEY (FlightNumber, DepartureTime, Name, PassportNumber),
  FOREIGN KEY (FlightNumber, DepartureTime) REFERENCES Flights(FlightNumber, DepartureTime),
  FOREIGN KEY (Name, PassportNumber) REFERENCES Users(Name, PassportNumber),
  FOREIGN KEY (AirplaneType, SeatRow, SeatColumn) REFERENCES FlightSeats(AirplaneType, Row, Column)
);










