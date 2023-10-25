package WebSelenium.TaskSQT;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Unit test for simple App.
 */
public class AppTest {
	WebDriver driver;

	/*
	 * Test that verifies accuracy of test cost calculator Pages covered: Logins ->
	 * Verify Application Dashboard -> Test cost calculator add Patients Tests, Add
	 * Discount then verify the Total by Discount and Subtotal.
	 */
	@Test
	public void TestCostCalculator() throws InterruptedException {
		SetUp();
		Login();
		Dashboard();
		driver.close();
	}

	/*
	 * Test that verifies Add Patient flow Pages covered: Login -> Patients Tab ->
	 * Add Patients -> Enter General Details -> Enter Healt Details -> Enter Test
	 * Details ->Add Patient -> Search newly added Patient and verify -> Delete
	 * Patient.
	 */
	@Test
	public void AddPatient() throws InterruptedException {
		SetUp();
		Login();
		PatientDetails();
		driver.close();
	}

	/*
	 * Setupr environment for the test execution Chrome browser driver used
	 */
	public void SetUp() {
		// Setting environment variable for chrome dirve
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\target\\driver\\chromedriver-win32\\chromedriver.exe");

		// Initiating dirver
		driver = new ChromeDriver();

		// Navigating to the web Page
		driver.get("https://gor-pathology.web.app/");

		// Maximize the window
		driver.manage().window().maximize();
	}

	/*
	 * Logind Method can be used dynamically by replacing String with variabels
	 */
	public void Login() throws InterruptedException {
		ObjectRepository login = new ObjectRepository();

		driver.findElement(By.xpath(login.Username)).sendKeys("Test@Kennect.io");
		driver.findElement(By.xpath(login.Passowrd)).sendKeys("Qwerty@1234");
		driver.findElement(By.xpath(login.Loginbtn)).click();
		Thread.sleep(5000);
		System.out.println("After Sleep");
	}

	/*
	 * Method to validate dashboard and test cost calculator can Add single or
	 * multiple Tests - Multiple Tests Should be separated by ,(Commas) Discount
	 * should be given in Integer number
	 */
	public void Dashboard() throws InterruptedException {
		ObjectRepository dashboard = new ObjectRepository();
		String Tests = "AFP,beans";// Tests Input
		int Discount = 10;

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(dashboard.Discount)));
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");

		driver.findElement(By.id(dashboard.PatientTest)).click();
		String[] test = Tests.split(",");
		for (int i = 0; i < test.length; i++) {
			driver.findElement(By.id(dashboard.PatientTest)).sendKeys(test[i]);
			driver.findElement(By.id(dashboard.PatientTest)).sendKeys(Keys.ARROW_DOWN);
			driver.findElement(By.id(dashboard.PatientTest)).sendKeys(Keys.ENTER);
		}

		driver.findElement(By.xpath(dashboard.Discount)).click();
		driver.findElement(By.xpath("//*[contains(text(),'" + Discount + "%')]")).click();

		String sb = driver.findElement(By.xpath(dashboard.Subtotal)).getText();
		String subtotal = sb.substring(0, sb.length() - 2);

		int subtotals = Integer.parseInt(subtotal);
		int percent = subtotals / 100 * Discount;
		int Expectedtotal = subtotals - percent;
		System.out.println(Expectedtotal);

		String ExpectedTotal = "" + Expectedtotal + " ₹";
		String ActualTotal = driver.findElement(By.xpath(dashboard.total)).getText();

		Assert.assertEquals(ExpectedTotal, ActualTotal);
	}

	/*
	 * Add Patient Consolidated method
	 */
	public void PatientDetails() throws InterruptedException {
		ObjectRepository AddPatient = new ObjectRepository();

		GeneralDetails(AddPatient);
		HealthDetails(AddPatient);
		TestDetails(AddPatient);
		DeletePatient(AddPatient);
	}

	/*
	 * First page of Add Patient flow used to Enter General details of Patient
	 */
	public void GeneralDetails(ObjectRepository AddPatient) {
		driver.findElement(By.xpath(AddPatient.PatientsTab)).click();
		driver.findElement(By.xpath(AddPatient.AddPatient)).click();
		driver.findElement(By.name(AddPatient.pName)).sendKeys("Priya");
		driver.findElement(By.name(AddPatient.pEmail)).sendKeys("Priya@gmail.com");
		driver.findElement(By.name(AddPatient.pPhone)).sendKeys("9876543217");
		driver.findElement(By.xpath(AddPatient.GeneralDetbtn)).click();
	}

	/*
	 * Second Page of Add Patient flow used to Enter Patients health details
	 */
	public void HealthDetails(ObjectRepository AddPatient) throws InterruptedException {
		driver.findElement(By.name(AddPatient.PHeight)).clear();
		driver.findElement(By.name(AddPatient.PHeight)).sendKeys("154");
		driver.findElement(By.name(AddPatient.pWeight)).clear();
		driver.findElement(By.name(AddPatient.pWeight)).sendKeys("45");
		driver.findElement(By.id(AddPatient.pGender)).click();
		driver.findElement(By.xpath("//li[contains(text(),'Female')]")).click();
		driver.findElement(By.name(AddPatient.pAge)).clear();
		driver.findElement(By.name(AddPatient.pAge)).sendKeys("25");
		driver.findElement(By.name(AddPatient.pSystolic)).clear();
		driver.findElement(By.name(AddPatient.pSystolic)).sendKeys("158");
		driver.findElement(By.name(AddPatient.pDiastolic)).clear();
		driver.findElement(By.name(AddPatient.pDiastolic)).sendKeys("58");

		// Scroll Down
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(AddPatient.AddTests)));
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");

		driver.findElement(By.xpath(AddPatient.AddTests)).click();
	}

	/*
	 * Final stage of Add Patient Flow Adding Test details
	 */
	public void TestDetails(ObjectRepository AddPatient) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String Tests = "AFP,beans";
		int Discount = 10;

		driver.findElement(By.id(AddPatient.PatientTest)).click();
		String[] test = Tests.split(",");
		for (int i = 0; i < test.length; i++) {
			driver.findElement(By.id(AddPatient.PatientTest)).sendKeys(test[i]);
			driver.findElement(By.id(AddPatient.PatientTest)).sendKeys(Keys.ARROW_DOWN);
			driver.findElement(By.id(AddPatient.PatientTest)).sendKeys(Keys.ENTER);
		}
		driver.findElement(By.xpath(AddPatient.Discount)).click();
		driver.findElement(By.xpath("//li[contains(text(),'" + Discount + "%')]")).click();

		// Sleep is used to match the script speed to application response time
		driver.findElement(By.id(AddPatient.pLabs)).click();
		Thread.sleep(1000);
		driver.findElement(By.id(AddPatient.pLabs)).sendKeys("healthcare");
		Thread.sleep(1000);
		driver.findElement(By.id(AddPatient.pLabs)).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.id(AddPatient.pLabs)).sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		driver.findElement(By.name(AddPatient.pDocName)).click();
		Thread.sleep(1000);
