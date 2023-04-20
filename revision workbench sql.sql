select * from rvsp;

INSERT INTO `railway`.`rvsp`
(`name`,
`email`,
`phone`,
`confirmation_date`,
`comments`,
`food_type`)
VALUES
("Will","will@gmail.com","23568974","2023-02-07","testing","V"),
("Bray","bray@gmail.com","23568974","2023-01-05","testing","NV");

-- empty table
truncate rvsp; 