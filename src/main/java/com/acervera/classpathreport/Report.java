package com.acervera.classpathreport;

import lombok.extern.java.Log;
import lombok.val;

import java.io.File;
import java.util.Optional;
import java.util.regex.Pattern;

@Log
public class Report {

   // ^(?<name>[\d\w]*)(-(?<version>\d[\d\w\.]*))?\.jar$
   private final static Pattern regExp = Pattern.compile("^(?<name>[\\d\\w]*)(-(?<version>\\d[\\d\\w\\.]*))?\\.jar$", Pattern.CASE_INSENSITIVE);

   public static Optional<LibraryInfo> extractInfo(String location) {
      val name = new File(location).getName();
      val matcher = regExp.matcher(name);
      if(matcher.matches()) {
         return Optional.of(new LibraryInfo(
            matcher.group("name"),
            Optional.ofNullable(matcher.group("version"))
         ));
      } else {
         log.severe(String.format("No patter for a library name for location [%s]", location));
         return Optional.empty();
      }
   }

//   def extract() = {
//      System
//         .getProperty("java.class.path")
//         .split(":")
//         .filter(_.endsWith(".jar"))
//         .groupBy(extractLibName)
//         .toList
//         .sortBy(_._1)
//         .foreach { case (jar, locations) =>
//         println("------------")
//         println(
//            s"$jar => ${locations.map(extractLibVersion).distinct.mkString("[", ",", "]")}"
//      )
//         locations.foreach(l => println(s"   $l"))
//      }
//   }
//
//   public static void main(String[] args) {
//      System.out.println(System.getProperty("java.class.path"));
//   }
}
