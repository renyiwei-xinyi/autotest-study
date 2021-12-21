package com.evie.autotest.listener;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class CreateListener {

    public static LegacyXmlReportGeneratingListener getReportListener(){
        String p = "C:\\Project\\autotest-sutdy\\src\\main\\resources\\report";

        Path path = new File(p).toPath();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(p + "\\TEST-junit-jupiter.xml")) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LegacyXmlReportGeneratingListener(path, pw);

    }
}
