package com.ncl.ccp.common;

import com.ncl.ccp.data.OrderedHashtable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.ncl.ccp.generic.NCLApiReader;

import javax.xml.XMLConstants;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@SpringBootTest
@CucumberContextConfiguration
public class NCLHooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(NCLHooks.class);

    @Value("${test.web.screenshot}")
    String takeScreenshot;
    @Autowired(required=true)
    NCLWebActions nclWebActions;

    @Autowired
    NCLApiReader NCLApiReader;

    public static OrderedHashtable runtimeData;
    public static String CURR_PAGE = "";


    @BeforeAll
    public static void initialize() {

        runtimeData = new OrderedHashtable();
            /*System.setProperty("extent.reporter.spark.start", "true");
            System.setProperty("extent.reporter.spark.out", "automation-report.html");
            System.setProperty("extent.reporter.avent.config", "spark-config.xml");
*/
       /*     // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isDriverLoaded()) {
                LOGGER.info("Shutdown signal detected: Closing opened drivers");
                closeDriver();
                LOGGER.info("Opened drivers closed");
            }
        }));*/
    }

    public static void setRuntimeData(String key, String value) {
        if(runtimeData.containsKey(key)) {
            runtimeData.remove(key);
        }
        runtimeData.put(key, value);
    }

    public static String getRuntimeData( String key) {
        return runtimeData.get(key).toString();
    }

    private boolean isDriverLoaded() {
        return nclWebActions.webDriver != null;
    }

    @AfterStep
    public void tearDown(final Scenario scenario) {
        boolean flag = false;
        switch (takeScreenshot) {
            case "A" -> flag = true;
            case "F" -> {
                if (scenario.isFailed()) flag = true;
            }
            case "P" -> {
                if (!scenario.isFailed()) flag = true;
            }
        }
        if(flag){
            final byte[] screenshot = ((TakesScreenshot) nclWebActions.webDriver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    private String formatXMLOutput(String xmlValue) {
        xmlValue =  xmlValue.replaceAll("<", "\n\t&lt;").replaceAll(">", "&gt;");
        return xmlValue;
    }

    int counter = 0;
    private String htmlReport(String message) {
        String htmlFormart = "";

        htmlFormart = "<script>function myFunction() {" +
            "var x = document.getElementById(\"myDIV" + counter + "\");" +
            "if (x.style.display === \"none\") {" +
                "x.style.display = \"block\";" +
            "} else {" +
               " x.style.display = \"none\";" +
            "}" +
        "}</script><div id=\"myDIV" + counter + "\">" + message + "</div>";
        counter++;
        return htmlFormart;
    }

    private String prettyFormat(String input, int indent) throws TransformerException {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
    }

    private String prettyFormat(String input) throws TransformerException {
        return prettyFormat(input, 2);
    }

    public void closeDriver() {
        if (isDriverLoaded()) {
            nclWebActions.webDriver.quit();
            nclWebActions.webDriver = null;
        }
    }

    @After(order = 1)
    public void closeDriver(final Scenario scenario) {
        closeDriver();
        runtimeData = new OrderedHashtable();
    }

    @After(order = Integer.MAX_VALUE)
    public void logAfterScenario(final Scenario scenario) {
        LOGGER.debug(StringUtils.rightPad("Finished scenario:", 20) + "[{}] - [{}] [{}]",
                getFeatureName(scenario),
                scenario.getName(),
                scenario.getStatus());
    }

    private String getFeatureName(Scenario scenario) {
        String featureName = scenario.getId();

        featureName = StringUtils.substringBeforeLast(featureName, ".feature");
        featureName = StringUtils.substringAfterLast(featureName, "/");

        return featureName;
    }
}
