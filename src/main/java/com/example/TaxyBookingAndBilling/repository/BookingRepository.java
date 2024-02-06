package com.example.TaxyBookingAndBilling.repository;

import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
