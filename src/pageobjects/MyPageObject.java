package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="My Page Object"                                
     , summary=""
     , relativeUrl=""
     , connection="Frame"
     )             
public class MyPageObject {
		@PageFrame()

public static class Frame{
@PageFrame()
public static class InnerFrame{

@BooleanType
@FindBy(xpath = "//input[@type='checkbox']")
public WebElement username;

}
@FindBy(xpath ="//iframe[@id='frame3']")
public InnerFrame innerframe;	

}
@FindBy(xpath ="//iframe[@id='frame1']")
public Frame frame;	
}

