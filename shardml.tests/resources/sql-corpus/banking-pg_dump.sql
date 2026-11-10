/*
 * examples/banking.shardml as deployed by bench/integration/test-examples.sh (schema `banking`,
 * tables distributed), then `pg_dump --schema-only -n banking` 17.10 on the coordinator
 * (citusdata/citus:14.2.0-pg17).
 */

--
-- PostgreSQL database dump
--

\restrict CWSm6GyDPabTLgQXdsyJy31uBJKeibk5FWCGjq8yj1MaFQNBM8iySXEQ3iL5jMt

-- Dumped from database version 17.10 (Debian 17.10-1.pgdg13+1)
-- Dumped by pg_dump version 17.10 (Debian 17.10-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: banking; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA banking;


ALTER SCHEMA banking OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: banking; Owner: postgres
--

CREATE TABLE banking.accounts (
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    balance numeric,
    created_at timestamp without time zone
);


ALTER TABLE banking.accounts OWNER TO postgres;

--
-- Name: cards; Type: TABLE; Schema: banking; Owner: postgres
--

CREATE TABLE banking.cards (
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    card_number character varying(255),
    expires_at timestamp without time zone
);


ALTER TABLE banking.cards OWNER TO postgres;

--
-- Name: customers; Type: TABLE; Schema: banking; Owner: postgres
--

CREATE TABLE banking.customers (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255),
    created_at timestamp without time zone
);


ALTER TABLE banking.customers OWNER TO postgres;

--
-- Name: transactions; Type: TABLE; Schema: banking; Owner: postgres
--

CREATE TABLE banking.transactions (
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    account_id bigint NOT NULL,
    amount numeric NOT NULL,
    created_at timestamp without time zone
);


ALTER TABLE banking.transactions OWNER TO postgres;

--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: banking; Owner: postgres
--

ALTER TABLE ONLY banking.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (customer_id, id);


--
-- Name: cards cards_pkey; Type: CONSTRAINT; Schema: banking; Owner: postgres
--

ALTER TABLE ONLY banking.cards
    ADD CONSTRAINT cards_pkey PRIMARY KEY (customer_id, id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: banking; Owner: postgres
--

ALTER TABLE ONLY banking.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: banking; Owner: postgres
--

ALTER TABLE ONLY banking.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (customer_id, id);


--
-- PostgreSQL database dump complete
--

\unrestrict CWSm6GyDPabTLgQXdsyJy31uBJKeibk5FWCGjq8yj1MaFQNBM8iySXEQ3iL5jMt

