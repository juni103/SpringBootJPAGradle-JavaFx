CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `address` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Head & Shoulder', '00000000', 'USA');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Loreal', '2222222', 'France');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Garnier', '3222233', 'France');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Set Wet', '44444444', 'India');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Layer', '66666666', 'India');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Brylcreem', '777777', 'UK');
INSERT INTO `inventory`.`suppliers` (`name`, `phone`, `address`) VALUES ('Gatsby', '8888888', 'Canada');
