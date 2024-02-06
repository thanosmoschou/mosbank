use mosbank;

-- Fake data for my db
insert into users values (1, "thanos", "moschou", "than@example.com", "6944444444"),
                         (2, "John", "Doe", "john@john.com", "6977777777"),
                         (3, "Makis", "Kotsampasis", "makis@makis.com", "2310123654");

insert into cards values ("5011054488597827", "4523", "08/24", "503", 1),
                         ("3058860736529886", "4789", "11/30", "621", 2),
                         ("7958110142072234", "6985", "12/29", "777", 3);

insert into credentials values ("thanos", "changeme", 1),
                               ("johnjohn", "aaSS11@@", 2),
                               ("makis", "zzXX11@@", 3);

insert into ibans values ("GR815495047604432456", 1),
                         ("GR815495047604432968", 2),
                         ("GR916728324431088128", 3);

insert into bankaccounts values (10, 1000, 1),
                                (20, 3000, 2),
                                (30, 30000, 3);

insert into transactions values (1, "30/01/2024", "GR916728324431088128", 1000, "Receive Payroll");