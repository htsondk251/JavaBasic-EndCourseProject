package com.SonHoang;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.closeTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AppTest {
    Bank b1 = new Bank("ABC", "HN");
    List<Account> accounts = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    Map<String, String> userDatas = new HashMap<>();
    String fakeEmail = "htsondk251@mail.com";
    long fakeAccountNumber = 3805449018l;
//    b1.setAccounts(accounts);

    @Before
    public void setUp() throws Exception {
        Customer c1 = new Customer("172645991", "Hoang Thanh Son", "16/06/1988", "HBT, HN", "htsondk251@gmail.com", "0984964197");
        Customer c2 = new Customer("172645910", "Hoang Thi Dzung", "16/06/1986", "HBT, HN", "dzunghoang.n21@gmail.com", "0919581286");
        Customer c3 = new Customer("172645912", "Tran Minh Hanh", "16/06/1997", "HBT, HN", "bighero6@gmail.com", "0344592834");
        Customer c4 = new Customer("172645913", "Tran Nguyet Minh", "16/06/1993", "HBT, HN", "nguyetminhbs93@gmail.com", "0335198726");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        customers.add(c4);
        b1.setCustomers(customers);

        Account a1 = new Account(3805449019l, c1.getId());
        Account a2 = new Account(3805449020l, c2.getId());
        Account a3 = new Account(3805449021l, c3.getId());
        Account a4 = new Account(3805449022l, c4.getId());
        accounts.add(a1);
        accounts.add(a2);
        accounts.add(a3);
        accounts.add(a4);
        b1.setAccounts(accounts);

        userDatas.put("htsondk251@gmail.com", "#123456");
        userDatas.put("dzunghoang.n21@gmail.com", "180288#");
        userDatas.put("bighero6@gmail.com", "matkhaucuaHah");
        userDatas.put("nguyetminhbs93@gmail.com", "matkhaucuaMih");
        b1.setUserDatas(userDatas);
    }

    @Test
    public void testAddValue() {
        b1.getAccounts().get(0).addValue(50.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(100.0, 0.0));
        b1.getAccounts().get(0).addValue(0.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(100.0, 0.0));
        b1.getAccounts().get(0).addValue(60.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(160.0, 0.0));
    }

    @Test
    public void testSubValue() {
        b1.getAccounts().get(0).setBalance(50.0);
        b1.getAccounts().get(0).subValue(40.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(10.0, 0.1));
        assertThrows(IllegalArgumentException.class, () -> {b1.getAccounts().get(0).subValue(10.0);});
    }

    @Test
    public void testAddMonthlyInterest() {
        assertThat(b1.getAccounts().get(1).getBalance(), closeTo(50.0, 0.042));
//        assertThat(b1.getAccounts().get(1).getBalance(), closeTo(50.0, 0.0));
//        b1.getAccounts().get(1).addValue(50);
//        b1.getAccounts().get(1).addMonthlyInterest();

    }

    @Test
    public void testCheckValidity() {
        b1.verifyAccount("htsondk251@gmail.com", "#123456");
        assertThrows("email not exist", IllegalArgumentException.class, () -> b1.verifyAccount("htsondk251@gmail.com", "#123456#"));
        assertThrows("password not correct", IllegalArgumentException.class, () -> b1.verifyAccount("htsondk251@gmail.co", "#123456"));
    }

    @Test
    public void testGetAccount() {
        assertEquals(b1.getAccountFromEmail("htsondk251@gmail.com").getAccountNumber(), 3805449019L);
        assertEquals(b1.getAccountFromEmail("htsondk251@gmail.co") == null, true);
    }

    @Test
    public void testLogIn() {
        assertTrue(b1.logIn("htsondk251@gmail.com", "#123456"));
        assertFalse(b1.logIn(fakeEmail, "#123456"));
    }

    @Test
    public void testTransferMoney() {
        assertThrows("not enough money", IllegalArgumentException.class, () -> b1.transferMoney(b1.getAccounts().get(0).getAccountNumber(), b1.getAccounts().get(1).getAccountNumber(), -60.0));
        assertThrows("false destination account number", IllegalArgumentException.class, () -> b1.transferMoney(b1.getAccounts().get(0).getAccountNumber(), fakeAccountNumber, 60.0));
        assertThrows(IllegalArgumentException.class, () -> b1.transferMoney(b1.getAccounts().get(0).getAccountNumber(), b1.getAccounts().get(1).getAccountNumber(), -60.0));
        assertTrue(b1.transferMoney(b1.getAccounts().get(0).getAccountNumber(), b1.getAccounts().get(1).getAccountNumber(), 30.0));
        assertFalse(b1.transferMoney(b1.getAccounts().get(0).getAccountNumber(), b1.getAccounts().get(1).getAccountNumber(), 30.0));
    }
}
