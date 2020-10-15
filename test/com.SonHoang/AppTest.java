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
//    b1.setAccounts(accounts);

    @Before
    public void setUp() throws Exception {
        Customer c1 = new Customer("172645991", "Son", "Hoang Thanh", "16/06/1988", "HBT, HN", "htsondk251@gmail.com", "0984964197");
        customers.add(c1);
        Customer c2 = new Customer("172645910", "Dzung", "Hoang Thi", "16/06/1986", "HBT, HN", "dzunghoang.n21@gmail.com", "0919581286");
        customers.add(c2);
        Customer c3 = new Customer("172645912", "Hanh", "Tran Minh", "16/06/1997", "HBT, HN", "bighero6@gmail.com", "0344592834");
        customers.add(c3);
        Customer c4 = new Customer("172645913", "Minh", "Tran Nguyet", "16/06/1993", "HBT, HN", "nguyetminhbs93@gmail.com", "0335198726");
        customers.add(c4);

        Account a1 = new Account(3805449019l, c1.getId());
        accounts.add(a1);
        Account a2 = new Account(3805449019l, c2.getId());
        accounts.add(a2);
        Account a3 = new Account(3805449019l, c3.getId());
        accounts.add(a3);
        Account a4 = new Account(3805449019l, c4.getId());
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
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(50.0, 0.0));
        b1.getAccounts().get(0).addValue(0.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(50.0, 0.0));
        b1.getAccounts().get(0).addValue(60.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(110.0, 0.0));
    }


    @Test
    public void testSubValue() {
        b1.getAccounts().get(0).setBalance(50.0);
        b1.getAccounts().get(0).subValue(40.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(10.0, 0.0));
        b1.getAccounts().get(0).subValue(10.0);
        assertThat(b1.getAccounts().get(0).getBalance(), closeTo(0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> {b1.getAccounts().get(0).subValue(10.0);});

    }


    @Test
    public void testAddMonthlyInterest() {
        assertThat(b1.getAccounts().get(1).getBalance(), closeTo(0.0, 0.0));
        b1.getAccounts().get(1).addValue(100);
        b1.getAccounts().get(1).addMonthlyInterest();
        assertThat(b1.getAccounts().get(1).getBalance(), closeTo(100.0, 0.084));
        b1.getAccounts().get(1).addValue(-110);
        b1.getAccounts().get(1).addMonthlyInterest();
        assertThat(b1.getAccounts().get(1).getBalance(), closeTo(100.0, 0.084));
    }

    @Test
    public void testCheckValidity() {
        b1.checkValidity("htsondk251@gmail.com", "#123456");
        assertThrows("email not exist", IllegalArgumentException.class, () -> b1.checkValidity("htsondk251@gmail.com", "#123456#"));
        assertThrows("password not correct", IllegalArgumentException.class, () -> b1.checkValidity("htsondk251@gmail.co", "#123456"));
    }
}
