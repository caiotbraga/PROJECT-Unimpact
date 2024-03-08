package unicap.br.unimpact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnimpactApplicationLoginTests {

	private WebDriver webDriver ;

	@BeforeAll
	public  void setupAll(){
		System.setProperty("webdriver.chrome.driver","src\\driver\\chromedriver.exe");
	}

	@BeforeEach
	public void setupEach(){
		this.webDriver = new ChromeDriver();
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
	@DisplayName("Realizando Login de um Executor")
	public void testLoginExecutor() throws InterruptedException {
		String actualUrl; // URL esperada
		String expectUrl; // URL recebida
		String labelRecebido; // label do botão depois do LOGIN
		actualUrl = "http://localhost:3000/voce/solicitacoes";

		this.webDriver.get("http://localhost:3000/");

		//Clicando no item LOGIN
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
		this.webDriver.findElement(By.id("email")).sendKeys("executor@gmail.com");
		this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

		//Clicando no botão Login
		this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();


		//Dando timeOut de 4 segundos
		Thread.sleep(4000);

		//solicitando URL mostrada em tela
		expectUrl = this.webDriver.getCurrentUrl();

		// Comparação URL recebida com esperada
		Assertions.assertEquals(expectUrl, actualUrl);

		labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
		Assertions.assertEquals(labelRecebido, "EXECUTOR");

		//Deslogando do Sistema
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).click();
	}

	@Test
	@DisplayName("Realizando Login de um Solicitante")
	public void testLoginSolicitante() throws InterruptedException {
		String actualUrl; // URL esperada
		String expectUrl; // URL recebida
		String labelRecebido; // label do botão depois do LOGIN
		actualUrl = "http://localhost:3000/voce/solicitacoes";

		this.webDriver.get("http://localhost:3000/");

		//Clicando no item LOGIN
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
		this.webDriver.findElement(By.id("email")).sendKeys("solicitante@gmail.com");
		this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

		//Clicando no botão Login
		this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();


		//Dando timeOut de 4 segundos
		Thread.sleep(4000);

		//solicitando URL mostrada em tela
		expectUrl = this.webDriver.getCurrentUrl();

		// Comparação URL recebida com esperada
		Assertions.assertEquals(expectUrl, actualUrl);

		labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
		Assertions.assertEquals(labelRecebido, "SOLICITANTE");

		//Deslogando do Sistema
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).click();
	}

	@Test
	@DisplayName("Realizando Login de uma Empresa")
	public void testLoginEmpresa() throws InterruptedException {
		String actualUrl; // URL esperada
		String expectUrl; // URL recebida
		String labelRecebido; // label do botão depois do LOGIN
		actualUrl = "http://localhost:3000/voce/grupos-e-representantes";

		this.webDriver.get("http://localhost:3000/");

		//Clicando no item LOGIN
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
		this.webDriver.findElement(By.id("email")).sendKeys("empresa@gmail.com");
		this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

		//Clicando no botão Login
		this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();


		//Dando timeOut de 4 segundos
		Thread.sleep(4000);

		//solicitando URL mostrada em tela
		expectUrl = this.webDriver.getCurrentUrl();

		// Comparação URL recebida com esperada
		Assertions.assertEquals(expectUrl, actualUrl);

		labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
		Assertions.assertEquals(labelRecebido, "EMPRESA");

		//Deslogando do Sistema
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).click();
	}

	@Test
	@DisplayName("Realizando Login de Um Intermediador")
	public void testLoginIntermediador() throws InterruptedException {
		String actualUrl; // URL esperada
		String expectUrl; // URL recebida
		String labelRecebido; // label do botão depois do LOGIN
		actualUrl = "http://localhost:3000/inter/solicitacoes";

		this.webDriver.get("http://localhost:3000/");

		//Clicando no item LOGIN
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button[2]")).click();
		this.webDriver.findElement(By.id("email")).sendKeys("intermediario@gmail.com");
		this.webDriver.findElement(By.id("password")).sendKeys("Aa123456#");

		//Clicando no botão Login
		this.webDriver.findElement(By.xpath("//*[@id=\"login-page\"]/form/button")).click();


		//Dando timeOut de 4 segundos
		Thread.sleep(4000);

		//solicitando URL mostrada em tela
		expectUrl = this.webDriver.getCurrentUrl();

		// Comparação URL recebida com esperada
		Assertions.assertEquals(expectUrl, actualUrl);

		labelRecebido = this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).getText();
		Assertions.assertEquals(labelRecebido, "INTERMEDIARIO");

		//Deslogando do Sistema
		this.webDriver.findElement(By.xpath("//*[@id=\"_nav_options\"]/button")).click();





	}




}
