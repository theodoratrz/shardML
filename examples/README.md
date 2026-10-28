# Examples

| Model | Schema | Scenario |
|---|---|---|
| `banking.shardml` | `banking.msql` | Customers, their accounts, transactions and cards |
| `social.shardml` | `social.msql` | Users, posts, comments, likes, and a reference table of countries |

Generated output for both is committed in `src-gen/`. `bench/integration/test-examples.sh`
deploys each example to the bench Citus cluster and, if a `<model>.probe.sql`
exists, runs the joins the application relies on.

---

## Banking: before and after

The original banking model validated cleanly but **could not be deployed to
Citus**, and the colocation it declared did not keep related rows together.
It is kept here as the "before" because it shows exactly the two mistakes
ShardML now catches.

### Before

```
customers     key: id
accounts      key: customer_id   colocate_with: customers
transactions  key: account_id    colocate_with: accounts
cards         key: id            colocate_with: customers
```

Schema: every table has a single-column `id BIGINT PRIMARY KEY`;
`customers.email` and `cards.card_number` are `UNIQUE`.

**Problem 1: constraints Citus cannot enforce.** Citus enforces a primary key
or unique constraint only within one shard, so it requires every such
constraint to include the distribution column. Here:

| Table | Distributed by | Constraint | Includes it? |
|---|---|---|---|
| customers | `id` | `PRIMARY KEY (id)` | yes |
| customers | `id` | `UNIQUE (email)` | **no** |
| accounts | `customer_id` | `PRIMARY KEY (id)` | **no** |
| transactions | `account_id` | `PRIMARY KEY (id)` | **no** |
| cards | `id` | `UNIQUE (card_number)` | **no** |

`create_distributed_table` fails on every row marked **no**.

**Problem 2: colocation by name, not by key.** `colocate_with` only makes two
tables share shard boundaries; a row lands on the shard picked by *its own*
distribution column. A transaction is placed by `account_id`, but its account is
placed by `customer_id`, so the two hash to unrelated shards and
`transactions JOIN accounts ON t.account_id = a.id` is not a colocated join.
The same holds for `cards` (by `id`) against `customers` (by `id`): a card's
own id says nothing about its customer. Citus rejects these joins unless
repartition joins are enabled, and even then it reshuffles data on every query.

### After

Every table in the customer's tree is distributed by the customer, and every
relationship joins through that column:

```
customers     key: id
accounts      key: customer_id   colocate_with: customers
transactions  key: customer_id   colocate_with: accounts
cards         key: customer_id   colocate_with: customers

accounts     belongs_to customers via customer_id
transactions belongs_to accounts  via customer_id
cards        belongs_to customers via customer_id
```

Schema changes:

- `accounts`, `transactions` and `cards` get a composite primary key
  `(customer_id, id)`. `id` stays unique in practice (it comes from a
  sequence); the key just tells Citus which shard owns it.
- `transactions` gets a `customer_id` column, copied from its account at
  insert time. This denormalisation is what makes the account–transaction
  join colocated: `ON t.customer_id = a.customer_id AND t.account_id = a.id`.
- `customers.email` and `cards.card_number` lose their `UNIQUE`, with a
  comment in `banking.msql` saying why. A globally unique column that is not
  the distribution column cannot be enforced by Citus. ShardML reports it
  (SHML_024) and lists the alternatives:
  - enforce it in the application;
  - a lookup table distributed by that column (e.g. `card_numbers(card_number
    PRIMARY KEY, customer_id)`), written in the same transaction;
  - a reference table, if the table is small enough to copy to every node.

The validator catches both original problems: a constraint without the
distribution column is an error, and a relationship between colocated tables
must name a column that is the distribution column on both sides.
