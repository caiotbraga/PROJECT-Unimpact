package unicap.br.unimpact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class unimpactApplicatioEmpresaTests {

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
    @DisplayName("Visualizando Seus Dados")
    public void testSeusDados() throws InterruptedException {

        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("empresa@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[3]/a")).click();

        Assertions.assertEquals(this.webDriver.getCurrentUrl(),"http://localhost:3000/voce/seus-dados");

    }

    @Test
    @DisplayName("Enviando Solicitação")
    public void testEnviandoSolicitacao() throws InterruptedException {
        this.webDriver.get("http://localhost:3000/");

        //Clicando no item LOGIN
        this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
        this.webDriver.findElement(By.id("email")).sendKeys("empresa@gmail.com");
        this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

        //Clicando no botão Login
        this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

        //Dando timeOut de 4 segundos
        Thread.sleep(4000);

        //Clicando em SOLICITACOES
        this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[1]/a")).click();

        //nova proposta
        this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/button")).click();

        //Preencha cuidadosamente os campos abaixo. Favor, descreva sua proposta com claresa, não inclua informações desnecessárias.

        Thread.sleep(4000);

        //Titulo da proposta

        this.webDriver.findElement(By.id("newProposalTitle")).sendKeys("Titulo da Proposta Teste ");

        //Faça um breve resumo da proposta

        //Resumo da proposta
        this.webDriver.findElement(By.id("inSummary")).sendKeys("Campo para inserção do resumo da proposta, essa proposta tem como intuito testar a inserção");

        //Em poucas palavras descreva:

        //Objetivo Principal
        this.webDriver.findElement(By.id("inMainObjective")).sendKeys("objetivo principal é testar o submit");

        //objetivo especifico 1
        this.webDriver.findElement(By.id("inSecondaryObjective1")).sendKeys("Teste do Objetivo Especifo 1");

        //objetivo especifico 2
        this.webDriver.findElement(By.id("inSecondaryObjective2")).sendKeys("Teste do Objetivo Especifo 2");

        //objetivo especifico 3
        this.webDriver.findElement(By.id("inSecondaryObjective3")).sendKeys("Teste do Objetivo Especifo 3");

        //publico alvo
        this.webDriver.findElement(By.id("inTargetPublic")).sendKeys("professores,alunos");

        //Qtd pessoas
        this.webDriver.findElement(By.id("inQntPeople")).sendKeys("5");

        //Qual a data ou período de execução data inicial?
        this.webDriver.findElement(By.id("start-date")).sendKeys("31/05/2023");

        //Qual a data ou período de execução data final?
        this.webDriver.findElement(By.id("end-date")).sendKeys("31/07/2023");

        scroll(this.webDriver.findElement(By.id("end-date")));

        //Forneça informações relevantes para o atendimento da sua solicitação
        this.webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/div[7]/div[2]/textarea")).sendKeys("Informações relevantes para o atendimento da sua solicitação aqui!");

        //Clicando em ENVIAR PROPOSTA
        this.webDriver.findElement(By.xpath("//*[@id=\"formNewProposal\"]/button")).click();
        this.webDriver.findElement(By.xpath("//*[@id=\"formNewProposal\"]/button")).click();

        Thread.sleep(5000);



    }
    private void scroll(WebElement parm) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;

        WebElement element  =  parm;

        js.executeScript("arguments[0].scrollIntoView();", element);

        Thread.sleep(3000);

    }


}
