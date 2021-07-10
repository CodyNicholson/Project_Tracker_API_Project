CREATE TABLE projecttask (
  id UUID DEFAULT uuid_generate_v4 (),
  project_id UUID NOT NULL,
  name VARCHAR(255),
  description VARCHAR(255),
  acceptance_criteria TEXT,
  points NUMERIC (2, 1),
  status VARCHAR(255),
  PRIMARY KEY (id),
  CONSTRAINT fk_user
        FOREIGN KEY(project_id)
            REFERENCES project(id)
);
