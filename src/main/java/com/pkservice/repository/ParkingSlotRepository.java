package com.pkservice.repository;

import com.pkservice.entity.ParkingSlot;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    Set<ParkingSlot> findAllByParkingLot_Id(Long parkingLotId);

    Optional<ParkingSlot> findBySlotNumber(Long slotNumber);

}
