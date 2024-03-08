package unicap.br.unimpact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnimpactApplicationCadastroTests {


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
    @DisplayName("Abringo Pagina Unimpact")
    public void testAbrindoPagina(){
        this.webDriver.get("http://localhost:3000/");
        Assertions.assertEquals("http://localhost:3000/", this.webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Cadastro de Pessoa Fisica")
    public void testInserindoPessoaFisica() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[1]")).click();
        this.webDriver.findElement(By.xpath("//*[@id=\"user_login_method_page\"]/div/div[3]/a[1]")).click();

        //https://geradorbrasileiro.com/rg
        //informações sobre a instituição
        this.webDriver.findElement(By.id("fullName")).sendKeys("Danilo Iago da Rosa");
        this.webDriver.findElement(By.id("cpf")).sendKeys("42508941425");
        this.webDriver.findElement(By.id("rg")).sendKeys("15144458");
        this.webDriver.findElement(By.id("housePhone")).sendKeys("8136052345");
        this.webDriver.findElement(By.id("phone")).sendKeys("10045124");

        //Endereço
        this.webDriver.findElement(By.id("cep")).sendKeys("50060320");
        this.webDriver.findElement(By.id("street")).sendKeys("Professora Priscila Ferreira");
        this.webDriver.findElement(By.id("state")).sendKeys("PE");
        this.webDriver.findElement(By.id("disctrict")).sendKeys("Coelhos");
        this.webDriver.findElement(By.id("houseNumber")).sendKeys("122");
        this.webDriver.findElement(By.id("addressComplement")).sendKeys("Apt");

        //Informações de Login
        this.webDriver.findElement(By.id("email")).sendKeys("danilo_darosa@hotmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("danilo123");
        this.webDriver.findElement(By.id("repeatedPassword")).sendKeys("danilo123");

        //marcando checkbox
        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;

        WebElement element  = this.webDriver.findElement(By.id("flexCheckDefault"));

        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(3000);
        this.webDriver.findElement(By.id("flexCheckDefault")).click();
        this.webDriver.findElement(By.xpath("//*[@id=\"register-physical-person-form\"]/button")).click();
        //Desenvolver forma de validar Registro bem sucedido
        //tentar usando algum Alert

    }

    @Test
    @DisplayName("Cadastro de Pessoa Juridica")
    public void testInserindoPessoaJuridica() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[1]")).click();
        this.webDriver.findElement(By.xpath("//*[@id=\"user_login_method_page\"]/div/div[3]/a[2]")).click();

        Select dropdown = new Select(webDriver.findElement(By.xpath("//*[@id=\"civilStateSelect\"]\n")));

        //Escolhendo Valor do DropDown
        dropdown.selectByValue("governo");
        Thread.sleep(3000);


        this.webDriver.findElement(By.id("corporateName")).sendKeys("Gabriel e Allana Entulhos Ltda");
        this.webDriver.findElement(By.id("fantasyName")).sendKeys("Entulhos");
        this.webDriver.findElement(By.id("cnpj")).sendKeys("51893191000107");

        this.webDriver.findElement(By.id("phone1")).sendKeys("8138308797");
        this.webDriver.findElement(By.id("phone2")).sendKeys("81988513987");

        //Endereço
        this.webDriver.findElement(By.id("cep")).sendKeys("52041240");
        this.webDriver.findElement(By.id("street")).sendKeys("Euclides Fonseca");
        this.webDriver.findElement(By.id("state")).sendKeys("PE");
        this.webDriver.findElement(By.id("disctrict")).sendKeys("Encruzilhada");
        this.webDriver.findElement(By.id("houseNumber")).sendKeys("327");
        this.webDriver.findElement(By.id("addressComplement")).sendKeys("Apt");

        //Informações de Login
        this.webDriver.findElement(By.id("email")).sendKeys("producao@gabrielentulhos.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");
        this.webDriver.findElement(By.id("repeatedPassword")).sendKeys("Aa123456#");

        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;

        WebElement element  = this.webDriver.findElement(By.id("inBoxTermsAndConditions"));

        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(5000);
        this.webDriver.findElement(By.id("inBoxTermsAndConditions")).click();
        this.webDriver.findElement(By.xpath("//*[@id=\"register-physical-person-form\"]/button")).click();
        //Pagina para login  depois de criar usuário http://localhost:3000/voce/grupos-e-representantes







    }




}
