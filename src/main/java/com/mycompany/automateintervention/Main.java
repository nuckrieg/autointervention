/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.automateintervention;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FabioAbreu
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Main {

    public static List<WebElement> innerList = new ArrayList<WebElement>();
    public static List<WebElement> xInnerList = new ArrayList<WebElement>();

    static boolean inLogin = false;
    static boolean inDownloadPage = false;
    static boolean inCitsAccordion = false;
    static boolean inDownloadItem = false;
    public static boolean isFileDownloaded = false;
    public static String genericErrorPage = "https://extranet.infarmed.pt/CITS-fo/error/generic.jsf";
    public static String firstPage = "https://extranet.infarmed.pt/CITS-fo/";
    public static final String filePath = "C:\\Auto_updates_server\\infarmeds";
    Thread errorcheck;

    public static void main(String[] args) throws InterruptedException, AWTException {

        Main app = new Main();

        app.openBrowser();

    }

    public void openBrowser() throws InterruptedException, AWTException {

        String driverPath = "C:\\tactis\\chromedriver.exe";
        Map<String, Object> prefs;
        prefs = new HashMap<>();
        prefs.put("download.default_directory", filePath);
        System.setProperty("webdriver.chrome.driver", driverPath);
        // FirefoxOptions options = new FirefoxOptions();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        //options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        //options.add
        WebDriver driver = new ChromeDriver(options);
        //ErrorCheck ecMain = new ErrorCheck();
        //       Thread ecThread = new Thread(new ErrorCheck());
        //        ErrorCheck.driver = driver;
        //Thread.sleep(500);
        //        ecThread.start();
        //HtmlUnitDriver driver = new HtmlUnitDriver();
        // driver.setJavascriptEnabled(true);
        // driver.manage().timeouts().pageLoadTimeout(Duration.ZERO);
        //System.out.println(driver.getCapabilities());
        errorcheck = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        System.out.println("Errorcheck is alive and running...");
//                        if (driver.getCurrentUrl().equals(genericErrorPage)) {
//                            System.out.println("@ BAD PAGE: " + genericErrorPage);
//                            //throw new RuntimeException("BROWSER RETORNOU ERRO GENÉRICO! VOU SAIR DO PROGRAMA!");
//
//                            /*kill(null, driver, null);
//                            Runtime.getRuntime().halt(0);;*/
//                        } else {
//                            System.out.println("@ GOOD PAGE: " + driver.getCurrentUrl());
//                        }
                    } catch (Exception ube) {
                        if (ube.getMessage().equals("sleep interrupted")) {
                            System.out.println("Errorcheck was interrupted!");
                            System.out.println("Download was completed!");
                        } else {
                            System.out.println("Some other exception was thrown...");
                            System.out.println(ube.getMessage());
                        }
                        //System.out.println("@ EXCEPTION! Will throw the towel in 60 seconds!!!");
                        //ube.printStackTrace();
//                        System.out.println(ube.getMessage());
//                        for (StackTraceElement s : ube.getStackTrace()) {
//                            System.out.println("\t" + s.toString());
//                        }
//                        for (int i = 0; i < 60; i++) {
//                            try {
//                                Thread.sleep(1000);
//
//                            } catch (Exception innards) {
//                                for (StackTraceElement s : innards.getStackTrace()) {
//                                    System.out.println("\t" + s.toString());
//                                }
//                            }
//                        }
//                        System.out.println("EXITING DUE TO EXCEPTION!!!");
//                        for (StackTraceElement s : ube.getStackTrace()) {
//                            System.out.println("\t" + s.toString());
//                        }
//                        Runtime.getRuntime().halt(0);

                        // Logger.getLogger(AutomateWebsite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
        errorcheck.start();
        String mainPage = "https://tactiscloud.no-ip.net:444/TactisCRM/home";
        // String mainPage = "https://www.joindota.com/en/start";
        //  test(driver,mainPage);
        //Runtime.getRuntime().halt(0);;
        login(driver, mainPage, filePath);
        //   isFileDownloaded(filePath);
        errorcheck.interrupt();
        driver.close();
        driver.quit();
        // System.exit(0);
    }

    public void test(WebDriver driver, String page) {
        Actions actions = new Actions(driver);

        System.out.println(page);
        driver.get(page);
        WebElement newsArchive = driver.findElement(By.xpath("//*[@id=\"container\"]/section[1]/div[2]/a"));
        Point location = newsArchive.getLocation();
        System.out.println(newsArchive.getLocation());
        System.out.println("# MOVING TO ELEMENT");
        actions.moveToElement(newsArchive);
        System.out.println("# CLICKING ELEMENT");
        actions.moveByOffset(location.x, location.y);
        actions.click();
        System.out.println("# WAITING FOR 2 MAX MINUTES");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(driver.getCurrentUrl());
        System.out.println(driver.getCurrentUrl());
        WebElement metaAnalysis = driver.findElement(By.xpath("//*[@id=\"list_news\"]/ul/li[11]/div/a/div[2]/h3"));
        metaAnalysis.submit();
        driver.get(driver.getCurrentUrl());
        WebElement text = driver.findElement(By.xpath("//*[@id=\"container\"]/main/section[1]/div/text()[3]"));
        System.out.println(text.getText());
    }

    public boolean isFileDownloaded(String filePath) throws InterruptedException {
        System.out.println("@IS-FILE-DOWNLOADED");
        //System.out.println(driver.);
        String fileName = "CITS";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthInt = (calendar.get(Calendar.MONTH)) + 1;
        int dayInt = calendar.get(Calendar.DAY_OF_MONTH);
        String month = String.valueOf(monthInt);
        String day = String.valueOf(dayInt);
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }

        fileName = fileName + String.valueOf(year) + String.valueOf(month) + day + "A.zip";

        //System.out.println(fileName);
        File finalPath = new File(filePath + "\\" + fileName);
        Thread checkFileExists = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //System.out.println("Countdown: "+i+"sec" );
                        Thread.sleep(1000);
                        //     System.out.println(finalPath.toString());
                        boolean dummy = true;
                        if (dummy/*inLogin & inDownloadPage & inCitsAccordion & inDownloadItem*/) {
                            //System.out.println(finalPath.getAbsolutePath());
                            if (!finalPath.exists()) {
                                // System.out.println("File is downloading...");

                            } else {
                                System.out.println("File downloaded!!!");
                                isFileDownloaded = true;
                                if (isFileDownloaded) {
                                    break;
                                }
                                break;

                            }

                        }
                        if (dummy & inLogin & inDownloadPage & inCitsAccordion & inDownloadItem) {
                            //System.out.println(finalPath.getAbsolutePath());
                            if (!finalPath.exists()) {
                                System.out.println("File is downloading...");

                            } else {
                                System.out.println("File downloaded!!!");
                                isFileDownloaded = true;
                                if (isFileDownloaded) {
                                    break;
                                }
                                break;

                            }

                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        checkFileExists.start();
        Thread.sleep(5000);

        return isFileDownloaded;

    }

    public void login(WebDriver driver, String page, String filePath) throws InterruptedException, AWTException {

        System.out.println("GETTING LOGIN PAGE");
//        System.out.println("-----");
//        System.out.println(driver.getCurrentUrl());
//        System.out.println("-----");
        try {
            driver.get(page);
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("C:\\tactis\\new.png"));

        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);;
        }

