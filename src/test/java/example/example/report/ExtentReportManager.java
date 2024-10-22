package example.example.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import example.example.context.Constants;

/**
 * The Class handles the report activities.
 *
 * @author Bharathish
 */
public class ExtentReportManager {

    /** The extent reports. */
    private static ExtentReports extentReports;

    /** The map for storing tests. */
    private static Map<Long, ExtentTest> map = new HashMap<>();

    /**
     * Gets the extent reports.
     *
     * @return the extent reports
     */
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            // Initialize the HtmlReporter
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Constants.REPORT_DIRECTORY);
            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
            extentReports.setProjectName(Constants.PROJECT_NAME);
            htmlReporter.loadConfig(new File(Constants.EXTENT_CONFIG_PATH));
        }
        return extentReports;
    }

    /**
     * Start test.
     *
     * @param testName the test name
     * @param desc the description
     */
    public synchronized static void startTest(String testName, String desc) {
        ExtentTest test = getExtentReports().createTest(testName, desc);
        map.put(Thread.currentThread().getId(), test);
    }

    /**
     * Gets the current test.
     *
     * @return the current test
     */
    public synchronized static ExtentTest getCurrentTest() {
        return map.get(Thread.currentThread().getId());
    }

    /**
     * End current test.
     */
    public synchronized static void endCurrentTest() {
        getExtentReports().flush(); // Flush the reports to update
    }
}
