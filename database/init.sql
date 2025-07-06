--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-05 19:31:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 24713)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'public'
    ) THEN
        EXECUTE 'CREATE SCHEMA public';
END IF;
END
$$;


DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_namespace
        WHERE nspname = 'public'
    ) THEN
        EXECUTE 'ALTER SCHEMA public OWNER TO postgres';
END IF;
END
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 24775)
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
                                  guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                  user_guid uuid NOT NULL
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 24721)
-- Name: food_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.food_types (
                                   guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                   description character varying(255) NOT NULL
);


ALTER TABLE public.food_types OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24757)
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
                              guid uuid DEFAULT gen_random_uuid() NOT NULL,
                              name character varying(255) NOT NULL,
                              price numeric(38,2) NOT NULL,
                              stock_quantity integer NOT NULL,
                              restaurant_guid uuid NOT NULL,
                              food_type_guid uuid NOT NULL,
                              created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                              updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.items OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24826)
-- Name: order_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items (
                                    guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                    order_guid uuid NOT NULL,
                                    item_guid uuid NOT NULL,
                                    quantity integer NOT NULL,
                                    price numeric(38,2),
                                    createdat timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.order_items OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24842)
-- Name: order_status_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_status_logs (
                                          guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                          order_guid uuid NOT NULL,
                                          old_status character varying(255),
                                          new_status character varying(255) NOT NULL,
                                          changed_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.order_status_logs OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24807)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
                               guid uuid DEFAULT gen_random_uuid() NOT NULL,
                               customer_guid uuid NOT NULL,
                               restaurant_guid uuid NOT NULL,
                               payment_type_guid uuid NOT NULL,
                               status character varying(255) DEFAULT 'PENDING'::character varying NOT NULL,
                               payment_status character varying(255) DEFAULT 'AWAITING_PAYMENT'::character varying NOT NULL,
                               created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 24854)
-- Name: payment_status_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payment_status_logs (
                                            guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                            order_guid uuid NOT NULL,
                                            old_payment_status character varying(255),
                                            new_payment_status character varying(255) NOT NULL,
                                            changeant timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                            changed_at timestamp(6) without time zone
);


ALTER TABLE public.payment_status_logs OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24715)
-- Name: payment_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payment_types (
                                      guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                      description character varying(255) NOT NULL
);


ALTER TABLE public.payment_types OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24786)
-- Name: restaurant_staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant_staff (
                                         guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                         role_guid uuid NOT NULL,
                                         user_guid uuid NOT NULL,
                                         restaurant_guid uuid NOT NULL
);


ALTER TABLE public.restaurant_staff OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24747)
-- Name: restaurants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurants (
                                    guid uuid DEFAULT gen_random_uuid() NOT NULL,
                                    name character varying(255) NOT NULL,
                                    cnpj character varying(255) NOT NULL,
                                    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.restaurants OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24727)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
                              guid uuid DEFAULT gen_random_uuid() NOT NULL,
                              description character varying(255) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24733)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              guid uuid DEFAULT gen_random_uuid() NOT NULL,
                              name character varying(255) NOT NULL,
                              email character varying(255) NOT NULL,
                              cpf character varying(255) NOT NULL,
                              created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                              updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3287 (class 2606 OID 24780)
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (guid);


--
-- TOC entry 3273 (class 2606 OID 24726)
-- Name: food_types food_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_types
    ADD CONSTRAINT food_types_pkey PRIMARY KEY (guid);


--
-- TOC entry 3285 (class 2606 OID 24764)
-- Name: items items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (guid);


--
-- TOC entry 3293 (class 2606 OID 24831)
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (guid);


--
-- TOC entry 3295 (class 2606 OID 24848)
-- Name: order_status_logs order_status_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_status_logs
    ADD CONSTRAINT order_status_logs_pkey PRIMARY KEY (guid);


--
-- TOC entry 3291 (class 2606 OID 24815)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (guid);


--
-- TOC entry 3297 (class 2606 OID 24860)
-- Name: payment_status_logs payment_status_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_status_logs
    ADD CONSTRAINT payment_status_logs_pkey PRIMARY KEY (guid);


--
-- TOC entry 3271 (class 2606 OID 24720)
-- Name: payment_types payment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_types
    ADD CONSTRAINT payment_types_pkey PRIMARY KEY (guid);


--
-- TOC entry 3289 (class 2606 OID 24791)
-- Name: restaurant_staff restaurant_staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_staff
    ADD CONSTRAINT restaurant_staff_pkey PRIMARY KEY (guid);


--
-- TOC entry 3283 (class 2606 OID 24756)
-- Name: restaurants restaurants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurants
    ADD CONSTRAINT restaurants_pkey PRIMARY KEY (guid);


