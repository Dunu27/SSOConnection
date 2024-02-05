package pageobjects;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.provar.core.model.base.api.IRuntimeConfiguration;
import com.provar.core.testapi.ILoginPage;
import com.provar.core.testapi.ILoginResult;
import com.provar.core.testapi.annotations.Page;
import com.provar.core.testapi.java.UiLoginResultImpl;
@Page(title = "SSOFile", connection = "Connection Name")
public class SSOFile implements ILoginPage {
    @Override
    public ILoginResult doLogin(IRuntimeConfiguration runtimeConfiguration, WebDriver driver,
            Map<String, String> credentials) {
        // Connection 'SSO Username' and 'SSO Password' stored in userName and password
        // variables
        String userName = credentials.get(CREDENTIAL_USER);
        String password = credentials.get(CREDENTIAL_PASSWORD);
        // User can put wait here to load the page
        // Check if the user is already logged in
        if (isLoggedIn(driver)) {
            return new UiLoginResultImpl(true, driver);
        } else {
             try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
			}
            // Enter user secret details to login and submit
            driver.findElement(By.xpath("//input[@type='email']")).sendKeys(userName);
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys(password);
            driver.findElement(By.xpath("//input[@type='submit']")).click();
            // User can put wait here to load the page (Added 2 second wait)
            waitForPageToLoad(2000);
            // Check if user navigate to successfully on opt page(if this is true it means
            // user still on login page).
            if (isInvalidLoginCredentials(driver)) {
                return new UiLoginResultImpl(false, "Invalid login credentials", driver);
            }
            // Get Generated OTP and enter in otp in the field 
            int count = 0;
            WebElement otpElement = driver.findElement(By.xpath("//input"));
            while (count < 2) {
                try {
                    String otp = generateTOTP(credentials);
                    // Optionally user can also pass time interval as 60 sec ,passgenerateTOTP(credentials,60);
                     
                    otpElement.clear();
                    otpElement.sendKeys(otp);
                    driver.findElement(By.xpath("//input")).click();
                    otpElement = driver.findElement(By.xpath("//input"));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            // Check again if user entered correct OTP and logged in successfully
            boolean loginSuccess = isLoggedIn(driver);
            if (loginSuccess) {
                return new UiLoginResultImpl(true, "Login Successful.", driver);
            } else {
                return new UiLoginResultImpl(false, "Login failed : Please check your credentials.", driver);
            }
        }
    }
    private boolean isLoggedIn(WebDriver driver) {
        try {
            driver.findElement(By.xpath("<Xpath for field to check user is logged in>"));
        } catch (NoSuchElementException ex) {
            return false;
        }
        return true;
    }
    private  void waitForPageToLoad(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private boolean isInvalidLoginCredentials(WebDriver driver) {
        try {
            driver.findElement(By.xpath("<Xpath for field available in login page(like: Password field Xpath)"));
        } catch (NoSuchElementException ex) {
            return false;
        }
        return true;
    }
}