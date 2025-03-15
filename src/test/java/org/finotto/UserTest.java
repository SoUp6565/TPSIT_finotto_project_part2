package org.finotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password123");
    }

    //TEST DEPOSIT
    @Test
    void depositToAccountGeneral() {
        user.depositToAccount(100);
        assertEquals(100, user.getAccount());
    }

    @Test
    void depositToAccountNegativeNumber(){
        assertFalse(user.depositToAccount(-100));
    }

    @Test
    void depositToAccountZero(){
        assertFalse(user.depositToAccount(0));
    }

    @Test
    void depositToAccountMoreThanWallet(){
        assertFalse(user.depositToAccount(200));
    }

    @Test
    void depositToAccountNegativeNumberShouldNotChangeBalance() {
        double initialBalance = user.getAccount();
        user.depositToAccount(-100);
        assertEquals(initialBalance, user.getAccount());
    }



    //TEST WITHDRAW
    @Test
    void withdrawFromAccountGeneral() {
        user.depositToAccount(100);
        user.withdrawFromAccount(50);
        assertEquals(50, user.getAccount());
    }

    @Test
    void withdrawFromAccountNegativeNumber(){
        user.depositToAccount(100);
        assertFalse(user.withdrawFromAccount(-100));
    }

    @Test
    void withdrawFromAccountZero(){
        user.depositToAccount(100);
        assertFalse(user.withdrawFromAccount(0));
    }
    @Test
    void withdrawFromAccountMoreThanBalance(){
        user.depositToAccount(100);
        assertFalse(user.withdrawFromAccount(200));
    }

    @Test
    void withdrawFromAccountMoreThanBalanceShouldNotChangeBalance() {
        user.depositToAccount(100);
        double balanceBefore = user.getAccount();
        user.withdrawFromAccount(200);
        assertEquals(balanceBefore, user.getAccount());
    }

    @Test
    void multipleDepositsAndWithdrawals() {
        user.depositToAccount(50);
        user.depositToAccount(50);
        user.withdrawFromAccount(30);
        assertEquals(70, user.getAccount());
    }




    //TEST INVESTMENT
    @Test
    void checkInvestmentsGeneral() {
        user.depositToAccount(100);
        user.invest(100.0, 10, (int)((Math.random()*3)+1));
        boolean isTrue = false;
        for(int i = 0; i<user.getInvestments().size(); i++){
            user.getInvestments().get(i).advanceTime(10);
            if (user.getInvestments().get(i).getReturn() > 0){
                isTrue = true;
            }
        }
        assertTrue(isTrue);
    }

    @Test
    void checkInvestmentsLowRisk(){
        user.depositToAccount(100);
        user.invest(100.0, 10, 1);
        boolean isTrue = false;
        for(int i = 0; i<user.getInvestments().size(); i++){
            user.getInvestments().get(i).advanceTime(10);
            if (user.getInvestments().get(i).getReturn() <=115 && user.getInvestments().get(i).getReturn() >=90){
                isTrue = true;
            }
        }
        assertTrue(isTrue);
    }

    @Test
    void checkInvestmentsMidRisk(){
        user.depositToAccount(100);
        user.invest(100.0, 10, 2);
        boolean isTrue = false;
        for(int i = 0; i<user.getInvestments().size(); i++){
            user.getInvestments().get(i).advanceTime(10);
            if (user.getInvestments().get(i).getReturn() <=125 && user.getInvestments().get(i).getReturn() >=75){
                isTrue = true;
            }
        }
        assertTrue(isTrue);
    }

    @Test
    void checkInvestmentsHighRisk(){
        user.depositToAccount(100);
        user.invest(100.0, 10, 3);
        boolean isTrue = false;
        for(int i = 0; i<user.getInvestments().size(); i++){
            user.getInvestments().get(i).advanceTime(10);
            if (user.getInvestments().get(i).getReturn() <=200 && user.getInvestments().get(i).getReturn() >=50){
                isTrue = true;
            }
        }
        assertTrue(isTrue);
    }

    @Test
    void checkInvestmentsZero(){
        user.depositToAccount(100);
        assertFalse(user.invest(0, 10, (int)((Math.random()*3)+1)));
    }

    @Test
    void checkInvestmentsMoreThanBalance(){
        user.depositToAccount(100);
        assertFalse(user.invest(200, 10, (int)((Math.random()*3)+1)));
    }

    @Test
    void investZeroDaysShouldFail() {
        user.depositToAccount(100);
        assertFalse(user.invest(50, 0, 1));
    }

    @Test
    void investInvalidRiskLevelShouldFail() {
        user.depositToAccount(100);
        assertFalse(user.invest(50, 10, 4)); // Rischio fuori dal range
    }


}
