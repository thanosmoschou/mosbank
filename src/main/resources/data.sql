use mosbank;

-- Fake data for my db
insert into users values (1, "thanos", "moschou", "than@example.com", "6944444444"),
                         (3, "Makis", "Kotsampasis", "makis@makis.com", "2310123654");
                         --(2, "John", "Doe", "john@john.com", "6977777777"),

insert into cards values ("5011054488597827", "4523", "08/24", "503", 1),
                         ("7958110142072234", "6985", "12/29", "777", 3);
                         --("3058860736529886", "4789", "11/30", "621", 2),

insert into credentials values ("thanos", "changeme", 1),
                               ("makis", "zzXX11@@", 3);
                               --("johnjohn", "aaSS11@@", 2),

insert into ibans values ("GR815495047604432456", 1),
                         ("GR916728324431088128", 3);
                         --("GR815495047604432968", 2),

insert into bankaccounts values (10, 1000, 1),
                                (30, 30000, 3);
                                --(20, 3000, 2),

insert into transactions values (1, "30/01/2024", "GR916728324431088128", 1000, "Receive Payroll");