-- Create authorities table
CREATE TABLE authorities (
    name VARCHAR(50) PRIMARY KEY
);

-- Create users table
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create user_authorities table
CREATE TABLE user_authorities (
                                  user_id INT,
                                  authority_name VARCHAR(50),
                                  FOREIGN KEY (user_id) REFERENCES users(id),
                                  FOREIGN KEY (authority_name) REFERENCES authorities(name),
                                  PRIMARY KEY (user_id, authority_name)
);

CREATE TABLE users_details (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(50),
                               surname VARCHAR(50),
                               phone VARCHAR(20),
                               email VARCHAR(100),
                               birthday DATE,
                               country VARCHAR(50),
                               summary VARCHAR(500),
                               required_minimum_wage INT,
                               user_id INT,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create projects table
CREATE TABLE projects (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
                                        description TEXT,
                                        start_date DATE,
                                        end_date DATE,
                                        project_url VARCHAR(255),
                                        user_id INT,
                                        FOREIGN KEY (user_id) REFERENCES users(id) -- Foreign key to users table
);

-- Create project_technologies table to store technologies used for each project
CREATE TABLE project_technologies (
                                                    project_id INT,
                                                    technology VARCHAR(255),
                                                    PRIMARY KEY (project_id, technology),
                                                    FOREIGN KEY (project_id) REFERENCES projects(id) -- Foreign key to projects table
);



insert into authorities(name)
values ('USER');

insert into users(username, password)
values ('user1', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'), -- password: 1234
       ('user2', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'), -- password: 1234
       ('user3', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'); -- password: 1234

insert into user_authorities(user_id, authority_name)
values (1, 'USER'),
       (2, 'USER'),
       (3, 'USER');

INSERT INTO users_details (name, surname, phone, email, birthday, country, summary, required_minimum_wage, user_id)
VALUES
    ('John', 'Doe', '+994556787878', 'aqilazizov2005@gmail.com', '1990-05-15', 'USA', 'A software engineer with a passion for technology.', 50000, 1),
    ('Jane', 'Smith', '+994778901122', 'aqilazizov05@gmail.com', '1992-08-25', 'Canada', 'Experienced data analyst with a focus on AI.', 60000, 2);

-- Insert example data into projects table
INSERT INTO projects (title, description, start_date, end_date, project_url, user_id) VALUES
                                                                                          ('Project A', 'Description of Project A', '2024-01-01', '2024-06-01', 'http://example.com/project-a', 1),
                                                                                          ('Project B', 'Description of Project B', '2024-02-01', '2024-07-01', 'http://example.com/project-b', 2);

-- Insert technologies used for Project A
INSERT INTO project_technologies (project_id, technology) VALUES
                                                              (1, 'Java'),
                                                              (1, 'Spring Boot'),
                                                              (1, 'MySQL'),
                                                              (2, 'Python'),
                                                              (2, 'Django'),
                                                              (2, 'PostgreSQL');