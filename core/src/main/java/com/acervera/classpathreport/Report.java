package com.acervera.classpathreport;

import lombok.extern.java.Log;
import lombok.val;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class Report {

   // ^(?<name>[\d\w_-]*)(-(?<version>\d[\d\w\.-]*))?\.jar$
   private final static Pattern regExp = Pattern.compile("^(?<name>[\\d\\w_-]*)(-(?<version>\\d[\\d\\w\\.-]*))?\\.jar$", Pattern.CASE_INSENSITIVE);

   public static Optional<LibraryInfo> extractInfo(String location) {
      val name = new File(location).getName();
      val matcher = regExp.matcher(name);
      if (matcher.matches()) {
         return Optional.of(new LibraryInfo(
            matcher.group("name"),
            Optional.ofNullable(matcher.group("version"))
         ));
      } else {
         log.severe(String.format("Not matching pattern for the library named [%s]. Full location [%s]", name, location));
         return Optional.empty();
      }
   }

   public static Map<String, Set<Optional<String>>> extract(String classpath) {
      return Arrays.stream(classpath.trim().split(":"))
         .parallel()
         .flatMap(line -> extractInfo(line).map(Stream::of).orElseGet(Stream::empty))
         .collect(Collectors.groupingByConcurrent(LibraryInfo::getName, Collectors.mapping(LibraryInfo::getVersion, Collectors.toSet())));
   }

   public static String csv(Map<String, Set<Optional<String>>> libs) {
      val report = new StringBuffer();
      for (val name : libs.keySet()) {
         val versions = libs.get(name);
         report.append(name);
         for (val version : versions) {
            report.append(",");
            report.append(version.orElse("NO_VERSION"));
         }
         report.append("\n");
      }
      return report.toString();
   }

   public static String csv() {
      return csv(extract(System.getProperty("java.class.path")));
   }

   public static void main(String[] args) {
      System.out.println(csv());
   }
}
