## Code Review

### General Comments
- You have to add README.md
- Add spotless
- Remove unnecessary imports
- Add Exceptions and appropriate ExceptionTests
- Swagger is not added
- Spell Mistake in main method


## model
### Booking

- Remove unnecessary imports->import java.util.Date;

### TaxiRepository
- No usage file added->   List<Taxi> findByCurrentLocationAndIsAvailable(String currentLocation, boolean isAvailable);
### security
- Remove unnecessary imports from all the classes
### TaxiBookingService & TaxiBookingServiceTest
- Remove commented codes
- Add Exceptions tests 
- Try to use builder() while testing-> in service class builder() is used for createBooking and cancelBooking
### UserService & UserServiceTest
  - Remove commented imports
  - Try to use builder() while testing->used setter method
### TaxiService
- try to use builder() while testing.-> used setter method
- No testcase for Invalid Login
