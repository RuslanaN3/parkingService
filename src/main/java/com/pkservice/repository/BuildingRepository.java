package com.pkservice.repository;

import com.pkservice.entity.Building;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query(nativeQuery = true,
           value = "SELECT b.* FROM building b " +
                   "INNER JOIN sections s ON s.building_id = b.id " +
                   "INNER JOIN vehicle v ON v.section_id = s.id " +
                   "WHERE v.license_plate = :licensePlate")
    Optional<Building> findBuildingByVehicleLicensePlate(String licensePlate);

}