//		driver.findElement(By.xpath("//*[contains(text(),'Dr.roheet rathod')]")).click();
		driver.findElement(By.name(AddPatient.pDocName)).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.name(AddPatient.pDocName)).sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		driver.findElement(By.id(AddPatient.pDocCommi)).click();
		driver.findElement(By.xpath("//li[contains(text(),'" + Discount + "%')]")).click();

		// Scroll Down to Add Equipment
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(AddPatient.AddPatient)));
		Thread.sleep(1000);
		js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");

		// JavaScript click on Add Equipment icon
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath(AddPatient.AddEquip)));

		// Wait for Equipment name drop down to load
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AddPatient.EquipName)));

		// Action Class Click on Equipment Dropdown
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath(AddPatient.EquipName));
		action.moveToElement(we).moveToElement(driver.findElement(By.xpath(AddPatient.EquipName))).click().build()
				.perform();
		/*
		 * Javascript click and normal click not working on Equipment Name
		 * driver.findElement(By.xpath(AddPatient.EquipName)).click();
		 * js.executeScript("arguments[0].click();",
		 * driver.findElement(By.xpath(AddPatient.EquipName)));
		 * 
		 * driver.findElement(By.xpath("//*[contains(text(),'test equipment')]")).click(
		 * );
		 * 
		 */

		driver.findElement(By.xpath(AddPatient.Check)).click();

		String sb = driver.findElement(By.xpath(AddPatient.Subtotal)).getText();
		String subtotal = sb.substring(0, sb.length() - 2);

		int subtotals = Integer.parseInt(subtotal);
		int percent = subtotals / 100 * Discount;
		int Expectedtotal = subtotals - percent;
		System.out.println(Expectedtotal);

		String ExpectedTotal = "" + Expectedtotal + " ₹";
		String ActualTotal = driver.findElement(By.xpath(AddPatient.total)).getText();
		Assert.assertEquals(ExpectedTotal, ActualTotal);

		// Scroll and click to Add Patient Button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(AddPatient.AddPatient)));
		Thread.sleep(1000);
		js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");
		driver.findElement(By.xpath(AddPatient.AddPatient)).click();

		// wait for success Message and assert
		WebElement element1 = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AddPatient.SuccessMsg)));
		driver.findElement(By.xpath(AddPatient.SuccessMsg));
		String ActualMsg = driver.findElement(By.xpath(AddPatient.SuccessMsg)).getText();
		Assert.assertEquals("Patient added successfully.", ActualMsg);
	}

	/*
	 * Verify and Delete newly Added Patient
	 */
	public void DeletePatient(ObjectRepository AddPatient) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Wait for page to load and search bar to appear
		WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AddPatient.search)));
		driver.findElement(By.xpath(AddPatient.search)).sendKeys("priya");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Priya')]")));
		driver.findElement(By.xpath("//*[contains(text(),'Priya')]")).click();

		// Scroll and click on delete button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AddPatient.delete)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(AddPatient.delete)));
		Thread.sleep(1000);
		js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");
		driver.findElement(By.xpath(AddPatient.delete)).click();
	}
}
