



INSERT INTO `user` (`username`, `password`, `fname`, `address`, `email`, `phone`, `lname`, `sinNumber`, `role`) VALUES ('ElectricityBill', 'qwerty', 'Ontario Power', '67 Qwerty Road', 'op@op.com', '987-456-3444', 'EB', '000-000-000', 'provider');
INSERT INTO `user` (`username`, `password`, `fname`, `address`, `email`, `phone`, `lname`, `sinNumber`, `role`) VALUES ('Hydro', 'qwerty', 'Hydro', '123 Canada ', 'hydro@hy.com', '867-446-456', 'Canada', '000-000-000', 'provider');
INSERT INTO `user` (`username`, `password`, `fname`, `address`, `email`, `phone`, `lname`, `sinNumber`, `role`) VALUES ('Internet ', 'qwerty', 'Fiko', '45 Some Rd', 'net@ex.com', '456-345-444', 'dodo', '000-000-000', 'provider');

INSERT INTO `account` (`accountNo`, `accountType`, `accountBalance`, `username`) VALUES ('10000', 'current', '100', 'ElectricityBill');
INSERT INTO `account` (`accountNo`, `accountType`, `accountBalance`, `username`) VALUES ('10001', 'current', '100', 'Hydro');
INSERT INTO `account` (`accountNo`, `accountType`, `accountBalance`, `username`) VALUES ('10002', 'current', '100', 'Internet');
