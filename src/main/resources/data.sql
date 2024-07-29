-- Initial dummy data --
INSERT INTO employee (id, name) VALUES ('klm100000', 'User 1');
INSERT INTO employee (id, name) VALUES ('klm100001', 'User 2');
INSERT INTO holiday (id, holiday_label, employee_id, start_of_holiday, end_of_holiday, status)
VALUES ('938e6ddd-b055-48e3-ba10-bf346147ba5a', 'Holiday to Bali', 'klm100000', '2024-05-01', '2024-06-01', 3);
INSERT INTO holiday (id, holiday_label, employee_id, start_of_holiday, end_of_holiday, status)
VALUES ('3521a658-e716-4aed-94ea-83057e36416a', 'Paternity leave', 'klm100000', '2024-09-05', '2024-09-30', 2);
INSERT INTO holiday (id, holiday_label, employee_id, start_of_holiday, end_of_holiday, status)
VALUES ('6b4ed047-9e99-4969-ade0-bc7d7263b8ee', 'Travel around the world', 'klm100000', '2024-08-01', '2024-09-01', 1);
INSERT INTO holiday (id, holiday_label, employee_id, start_of_holiday, end_of_holiday, status)
VALUES ('dfe964ff-7385-454e-8da1-69b2195bd3ce', 'Sabbatical', 'klm100001', '2024-11-01', '2024-12-01', 0);