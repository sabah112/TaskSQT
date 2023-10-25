package WebSelenium.TaskSQT;

public class ObjectRepository {

	String Username = "//input[@name='email']";
	String Passowrd = "//input[@name='password']";
	String Loginbtn = "//*[text()='Login']";
	String Dashboard = "//div[text()='Dashboard']";

	String PatientTest = "patient-test";
	String Discount = "//*[text()='None']";
	String Subtotal = "//div[text()='Subtotal']/following-sibling::div";
	String total = "//div[text()='Total']/following-sibling::div";

	String PatientsTab = "//span[text()='Patients']";
	String AddPatient = "(//span[text()='Add Patient'])[1]";
	String pName = "name";
	String pEmail ="email";
	String pPhone = "phone";
	String GeneralDetbtn = "(//span[text()='General Details'])[2]";
	
	String PHeight = "height";
	String pWeight = "weight";
	String pGender = "mui-component-select-gender";
	String pAge = "age";
	String pSystolic = "systolic";
	String pDiastolic = "diastolic";
	String AddTests = "(//span[text()='Add Tests'])[2]";
	
	String pLabs = "patient-tests-labs"; //id
	String pDocName = "doctor_name";
	String pDocCommi = "mui-component-select-doctor_commission"; //id
	
	String AddEquip = "//span[text()='add_box']";
	String EquipName = "//*[@class='MuiSvgIcon-root MuiSelect-icon']";
	String Check = "//*[text()='check']";
	String SuccessMsg = "//*[contains(text(),'Patient added successfully.')]";
	
	String search = "//input[@placeholder='Search']";
	String delete = "//*[contains(text(),'Yes Delete')]";
}
