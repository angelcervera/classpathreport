package com.acervera.classpathreport;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportTest {

   @ParameterizedTest
   @CsvSource({
      "/home/xxxxx/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar,opentest4j,1.2.0",
      "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/cldrdata.jar,cldrdata,",
      "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunpkcs11.jar,sunpkcs11,",
      "/home/xxxxx/projects/classpathreport/target/classes,,",
   })
   void extractLibVersion(String location, String expectedName, String expectedVersion) {
      if(expectedName == null) {
         assertEquals(Optional.empty(), Report.extractInfo(location));
      } else {
         assertEquals(Optional.of(new LibraryInfo(expectedName, Optional.ofNullable(expectedVersion))), Report.extractInfo(location));
      }
   }

}
