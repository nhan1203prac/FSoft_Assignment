package com.fa_training.finalTest.Service;

import com.fa_training.finalTest.Dto.RegistrationDTO;
import com.fa_training.finalTest.Exception.CountryNotFoundException;
import com.fa_training.finalTest.Exception.InvalidPackageUpdateException;
import com.fa_training.finalTest.Exception.PackageNotFoundException;
import com.fa_training.finalTest.Model.Country;
import com.fa_training.finalTest.Model.Registration;
import com.fa_training.finalTest.Model.Package;
import com.fa_training.finalTest.Repository.CountryRepository;
import com.fa_training.finalTest.Repository.PackageRepository;
import com.fa_training.finalTest.Repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CountryRepository countryRepository;
    private final PackageRepository packageRepository;

    /**
     * Creates a new course registration record.
     * Calculates the total amount based on country and package rules.
     *
     * @param dto Data Transfer Object containing registration details.
     * @return The saved Registration entity.
     */
    @Transactional
    public Registration create(RegistrationDTO dto) {

        Country country = countryRepository.findById(dto.getCountryID())
                .orElseThrow(() -> new CountryNotFoundException(
                        "Country information does not exist in the system"));

        Package pkg = packageRepository.findById(dto.getPackageID())
                .orElseThrow(() -> new PackageNotFoundException(
                        "Package information does not exist in the system"));

        double total = calculateTotalAmount(
                dto.getNumLessons(),
                pkg.getCostOfLessons(),
                country.getCountryID());

        Registration reg = new Registration();
        reg.setRegistrationID(generateNextId());
        reg.setFullName(dto.getFullName());
        reg.setPhoneNumber(dto.getPhoneNumber());
        reg.setNumLessons(dto.getNumLessons());
        reg.setEffectiveDate(dto.getEffectiveDate());
        reg.setExpirationDate(dto.getExpirationDate());
        reg.setTotalAmount(total);
        reg.setCountry(country);
        reg.setLessonPackage(pkg);

        return registrationRepository.save(reg);
    }

    /**
     * Searches for registrations by customer name and country ID.
     *
     * @param name Name of the customer (partial match).
     * @param countryID ID of the country.
     * @return List of matching Registration records.
     */
    @Transactional(readOnly = true)
    public List<Registration> search(String name, String countryID) {
        String n  = (name      != null) ? name.trim()      : "";
        String cid = (countryID != null) ? countryID.trim() : "";
        return registrationRepository.searchByNameAndCountry(n, cid);
    }

    /**
     * Finds a specific registration by its unique ID.
     *
     * @param id The Registration ID (e.g., R001).
     * @return The Registration entity.
     */
    @Transactional(readOnly = true)
    public Registration findById(String id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Registration not found: " + id));
    }

    /**
     * Updates an existing registration record.
     * Applies business rules: If the package is changed, the new cost per lesson must be higher.
     *
     * @param id The ID of the record to update.
     * @param dto DTO containing updated values.
     * @return The updated Registration entity.
     */
    @Transactional
    public Registration update(String id, RegistrationDTO dto) {

        Registration reg = findById(id);

        Country country = countryRepository.findById(dto.getCountryID())
                .orElseThrow(() -> new CountryNotFoundException(
                        "Country information does not exist in the system"));

        Package newPkg = packageRepository.findById(dto.getPackageID())
                .orElseThrow(() -> new PackageNotFoundException(
                        "Package information does not exist in the system"));

        Package oldPkg = reg.getLessonPackage();

        if (!newPkg.getPackageID().equals(oldPkg.getPackageID())) {
            if (newPkg.getCostOfLessons() <= oldPkg.getCostOfLessons()) {
                String dateToken = (reg.getEffectiveDate() != null)
                        ? reg.getEffectiveDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                throw new InvalidPackageUpdateException(
                        "Error_[" + dateToken + "] " + reg.getFullName() + ": Invalid package change");
            }
        }

        boolean isPackageChanged = !newPkg.getPackageID().equals(oldPkg.getPackageID());
        boolean isCountryChanged = !country.getCountryID().equals(reg.getCountry().getCountryID());
        boolean isNumLessonsChanged = !dto.getNumLessons().equals(reg.getNumLessons());

        double total;
        if (isPackageChanged || isCountryChanged || isNumLessonsChanged) {
            total = calculateTotalAmount(dto.getNumLessons(), newPkg.getCostOfLessons(), country.getCountryID());
        } else {
            total = reg.getTotalAmount();
        }

        reg.setFullName(dto.getFullName());
        reg.setCountry(country);
        reg.setLessonPackage(newPkg);
        reg.setNumLessons(dto.getNumLessons());
        reg.setTotalAmount(total);

        return registrationRepository.save(reg);
    }

    /**
     * Calculates the total cost of a registration based on business discounts.
     * Rule 1: Lessons beyond the 30th get a 10% discount.
     * Rule 2: Customers from Vietnam (VN) get a 5% discount on the final total.
     *
     * @param numLessons Total number of lessons.
     * @param costPerLesson Base cost per lesson from the package.
     * @param countryID ID of the country to check for regional discount.
     * @return The final calculated total, rounded to 2 decimal places.
     */
    public double calculateTotalAmount(int numLessons, double costPerLesson, String countryID) {

        double total;

        if (numLessons <= 30) {
            total = numLessons * costPerLesson;
        } else {
            double first30  = 30 * costPerLesson;
            double extra    = (numLessons - 30) * costPerLesson * 0.90;
            total = first30 + extra;
        }

        if ("VN".equalsIgnoreCase(countryID)) {
            total = total * 0.95;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Generates a sequential ID for new registrations based on current database count.
     * Format: Rxxx (e.g., R001, R002).
     *
     * @return A formatted String ID.
     */
    public String generateNextId() {
        long count = registrationRepository.countAll();
        return String.format("R%03d", count + 1);
    }
}