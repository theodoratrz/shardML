SELECT create_distributed_table('customers', 'id'  ,shard_count => 32 );
SELECT create_distributed_table('accounts', 'customer_id'  ,colocate_with => 'customers' );
SELECT create_distributed_table('transactions', 'customer_id'  ,colocate_with => 'accounts' );
SELECT create_distributed_table('cards', 'customer_id'  ,colocate_with => 'customers' );
