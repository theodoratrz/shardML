-- Joins the social application runs, one per relationship in
-- social.shardml. bench/integration/test-examples.sh runs them after the
-- distribution script. Citus refuses a join between distributed tables that
-- does not match their distribution columns, so each one checks that the
-- declared colocation actually puts related rows on the same shard.

-- posts belongs_to users via user_id
SELECT count(*) FROM posts p JOIN users u ON p.user_id = u.id;

-- comments belongs_to posts via post_user_id
SELECT count(*) FROM comments c JOIN posts p ON c.post_user_id = p.user_id AND c.post_id = p.id;

-- likes belongs_to users via user_id
SELECT count(*) FROM likes l JOIN users u ON l.user_id = u.id;

-- users join the countries reference table on any column
SELECT count(*) FROM users u JOIN countries c ON u.country_id = c.id;
