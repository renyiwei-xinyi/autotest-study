package com.evie.autotest.platform;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * @author evie
 */
public class TestPlanStart {


    public static void run(String className, String... methodName){

        //todo：执行用例
        try (LauncherSession session = LauncherFactory.openSession()) {
            Launcher launcher = session.getLauncher();

            LauncherDiscoveryRequest request = new DiscoveryStart(className, methodName).request();
            TestPlan testPlan = launcher.discover(request);
            launcher.execute(testPlan);
        }

    }
}
