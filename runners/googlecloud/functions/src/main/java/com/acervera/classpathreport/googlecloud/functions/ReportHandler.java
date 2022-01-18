package com.acervera.classpathreport.googlecloud.functions;

import com.acervera.classpathreport.Report;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;


public class ReportHandler implements HttpFunction {
   @Override
   public void service(HttpRequest request, HttpResponse response) throws IOException {
      response.setContentType("text/plain");

      String csv = Report.csv();
      System.out.println(csv);
      System.out.println("UNTIL here _______________________________");

      BufferedWriter writer = response.getWriter();
      writer.write("CSV from here");
      writer.newLine();
      writer.write("---------------------------------------");
      writer.newLine();
      writer.write(csv);
      writer.write("---------------------------------------");
      writer.newLine();
      writer.write("CSV until here");
      writer.newLine();
   }
}
