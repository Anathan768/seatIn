package seatInServer.JDBC.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitDataBase {
	
	private final String db_name = "dbSeatIn";
	private final String db_host;
	private final String db_username;
	private final String db_password;
		
	protected InitDataBase(String host, String username, String password) {
		this.db_host = host;
		this.db_username = username;
		this.db_password = password;
	}
	
	protected Configuration createIfNotExist(Connection testConn) {
		Connection conn = null;
		PreparedStatement stmt;
		ResultSet rs;
		String checkExistsDB = "SELECT exists(SELECT datname FROM pg_catalog.pg_database WHERE datname = 'dbSeatIn' )";
		boolean exist = false;
		Configuration config = null;		
		try {
			stmt = testConn.prepareStatement(checkExistsDB);
			rs = stmt.executeQuery();
			while(rs.next()) {
				exist = rs.getBoolean(1);
				config = new Configuration(this.db_host, this.db_name, this.db_username, this.db_password);
				if(exist) {
					continue;
				}else {
					this.createDataBase(testConn);
					testConn.close();
					conn = DriverManager.getConnection(config.getURL(), config.getUsername(), config.getPassword());
					this.createTables(conn);
					this.insertTestData(conn);
					conn.close();
				}
			}	
			rs.close();
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	private void createDataBase(Connection testConn) {
		PreparedStatement stmt;
		String createDB = "CREATE DATABASE \"dbSeatIn\";";
		try {
			stmt = testConn.prepareStatement(createDB);
			stmt.executeUpdate();
			
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTables(Connection conn) {
		PreparedStatement stmt;
		String tables = this.getTables();
		
		try {
			stmt = conn.prepareStatement(tables);
			stmt.executeUpdate();
			
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertTestData(Connection conn) {
		PreparedStatement stmt;
		String insertTestData = this.getTestData();
		try {
			stmt = conn.prepareStatement(insertTestData);
			stmt.executeUpdate();
			
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getTables() {
		
		String tables = "CREATE TABLE departments (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    name character varying(100) NOT NULL,\r\n" + 
				"    description text NOT NULL,\r\n" + 
				"    created TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    modified TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    UNIQUE(name)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE degree_courses (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    name character varying(150) NOT NULL,\r\n" + 
				"    description text NOT NULL,\r\n" + 
				"    activation_date timestamp with time zone NOT NULL CHECK (activation_date < deactivation_date),\r\n" + 
				"    deactivation_date timestamp with time zone NOT NULL CHECK (deactivation_date > activation_date),\r\n" + 
				"    is_active boolean NOT NULL DEFAULT FALSE,\r\n" + 
				"    UNIQUE(name)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE groups (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    name character varying(30) NOT NULL,\r\n" + 
				"    description character varying(30) NOT NULL,\r\n" + 
				"    UNIQUE(name)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE users (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    surname character varying(30) NOT NULL,\r\n" + 
				"    first_name character varying(30) NOT NULL,\r\n" + 
				"    password character varying(128) NOT NULL,\r\n" + 
				"    email character varying(200) NOT NULL,\r\n" + 
				"    group_id INTEGER REFERENCES groups(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    UNIQUE(email)\r\n" + 
				");\r\n" + 
				"CREATE INDEX email_idx ON users (email);\r\n" + 
				" \r\n" + 
				"-- stato della carriera dello studente (codifica l’iscrizione al I / II / III anno in corso / fuori corso)\r\n" + 
				"-- p, s, t, f\r\n" + 
				"CREATE TABLE profiles (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    last_login timestamp with time zone,\r\n" + 
				"    wrong_logins integer NOT NULL DEFAULT 0,\r\n" + 
				"    activation_code character varying(30) NOT NULL,\r\n" + 
				"    created TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    modified TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    is_active boolean NOT NULL DEFAULT FALSE,\r\n" + 
				"    is_logged_in boolean NOT NULL DEFAULT FALSE,\r\n" + 
				"    department_id INTEGER REFERENCES departments(id) ON DELETE CASCADE ON UPDATE CASCADE, -- for lecturer only\r\n" + 
				"    degree_course_id INTEGER REFERENCES degree_courses(id) ON DELETE CASCADE ON UPDATE CASCADE, -- for students only\r\n" + 
				"    career_status character varying(1), -- for students only\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE courses (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    name character varying(150) NOT NULL,\r\n" + 
				"    description text NOT NULL,\r\n" + 
				"    activation_date timestamp with time zone NOT NULL CHECK (activation_date < deactivation_date),\r\n" + 
				"    deactivation_date timestamp with time zone CHECK (deactivation_date > activation_date),\r\n" + 
				"    is_active boolean NOT NULL DEFAULT FALSE,\r\n" + 
				"    degree_course_id INTEGER REFERENCES degree_courses(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE lectures_courses(\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    UNIQUE (user_id, course_id)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE study_plans (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    created timestamp with time zone,\r\n" + 
				"    UNIQUE (user_id, course_id)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE exams (\r\n" + 
				"    study_plan_id INTEGER REFERENCES study_plans(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    passed boolean NOT NULL DEFAULT FALSE,\r\n" + 
				"    date timestamp with time zone\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"-- Nelle specifiche viene chiamato sezione\r\n" + 
				"CREATE TABLE modules (\r\n" + 
				"    id SERIAL,\r\n" + 
				"    parent_id integer,\r\n" + 
				"    title character varying(150) NOT NULL,\r\n" + 
				"    description text,\r\n" + 
				"    is_active boolean NOT NULL DEFAULT TRUE,  -- richiesta opzionale\r\n" + 
				"    active_from timestamp with time zone NOT NULL CHECK (active_from < active_to), -- richiesta opzionale\r\n" + 
				"    active_to timestamp with time zone NOT NULL CHECK (active_to > active_from), -- richiesta opzionale\r\n" + 
				"    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    FOREIGN KEY (parent_id) REFERENCES modules(id),\r\n" + 
				"    PRIMARY KEY(id)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE resources (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    title character varying(150) NOT NULL,\r\n" + 
				"    description text,\r\n" + 
				"    is_active boolean NOT NULL DEFAULT TRUE, -- richiesta opzionale\r\n" + 
				"    module_id INTEGER REFERENCES modules(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    UNIQUE (title,module_id)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE files (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    file character varying(200) NOT NULL,\r\n" + 
				"    resource_id INTEGER REFERENCES resources(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    UNIQUE (file,resource_id)\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE newsletters (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    subject character varying(150) NOT NULL,\r\n" + 
				"    message text NOT NULL,\r\n" + 
				"    created TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    modified TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE course_stats (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    logged_in timestamp with time zone NOT NULL CHECK (logged_in < logged_out),\r\n" + 
				"    logged_out timestamp with time zone CHECK (logged_out > logged_in),\r\n" + 
				"    course_id INTEGER REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE\r\n" + 
				");\r\n" + 
				" \r\n" + 
				"CREATE TABLE file_downloads (\r\n" + 
				"    id SERIAL PRIMARY KEY,\r\n" + 
				"    date TIMESTAMPTZ NOT NULL DEFAULT NOW(),\r\n" + 
				"    file_id INTEGER REFERENCES files(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
				"    UNIQUE (file_id,user_id,date)\r\n" + 
				");";
		
		return tables;
	}
	
	private String getTestData() {
		
		String testData = "INSERT INTO degree_courses (\"name\",description,activation_date,deactivation_date,is_active) VALUES\r\n" + 
				"('Informatica triennale','Informatica laurea triennale','2018-05-01 02:00:00.000','2019-05-01 02:00:00.000',true),\r\n" + 
				"('Informatica magistrale','Informatica laurea magistrale','2018-05-01 02:00:00.000','2019-05-01 02:00:00.000',true),\r\n" + 
				"('Elettronica','Elettronica laurea triennale','2018-05-01 02:00:00.000','2019-05-01 02:00:00.000',true),\r\n" + 
				"('Medicina','Medicina e chirurgia','2018-05-01 02:00:00.000','2019-05-01 02:00:00.000',true)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO departments (\"name\",description,created,modified) VALUES\r\n" + 
				"('Dipartimento di Informatica','Dipartimento di Informatica','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000'),\r\n" + 
				"('Dipartimento di Elettronica','Dipartimento di Elettronica','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000'),\r\n" + 
				"('Dipartimento di Medicina','Dipartimento di Medicina','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000')\r\n" + 
				";\r\n" + 
				" \r\n" + 
				"INSERT INTO groups (\"name\",description) VALUES\r\n" + 
				"('admin','admin user'),\r\n" + 
				"('lecturer','lecturer user'),\r\n" + 
				"('student','student')\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO users (password,surname,first_name,email,group_id) VALUES\r\n" + 
				"('pass','admin1 surname','admin1 name','admin1@domain.com',1),\r\n" + 
				"('pass','admin2 surname','admin2 name','admin2@domain.com',1),\r\n" + 
				"('pass','admin3 surname','admin3 name','admin3@domain.com',1),\r\n" + 
				" \r\n" + 
				"-- ID from 4-9\r\n" + 
				"('pass','lecture1 surname','lecture1 name','lecture1@domain.com',2),\r\n" + 
				"('pass','lecture2 surname','lecture2 name','lecture2@domain.com',2),\r\n" + 
				"('pass','lecture3 surname','lecture3 name','lecture3@domain.com',2),\r\n" + 
				"('pass','lecture4 surname','lecture4 name','lecture4@domain.com',2),\r\n" + 
				"('pass','lecture5 surname','lecture5 name','lecture5@domain.com',2),\r\n" + 
				"('pass','lecture6 surname','lecture6 name','lecture6@domain.com',2),\r\n" + 
				" \r\n" + 
				"-- ID from 10 to 18\r\n" + 
				"('pass','student1 surname','student1 name','student1@domain.com',3),\r\n" + 
				"('pass','student2 surname','student2 name','student2@domain.com',3),\r\n" + 
				"('pass','student3 surname','student3 name','student3@domain.com',3),\r\n" + 
				"('pass','student4 surname','student4 name','student4@domain.com',3),\r\n" + 
				"('pass','student5 surname','student5 name','student5@domain.com',3),\r\n" + 
				"('pass','student6 surname','student6 name','student6@domain.com',3),\r\n" + 
				"('pass','student7 surname','student7 name','student7@domain.com',3),\r\n" + 
				"('pass','student8 surname','student8 name','student8@domain.com',3),\r\n" + 
				"('pass','student9 surname','student9 name','student9@domain.com',3)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO profiles (last_login,wrong_logins,activation_code,created,modified,is_active,is_logged_in,department_id,degree_course_id,career_status,user_id) VALUES\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,true,NULL,NULL,NULL,1),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,NULL,NULL,2),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,NULL,NULL,3),\r\n" + 
				" \r\n" + 
				"-- ID from 4 to 9 lecturer\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,true,1,NULL,NULL,4),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,1,NULL,NULL,5),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,1,NULL,NULL,6),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,2,NULL,NULL,7),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,2,NULL,NULL,8),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,2,NULL,NULL,9),\r\n" + 
				" \r\n" + 
				"-- ID from 10 to 19 users\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,true,NULL,1,'p',10),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,1,'p',11),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,1,'p',12),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'p',13),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'p',14),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'p',15),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'f',16),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'f',17),\r\n" + 
				"(NULL,0,'','2018-05-01 02:00:00.000','2018-05-01 02:00:00.000',true,false,NULL,2,'f',18)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO courses (name,description,activation_date,deactivation_date,is_active,degree_course_id) VALUES\r\n" + 
				"('Applicazioni web', 'Corso di applicazioni web','2017-05-01', null, true, 1), \r\n" + 
				"('Analisi matematica', 'Corso di analisi matematica 1','2018-06-11', null, true, 1),\r\n" + 
				"('Analisi matematica', 'Corso di analisi matematica 2','2018-07-12', null, true, 2),\r\n" + 
				"('Analisi matematica', 'Corso di analisi matematica 3','2018-03-13', null, true, 3),\r\n" + 
				"('Big data ', 'Corso di big data','2015-05-25', null, true, 1),\r\n" + 
				"('Algoritmi', 'Corso di algoritmi','2013-05-21', null, false, 1),\r\n" + 
				"('Microcontrollori', 'Corso di Microcontrollori','2018-05-01', null, false, 2)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO modules (parent_id,title,description,is_active,active_from,active_to,course_id) VALUES\r\n" + 
				"(NULL,'Introduzione al web', 'Applicazioni web Intro',true,'2017-05-01','2027-05-01',1), -- ID 1\r\n" + 
				"(NULL,'Introduzione al web 2', 'Applicazioni web modulo 2',true,'2017-05-01','2027-05-01',1), -- ID 2\r\n" + 
				"(NULL,'Web avanzato padre 1', 'Applicazioni web modulo 3 padre',true,'2017-05-01','2027-05-01',1), -- ID 3\r\n" + 
				"(3,'Web avanzato figlio 1', 'Applicazioni web modulo 3 figlio 1',true,'2017-05-01','2027-05-01',1), -- ID 4\r\n" + 
				"(3,'Web avanzato figlio 2', 'Applicazioni web modulo 3 figlio 2',true,'2017-05-01','2027-05-01',1), -- ID 5\r\n" + 
				"(NULL,'Analisi Intro', 'Analisi modulo di Intro',true,'2017-05-01','2027-05-01',2), \r\n" + 
				"(NULL,'Analisi modulo 2', 'Analisi modulo 2',true,'2016-05-01','2025-05-01',2), \r\n" + 
				"(NULL,'Analisi modulo 3', 'Analisi modulo 3',true,'2016-05-01','2025-05-01',2), \r\n" + 
				"(NULL,'Big data modulo 1', 'Big data modulo 1',true,'2016-05-01','2025-05-01',5), \r\n" + 
				"(NULL,'Big data modulo 2', 'Big data modulo 2',true,'2016-05-01','2025-05-01',5), \r\n" + 
				"(NULL,'Big data modulo 3', 'Big data modulo 3',true,'2016-05-01','2025-05-01',5)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				"INSERT INTO resources (title,description,module_id) VALUES\r\n" + 
				"('Introduzione al web resource 1', 'Introduzione al web resource 1',1), \r\n" + 
				"('Introduzione al web resource 2', NULL, 1), \r\n" + 
				"('Introduzione al web resource 3', NULL, 1), \r\n" + 
				"('Introduzione al web resource 4', NULL ,1),\r\n" + 
				" \r\n" + 
				"('Introduzione al web 2 resource 1', NULL, 2), \r\n" + 
				"('Introduzione al web 2 resource 2', NULL ,2), \r\n" + 
				"('Introduzione al web 2 resource 3', NULL ,2), \r\n" + 
				"('Introduzione al web 2 resource 4', NULL ,2),\r\n" + 
				" \r\n" + 
				"('Web avanzato resource 1', NULL, 3), \r\n" + 
				"('Web avanzato resource 2', NULL, 3), \r\n" + 
				"('Web avanzato resource 3', NULL, 3), \r\n" + 
				"('Web avanzato resource 4', NULL, 3),\r\n" + 
				" \r\n" + 
				"('Web avanzato resource 1 figlio 1', NULL, 4), \r\n" + 
				"('Web avanzato resource 2 figlio 1', NULL, 4), \r\n" + 
				"('Web avanzato resource 3 figlio 1', NULL, 4), \r\n" + 
				"('Web avanzato resource 4 figlio 1', NULL, 4)\r\n" + 
				" \r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO files (file,resource_id) VALUES\r\n" + 
				"('/home/davide/introweb1.png',1), \r\n" + 
				"('/home/davide/introweb2.png',1), \r\n" + 
				"('/home/davide/introweb3.png',1), \r\n" + 
				"('/home/davide/web modulo 2 file 1.pdf',2), \r\n" + 
				"('/home/davide/web modulo 2 file 2.pdf',2), \r\n" + 
				"('/home/davide/web modulo 2 file 3.pdf',2)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO file_downloads (user_id,file_id) VALUES\r\n" + 
				"(10, 1),\r\n" + 
				"(10, 2),\r\n" + 
				"(11, 2),\r\n" + 
				"(11, 3),\r\n" + 
				"(11, 4)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO lectures_courses (user_id,course_id) VALUES\r\n" + 
				"(4, 1),\r\n" + 
				"(4, 2),\r\n" + 
				"(5, 2),\r\n" + 
				"(5, 3),\r\n" + 
				"(5, 4),\r\n" + 
				"(6, 5),\r\n" + 
				"(2, 3),\r\n" + 
				"(2, 4),\r\n" + 
				"(2, 5),\r\n" + 
				"(2, 6),\r\n" +
				"(2, 7)\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO study_plans (user_id,course_id,created) VALUES\r\n" + 
				"(10, 2, '2018-01-14'),\r\n" + 
				"(10, 5, '2018-01-17'),\r\n" + 
				"(10, 6, '2018-01-19'),\r\n" + 
				"(12, 5, '2018-01-17'),\r\n" + 
				"(12, 6, '2018-01-19'),\r\n" + 
				"(14, 1, '2018-01-13'),\r\n" + 
				"(14, 2, '2018-01-14'),\r\n" + 
				"(11, 1, '2018-01-14'),\r\n" + 
				"(11, 2, '2018-01-14'),\r\n" + 
				"(11, 5, '2018-01-14'),\r\n" + 
				"(11, 6, '2018-01-14'),\r\n" + 
				"(12, 1, '2018-01-14'),\r\n" + 
				"(12, 2, '2018-01-14'),\r\n" + 
				"(13, 1, '2018-01-14'),\r\n" + 
				"(13, 2, '2018-01-14'),\r\n" + 
				"(14, 3, '2018-01-14'),\r\n" + 
				"(14, 4, '2018-01-14')\r\n" + 
				";\r\n" + 
				" \r\n" + 
				" \r\n" + 
				"INSERT INTO course_stats (user_id,course_id,logged_in,logged_out) VALUES\r\n" + 
				"(10, 2, '2018-11-10 10:00:00', '2018-11-10 12:00:00'),\r\n" + 
				"(10, 2, '2018-11-11 17:00:00', '2018-11-11 18:00:00'),\r\n" + 
				"(10, 2, '2018-11-27 10:00:00', NULL),\r\n" + 
				"(11, 2, '2018-10-10 10:00:00', '2018-10-10 12:00:00'),\r\n" + 
				"(11, 2, '2018-10-11 17:00:00', '2018-10-11 18:00:00')\r\n" + 
				";";
		
		return testData;
	}
}