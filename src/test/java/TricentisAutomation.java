import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.awt.windows.ThemeReader;

import java.time.Duration;

public class TricentisAutomation extends SeleniumHelper {
    // end-to-end(de cand ajungem pe apgina pana cand plasam comanda)
    /*
     1. Navigati la https://demowebshop.tricentis.com/
     2. Faceti un asert care verifica faptul ca ati ajuns pe pagina corecta folosind mesajul de avertizare din mijlocul paginii:
     Welcome to our store
     3. Cautati urmatorul produs: blue jeans
     4. Adaugati produsul in cos apoi mergeti la Sopping Cart si modificati cantitatea
     5. Faceti un asert care verifica faptul ca tottalul s-a modificat
     6. Mergeti pe home page apasand pe imaginea Demo Web Shop
     7. Din lista categorii porduse apasati pe Computers si apoi pe desktop
     8. Alegeti Build your own computer
     9. Pentru procesor alegeti 2.2 GHz
     10. Pentru RAM alegeti 8 GB
     11. OS => Win 10
     12. Software, Acrobat Reader + Toatal Commander fara MS Office
     13. Apasati butonul cumpara
     14. Faceti un assert care sa verifice ca totalul de plata
     pentru ultimul produs corespunde cu pretul de baza plus suma tuturor optiunilor
     15. Faceti un assert care sa verifice ca totalul de plata din cosul de produse este cel corect.
     16. Mergeti la Estimate Shipping, alegeti ca tara Romania si apasati pe butonul "Estimate Shipping"
     17. Faceti un assert care sa verifice pentru metoda "Next Day Air" costul este 0.00
     18. Bifati:  I agree with the terms of service and I adhere to them unconditionally (read)
     19. Click pe check-out
     20. Assert pe mesajul: Welcome, Please Sign In!
     21. Apasati pe Checkout as Guest
     22. Compleati toate campurile cu DATE FICTIVE pastrand formatul specific pentru fiecare tip de camp, Apasati Continue
     23. Shipping > Next Day Air, Apasati Continue
     24. Payment > Credit Card, Apasati Continue
     25. Payment info > completati cu DATE FICTIVE, pentru Card Number folositi 4485132044749619, Apasati Continue
     26. Apasati Confirm
     27. Assert pe mesajele: Your order has been successfully processed!
     Order number: 1511475
     28. Salavti numarul comenzii intr-o variabila.
     */
    @Test
    public void shoppingProducts() throws InterruptedException {
        //  1. Navigati la https://demowebshop.tricentis.com/
        driver.get(url);
        Thread.sleep(1000);
        //     2. Faceti un asert care verifica faptul ca ati ajuns pe pagina corecta folosind mesajul de avertizare din mijlocul paginii:
        //     Welcome to our store
        String contentHeadingHome = driver.findElement(By.className("topic-html-content-header")).getText();
        System.out.println("aici am verificat daca suntem pe pagina de Home, comparand un titlu " + contentHeadingHome); //
        Assert.assertEquals(contentHeadingHome, "Welcome to our store");
        // 3. Cautati urmatorul produs: blue jeans
        WebElement inputSearch = driver.findElement(By.id("small-searchterms"));
        Thread.sleep(500);
        String product = "blue jeans";
        inputSearch.sendKeys(product);
        inputSearch.sendKeys(Keys.ENTER);
        Thread.sleep(600);
        //   4. Adaugati produsul in cos apoi mergeti la Sopping Cart
        WebElement addToCartButton = driver.findElement(By.xpath("//input[@ value='Add to cart']"));
        addToCartButton.click();
        // adaugarea produsului in cosul de cumparaturi
        Thread.sleep(2000);
        // mapare ShoppingCart
        WebElement shoppingCart = driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]"));
        shoppingCart.click();
        Thread.sleep(1000);
        // 4. modificare cantitate input Quantity
        WebElement quantityInput = driver.findElement(By.xpath("//tbody//td[5]//input")); // cele mai mare probleme sunt la acest Xpath
        quantityInput.clear();
        quantityInput.sendKeys("10");
        Thread.sleep(1000);
        WebElement updateShoppingCart = driver.findElement(By.xpath("//input[@ value='Update shopping cart']"));
        updateShoppingCart.click();
        String totalQuantity = driver.findElement(By.className("product-subtotal")).getText();
        System.out.println("Am stocat totalul de produse dupa modificarea cantitatii acestora " + totalQuantity);
        Assert.assertEquals(totalQuantity, "10.00");
        Thread.sleep(1000);
        WebElement homePage = driver.findElement(By.xpath("//img[@ alt='Tricentis Demo Web Shop']"));
        homePage.click();
        WebElement computersList = driver.findElement(By.xpath("//a[@ href='/computers']"));
        computersList.click();
        Thread.sleep(500);
        WebElement desktopOption = driver.findElement(By.xpath("//img[@ title='Show products in category Desktops']"));
        desktopOption.click();
        Thread.sleep(500);
        WebElement buildOnYourComputer = driver.findElement(By.xpath("//img[@ title='Show details for Build your own computer']"));
        buildOnYourComputer.click();
        // pretul de baza al produsului
        String initialPrice = driver.findElement(By.className("price-value-16")).getText(); // am mapat pentru pretul initial al produsului
        System.out.println("Pretul intial al produsului este: " + initialPrice);
        int startInitialPrice = initialPrice.length() - 7;
        int endInitialPrice = initialPrice.length() - 3;
        String pretInitialPriceString = initialPrice.substring(startInitialPrice, endInitialPrice);
        System.out.println("Pretul initial este ca string: " + pretInitialPriceString);
        int pretInitialPriceInt = Integer.parseInt(pretInitialPriceString);
        System.out.println("Pretul initial este ca int: " + pretInitialPriceInt);
        Select selectProcessor = new Select(driver.findElement(By.id("product_attribute_16_5_4"))); // intantieim inpoutul de tip select
        selectProcessor.selectByValue("13");
        Select selectRAMI = new Select(driver.findElement(By.id("product_attribute_16_6_5")));
        selectRAMI.selectByValue("17");
        // STOCAM INTR-O VARIABILA VALOREA RAMILOR DE 8GB -> select RAMI
        System.out.println("Pretul la RAMI este: " + selectRAMI.getFirstSelectedOption().getText());
        String pretRAMI = selectRAMI.getFirstSelectedOption().getText();
        int startRAMI = pretRAMI.length() - 6;
        int endRAMI = pretRAMI.length() - 4;
        String pretRAMIString = pretRAMI.substring(startRAMI, endRAMI);
        System.out.println("Pretul Ramilor de baza este: " + pretRAMIString);
        // string -> to int
        int pretRAMIint = Integer.parseInt(pretRAMIString);
        System.out.println("Pretul ramilor in INT este: " + pretRAMIint);
        // SELECT RADIO BUttons -> OS
        WebElement radioButtonOS = driver.findElement(By.id("product_attribute_16_4_7_21")); //OS ->Windows10
        radioButtonOS.click();
        String lableOs = driver.findElement(By.xpath("//div[6]/dl/dd[4]/ul/li[3]/label")).getText();
        int startOS = lableOs.length() - 6;
        int endOS = lableOs.length() - 4;
        String pretOsString = lableOs.substring(startOS, endOS);
        System.out.println("Pretul la OS este: " + pretOsString);
        // string -> to int
        int pretOsInt = Integer.parseInt(pretOsString);
        System.out.println("Pretul la OS ca int este: " + pretOsInt);
        //Checkbox Software -> Acrobat Reader
        WebElement acrobatReader = driver.findElement(By.id("product_attribute_16_8_8_23"));
        acrobatReader.click();
        //afisam continutul de label din cadul acestui input -pentru a extrage valoarea
        String lableAcrobatReader = driver.findElement(By.xpath("//dd[5]/ul/li[2]/label")).getText();
        int startAcrobatReader = lableAcrobatReader.length() - 6;
        int endAcrobatReader = lableAcrobatReader.length() - 4;
        String pretAcrobatReaderString = lableAcrobatReader.substring(startAcrobatReader, endAcrobatReader);
        System.out.println("Pretul la acrobat Reader este ca string: " + pretAcrobatReaderString);
        // string -> to int
        int pretAcrobatReaderInt = Integer.parseInt(pretAcrobatReaderString);
        System.out.println("Pretul la acrobat Reader ca int este: " + pretAcrobatReaderInt);
        // Checkbox Software  -> Total Commander
        WebElement totalCommander = driver.findElement(By.id("product_attribute_16_8_8_24"));
        totalCommander.click();
        String lableTotalCommander = driver.findElement(By.xpath("//dd[5]/ul/li[3]/label")).getText();
        int startTotalCommander = lableTotalCommander.length() - 6;
        int endTotalCommander = lableAcrobatReader.length() - 4;
        String pretTotalCommanderString = lableTotalCommander.substring(startTotalCommander, endTotalCommander);
        System.out.println("Pretul la total commander in string este: " + pretTotalCommanderString);
        // string -> to int
        int pretTotalCommanderInt = Integer.parseInt(pretTotalCommanderString);
        System.out.println("Pretul la total commander in int este: " + pretTotalCommanderInt);
        // deselectare checkbox Microsoft Office
        WebElement microsoftOffice = driver.findElement(By.id("product_attribute_16_8_8_22"));
        microsoftOffice.click();
        // Radio Buttons HDD
        WebElement hddOption = driver.findElement(By.id("product_attribute_16_3_6_19"));
        hddOption.click();
        String labelHdd = driver.findElement(By.xpath("//dl/dd[3]/ul/li[2]/label")).getText();
        int startHdd = labelHdd.length() - 8;
        int endHdd = labelHdd.length() - 4;
        String pretHddString = labelHdd.substring(startHdd, endHdd);
        System.out.println("Pretul la Hdd ca string este: " + pretHddString);
        // string ->to int
        int pretHddInt = Integer.parseInt(pretHddString);
        System.out.println("Pretul la Hdd ca int este: " + pretHddInt);
        // stocam intr-o varibila pretul Optiunilor
        int pretOptiuni = pretRAMIint + pretHddInt + pretOsInt + pretAcrobatReaderInt + pretTotalCommanderInt;
        System.out.println("Pretul optiunilor este: " + pretOptiuni);
        WebElement cumpara = driver.findElement(By.id("add-to-cart-button-16"));
        cumpara.click();
        // ne ducem din nou pe pagina de cart
        Thread.sleep(3000); //imi lipsea un timeout pan la radnarea paginii de cart si de aceea dadea fail
        WebElement shoppingCartSecond = driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]"));
        shoppingCartSecond.click();
        Thread.sleep(5000);
        // extragem totalul de plata pentru ultimul produs
        String productSubTotal = driver.findElement(By.xpath("//*[@ class='cart-item-row'][2]/td[6]/span[2]")).getText();
        System.out.println("Ultimul pret din tabel este: " + productSubTotal);
        int startProductSubTotal = productSubTotal.length() - 7;
        int endProductSubTotal = productSubTotal.length() - 3;
        String productSubTotalString = productSubTotal.substring(startProductSubTotal, endProductSubTotal);
        System.out.println("Pretul ultimului produs ca string este: " + productSubTotalString);
        // string -> to int
        int productSubTotalInt = Integer.parseInt(productSubTotalString);
        System.out.println("Pretul ultimului produs ca int este: " + productSubTotalInt);
        // comparam daca pretul ultimul produs din cart = pret de baza + pretOptiuni
        int pretInitialAlaturiDeOptiuni = pretInitialPriceInt + pretOptiuni;
        System.out.println("Pretul de baza + toate optiunile ultimului produs este: " + pretInitialAlaturiDeOptiuni);
        Assert.assertEquals(productSubTotalInt, pretInitialAlaturiDeOptiuni);
        // assert care sa verifica ca totalul de plata din cosul de produse este cel corect
        // extragem totalul de plata din cosul de cumparaturi
        String totalPrice = driver.findElement(By.className("product-price")).getText();
        System.out.println("Total: " + totalPrice);
        Assert.assertEquals(totalPrice, "1445.00");
        // Mergeti la Estimate Shipping, alegeti ca tara Romania si apasati pe butonul "Estimate Shipping"
        Thread.sleep(500);
        Select estimateShipping = new Select(driver.findElement(By.id("CountryId")));
        estimateShipping.selectByValue("65");
        Thread.sleep(500);
        WebElement estimateShippingButton = driver.findElement(By.xpath("//input[@ value='Estimate shipping']"));
        estimateShippingButton.click();
        // 17. Faceti un assert care sa verifice pentru metoda "Next Day Air" ca, costul este 0.00
        String nextDayAir = driver.findElement(By.xpath("//form//li[2]/strong")).getText();
        System.out.println(nextDayAir);
        int startNextDayAir = nextDayAir.length() - 5;
        int endNextDayAir = nextDayAir.length() - 1;
        String priceNextDayYearString = nextDayAir.substring(startNextDayAir, endNextDayAir);
        System.out.println("Pretul pentru Next Day Year este ca string: " + priceNextDayYearString);
        // string -> to int
        Thread.sleep(1000);
        double priceNextDayYearInt = Double.parseDouble(priceNextDayYearString);
        System.out.println("Pretul pentru Next Day Year este ca numar real: " + priceNextDayYearInt);
        // 18. Bifati:  I agree with the terms of service and I adhere to them unconditionally (read)
        // functionalitate de scroll lent in jos
        JavascriptExecutor jsFirst = (JavascriptExecutor) driver;
        for (int j = 1; j < 2; j++) {//implementare functionaltate de scroll lent in jos
            jsFirst.executeScript("window.scrollBy(0,250)", "");
            System.out.println("Scroll lent in jos");
            Thread.sleep(100);
        }
        WebElement checkboxTermsAndConditions = driver.findElement(By.id("termsofservice"));
        checkboxTermsAndConditions.click();
        Thread.sleep(500);
        // 19. Click pe check-out
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();
        // 20. Assert pe mesajul: Welcome, Please Sign In!
        // extragem continutul din heading
        // verificam daca am ajuns pe pagina corecta prin compararea titlului
        String headingWelcome = driver.findElement(By.xpath("//div/div[1]/h1")).getText();
        System.out.println(headingWelcome);
        Assert.assertEquals(headingWelcome, "Welcome, Please Sign In!");
        // 21. Apasati pe Checkout as Guest
        Thread.sleep(1000);
        WebElement checkoutAsGuest = driver.findElement(By.xpath("//input[@ value='Checkout as Guest']"));
        checkoutAsGuest.click();
        // 22. Compleati toate campurile cu DATE FICTIVE pastrand formatul specific pentru fiecare tip de camp, Apasati Continue
        // primul pas este sa mapam printele elementele din formular
        WebElement firstNameInput = driver.findElement(By.id("BillingNewAddress_FirstName"));
        String firstName = "Mihai";
        firstNameInput.sendKeys(firstName);
        Thread.sleep(500);
        WebElement lastNameInput = driver.findElement(By.id("BillingNewAddress_LastName"));
        String lastName = "Apostol";
        lastNameInput.sendKeys(lastName);
        Thread.sleep(500);
        WebElement emailInput = driver.findElement(By.id("BillingNewAddress_Email"));
        String email = "george198@yahoo.ro";
        emailInput.sendKeys(email);
        Thread.sleep(500);
        Select selectCountry = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
        selectCountry.selectByValue("65");
        Thread.sleep(500);
        WebElement cityInput = driver.findElement(By.id("BillingNewAddress_City"));
        String city = "Oradea";
        cityInput.sendKeys(city);
        Thread.sleep(500);
        WebElement address1 = driver.findElement(By.id("BillingNewAddress_Address1"));
        String myAddress1 = "Strada Vladescu, Nr 5";
        address1.sendKeys(myAddress1);
        Thread.sleep(500);
        WebElement zipCode = driver.findElement(By.id("BillingNewAddress_ZipPostalCode"));
        String myZipCode = "06210";
        zipCode.sendKeys(myZipCode);
        Thread.sleep(500);
        WebElement phoneNumber = driver.findElement(By.id("BillingNewAddress_PhoneNumber"));
        String myNumber = "010050076";
        phoneNumber.sendKeys(myNumber);
        Thread.sleep(500);
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/input"));
        continueButton.click();
        //   23. Shipping > Next Day Air, Apasati Continue
        Thread.sleep(4000);
        WebElement continueButtonSecond = driver.findElement(By.xpath("//*[@id=\"shipping-buttons-container\"]/input"));
        continueButtonSecond.click();
        Thread.sleep(4000);
        WebElement nextDayAirRadioButton = driver.findElement(By.id("shippingoption_1"));
        nextDayAirRadioButton.click();
        Thread.sleep(2000);
        WebElement shippingContinueButton = driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/input"));
        shippingContinueButton.click();
        // 24. Payment > Credit Card, Apasati Continue
        Thread.sleep(3000);
        WebElement crediCardRadioButton = driver.findElement(By.id("paymentmethod_2"));
        crediCardRadioButton.click();
        Thread.sleep(500);
        WebElement paymentContinue = driver.findElement(By.xpath("//*[@id=\"payment-method-buttons-container\"]/input"));
        paymentContinue.click();
        Thread.sleep(1000);
        // 25. Payment info > completati cu DATE FICTIVE, pentru Card Number folositi 4485132044749619, Apasati Continue
        Select selectCreditCard = new Select(driver.findElement(By.id("CreditCardType")));
        selectCreditCard.selectByValue("Discover");
        Thread.sleep(500);
        WebElement cardHolderNameInput = driver.findElement(By.id("CardholderName"));
        String cardHolderName = "George Popa";
        cardHolderNameInput.sendKeys(cardHolderName);
        Thread.sleep(500);
        WebElement cardNumberInput = driver.findElement(By.id("CardNumber"));
        String cardNumberClient = "4485132044749619";
        cardNumberInput.sendKeys(cardNumberClient);
        Thread.sleep(500);
        Select selectExpirationDateMonth = new Select(driver.findElement(By.id("ExpireMonth")));
        selectExpirationDateMonth.selectByValue("5");
        Thread.sleep(500);
        Select selectExpirationDateYear = new Select(driver.findElement(By.id("ExpireYear")));
        selectExpirationDateYear.selectByValue("2031");
        Thread.sleep(500);
        WebElement cardCodePin = driver.findElement(By.id("CardCode"));
        String personalPin = "3060";
        cardCodePin.sendKeys(personalPin);
        Thread.sleep(500);
        WebElement continuePaymentInformation = driver.findElement(By.xpath("//*[@id=\"payment-info-buttons-container\"]/input"));
        continuePaymentInformation.click();
        Thread.sleep(500);
        // 26. Apasati Confirm
        // pentru a apasa butonul Confirm ->trebuie sa se faca scroll lent in jos pana la gasirea butonului respectiv
        JavascriptExecutor jsSecond = (JavascriptExecutor) driver;
        for (int i = 1; i < 3; i++) {//implementare functionaltate de scroll lent in jos
            jsSecond.executeScript("window.scrollBy(0,250)", "");
            System.out.println("Scroll lent in jos");
            Thread.sleep(300);
        }
        WebElement confirmButtonPayment = driver.findElement(By.xpath("//*[@id=\"confirm-order-buttons-container\"]/input"));
        confirmButtonPayment.click();
        Thread.sleep(2000);
        //     27. Assert pe mesajele: Your order has been successfully processed!
        //     Order number: 1511475
        String messageAfterPayment = driver.findElement(By.tagName("strong")).getText();
        System.out.println(messageAfterPayment);
        Thread.sleep(1000);
        Assert.assertEquals(messageAfterPayment, "Your order has been successfully processed!");
        String orderNumber = driver.findElement(By.xpath("//*[@ class='details']/li[1]")).getText(); // se preia continutul lui Oder  number
        System.out.println("Numarul de ordine a comenzii este: " + orderNumber);
        // 28.Stocare numar comanda intr-o variabila.
        String myOderNumber = "1515392";
        System.out.println(myOderNumber);
    }
}