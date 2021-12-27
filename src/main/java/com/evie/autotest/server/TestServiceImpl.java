package com.evie.autotest.server;


import com.evie.autotest.api.TestService;
import com.evie.autotest.listener.CreateListener;
import com.evie.autotest.platform.DiscoveryStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LogManager.getLogger(TestServiceImpl.class);

    @Override
    public String run(String className, String... methodName) {

        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        LegacyXmlReportGeneratingListener reportListener = CreateListener.getReportListener();

        try (LauncherSession session = LauncherFactory.openSession()) {
            Launcher launcher = session.getLauncher();
            launcher.registerTestExecutionListeners(summaryGeneratingListener,reportListener);
            launcher.execute(new DiscoveryStart(className, methodName).request());
        }
        return "用例已经触发成功";
    }


    @Override
    public String run(String className) {

        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        LegacyXmlReportGeneratingListener reportListener = CreateListener.getReportListener();

        try (LauncherSession session = LauncherFactory.openSession()) {
            Launcher launcher = session.getLauncher();
            launcher.registerTestExecutionListeners(summaryGeneratingListener,reportListener);
            launcher.execute(new DiscoveryStart(className).request());
        }
        return "用例已经触发成功";
    }

}
