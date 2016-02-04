CREATE TABLE assignment_dim_stage AS SELECT * FROM assignment_dim LIMIT 0;
COPY assignment_dim_stage FROM '<intermediates3bucketandpath>/assignment_dim' CREDENTIALS '<awskeyandsecret>' GZIP DELIMITER '\t' TRUNCATECOLUMNS;
