package unicap.br.unimpact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnimpactApplicationTelaPrincipalTests {
    private WebDriver webDriver ;

    @BeforeAll
    public  void setupAll(){
        System.setProperty("webdriver.chrome.driver","src\\driver\\chromedriver.exe");
    }

    @BeforeEach
    public void setupEach(){
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
    }


    @AfterEach
    public void closeBrowser() {
        this.webDriver.close();
    }

    @Test
    @DisplayName("Abringo Página Unimpact")
    public void testAbrindoPagina(){
        this.webDriver.get("http://localhost:3000/");
        Assertions.assertEquals("http://localhost:3000/", this.webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Abrindo Sobre o Unimpact")
    public void testAbrindoSobreunimpact() throws InterruptedException {
        String actual = "http://localhost:3000/sobre";

        this.webDriver.get("http://localhost:3000/");
        this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[2]")).click();
        Thread.sleep(3000);
        Assertions.assertEquals(actual, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Abrindo Editais")
    public void testAbrindoEditais() throws InterruptedException {
        String actual = "http://localhost:3000/docs/editais";

        this.webDriver.get("http://localhost:3000/");
        this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[3]")).click();
        Thread.sleep(3000);
        Assertions.assertEquals(actual, webDriver.getCurrentUrl());

    }

    @Test
    @DisplayName("Abrindo Noticias")
    public void testAbrindoNoticias() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");
        String actual = "http://localhost:3000/noticias";

        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;
        WebElement element  = this.webDriver.findElement(By.xpath("//*[@id=\"__news\"]/div[1]/a"));
        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(3000);

        this.webDriver.findElement(By.xpath("//*[@id=\"section-3\"]/div/div[2]/div[1]/a")).click();

        Assertions.assertEquals(actual, this.webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Abrindo Cards")
    public void testAbrindoCards() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");
        String actual = "http://localhost:3000/news";

        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;
        WebElement element  = this.webDriver.findElement(By.xpath("//*[@id=\"__news\"]/div[2]/a[3]/div/img"));
        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(3000);

        this.webDriver.findElement(By.xpath("//*[@id=\"__news\"]/div[2]/a[3]/div/img")).click();

        Assertions.assertEquals(actual, this.webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Abrindo Login")
    public void testAbrindoLogin() throws InterruptedException {
        String actual = "http://localhost:3000/entrar";

        this.webDriver.get("http://localhost:3000/");
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        Thread.sleep(3000);
        Assertions.assertEquals(actual, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Abrindo Cadastro")
    public void testAbrindoCadastrp() throws InterruptedException {
        String actual = "http://localhost:3000/cadastro";

        this.webDriver.get("http://localhost:3000/");
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[1]")).click();
        Thread.sleep(3000);
        Assertions.assertEquals(actual, webDriver.getCurrentUrl());
    }


}
