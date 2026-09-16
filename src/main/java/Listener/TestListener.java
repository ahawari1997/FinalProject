package Listener;

import Core.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


    public class TestListener implements ITestListener {

        private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

        @Override
        public void onTestStart(ITestResult result) {
            LOGGER.info("START  {}", testName(result));
        }

        @Override
        public void onTestSuccess(ITestResult result) {
            LOGGER.info("PASS   {}", testName(result));
        }

        @Override
        public void onTestFailure(ITestResult result) {
            LOGGER.error("FAIL   {} - {}", testName(result), result.getThrowable() == null
                    ? "no exception captured"
                    : result.getThrowable().getMessage());
            attachScreenshot(testName(result));
        }

        @Override
        public void onTestSkipped(ITestResult result) {
            LOGGER.warn("SKIP   {}", testName(result));
        }

        private String testName(ITestResult result) {
            return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
        }

        private void attachScreenshot(String testName) {
            try {
                WebDriver driver = DriverFactory.get();
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Path dir = Path.of("target", "screenshots");
                Files.createDirectories(dir);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS"));
                String safeName = testName.replace('#', '_').replace('.', '_');
                Path destination = dir.resolve(safeName + "_" + timestamp + ".png");
                Files.write(destination, screenshot);

                Allure.addAttachment("Screenshot on failure: " + testName, new ByteArrayInputStream(screenshot));
                LOGGER.info("Saved failure screenshot to {}", destination);
            } catch (Exception e) {
                // A screenshot failure must never hide the original test
                // failure or crash the listener - just log it.
                LOGGER.warn("Could not capture screenshot for {}: {}", testName, e.getMessage());
            }
        }
    }