//        System.out.println("-----");
//        System.out.println(driver.getCurrentUrl());
        System.out.println("@ LOGIN PAGE");
        WebElement username = null;
        WebElement password = null;
        WebElement login = null;
        takeScreenshot(driver, "0");
        try {
//        driver.manage().window().maximize();

            username = driver.findElement(By.xpath("//*[@id=\"username\"]"));
            password = driver.findElement(By.xpath("//*[@id=\"corpo\"]/form/div[2]/input"));
            login = driver.findElement(By.xpath("//*[@id=\"corpo\"]/form/div[3]/button"));
        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);;

        }
        username.sendKeys("fabreu");
        // Thread.sleep(1000);
        password.sendKeys("fabreu");
        //  Thread.sleep(1000);
        System.out.println("LOGGING IN");
        login.click();
        // Thread.sleep(1000);
        inLogin = true;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
        takeScreenshot(driver, "1");
        //  System.exit(0);

        downloadPage(driver, filePath);

    }

    public void takeScreenshot(WebDriver driver, String name) {
        try {
            // driver.get(driver.getCurrentUrl());
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("C:\\tactis\\" + name + ".png"));

        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);;
        }
    }

    public void downloadPage(WebDriver driver, String filePath) throws InterruptedException, AWTException {
        takeScreenshot(driver, "2");
        System.out.println("@ MAIN PAGE");
//        System.out.println("-----");
//        System.out.println(driver.getCurrentUrl());

//  driver.get("https://extranet.infarmed.pt/CITS-fo/pages/parceiro/download.jsf");
        WebElement downloadButton = null;

        try {
            downloadButton = driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul[2]/li[1]/div/input"));
        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);;
        }
