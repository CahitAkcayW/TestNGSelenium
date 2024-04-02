package com.cybertek.tests.day16_ddf;

import com.cybertek.pages.DashboardPage;
import com.cybertek.pages.LoginPage;
import com.cybertek.tests.TestBase;
import com.cybertek.utilities.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DDFLoginTest extends TestBase {

    //getting data with 2d array
    @DataProvider
    public Object[][] userData() {
        ExcelUtil qa3short = new ExcelUtil("src/test/resources/Vytracktestdata.xlsx", "QA3-short");

        String [][] dataArray = qa3short.getDataArrayWithoutFirstRow();

        return dataArray;
    }

    //how make connection with your data provider
    @Test(dataProvider = "userData")
    //putting the keyname of the sheet as our excel file
    public void test1(String username,String password,String firstname,String lastname){

        extentLogger=report.createTest("test" +firstname+" "+ lastname);

        //we created login page
        LoginPage loginPage= new LoginPage();
        //we're passing the names and password from data provider to login page
        loginPage.login(username, password);

        //we are at dashboard page and we said to wait until loader screen disappear
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.waitUntilLoaderScreenDisappear();

        String actualFullname = dashboardPage.getUserName();
        String expectedFullname = firstname+" "+lastname;

        Assert.assertEquals(actualFullname,expectedFullname,"verify fullname");

        extentLogger.pass("Pass");

    }
}