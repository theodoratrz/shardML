SELECT create_distributed_table('users', 'id' );
SELECT create_distributed_table('posts', 'user_id'  ,colocate_with => 'users' );
SELECT create_distributed_table('comments', 'user_id'  ,colocate_with => 'posts' );
SELECT create_distributed_table('likes', 'user_id'  ,colocate_with => 'users' );
SELECT create_reference_table('countries');
