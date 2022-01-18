package com.acervera.classpathreport;

import lombok.Value;

import java.util.Optional;

@Value
public class LibraryInfo {
   String name;
   Optional<String> version;
}
