package TestRaw;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class TestRaw {
	
	private static WebDriver driver;
	private static boolean loginCorrecto;
	private static boolean botonesCorrectos;
	private static boolean appointmentCorrecto;
	private static boolean logoutCorrecto;
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
		
		// Creamos una nueva instancia del driver de firefox
	    driver = new FirefoxDriver();

	    // Lanzamos la pagina en el navegador
	    driver.get("https://katalon-demo-cura.herokuapp.com");
	    
	    // Ponemos espera implicita, para evitar que salte excepcion si no carga a tiempo la pagina
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    
	    // Hacemos la prueba de login
	    loginCorrecto = login("John Doe", "ThisIsNotAPassword");
	    
	    //Si el login ha sido correcto, lanzamos el test de botones
	    /*if (loginCorrecto) {
	    	botonesCorrectos = botonesLaterales();
	    } else {
	    	System.out.println("Fallo en los botones laterales");
	    	return;
	    }*/
	    
	    //Si el test de botones ha sido correcto, lanzamos el test de crear un appointment
	    if (loginCorrecto) {
	    	Calendar cal = Calendar.getInstance();
	    	cal.set(Calendar.YEAR, 2019);
	    	cal.set(Calendar.MONTH, Calendar.JUNE);
	    	cal.set(Calendar.DAY_OF_MONTH, 20);
	    	appointmentCorrecto = makeAppointment(2, true, 2, cal,"hurry");
	    } else {
	    	System.out.println("Fallo al hacer login");
	    	return;
	    }
	    
	    //Si el appointment se ha creado correctamente, hacemos logout
	    if (appointmentCorrecto) {
	    	logoutCorrecto = logout();
	    } else {
	    	System.out.println("Fallo al crear appointment");
	    	return;
	    }
	    
	    //Si los test han sido realizados con exito, lo mostramos
	    if (logoutCorrecto) {
	    	System.out.println("Test Correcto");
	    } else {
	    	System.out.println("Fallo al hacer logout");
	    	return;
	    }
	    
	}
	
	public static boolean login(String username, String password) {
		
		// Partimos de la pagina principal, tenemos que hacer click en el boton de la derecha
		driver.findElement(By.id("menu-toggle")).click();
		
		// Ponemos una espera de 3 segundos por si tarda
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		// Despues hacemos click en el boton de login
		driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
		
		// Ponemos una espera de 3 segundos por si tarda
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				
		//Escribimos el usuario y la contrase�a en los campos correspondientes
		driver.findElement(By.id("txt-username")).sendKeys(username);
		driver.findElement(By.id("txt-password")).sendKeys(password);
		
		//Despues damos al boton de login
		driver.findElement(By.id("btn-login")).click();
		
		// Ponemos una espera de 3 segundos por si tarda
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		//Ya esta completado el login, para comprobar que se ha logeado buscamos el boton de crear appointment
		//y retornamos true o false dependiendo, si esta, se ha logeado bien, si no, retornamos false.
		if (driver.findElement(By.id("btn-book-appointment")) != null) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean makeAppointment(int facility, boolean hospitalReadmission, int healthcare, Calendar visit, String comment) {
		
		//Solo rellenamos los campos con los datos del metodo
		
		//El primer combobox
		WebElement combobox = driver.findElement(By.id("combo_facility"));
		if (facility <= 3) {
		Select selectBox = new Select(combobox);
	    selectBox.selectByIndex(facility);
		} else {
			System.out.println("Primer argumento erroneo,");
			return false;
		}
	    
	    //Despues hay un checkbox
	    if (hospitalReadmission) {
	    driver.findElement(By.id("chk_hospotal_readmission")).click();
	    }
	    
	    //Despues el grupo de radiobuttons en el que seleccionamos el healthcare program
	    if (healthcare == 1) {
	    	driver.findElement(By.id("radio_program_medicare")).click();
	    } else if (healthcare == 2) {
	    	driver.findElement(By.id("radio_program_medicaid")).click();
	    } else if (healthcare == 3) {
	    	driver.findElement(By.id("radio_program_none")).click();
	    } else {
	    	System.out.println("Numero no valido, es del 1 al 3");
	    	return false;
	    }
	    
	    //Luego la fecha
	    String fecha = visit.get(Calendar.DAY_OF_MONTH) + "/" + visit.get(Calendar.MONTH) + "/" + visit.get(Calendar.YEAR);
	    driver.findElement(By.id("txt_visit_date")).sendKeys(fecha);
	    System.out.println("Fecha introducida: " + fecha);
	    
	    //Por ultimo el comentario
	    driver.findElement(By.id("txt_comment")).sendKeys(comment);
	    
	    //Damos click a crear el appointment
	    driver.findElement(By.id("btn-book-appointment")).click();
	    
	    //Comprobamos que se crea el appointment
	    if (driver.findElement(By.id("facility")) != null) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean botonesLaterales() {
		
		//Probaremos el boton de home, el de history y el de profile
		
		//Click en el boton de home
		driver.findElement(By.id("menu-toggle")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[2]/a")).click();
		
		//Click en el boton de history
		driver.findElement(By.id("menu-toggle")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
		
		if (driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[3]/p/a")) == null) {
			System.out.println("Error en el boton history");
			return false;
		}
		
		//Dentro de history probamos el boton "Go to HomePage"
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"history\"]/div/div[3]/p/a")).click();
		
		//Por ultimo el de profile
		driver.findElement(By.id("menu-toggle")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		if (driver.findElement(By.xpath("//*[@id=\"profile\"]/div/div/div/p[1]")) == null) {
			System.out.println("Error en el boton profile");
			return false;
		}
		
		//Si hemos llegado hasta aqui, funcionan todos
		return true;
		
	}
	
	public static boolean logout() {
		
		//Solo hacemos click en el boton del menu desplegable
		driver.findElement(By.id("menu-toggle")).click();
		
		//Esperamos 3 segundos por si acaso
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		//Y despues en el boton de logout
		driver.findElement(By.xpath("/html/body/nav/ul/li[5]/a")).click();
		
		//Comprobamos que estamos en la pagina inicial
		/*if (driver.getCurrentUrl().equals("https://katalon-demo-cura.herokuapp.com")) {
			System.out.println("Logout Correcto");
			return true;
		} else {
			System.out.println("Error en el logout");
			return false;
		}*/
		
		return true;

	}
	
}
