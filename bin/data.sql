-- Create the database if it does not exist
CREATE DATABASE IF NOT EXISTS springupload;

-- Switch to the database
-- Grant privileges to the root user for the new database
GRANT ALL PRIVILEGES ON springupload.* TO 'root'@'%';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;
