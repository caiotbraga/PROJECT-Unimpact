-- Endereços
INSERT INTO public.address (id, cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 1, '50050900', 'Recife', 'Brasil', 'Boa Vista', '526', 'Pernambuco', 'Rua do Principe') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.address
(id, cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 2, '51230021', 'Recife', 'Brasil', 'Boa Vista', '322', 'PE', 'Rua do principe') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 2
    ) LIMIT 1;

INSERT INTO public.address
(id, cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 3, '51230021', 'Recife', 'Brasil', 'Boa Viagem', '323', 'PE', 'Rua da pipoca') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 3
    ) LIMIT 1;

INSERT INTO public.address
(id,  cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 4, '51230021', 'Recife', 'Brasil', 'Boa Viagem', '223', 'PE', 'Rua do doce') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 4
    ) LIMIT 1;

INSERT INTO public.address
(id, cep, city, country, district, "number", state, street)
SELECT * FROM (SELECT 5, '51230022', 'Recife', 'Brasil', 'Boa Viajem', '222', 'PE', 'Rua do salgado') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.address WHERE id = 5
    ) LIMIT 1;

-- Telefones
INSERT INTO public.phone (id, "number")
SELECT * FROM (SELECT 1, '(81)2119-4000') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.phone WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.phone
(id, "number")
SELECT * FROM (SELECT 2, '81986325545') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.phone WHERE id = 2
    ) LIMIT 1;

-- Usuários
INSERT INTO public.university (id, email, name, "password", cnpj)
SELECT * FROM (SELECT 1, 'admin.unimpact@unicap.br', 'Universidade Católica de Pernambuco', '$2a$10$/BMaeYIUNRctNWc1rh1LBujHzzwL9nohNz2mB3VzYOappnQrSyxC2', '10847721000195') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.university WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.juridical_person
(id, email, name, "password", cnpj, fantasy_name, institution_type)
SELECT * FROM (SELECT 1, 'empresa@gmail.com', 'Empresa Teste', '$2a$10$tGGzvTZJeTNU/LsES5psZONo53gYhu9jWbQrcW317yvI2Oy1xlH2e', '90.041.682/0001-73', 'Empresa', 'EMPRESA_PRIVADA') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.juridical_person WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.physical_person
(id, email, "name", "password", cpf, rg)
SELECT * FROM (SELECT 1, 'solicitante@gmail.com', 'Solicitante', '$2a$10$5W1/CDeA0hnC32gDeRuC2OuyAyD3FKeafeDSMsBKC2f4TfM6yu6ra', '304.903.010-02', '10042219') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.physical_person WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.physical_person
(id, email, "name", "password", cpf, rg)
SELECT * FROM (SELECT 2, 'intermediario@gmail.com', 'Intermediario', '$2a$10$5W1/CDeA0hnC32gDeRuC2OuyAyD3FKeafeDSMsBKC2f4TfM6yu6ra', '843.057.140-00', '10042212') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.physical_person WHERE id = 2
    ) LIMIT 1;

INSERT INTO public.physical_person
(id, email, "name", "password", cpf, rg)
SELECT * FROM (SELECT 3, 'executor@gmail.com', 'Excutor', '$2a$10$5W1/CDeA0hnC32gDeRuC2OuyAyD3FKeafeDSMsBKC2f4TfM6yu6ra', '573.629.490-73', '10042211') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public.physical_person WHERE id = 3
    ) LIMIT 1;

-- Usuários e Endereços
INSERT INTO public.university_addresses (university_id, addresses_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_addresses WHERE university_id = 1
    ) LIMIT 1;

INSERT INTO public.juridical_person_addresses
(juridical_person_id, addresses_id)
SELECT * FROM (SELECT 1, 2) AS tmp
WHERE NOT EXISTS (
        SELECT juridical_person_id FROM public.juridical_person_addresses WHERE juridical_person_id = 1
    ) LIMIT 1;

INSERT INTO public.physical_person_addresses
(physical_person_id, addresses_id)
SELECT * FROM (SELECT 1, 3) AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_addresses WHERE physical_person_id = 1
    ) LIMIT 1;

INSERT INTO public.physical_person_addresses
(physical_person_id, addresses_id)
SELECT * FROM (SELECT 2, 4) AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_addresses WHERE physical_person_id = 2
    ) LIMIT 1;