//WebElement downloadButton = driver.findElement(By.xpath("//*[@id=\"j_idt33:j_idt34\"]/ul/li[2]/a"));
        System.out.println("CLICKING DOWNLOAD BUTTON!");

        downloadButton.sendKeys("guimadent");
        takeScreenshot(driver, "3");
        WebElement search = driver.findElement(
                By.xpath("//*[@id=\"myNavbar\"]/ul[2]/li[1]/div/button/span[1]"));

        search.click();
        takeScreenshot(driver, "4");
        WebElement clickGuima1 = driver.findElement(
                By.xpath("/html/body/main/app-root/div/app-home-menu/app-client-view-search/p-treetable/div/div[1]/table/tbody/tr/td[3]/p-treetabletoggler"));
        clickGuima1.click();
        takeScreenshot(driver, "5");
        WebElement clickGuima2 = driver.findElement(
                By.xpath("/html/body/main/app-root/div/app-home-menu/app-client-view-search/p-treetable/div/div[1]/table/tbody/tr[2]/td[3]/p-treetabletoggler"));
        clickGuima2.click();
        takeScreenshot(driver, "6");

        WebElement getId = driver.findElement(
                By.xpath("/html/body/main/app-root/div/app-home-menu/app-client-view-search/p-treetable/div/div[1]/table/tbody/tr[2]/td[2]"));
        String clinicId = getId.getText();
        System.out.println("CLINIC IS: " + clinicId + "");
        driver.get("https://tactiscloud.no-ip.net:444/TactisCRM/home/clinic/" + clinicId);
        takeScreenshot(driver, "7");
        WebElement plusInt = driver.findElement(
                By.xpath("//*[@id=\"myNavbar2\"]/ul/li[11]/a"));
        plusInt.click();
        takeScreenshot(driver, "8");
