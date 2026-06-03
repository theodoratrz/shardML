SELECT create_distributed_table('customers', 'id' );
SELECT create_distributed_table('accounts', 'customer_id'  ,colocate_with => 'customers' );
SELECT create_distributed_table('transactions', 'account_id'  ,colocate_with => 'accounts' );
SELECT create_distributed_table('cards', 'id'  ,colocate_with => 'customers' );
