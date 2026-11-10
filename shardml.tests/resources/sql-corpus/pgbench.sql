/*
 * pgbench's schema: `pgbench -i -I dtpf` (tables, primary keys, foreign keys; no data) by
 * pgbench 17.10, then `pg_dump --schema-only` 17.10, on the bench cluster's coordinator
 * (citusdata/citus:14.2.0-pg17). PostgreSQL License.
 */

--
-- PostgreSQL database dump
--

\restrict ZvAYlWTRhxKpa9YLzFcMYgXtFBJX79Ky3ZvWhpmHAyvuoz2lfCfgEfZIdZC7Umb

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: pgbench_accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pgbench_accounts (
    aid integer NOT NULL,
    bid integer,
    abalance integer,
    filler character(84)
)
WITH (fillfactor='100');


ALTER TABLE public.pgbench_accounts OWNER TO postgres;

--
-- Name: pgbench_branches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pgbench_branches (
    bid integer NOT NULL,
    bbalance integer,
    filler character(88)
)
WITH (fillfactor='100');


ALTER TABLE public.pgbench_branches OWNER TO postgres;

--
-- Name: pgbench_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pgbench_history (
    tid integer,
    bid integer,
    aid integer,
    delta integer,
    mtime timestamp without time zone,
    filler character(22)
);


ALTER TABLE public.pgbench_history OWNER TO postgres;

--
-- Name: pgbench_tellers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pgbench_tellers (
    tid integer NOT NULL,
    bid integer,
    tbalance integer,
    filler character(84)
)
WITH (fillfactor='100');


ALTER TABLE public.pgbench_tellers OWNER TO postgres;

--
-- Name: pgbench_accounts pgbench_accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_accounts
    ADD CONSTRAINT pgbench_accounts_pkey PRIMARY KEY (aid);


--
-- Name: pgbench_branches pgbench_branches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_branches
    ADD CONSTRAINT pgbench_branches_pkey PRIMARY KEY (bid);


--
-- Name: pgbench_tellers pgbench_tellers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_tellers
    ADD CONSTRAINT pgbench_tellers_pkey PRIMARY KEY (tid);


--
-- Name: pgbench_accounts pgbench_accounts_bid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_accounts
    ADD CONSTRAINT pgbench_accounts_bid_fkey FOREIGN KEY (bid) REFERENCES public.pgbench_branches(bid);


--
-- Name: pgbench_history pgbench_history_aid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_history
    ADD CONSTRAINT pgbench_history_aid_fkey FOREIGN KEY (aid) REFERENCES public.pgbench_accounts(aid);


--
-- Name: pgbench_history pgbench_history_bid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_history
    ADD CONSTRAINT pgbench_history_bid_fkey FOREIGN KEY (bid) REFERENCES public.pgbench_branches(bid);


--
-- Name: pgbench_history pgbench_history_tid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_history
    ADD CONSTRAINT pgbench_history_tid_fkey FOREIGN KEY (tid) REFERENCES public.pgbench_tellers(tid);


--
-- Name: pgbench_tellers pgbench_tellers_bid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pgbench_tellers
    ADD CONSTRAINT pgbench_tellers_bid_fkey FOREIGN KEY (bid) REFERENCES public.pgbench_branches(bid);


--
-- PostgreSQL database dump complete
--

\unrestrict ZvAYlWTRhxKpa9YLzFcMYgXtFBJX79Ky3ZvWhpmHAyvuoz2lfCfgEfZIdZC7Umb