--
-- TOC entry 3275 (class 2606 OID 24732)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (guid);


--
-- TOC entry 3277 (class 2606 OID 24746)
-- Name: users users_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_cpf_key UNIQUE (cpf);


--
-- TOC entry 3279 (class 2606 OID 24744)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 3281 (class 2606 OID 24742)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (guid);


--
-- TOC entry 3304 (class 2606 OID 24874)
-- Name: orders fk7rfj2iigp0o4samtyoajid7hw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk7rfj2iigp0o4samtyoajid7hw FOREIGN KEY (restaurant_guid) REFERENCES public.restaurants(guid);


--
-- TOC entry 3300 (class 2606 OID 24781)
-- Name: customers fk_customer_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT fk_customer_user FOREIGN KEY (user_guid) REFERENCES public.users(guid);


--
-- TOC entry 3298 (class 2606 OID 24770)
-- Name: items fk_item_foodtype; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk_item_foodtype FOREIGN KEY (food_type_guid) REFERENCES public.food_types(guid);


--
-- TOC entry 3299 (class 2606 OID 24765)
-- Name: items fk_item_restaurant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk_item_restaurant FOREIGN KEY (restaurant_guid) REFERENCES public.restaurants(guid);


--
-- TOC entry 3305 (class 2606 OID 24816)
-- Name: orders fk_order_customer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_order_customer FOREIGN KEY (customer_guid) REFERENCES public.customers(guid);


--
-- TOC entry 3307 (class 2606 OID 24837)
-- Name: order_items fk_order_item_item; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fk_order_item_item FOREIGN KEY (item_guid) REFERENCES public.items(guid);


--
-- TOC entry 3308 (class 2606 OID 24832)
-- Name: order_items fk_order_item_order; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fk_order_item_order FOREIGN KEY (order_guid) REFERENCES public.orders(guid);


--
-- TOC entry 3306 (class 2606 OID 24821)
-- Name: orders fk_order_payment_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_order_payment_type FOREIGN KEY (payment_type_guid) REFERENCES public.payment_types(guid);


--
-- TOC entry 3301 (class 2606 OID 24797)
-- Name: restaurant_staff fk_staff_restaurant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_staff
    ADD CONSTRAINT fk_staff_restaurant FOREIGN KEY (restaurant_guid) REFERENCES public.restaurants(guid);


--
-- TOC entry 3302 (class 2606 OID 24802)
-- Name: restaurant_staff fk_staff_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_staff
    ADD CONSTRAINT fk_staff_role FOREIGN KEY (role_guid) REFERENCES public.roles(guid);


--
-- TOC entry 3303 (class 2606 OID 24792)
-- Name: restaurant_staff fk_staff_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_staff
    ADD CONSTRAINT fk_staff_user FOREIGN KEY (user_guid) REFERENCES public.users(guid);


--
-- TOC entry 3309 (class 2606 OID 24849)
-- Name: order_status_logs order_status_logs_order_guid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_status_logs
    ADD CONSTRAINT order_status_logs_order_guid_fkey FOREIGN KEY (order_guid) REFERENCES public.orders(guid);


--
-- TOC entry 3310 (class 2606 OID 24861)
-- Name: payment_status_logs payment_status_logs_order_guid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_status_logs
    ADD CONSTRAINT payment_status_logs_order_guid_fkey FOREIGN KEY (order_guid) REFERENCES public.orders(guid);


--
-- TOC entry 3459 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2025-07-05 19:31:09

--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 17.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (guid, name, email, cpf, created_at, updated_at) VALUES ('af9a9bbb-ea8b-4938-be73-64ebf8d84a9e', 'leticia', 'leticia@example.com', '12345678900', '2025-07-05 21:57:29.232865', '2025-07-05 21:57:29.232865');


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customers (guid, user_guid) VALUES ('b29c3d4f-77e0-4b41-9e7d-3c1a1f6de1ef', 'af9a9bbb-ea8b-4938-be73-64ebf8d84a9e');


--
-- Data for Name: food_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.food_types (guid, description) VALUES ('8926cdfe-3462-4cd9-a89e-b3652ce15afd', 'JAPANESE');
INSERT INTO public.food_types (guid, description) VALUES ('d26416f3-d42c-471a-8608-aafcc94c0066', 'ITALIAN');
INSERT INTO public.food_types (guid, description) VALUES ('f3c37358-1bea-496f-9aae-f433aa5c939e', 'BURGUER');
INSERT INTO public.food_types (guid, description) VALUES ('24e6b64e-1644-42a5-9d07-729d17e4b8ca', 'DESSERT');


