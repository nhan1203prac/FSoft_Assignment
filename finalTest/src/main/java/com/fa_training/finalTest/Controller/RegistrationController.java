package com.fa_training.finalTest.Controller;

import com.fa_training.finalTest.Dto.RegistrationDTO;
import com.fa_training.finalTest.Exception.CountryNotFoundException;
import com.fa_training.finalTest.Exception.InvalidPackageUpdateException;
import com.fa_training.finalTest.Model.Registration;
import com.fa_training.finalTest.Repository.CountryRepository;
import com.fa_training.finalTest.Repository.PackageRepository;
import com.fa_training.finalTest.Service.RegistrationService;
import com.fa_training.finalTest.Validator.RegistrationValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CountryRepository countryRepository;
    private final PackageRepository packageRepository;

    /**
     * Displays the form for creating a new registration.
     * @param model Spring UI Model to hold form data and selection lists.
     * @return The view path for the creation form.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setRegistrationDate(LocalDate.now());

        model.addAttribute("registration", dto);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("packages",  packageRepository.findAll());
        return "registration/form";
    }

    /**
     * Processes the submission of a new registration form.
     *
     * @param dto Data bound from the form.
     * @param bindingResult Holds validation results.
     * @param model Spring UI Model for returning data in case of errors.
     * @param redirectAttributes For passing success messages across redirects.
     * @return Redirect to list view on success, or back to form on failure.
     */
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("registration") RegistrationDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        RegistrationValidator.validate(dto, bindingResult);

        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("packages",  packageRepository.findAll());

        if (bindingResult.hasErrors()) {
            return "registration/form";
        }

        try {
            registrationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Registration saved successfully!");
            return "redirect:/registrations";

        } catch (CountryNotFoundException ex) {
            model.addAttribute("countryError", ex.getMessage());
            return "registration/form";

        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "registration/form";
        }
    }

    /**
     * Lists all registrations with optional filtering by customer name and country.
     * @param searchName Partial customer name to filter by.
     * @param searchCountry Country ID to filter by.
     * @param model Spring UI Model.
     * @return The registration list view.
     */
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "") String searchName,
            @RequestParam(required = false, defaultValue = "") String searchCountry,
            Model model) {

        List<Registration> registrations =
                registrationService.search(searchName, searchCountry);

        double grandTotal = registrations.stream()
                .filter(r -> r.getTotalAmount() != null)
                .mapToDouble(Registration::getTotalAmount)
                .sum();

        model.addAttribute("registrations",  registrations);
        model.addAttribute("grandTotal",     grandTotal);
        model.addAttribute("countries",      countryRepository.findAll());
        model.addAttribute("searchName",     searchName);
        model.addAttribute("searchCountry",  searchCountry);
        return "registration/list";
    }

    /**
     * Displays the edit form for an existing registration.
     *
     * @param id Unique ID of the registration to edit.
     * @param model Spring UI Model containing DTO and reference data.
     * @return The edit view path.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Registration reg = registrationService.findById(id);
        RegistrationDTO dto = mapToDTO(reg);

        double oldPackageCost = reg.getLessonPackage() != null
                ? reg.getLessonPackage().getCostOfLessons() : 0.0;

        model.addAttribute("oldCountryName", reg.getCountry() != null ? reg.getCountry().getCountryName() : "—");
        model.addAttribute("oldPackageName", reg.getLessonPackage() != null ? reg.getLessonPackage().getPackageID() : "—");

        model.addAttribute("registration", dto);
        model.addAttribute("oldPackageCost", oldPackageCost);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("packages", packageRepository.findAll());
        return "registration/edit";
    }

    /**
     * Processes updates for an existing registration.
     * @param id Registration ID from path variable.
     * @param dto Updated registration data.
     * @param bindingResult Validation results.
     * @param oldPackageCost The cost of the package before this update attempt.
     * @param model Spring UI Model.
     * @param redirectAttributes Redirect message storage.
     * @return Redirect to list or back to edit view with error details.
     */
    @PostMapping("/update/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("registration") RegistrationDTO dto,
            BindingResult bindingResult,
            @RequestParam(required = false, defaultValue = "0") double oldPackageCost,
            Model model,
            RedirectAttributes redirectAttributes) {

        RegistrationValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors() || model.containsAttribute("packageError")) {
            Registration origin = registrationService.findById(id);

            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("packages", packageRepository.findAll());
            model.addAttribute("oldPackageCost", oldPackageCost);

            model.addAttribute("oldCountryName", origin.getCountry() != null ? origin.getCountry().getCountryName() : "—");
            model.addAttribute("oldPackageName", origin.getLessonPackage() != null ? origin.getLessonPackage().getPackageID() : "—");
            model.addAttribute("origin", origin);

            return "registration/edit";
        }

        try {
            registrationService.update(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Course registration update successful");
            return "redirect:/registrations";

        } catch (InvalidPackageUpdateException ex) {
            Registration origin = registrationService.findById(id);
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("packages", packageRepository.findAll());
            model.addAttribute("oldPackageCost", oldPackageCost);
            model.addAttribute("oldCountryName", origin.getCountry() != null ? origin.getCountry().getCountryName() : "—");
            model.addAttribute("oldPackageName", origin.getLessonPackage() != null ? origin.getLessonPackage().getPackageID() : "—");
            model.addAttribute("origin", origin);

            model.addAttribute("packageError", ex.getMessage());
            return "registration/edit";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "registration/edit";
        }
    }

    /**
     * Internal utility to map a Registration Entity to a RegistrationDTO.
     *
     * @param reg The source Registration Entity.
     * @return A RegistrationDTO populated with entity values.
     */
    private RegistrationDTO mapToDTO(Registration reg) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setRegistrationID(reg.getRegistrationID());
        dto.setFullName(reg.getFullName());
        dto.setPhoneNumber(reg.getPhoneNumber());
        dto.setNumLessons(reg.getNumLessons());
        dto.setEffectiveDate(reg.getEffectiveDate());
        dto.setExpirationDate(reg.getExpirationDate());
        dto.setTotalAmount(reg.getTotalAmount());
        dto.setCountryID(reg.getCountry() != null ? reg.getCountry().getCountryID() : null);
        dto.setPackageID(reg.getLessonPackage() != null ? reg.getLessonPackage().getPackageID() : null);
        return dto;
    }
}