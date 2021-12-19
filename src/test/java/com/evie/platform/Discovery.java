package com.evie.platform;

import com.evie.example.xUnit0410.TestLife;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.UniqueIdTrackingListener;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;

import java.util.Set;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

/**
 * @author evie
 */
public class Discovery {

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectClass(TestLife.class)
                )
//                .filters(
//                        includeClassNamePatterns(".*Tests")
//                )
                .build();



        SummaryGeneratingListener listener = new SummaryGeneratingListener();
//        LegacyXmlReportGeneratingListener legacyXmlReportGeneratingListener =
//                new LegacyXmlReportGeneratingListener();

        try (LauncherSession session = LauncherFactory.openSession()) {
            Launcher launcher = session.getLauncher();
            // Register a listener of your choice
            launcher.registerTestExecutionListeners(listener);
            // Discover tests and build a test plan
            TestPlan testPlan = launcher.discover(request);
            // Execute test plan
            launcher.execute(testPlan);
            // Alternatively, execute the request directly
//            launcher.execute(request);


        }

        TestExecutionSummary summary = listener.getSummary();
        System.out.println(summary);
// Do something with the summary...
    }

}
