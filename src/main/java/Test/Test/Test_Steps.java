package Test.Test;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.DataTable;
import cucumber.api.java.en.*;

public class Test_Steps {

	private static WebDriver driver;

	@Given("^User is on homepage$")
	public void user_is_on_homepage() throws Throwable {

		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette", true);

		// Creamos una nueva instancia del driver de firefox
		driver = new FirefoxDriver(options);

		// Lanzamos la pagina en el navegador
		driver.get("https://katalon-demo-cura.herokuapp.com");

		// Ponemos espera implicita, para evitar que salte excepcion si no carga a
		// tiempo la pagina
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@When("^User Login$")
	public void user_navigate_to_Login_page() throws Throwable {

		// Escribimos el usuario y la contrase�a en los campos correspondientes

		// Partimos de la pagina principal, tenemos que hacer click en el boton de la
		// derecha
		driver.findElement(By.id("menu-toggle")).click();

		// Ponemos una espera de 3 segundos por si tarda
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Despues hacemos click en el boton de login
		driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();

		// Ponemos una espera de 3 segundos por si tarda
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("txt-username")).sendKeys("John Doe");
		driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");

		// Despues damos al boton de login
		driver.findElement(By.id("btn-login")).click();

		// Esperamos 5 segundos por si acaso
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@Then("^Message displayed Login succesfully$")
	public void message_displayed_Login_succesfully() throws Throwable {

		System.out.println("Login succesfully");

	}

	@Given("^User is on appointment page$")
	public void user_is_on_appointment_page() throws Throwable {

		driver.get("https://katalon-demo-cura.herokuapp.com/index.php#appointment");

	}

	@When("^User enter appointment data$")
	public void user_enter_appointment_data() throws Throwable {
		// El primer combobox
		WebElement combobox = driver.findElement(By.id("combo_facility"));
		Select selectBox = new Select(combobox);
		selectBox.selectByIndex(2);

		// Despues hay un checkbox
		driver.findElement(By.id("chk_hospotal_readmission")).click();

		// Despues el grupo de radiobuttons en el que seleccionamos el healthcare
		// program
		driver.findElement(By.id("radio_program_medicare")).click();
		driver.findElement(By.id("radio_program_medicaid")).click();
		driver.findElement(By.id("radio_program_none")).click();

		// Luego la fecha
		String fecha = "25" + "/" + "06" + "/" + "2019";
		driver.findElement(By.id("txt_visit_date")).sendKeys(fecha);
		System.out.println("Fecha introducida: " + fecha);

		// Por ultimo el comentario
		driver.findElement(By.id("txt_comment")).sendKeys("Hurry up");

		// Damos click a crear el appointment
		driver.findElement(By.id("btn-book-appointment")).click();
	}

	@Then("^Message displayed appointment creation succesful$")
	public void message_displayed_appointment_cretion_succesful() throws Throwable {

		System.out.println("Appointment creation succesful");

	}

	@Given("^User is on appointment summary$")
	public void user_is_on_appointment_summary() throws Throwable {

		driver.get("https://katalon-demo-cura.herokuapp.com/appointment.php#summary");

	}

	@When("^User clicks logout button$")
	public void user_clicks_logout_button() throws Throwable {

		// Solo hacemos click en el boton del menu desplegable
		driver.findElement(By.id("menu-toggle")).click();

		// Esperamos 3 segundos por si acaso
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Y despues en el boton de logout
		driver.findElement(By.xpath("/html/body/nav/ul/li[5]/a")).click();

	}

	@Then("^Message displayed Logout succesfully$")
	public void message_displayed_Logout_succesfully() throws Throwable {

		System.out.println("Logout succesfully");

		driver.close();

	}

}
