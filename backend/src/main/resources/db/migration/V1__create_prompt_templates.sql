create table prompts (
	id				bigserial primary key,
	prompt_name		varchar(100) not null,
	version			varchar(30) not null,
	prompt_text		text not null,
	is_active		boolean not null default true,
	unique(prompt_name, version)
);