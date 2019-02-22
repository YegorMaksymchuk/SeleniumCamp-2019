package online.qastudy.okd.demo.deployment;


import online.qastudy.okd.demo.utils.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;


public class DemoDeploymetnTest {
    private DemoDeployment demoDeployment;
    private WebDriver driver;

    @Before
    public void deploy() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        demoDeployment = new DemoDeployment("seleniumcamp-2019");
    }

    @After
    public void cleanup() {
        demoDeployment.close();
    }

    @Test
    public void testAppDeployment() {
        demoDeployment.login()
                .createNewProject("seleniumcamp-2019", "Demo for Selenium Camp 2019", "Demo of Fabric8 and  XTF")
                .deployPod()
                .deployService()
                .createRout();

        driver.navigate().to(demoDeployment.getApplicationURL());
        Util.waitUntilAppWillBeReady();
        driver.navigate().refresh();

        WebElement element = driver.findElement(By.className("center"));

        assertThat(element.getText())
                .contains("Hello from POD!!");
    }
}