INSERT INTO public.physical_person_addresses
(physical_person_id, addresses_id)
SELECT * FROM (SELECT 3, 5) AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_addresses WHERE physical_person_id = 3
    ) LIMIT 1;

-- Usuários e Telefones
INSERT INTO public.university_phones (university_id, phones_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_phones WHERE university_id = 1
    ) LIMIT 1;

INSERT INTO public.juridical_person_phones
(juridical_person_id, phones_id)
SELECT * FROM (SELECT 1, 2) AS tmp
WHERE NOT EXISTS (
        SELECT juridical_person_id FROM public.juridical_person_phones WHERE juridical_person_id = 1
    ) LIMIT 1;

-- Usuários e funções
INSERT INTO public.university_functions (university_id, "functions")
SELECT * FROM (SELECT 1, 'ROLE_MIDDLE_MANAGER') AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_functions WHERE university_id = 1
    ) LIMIT 1;

INSERT INTO public.juridical_person_functions
(juridical_person_id, "functions")
SELECT * FROM (SELECT 1, 'ROLE_REQUESTING_MANAGER') AS tmp
WHERE NOT EXISTS (
        SELECT juridical_person_id FROM public.juridical_person_functions WHERE juridical_person_id = 1
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 1, 'ROLE_REQUESTING_SIMPLE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 1
                                                                          AND functions = 'ROLE_REQUESTING_SIMPLE'
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 1, 'ROLE_REQUESTING_EMPLOYEE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 1
                                                                          AND functions = 'ROLE_REQUESTING_EMPLOYEE'
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 2, 'ROLE_REQUESTING_SIMPLE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 2
                                                                          AND functions = 'ROLE_REQUESTING_SIMPLE'
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 2, 'ROLE_MIDDLE_EMPLOYEE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 2
                                                                          AND functions = 'ROLE_MIDDLE_EMPLOYEE'
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 3, 'ROLE_REQUESTING_SIMPLE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 3
                                                                          AND functions = 'ROLE_REQUESTING_SIMPLE'
    ) LIMIT 1;

INSERT INTO public.physical_person_functions
(physical_person_id, "functions")
SELECT * FROM (SELECT 3, 'ROLE_EXECUTING_EMPLOYEE') AS tmp
WHERE NOT EXISTS (
        SELECT physical_person_id FROM public.physical_person_functions WHERE physical_person_id = 3
                                                                          AND functions = 'ROLE_EXECUTING_EMPLOYEE'
    ) LIMIT 1;

-- Grupos
INSERT INTO public."groups"
(id, description, "name", owner_user_email)
SELECT * FROM (SELECT 1, 'Escola de computação', 'ICAM-TECH', 'admin.unimpact@unicap.br') AS tmp
WHERE NOT EXISTS (
        SELECT id FROM public."groups" WHERE id = 1
    ) LIMIT 1;

INSERT INTO public.group_areas_of_operation
(group_id, areas_of_operation)
SELECT * FROM (SELECT 1, 'Computação') AS tmp
WHERE NOT EXISTS (
        SELECT group_id FROM public.group_areas_of_operation WHERE group_id = 1
    ) LIMIT 1;

-- Membros do grupos
INSERT INTO public.groups_members
(group_id, members_id)
SELECT * FROM (SELECT 1, 3) AS tmp
WHERE NOT EXISTS (
        SELECT group_id FROM public.groups_members WHERE group_id = 1 AND members_id = 3
    ) LIMIT 1;

-- Representantes
INSERT INTO public.juridical_person_representatives
(juridical_person_id, representatives_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
        SELECT juridical_person_id FROM public.juridical_person_representatives WHERE juridical_person_id = 1 AND representatives_id = 1
    ) LIMIT 1;

INSERT INTO public.university_representatives
(university_id, representatives_id)
SELECT * FROM (SELECT 1, 2) AS tmp
WHERE NOT EXISTS (
        SELECT university_id FROM public.university_representatives WHERE university_id = 1 AND representatives_id = 2
    ) LIMIT 1;

-- Atualização de sequencia de ids
SELECT setval('address_id_seq', (SELECT MAX(id) from public.address));
SELECT setval('phone_id_seq', (SELECT MAX(id) from public.phone));
SELECT setval('physical_person_id_seq', (SELECT MAX(id) from public.physical_person));
SELECT setval('juridical_person_id_seq', (SELECT MAX(id) from public.juridical_person));
SELECT setval('groups_id_seq', (SELECT MAX(id) from public."groups"));