--
-- Data for Name: restaurants; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.restaurants (guid, name, cnpj, created_at, updated_at) VALUES ('2c5565c5-2f0a-49ab-abf8-294ae4a552bc', 'Sushi House', '12.345.678/0001-90', '2025-07-05 22:34:14.211259', '2025-07-05 22:34:14.211259');
INSERT INTO public.restaurants (guid, name, cnpj, created_at, updated_at) VALUES ('33fa4d6d-d3e5-43f5-9273-cb67f8077ea3', 'Bella Italia', '98.765.432/0001-21', '2025-07-05 22:34:14.211259', '2025-07-05 22:34:14.211259');
INSERT INTO public.restaurants (guid, name, cnpj, created_at, updated_at) VALUES ('c8d47244-d2a7-4c49-a966-ce3b3fbf43fb', 'Burger Point', '11.222.333/0001-44', '2025-07-05 22:34:14.211259', '2025-07-05 22:34:14.211259');
INSERT INTO public.restaurants (guid, name, cnpj, created_at, updated_at) VALUES ('2178d361-42c9-4bb5-a815-607fa2b859e8', 'Sweet Tooth', '44.555.666/0001-77', '2025-07-05 22:34:14.211259', '2025-07-05 22:34:14.211259');


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('e8a70461-b9c6-483e-96fd-47417dd50258', 'Sushi Combo', 55.00, 20, '2c5565c5-2f0a-49ab-abf8-294ae4a552bc', '8926cdfe-3462-4cd9-a89e-b3652ce15afd', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('c87cca0f-7288-4a99-afa7-94819db93210', 'Tempura', 30.00, 15, '2c5565c5-2f0a-49ab-abf8-294ae4a552bc', '8926cdfe-3462-4cd9-a89e-b3652ce15afd', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('6b9d5343-22c3-4ad5-a1d3-1df9a5190f7a', 'Pizza Margherita', 45.00, 25, '33fa4d6d-d3e5-43f5-9273-cb67f8077ea3', 'd26416f3-d42c-471a-8608-aafcc94c0066', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('0e29e84b-e220-4f87-a4f3-e5cd84d09ee7', 'Spaghetti Carbonara', 38.50, 18, '33fa4d6d-d3e5-43f5-9273-cb67f8077ea3', 'd26416f3-d42c-471a-8608-aafcc94c0066', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('9e047e88-cd40-4183-b0a6-924232f361a9', 'Classic Cheeseburger', 28.00, 30, 'c8d47244-d2a7-4c49-a966-ce3b3fbf43fb', 'f3c37358-1bea-496f-9aae-f433aa5c939e', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('dd26a16b-6f3e-4b02-9c47-b8999e1c2b22', 'Double Bacon Burger', 35.50, 15, 'c8d47244-d2a7-4c49-a966-ce3b3fbf43fb', 'f3c37358-1bea-496f-9aae-f433aa5c939e', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('65f89887-5273-4cd9-b576-0d46ebf3b01a', 'Chocolate Cake', 20.00, 40, '2178d361-42c9-4bb5-a815-607fa2b859e8', '24e6b64e-1644-42a5-9d07-729d17e4b8ca', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');
INSERT INTO public.items (guid, name, price, stock_quantity, restaurant_guid, food_type_guid, created_at, updated_at) VALUES ('9f9d0064-f5aa-4445-a898-955a641d8c83', 'Ice Cream Sundae', 15.00, 50, '2178d361-42c9-4bb5-a815-607fa2b859e8', '24e6b64e-1644-42a5-9d07-729d17e4b8ca', '2025-07-05 22:37:07.43039', '2025-07-05 22:37:07.43039');


--
-- Data for Name: payment_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.payment_types (guid, description) VALUES ('fc071d3d-c523-4f4b-ab28-bd907ae05a78', 'CREDIT_CARD');
INSERT INTO public.payment_types (guid, description) VALUES ('160e72be-ff7e-4ddb-89fa-fb1b4ce5e71c', 'DEBIT_CARD');
INSERT INTO public.payment_types (guid, description) VALUES ('020476ff-faab-4875-ba1b-aab7d457c72a', 'PIX');
INSERT INTO public.payment_types (guid, description) VALUES ('a5d7fcfa-c12a-4d08-96d4-43cea4e2be97', 'CASH');


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: order_status_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: payment_status_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (guid, description) VALUES ('53435a56-6443-4fb0-aa4c-d33036886629', 'OWNER');
INSERT INTO public.roles (guid, description) VALUES ('b2026230-422c-41c0-bd90-28807400d020', 'MANAGER');
INSERT INTO public.roles (guid, description) VALUES ('1670ce41-eb82-47d5-b995-ed74c8461b9a', 'WAITER');


--
-- Data for Name: restaurant_staff; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- PostgreSQL database dump complete
--