//        Robot bot = new Robot();
//        bot.keyPress(KeyEvent.VK_TAB);
//        bot.keyRelease(KeyEvent.VK_TAB);
//        bot.keyPress(KeyEvent.VK_TAB);
//        bot.keyRelease(KeyEvent.VK_TAB);
//        bot.keyPress(KeyEvent.VK_TAB);
//        bot.keyRelease(KeyEvent.VK_TAB);
//        bot.keyPress(KeyEvent.VK_TAB);
//        bot.keyRelease(KeyEvent.VK_TAB);
//        bot.keyPress(KeyEvent.VK_A);
//        bot.keyRelease(KeyEvent.VK_A);
        WebElement div1div2 = driver.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[1]/div[2]"));
        WebElement textAreaX = div1div2.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[1]/div[2]/textarea"));
        textAreaX.sendKeys("Teste");

        takeScreenshot(driver, "9");

        WebElement div1div1 = driver.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[1]/div[1]"));
        WebElement div2div1 = driver.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[2]/div[1]"));

        WebElement clickTipo = div1div1.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[1]/div[1]/p-dropdown/div/div[3]/span"));
        clickTipo.click();
        takeScreenshot(driver, "10");

        WebElement escolherTipo = clickTipo.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[1]/div[1]/p-dropdown/div/div[4]/div/ul/p-dropdownitem[2]/li/span"));
        escolherTipo.click();
        takeScreenshot(driver, "11");

        WebElement clickProdutoAtividade = div2div1.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[2]/div[1]/p-dropdown/div/div[3]/span"));
        clickProdutoAtividade.click();
        takeScreenshot(driver, "12");

        WebElement escolherProdutoAtividade = clickProdutoAtividade.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[2]/div[1]/p-dropdown/div/div[4]/div[2]/ul/p-dropdownitem[8]/li/div/div"));
        escolherProdutoAtividade.click();
        takeScreenshot(driver, "13");
        
        WebElement div2div3 = driver.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[2]/div[3]"));
        WebElement duration = div2div3.findElement(
                By.xpath("//*[@id=\"ui-accordiontab-0-content\"]/div/app-intervention-new-view/div/div/div/div/div[2]/div[3]/input"));
        duration.sendKeys("5");
                
        
        Thread.sleep(1000);
        takeScreenshot(driver, "14-FINAL");

       

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);

        System.out.println("um");
        System.out.println("um1");
        System.out.println("um2");
        System.out.println("um3");

        // System.exit(0);
 Thread.sleep(100000);
        //   inDownloadPage = true;
        //  driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
        //  clickCitsAccordion(driver, filePath);
    }

    public void clickCitsAccordion(WebDriver driver, String filePath) throws InterruptedException {
        // Thread.sleep(1000);
        System.out.println("@ DOWNLOAD PAGE");
//        System.out.println("-----");
//        System.out.println(driver.getCurrentUrl());
        //   System.out.println("-----");
        //driver.get(driver.getCurrentUrl());
        WebElement citsAccordion = null;
        try {
            citsAccordion = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52\"]/div[1]"));
        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);;
        }
        System.out.println("CLICKING CITS ACCORDION");
        citsAccordion.click();
        //Thread.sleep(60000);
        inCitsAccordion = true;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
        downloadItem(driver, filePath);

    }

    public void downloadItem(WebDriver driver, String filePath) throws InterruptedException {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("xD");
//                    if (driver.getCurrentUrl().equals(genericErrorPage)) {
//                        throw new RuntimeException("BROWSER RETORNOU ERRO GENÉRICO! VOU SAIR DO PROGRAMA!");
//                    }
//                }
//
//            }
//        }).start();
        System.out.println("ACCORDION IS LOADING");
//        System.out.println("-----");
//        System.out.println(driver.getCurrentUrl());
        //    System.out.println("-----");
        // driver.get(driver.getCurrentUrl());
        WebElement lastItem = null;
        // try {
        //  Thread t1 = new Thread(checkForErrorPage);
        //  t1.start();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
        System.out.println("TRYING TO FIND LAST DOWNLOAD FILE!!!");
        try {
            //lastItem = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59\"]/i"));
            // System.out.println("");
            //  Thread.sleep(10000);
            //  WebDriverWait wait = new WebDriverWait(driver, 10);
            lastItem = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59\"]/i"));

            //lastItem = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52:0:tablePedidosAcessoPort1_data\"]/tr[1]/td[2]"));
        } catch (Exception ube) {
            kill(null, driver, ube);
            Runtime.getRuntime().halt(0);
            //throw new UnreachableBrowserException("BROWSER RETORNOU ERRO GENÉRICO! VER PAGINA:\b"+driver.getCurrentUrl());

            // Runtime.getRuntime().halt(0);;
        }

//        } catch (NoSuchElementException | UnreachableBrowserException xD) {
//            while (true) {
//                System.out.println(driver.getCurrentUrl());
//                //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//                //System.out.println(xD.getMessage());
//                //lastItem = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59\"]/i"));
//                
//            }
//            //System.exit(-1);
//        }
        try {

            if (lastItem != null) {
                System.out.println("DOWNLOADING FILE!!!");
                //   System.out.println("clicking one");
                //     lastItem.click();
                JavascriptExecutor ex = (JavascriptExecutor) driver;
                ex.executeScript("arguments[0].click()", lastItem);
//                System.out.println("setting two");
//                WebElement actualLastItem = driver.findElement(By.xpath("//*[@id=\"form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59\"]/i"));
//                System.out.println("clicking two");
//                actualLastItem.click();
            } else {
                System.out.println("DOWNLOAD WAS NOT SUCCESSFUL!!!");
                System.exit(1);
            }
        } catch (Exception xD) {
            xD.printStackTrace();
            System.exit(1);
        }

        //     driver.g
        inDownloadItem = true;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);

        isFileDownloaded(filePath);

    }

    public void kill(String page, WebDriver driver, Exception e) {
        System.out.println("SOMETHING WENT WRONG! KILLING!");
        System.out.println("STAND BY FOR KILL CAUSE...");
        if (errorcheck.isAlive()) {
            System.out.println("INFO: ERRORCHECK WAS ALIVE, KILLING");
            //errorcheck.interrupt();
            errorcheck.stop();
        }
        if (driver != null) {
            System.out.println("INFO: DRIVER WAS NOT NULL");
            try {
                System.out.println("INFO: PAGE URL IS GETTABLE");
                System.out.println("INFO: PageUnavailableException: " + driver.getCurrentUrl());
                //  e.printStackTrace();
                for (StackTraceElement ste : e.getStackTrace()) {
                    System.out.println("\t" + ste);
                }

            } catch (Exception ube) {
                System.out.println("INFO: PAGE URL WAS NOT ABLE TO BE CAPTURED");
                System.out.println("INFO: PageUnavailableException: " + "Unknown page, unable to load current URL.");
                //  ube.printStackTrace();
                for (StackTraceElement ste : ube.getStackTrace()) {
                    System.out.println("\t" + ste);
                }

            }
        } else {
            System.out.println("INFO: DRIVER IS NULL, THROWING GENERIC ERROR");
            System.out.println("INFO: PageUnavailableException: " + page);
            // e.printStackTrace();
            for (StackTraceElement ste : e.getStackTrace()) {
                System.out.println("\t" + ste);
            }

        }
        Runtime.getRuntime().halt(0);
        // System.exit(1);

        //if (driver != null) {
        //    driver.close();
        //    driver.quit();
        //}
        //System.exit(1);
    }
}

