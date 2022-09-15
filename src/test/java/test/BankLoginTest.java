package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;


public class BankLoginTest {

    @AfterAll
    static void teardown(){
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccesfullLogin(){
        var LoginPage = open ("http:://localhost:9999", page.LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = LoginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }
    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        VerificationPage.verifyErrorNotificationVisiblity();
    }
}
