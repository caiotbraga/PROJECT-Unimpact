package unicap.br.unimpact;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class unimpactApplicationSolicitanteTests {

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
        @DisplayName("Criando Grupos")
        public void testCriandoGrupo() throws InterruptedException {
            String labelRecebido; // label do botão depois do LOGIN

            this.webDriver.get("http://localhost:3000/");

            //Clicando no item LOGIN
            this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
            this.webDriver.findElement(By.id("email")).sendKeys("solicitante@gmail.com");
            this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

            //Clicando no botão Login
            this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

            //Dando timeOut de 4 segundos
            Thread.sleep(4000);

            labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
            Assertions.assertEquals(labelRecebido, "SOLICITANTE");

            //Clicando em GRUPOS
            this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[2]/a")).click();
            //Clicando em Criar Grupo
            this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/button")).click();

            //Passando dados de criação de grupo

            this.webDriver.findElement(By.id("groupName")).sendKeys("RH");
            this.webDriver.findElement(By.id("description")).sendKeys("Grupo responsável pelo recursos humanos");
            this.webDriver.findElement(By.id("araesOfOperation")).sendKeys("Recursos Humanos, Financeiro, Contratação");

            //Clicando em CRIAR
            this.webDriver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[2]/button[1]")).click();
        }
        @Test
        @DisplayName("Deletando um Grupo")
        public void testDeletandoGrupo() throws InterruptedException {
            String labelRecebido; // label do botão depois do LOGIN

            this.webDriver.get("http://localhost:3000/");

            //Clicando no item LOGIN
            this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
            this.webDriver.findElement(By.id("email")).sendKeys("solicitante@gmail.com");
            this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

            //Clicando no botão Login
            this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

            //Dando timeOut de 4 segundos
            Thread.sleep(4000);

            labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
            Assertions.assertEquals(labelRecebido, "SOLICITANTE");

            //Clicando em GRUPOS
            this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[2]/a")).click();

            //Clicando no grupo
            this.webDriver.findElement(By.xpath("//*[@id=\"__grupos\"]")).click();

            Thread.sleep(3000);

            //Excluindo Grupo
            this.webDriver.findElement(By.xpath("//*[@id=\"__members\"]/button[2]/span")).click();

            //Confirmando deleção do grupo
            this.webDriver.findElement(By.xpath("//*[@id=\"confirmation-modal\"]/div/div[2]/div/button[1]")).click();
            Thread.sleep(3000);
            //Verificando mensagem se grupo foi excluido com sucesso.
            String toast = this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/p")).getText();
            Assertions.assertTrue(toast.equalsIgnoreCase("Grupo removido com sucesso!"));
        }
        @Test
        @DisplayName("Cadastro de uma Nova Proposta")
        public void testNovaProposta() throws InterruptedException {
            this.webDriver.get("http://localhost:3000/");

            //Clicando no item LOGIN
            this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
            this.webDriver.findElement(By.id("email")).sendKeys("solicitante@gmail.com");
            this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

            //Clicando no botão Login
            this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

            //Dando timeOut de 4 segundos
            Thread.sleep(4000);

            //entrando em SOLICITAÇÕES
            this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[1]/a")).click();

            //nova proposta
            this.webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/button")).click();

            //Preencha cuidadosamente os campos abaixo. Favor, descreva sua proposta com claresa, não inclua informações desnecessárias.

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

        @Test
        @DisplayName("Entrando em Seus Dados")
        public void testSeusDados() throws InterruptedException {
            this.webDriver.get("http://localhost:3000/");

            //Clicando no item LOGIN
            this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
            this.webDriver.findElement(By.id("email")).sendKeys("solicitante@gmail.com");
            this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

            //Clicando no botão Login
            this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();

            //Dando timeOut de 4 segundos
            Thread.sleep(4000);

            //Clicando em SEUS DADOS
            this.webDriver.findElement(By.xpath("//*[@id=\"_paths\"]/div[3]/a")).click();

            Assertions.assertEquals(this.webDriver.getCurrentUrl(),"http://localhost:3000/voce/seus-dados");
        }


}
