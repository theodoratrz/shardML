-- Joins the banking application runs, one per relationship in
-- banking.shardml. bench/integration/test-examples.sh runs them after the
-- distribution script. Citus refuses a join between distributed tables that
-- does not match their distribution columns, so each one checks that the
-- declared colocation actually puts related rows on the same shard.

-- accounts belongs_to customers
SELECT count(*) FROM accounts a JOIN customers c ON a.customer_id = c.id;

-- transactions belongs_to accounts
SELECT count(*) FROM transactions t JOIN accounts a ON t.account_id = a.id;

-- cards belongs_to customers
SELECT count(*) FROM cards k JOIN customers c ON k.customer_id = c.id;