//    public static String downloadPage(WebDriver driver, String page) throws InterruptedException {
//        System.out.println("@download");
//
//        driver.get(page);
//
//        //driver.switchTo().activeElement();
//        WebElement citsButton = driver.findElement(By.id("form1:j_idt52"));
//
//        citsButton.click();
//
//        WebElement dateButton = driver.findElement(By.id("form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59"));
//
//        dateButton.click();
//        System.out.println(driver.getCurrentUrl());
//
//        return "BRUH???";
//
//        // String fileList = driver.getCurrentUrl();
//        // return fileList;
//    }
//
////        String downloadPage = downloadPage(driver, testPage);
//        //      System.out.println(downloadPage);
//        //driver.get(testPage);
//        Document doc = Jsoup.connect(testPage).get();
//        System.out.println(driver.getCurrentUrl());
//
//        WebElement mainForm = driver.findElement(By.id("form1"));
//      //  driver.
//        List<WebElement> accordions = driver.findElements(By.className("ui-accordion-header"));
//        for (WebElement accord : accordions) {
//            
//        }
//        
//        mainForm.click();
//        driver.switchTo().frame("form1:j_idt52:0:j_idt53");
//        //WebElement citsForm = mainForm.findElement(By.id("form1:j_idt52:0:j_idt53"));
//        WebElement downloadButton = mainForm.findElement(By.xpath("/html/body/div[3]/div/div[4]/form/div/div[2]/fieldset/div/div/div[1]/table/tbody/tr[1]/td[2]/a/i"));
//        //System.out.println(citsForm.getAttribute("id"));
//        // WebElement inCitsForm = citsForm.findElement(By.id("form1:j_idt52:0:j_idt54"));
//        //WebElement insCitsForm = inCitsForm.findElement(By.id("form1:j_idt52:0:tablePedidosAcessoPort1"));
//        //WebElement todayDownload = insCitsForm.findElement(By.id("form1:j_idt52:0:tablePedidosAcessoPort1:0:j_idt57:0:j_idt59"));
////
////        for (WebElement ele : testForm) {
//////            System.out.println("#####");
//////            System.out.println("TAGNAME: " + ele.getTagName());
//////            System.out.println("ID: " + ele.getAttribute("id"));
//////            System.out.println("CLASS: " + ele.getAttribute("class"));
////            String eleClass = ele.getAttribute("class");
////            String eleId = ele.getAttribute("id");
////            System.out.println("#ENTER");
////            if (eleClass != null) {
////                if (eleClass.contains("ui-accordion-header ui-helper-reset ui-state-default ui-corner-all")) {
////                    System.out.println("##ENTER 2");
////                    System.out.println(ele.getTagName());
////                    System.out.println(ele.getText());
////                    System.out.println(ele.getValue());
////                    System.out.println(ele.getAttribute("id"));
////                    System.out.println(ele.getAttribute("class"));
////                    Thread.sleep(3000);
////                    //ele.setSelected();
////                    ele.click();
////                }
////
////            }
////
////            if (eleId != null) {
////
////                if (eleId.contains("form1:j_idt52:0")) {
////                    System.out.println("###ENTER 3");
////                    //  ele.setSelected();
////                    driver.switchTo().activeElement();
////                    innerList = ele.findElements(By.xpath("//*[@id]"));
////
////                    for (WebElement innerElement : innerList) {
////                        
////                        System.out.println("####ENTER 4");
////                        driver.switchTo().activeElement();
////                        String inEleClass = innerElement.getAttribute("class");
////                        String inEleId = innerElement.getAttribute("id");
////                        //System.out.println("O QUE E ISTO?????" + inEleId);
////                        if (inEleId.contains("form1:j_idt52:0:j_idt53")) {
////                            System.out.println(inEleId);
////                            System.out.println("#####ENTER 5");
////                            innerElement.click();
////                            Thread.sleep(5000);
////                            xInnerList = innerElement.findElements(By.xpath("//*[@id]"));
////                            for (WebElement xInnerElement : xInnerList) {
////                                System.out.println("######ENTER 6");
////                                System.out.println("ID: "+xInnerElement.getAttribute("id"));
////                                System.out.println("CL: "+xInnerElement.getAttribute("class"));
////                                System.out.println("TY: "+xInnerElement.getAttribute("type"));
////                                System.out.println("??: "+xInnerElement.getTagName());
////                                System.out.println("??: "+xInnerElement.getText());
////                                System.out.println("??: "+xInnerElement.getValue());
////                            }
////                        }
////                        
//////                        System.out.println("#####");
//////                        System.out.println("#####");
////                        
////                        System.out.println("TAGNAME: " + innerElement.getTagName());
////                        System.out.println("ID: " + innerElement.getAttribute("id"));
////                        System.out.println("CLASS: " + innerElement.getAttribute("class"));
////                        
//////                        System.out.println("#####");
//////                        System.out.println("#####");
////                    }
////                    break;
////                }
//                
//          //  }
//
//            // System.out.println("LEGEND: " + ele.getAttribute("legend"));
//            // System.out.println("TYPE: " + ele.getAttribute("type"));
//            // System.out.println("HREF: " + ele.getAttribute("href"));
//            
//
//            //if (ele.getAttribute("id").contains("form1:j_idt52:0:j_idt53")) {
//            // System.out.println("VOU SAIR FORA DESTE FOR!!!!!################################");
//            //  List<WebElement> innerForm = ele.findElements(By.xpath("//*[@id]"));
//            // for (WebElement innerEle : innerForm) {
//            //     System.out.println("#####");
//            //System.out.println("TAGNAME: " + innerEle.getTagName());
//            //System.out.println("ID: " + innerEle.getAttribute("id"));
//            //System.out.println("CLASS: " + innerEle.getAttribute("class"));
//            //System.out.println("LEGEND: " + innerEle.getAttribute("legend"));
//            //System.out.println("TYPE: " + innerEle.getAttribute("type"));
//            //System.out.println("HREF: " + innerEle.getAttribute("href"));
//            //System.out.println("#####");
//            // }
//            // break;
//            //}
//    //    }
//
//        //  System.out.println("ROW GET TEXT");
//        //   System.out.println(row.getValue());
//        //   List<WebElement> colID = mainForm.findElements(By.tagName("td"));
//        //System.out.println(colID.toString());
//        //todayDownload.getTagName();
//        //todayDownload.getText();
////        //WebElement mediaX = driver.findElement(By.partialLinkText("[src]"));
////        //WebElement importsX = driver.findElement(By.linkText("link[href]"));
////        Elements links = doc.select("a[href]");
////        Elements media = doc.select("[src]");
////        Elements imports = doc.select("link[href]");
////       /// print("\nMedia: (%d)", media.size());
////        for (Element src : media) {
////            if (src.normalName().equals("img")) {
////              //  print(" * %s: <%s> %sx%s (%s)",
////                      //  src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
////                       // trim(src.attr("alt"), 20));
////            } else {
////               // print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
////            }
////        }
////
////       // print("\nImports: (%d)", imports.size());
////        for (Element link : imports) {
////            //print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
////        }
////
////        //print("\nLinks: (%d)", links.size());
////        for (Element link : links) {
////           // print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
////        }
////
////        // System.out.println(driver.getPageSource());
//System.out.println("IM OUT BOYS");        
//Thread.sleep(10000);
//  driver.quit();
//    }
//
//    private static void print(String msg, Object... args) {
//        System.out.println(String.format(msg, args));
//    }
//
//    private static String trim(String s, int width) {
//        if (s.length() > width) {
//            return s.substring(0, width - 1) + ".";
//        } else {
//            return s;
//        }
//    }

