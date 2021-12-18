package com.sang9xpro.autopro;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sang9xpro.autopro");

        noClasses()
            .that()
            .resideInAnyPackage("com.sang9xpro.autopro.service..")
            .or()
            .resideInAnyPackage("com.sang9xpro.autopro.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.sang9xpro.autopro.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
