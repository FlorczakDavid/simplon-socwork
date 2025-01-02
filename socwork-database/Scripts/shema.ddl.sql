DROP TABLE IF EXISTS t_exercise;
DROP TABLE IF EXISTS t_accounts;
DROP TABLE IF EXISTS t_roles;

CREATE TABLE t_accounts (
	id bigint GENERATED ALWAYS AS IDENTITY,
	username varchar(255),
	password varchar(64),
	CONSTRAINT t_account_pkey PRIMARY KEY (id),
	CONSTRAINT t_account_ukey UNIQUE (username)
);

CREATE TABLE t_roles (
	id bigint GENERATED ALWAYS AS IDENTITY,
	code varchar(8),
	is_default boolean,
	CONSTRAINT t_role_pkey PRIMARY KEY (id),
	CONSTRAINT t_role_ukey UNIQUE (code)
);

CREATE TABLE t_exercise (
	account_id bigint,
	role_id bigint,
	PRIMARY KEY (account_id, role_id),
	CONSTRAINT t_exercise_account_id_fkey FOREIGN KEY(account_id) REFERENCES t_accounts(id),
	CONSTRAINT t_exercise_role_id_fkey FOREIGN KEY(role_id) REFERENCES t_roles(id)
);
