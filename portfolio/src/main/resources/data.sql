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

-- Create the `skills` table
CREATE TABLE skills (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50),
                        description VARCHAR(250),
                        proficiency_level INT CHECK (proficiency_level BETWEEN 1 AND 10),
                        user_id INT,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE educations (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            degree VARCHAR(255),
                            institution VARCHAR(255),
                            start_year INT,
                            end_year INT,
                            grade VARCHAR(50),
                            user_id INT,
                            FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE experiences (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(255),
                             company_name VARCHAR(255),
                             start_date DATE,
                             end_date DATE,
                             description TEXT,
                             user_id INT,
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE social_media (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              platform_name VARCHAR(255),
                              profile_url VARCHAR(255),
                              user_id INT,
                              FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE languages (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255),
                           proficiency_level VARCHAR(255),
                           user_id INT,
                           FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE certificates (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255),
                              institution VARCHAR(255),
                              issue_date DATE,
                              certificate_url VARCHAR(255),
                              user_id INT,
                              FOREIGN KEY (user_id) REFERENCES users(id)
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

-- Insert sample data into `skills` table
INSERT INTO skills (name, description, proficiency_level, user_id)
VALUES
    ('Java Programming', 'Strong knowledge of Java SE and EE', 8, 1),
    ('Spring Framework', 'Experience with Spring Boot, MVC, and Security', 9, 1),
    ('Database Management', 'Proficient in MySQL and PostgreSQL', 7, 2);

-- Insert sample data into the educations table
INSERT INTO educations (degree, institution, start_year, end_year, grade, user_id)
VALUES
    ('B.Sc. in Computer Science', 'Massachusetts Institute of Technology', 2015, 2019, 'A', 1),
    ('M.Sc. in Artificial Intelligence', 'Stanford University', 2020, 2022, 'A+', 1),
    ('B.A. in Economics', 'Harvard University', 2014, 2018, 'B', 2);

-- Insert sample experiences
INSERT INTO experiences (title, company_name, start_date, end_date, description, user_id)
VALUES
    ('Software Engineer Intern', 'TechCorp', '2022-06-01', '2022-08-31', 'Developed REST APIs using Spring Boot.', 1),
    ('Backend Developer', 'Innovatech Solutions', '2023-01-01', '2023-12-31', 'Worked on database optimization and microservices architecture.', 1),
    ('Frontend Developer', 'Designify', '2021-03-01', '2022-05-31', 'Implemented responsive web designs using ReactJS.', 2);

-- Insert sample data into the social_media table
INSERT INTO social_media (platform_name, profile_url, user_id)
VALUES
    ('LinkedIn', 'https://www.linkedin.com/in/johndoe', 1),
    ('Facebook', 'https://www.facebook.com/johndoe', 1),
    ('Twitter', 'https://twitter.com/john_doe123', 1),
    ('Instagram', 'https://www.instagram.com/johndoe_insta', 2);

INSERT INTO languages (name, proficiency_level, user_id)
VALUES
    ('English', 'Advanced', 1),
    ('Spanish', 'Intermediate', 1),
    ('German', 'Basic', 2),
    ('French', 'Advanced', 2);

INSERT INTO certificates (name, institution, issue_date, certificate_url, user_id)
VALUES
    ('Java Developer Certification', 'Oracle', '2023-05-15', 'https://www.oracle.com/certification', 1),
    ('AWS Certified Solutions Architect', 'Amazon', '2022-10-20', 'https://aws.amazon.com/certification', 2),
    ('Data Science Certificate', 'Coursera', '2022-08-30', 'https://www.coursera.org/certificates', 2);


