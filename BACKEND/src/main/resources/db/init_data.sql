INSERT INTO public.university (id, email, name, "password", cnpj)
SELECT * FROM (SELECT 1, 'admin.unimpact@unicap.br', 'Universidade Católica de Pernambuco', '$2a$10$/BMaeYIUNRctNWc1rh1LBujHzzwL9nohNz2mB3VzYOappnQrSyxC2', '10847721000195') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.university WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.address (id, cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 1, '50050900', 'Recife', 'Brasil', 'Boa Vista', '526', 'Pernambuco', 'Rua do Principe') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 1
    ) LIMIT 1;

SELECT setval('address_id_seq', (SELECT MAX(id) from public.address));

INSERT INTO public.phone (id, "number")
SELECT * FROM (SELECT 1, '(81)2119-4000') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.phone WHERE id = 1
    ) LIMIT 1;

SELECT setval('phone_id_seq', (SELECT MAX(id) from public.phone));

INSERT INTO public.university_addresses (university_id, addresses_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_addresses WHERE university_id = 1
    ) LIMIT 1;

INSERT INTO public.university_phones (university_id, phones_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_phones WHERE university_id = 1
    ) LIMIT 1;

INSERT INTO public.university_functions (university_id, "functions")
SELECT * FROM (SELECT 1, 'ROLE_MIDDLE_MANAGER') AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_functions WHERE university_id = 1
    ) LIMIT 1;

