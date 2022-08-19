package com.project.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.project.model.Account;
import com.project.model.Transaction;
import com.project.model.User;

@Component
public class Dao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int createAccount(Account account) {
		Random r = new Random(System.currentTimeMillis());
		long accNo = 10000 + r.nextInt(20000);
		account.setAccountNo(String.valueOf(accNo));
		int rowsInserted = 0;
		String sql = "INSERT INTO account(`accountNo`, `accountType`, `accountBalance`, `username`) "
				+ "VALUES (? , ? , ? , ?)";

		try {

			rowsInserted = jdbcTemplate.update(sql, account.getAccountNo(), account.getAccountType(),
					account.getAccountBalance(), account.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowsInserted;
	}

	public int register(User user) {
		int rowsInserted = 0;
		String sql = "INSERT INTO user(`username`, `password`, `fname`, "
				+ "`address`, `email`, `phone`, `lname`, `sinNumber`, `role`) " + "VALUES (?,?,?,?, ?,?,?,?, ?)";

		try {
			rowsInserted = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFname(),
					user.getAddress(), user.getEmail(), user.getPhone(), user.getLname(), user.getSinNumber(),
					user.getRole());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowsInserted;
	}

	public Map<String, String> login(String username, String password) {

		Map<String, String> res = new HashMap<>();
		String sql = "SELECT * FROM user where username =? and password =?";

		try {

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { username, password });

			if (rows.size() > 0) {
				res.put("isRegistered", "true");
				if (username.equals("admin")) {
					res.put("isAdmin", "true");
				} else {
					res.put("isAdmin", "false");
				}
			} else {
				res.put("isRegistered", "false");
				res.put("isAdmin", "false");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public Map<String, ArrayList<String>> getAllUserDetails() {

		Map<String, ArrayList<String>> accMap = new HashMap<>();
		Map<String, ArrayList<String>> userAccMap = new HashMap<>();

		String sql1 = "SELECT username,fname,lname FROM user where role= 'user'";
		String sql2 = "SELECT username, accountNo, accountType FROM account";

		try {

			List<Map<String, Object>> resultSet1 = jdbcTemplate.queryForList(sql1);
			List<Map<String, Object>> resultSet2 = jdbcTemplate.queryForList(sql2);

			for (Map rs2 : resultSet2) {

				if (!(accMap.containsKey(rs2.get("username").toString()))) {

					ArrayList<String> list = new ArrayList<>();
					list.add(rs2.get("accountType").toString());
					accMap.put(rs2.get("username").toString(), list);

				} else {
					ArrayList<String> list = accMap.get(rs2.get("username").toString());
					list.add(rs2.get("accountType").toString());
					accMap.put(rs2.get("username").toString(), list);
				}
			}
			for (Map rs1 : resultSet1) {
				if (!(userAccMap.containsKey(rs1.get("username").toString()))) {
					ArrayList<String> accList = accMap.get(rs1.get("username").toString());

					if (accList == null) {
						accList = new ArrayList<>();
					}

					userAccMap.put(rs1.get("username").toString(), accList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userAccMap;
	}

	public Map<String, String> getUserDetails(String username) {
		Map<String, String> userDetails = new HashMap<>();
		String sql = "SELECT username, fname, lname, address, email, phone, sinNumber, role FROM user WHERE username=?";

		try {

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { username });

			for (Map row : rows) {
				userDetails.put("username", row.get("username").toString());
				userDetails.put("fname", row.get("fname").toString());
				userDetails.put("lname", row.get("lname").toString());
				userDetails.put("address", row.get("address").toString());
				userDetails.put("email", row.get("email").toString());
				userDetails.put("phone", row.get("phone").toString());
				userDetails.put("sinNumber", row.get("sinNumber").toString());
				userDetails.put("role", row.get("role").toString());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return userDetails;
	}

	@SuppressWarnings("deprecation")
	public List<Account> getAccountDetails(String username) {
		List<Account> accArr = null;
		String sql = "SELECT `accountNo`, `accountType`, `accountBalance`, `username` FROM `account` WHERE username = ?";

		try {

			accArr = jdbcTemplate.query(sql, new Object[] { username },
					new BeanPropertyRowMapper<>(Account.class));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return accArr;
	}

	public int depositMoney(String accountNo, String amount) {
		String sql = "UPDATE `account` SET `accountBalance`= ? WHERE `accountNo`=?";
		int rowsAffected = 0;
		try {

			rowsAffected = jdbcTemplate.update(sql, new Object[] { amount, accountNo });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rowsAffected;
	}

	@SuppressWarnings("deprecation")
	public Account getAccount(String accountNo) {
		String sql = "SELECT `username`, `accountNo`, `accountType`, `accountBalance` FROM `account` WHERE `accountNo`=?";
		Account acc = null;
		try {
			acc = jdbcTemplate.queryForObject(sql, new Object[] { accountNo },
					new BeanPropertyRowMapper<>(Account.class));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return acc;
	}

	public int checkAccountNo(String accNo) {
		String sql = "SELECT `accountNo` FROM `account` WHERE accountNo = ?";
		int count = 0;

		try {

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { accNo });
			for (Map row : rows) {
				count++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return count;

	}

	public int insertTransaction(Map<String, String> row) {
		String sql = "INSERT INTO `transaction`(`transactionId`, `fromAccount`, `toAccount`, `status`, `Amount`, `Date`, `username`) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?)";
		int insertCount = 0;
		try {
			String uid = UUID.randomUUID().toString();

			insertCount = jdbcTemplate.update(sql, uid, row.get("fromAcc"), row.get("toAcc"), row.get("status"),
					row.get("amount"), row.get("date"), row.get("username"));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return insertCount;
	}

	public String getUnameForAcc(String acc) {
		String sql = "SELECT `username` FROM `account` WHERE accountNo = ?";
		String username = null;
		try {
			username = jdbcTemplate.queryForObject(sql, new Object[] { acc }, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return username;
	}

	@SuppressWarnings("deprecation")
	public List<Transaction> getTransactionForUser(String username) {
		String sql = "SELECT `transactionId`, `fromAccount`, `toAccount`, `status`, "
				+ "`Amount`, `Date`, `username` FROM `transaction` WHERE username = ?";

		List<Transaction> transactionList = null;
		try {

			transactionList = jdbcTemplate.query(sql, new Object[] { username },
					new BeanPropertyRowMapper<>(Transaction.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return transactionList;
	}

}
