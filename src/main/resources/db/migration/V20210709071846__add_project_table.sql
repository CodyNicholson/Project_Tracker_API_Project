SELECT * FROM pg_extension;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS organizations;

CREATE TABLE project (
  id UUID DEFAULT uuid_generate_v4 (),
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  deployed_link VARCHAR(255),
  documentation_link VARCHAR(255),
  code_link VARCHAR(255),
  PRIMARY KEY (id));
