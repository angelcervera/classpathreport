package com.acervera.classpathreport;

import lombok.val;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportTest {
   private static HashMap<String, Set<Optional<String>>> libsInExampleFile() {
      val expected = new HashMap<String, Set<Optional<String>>>();
      expected.put("junit5-rt", new HashSet<>(Arrays.asList(Optional.empty())));
      expected.put("junit-rt", new HashSet<>(Arrays.asList(Optional.empty())));
      expected.put("charsets", new HashSet<>(Arrays.asList(Optional.empty())));
      expected.put("cldrdata", new HashSet<>(Arrays.asList(Optional.empty(), Optional.of("9.9.9"))));
      expected.put("lombok", new HashSet<>(Arrays.asList(Optional.of("1.18.22"), Optional.of("1.18.23"), Optional.of("1.18.23-SNAPSHOT"))));

      return expected;
   }

   @ParameterizedTest
   @CsvSource({
      "/home/xxxxx/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar,opentest4j,1.2.0",
      "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/cldrdata.jar,cldrdata,",
      "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunpkcs11.jar,sunpkcs11,",
      "/home/xxxxx/projects/classpathreport/target/classes,,",
      "/home/xxxxx/.m2/repository/org/junit/platform/junit-platform-commons/1.8.2/junit-platform-commons-1.8.2.jar,junit-platform-commons,1.8.2",
      "/anotherlocation/.m2/repository/org/projectlombok/lombok/1.18.22/lombok-1.18.23-SNAPSHOT.jar,lombok,1.18.23-SNAPSHOT"
   })
   void extractLibVersion(String location, String expectedName, String expectedVersion) {
      if (expectedName == null) {
         assertEquals(Optional.empty(), Report.extractInfo(location));
      } else {
         assertEquals(Optional.of(new LibraryInfo(expectedName, Optional.ofNullable(expectedVersion))), Report.extractInfo(location));
      }
   }

   @Test
   void extract() throws IOException {
      val classpath = IOUtils.toString(new FileReader("src/test/resources/classpath_example.txt")).trim();
      val expected = libsInExampleFile();

      val libs = Report.extract(classpath);

      assertEquals(expected, libs);
   }

   @Test
   public void printReport() {
      System.out.println(Report.csv(libsInExampleFile()));
   }

}
