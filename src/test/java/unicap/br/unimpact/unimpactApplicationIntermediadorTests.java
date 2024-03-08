package unicap.br.unimpact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class unimpactApplicationIntermediadorTests {

    private WebDriver webDriver;

    @BeforeAll
    public void setupAll() {
        System.setProperty("webdriver.chrome.driver", "src\\driver\\chromedriver.exe");
    }

    @BeforeEach
    public void setupEach() {
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
    }


    @AfterEach
    public void closeBrowser() {
        this.webDriver.close();
    }

    @Test
    @DisplayName("Checar opção de Setores")
    public void testSIntermediadorSetores() throws InterruptedException {

        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em SETORES
        this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[2]/a")).click();

        Assertions.assertEquals(this.webDriver.getCurrentUrl(),"http://localhost:3000/inter/setores");
    }

    @Test
    @DisplayName("Visualizando uma Solicitação")
    public void testIntermediadorSolicitacoes() throws InterruptedException {

        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em Solicitações
        this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/a[1]/div[1]/span")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(3000);

        //Nome da Solicitação
        String requestName = this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[1]/div[1]/p")).getText();

        Assertions.assertEquals(requestName,this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[1]/div[1]/p")).getText());

        scroll(this.webDriver.findElement(By.xpath("//*[@id=\"inSummary\"]")));
    }

    @Test
    @DisplayName("Aceitar Sollicitação")
    public void testAceitarSolicitacao() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em Solicitações
        this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/a[1]/div[1]/span")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        scroll(this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[1]")));

        //Clicando no botão ACEITAR
        this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[1]")).click();

    }

    @Test
    @DisplayName("Recusando uma Solicitação")
    public void testRecusarSolicitacao() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em Solicitações
        this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/a[1]/div[1]/span")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);


        scroll(this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[3]")));

        //Clicando no botão RECUSAR
        this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[3]")).click();

        //Descreva o motivo  da recusa
        this.webDriver.findElement(By.xpath("//*[@id=\"form-modal\"]/div/form/div[1]/textarea")).sendKeys("Essa Solicitação precisa ser melhorada");

        Thread.sleep(3000);

        //Clicando no botão ENVIAR
        this.webDriver.findElement(By.xpath("//*[@id=\"form-modal\"]/div/form/div[2]/button")).click();

    }

    @Test
    @DisplayName("Solicitando um Ajuste na proposta")
    public void testSolicitarAjustes() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em Solicitações
        this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/a[1]/div[1]/span")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);


        scroll(this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[2]")));

        //Clicando no SOLICITAR AJUSTES
        this.webDriver.findElement(By.xpath("//*[@id=\"proposal-details\"]/div/div[2]/button[2]")).click();

        //Descreva os ajustes necessários para a solicitação
        this.webDriver.findElement(By.xpath("//*[@id=\"form-modal\"]/div/form/div[1]/textarea")).sendKeys("Coloque mais pessoas para o projeto para serem beneficiadas");

        Thread.sleep(3000);

        //Clicando no botão ENVIAR
        this.webDriver.findElement(By.xpath("//*[@id=\"form-modal\"]/div/form/div[2]/button")).click();
    }


    private void scroll(WebElement parm) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;

        WebElement element  =  parm;

        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(3000);

    }


}
