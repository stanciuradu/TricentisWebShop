import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class SeleniumHelper {
    // scopul acestei clase este de a fi o clasa parinte care contine cod reutilizabil ce va fi mostenit de alte clase
    WebDriver driver; // instantiere driver
    String url = "https://demowebshop.tricentis.com/";
    // metoda asta BeforeMethod ruleaza de fiecare data inaintea metodelor de test
    // este dedicata set-upului pentru deschiderea de browser si al URL-ului pentru aplicatia web pe care se vor implementa cazurile de testare
    @BeforeMethod
    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Radu\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver(); // aceasta comanda va deschide browserul
        driver.manage().window().maximize(); // maximizeze fereatra de browuser

    }
/*    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(10000);
        driver.close();
    }*/
}
