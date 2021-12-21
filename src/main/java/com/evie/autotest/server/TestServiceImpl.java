package com.evie.autotest.server;


import com.evie.autotest.api.TestService;
import com.evie.autotest.platform.DiscoveryStart;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Override
    public String run(String className, String... methodName) {

        try (LauncherSession session = LauncherFactory.openSession()) {
            Launcher launcher = session.getLauncher();

            LauncherDiscoveryRequest request = new DiscoveryStart(className, methodName).request();
            launcher.registerTestExecutionListeners();
            TestPlan testPlan = launcher.discover(request);
            launcher.execute(testPlan);
        }

        return "Hello Word";
    }

}
