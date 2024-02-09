Taxi Booking and Billing System using Spring Boot
This project implements a Taxi Booking and Billing System using Spring Boot. It allows users to book taxis, calculate fares, handle billing, and manage user accounts.

Entities
User:
Fields: id, name, email, password, accountBalance.
Taxi:
Fields: id, driverName, licenseNumber, currentLocation.
Booking:
Fields: id, userId, taxiId, pickupLocation, dropoffLocation, fare, bookingTime, status.

Fare Calculation and Billing
Fare is calculated based on distance and a fixed rate per kilometer.
After the ride is complete, the fare is deducted from the user's account balance.
Handling scenarios where the user's account balance is insufficient.
Exception Handling and Validation
Robust exception handling for various scenarios like invalid booking requests, payment failures, etc.
Spring's validation annotations are used for validating user inputs.
Unit Testing
Comprehensive unit tests for the system.
Mockito is used for mocking dependencies and JUnit for testing.