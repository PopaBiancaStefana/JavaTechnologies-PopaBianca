-- Table: public.Users

-- DROP TABLE IF EXISTS public."Users";

CREATE TABLE IF NOT EXISTS public."Users"
(
    user_id integer NOT NULL DEFAULT nextval('"Users_userId_seq"'::regclass),
    username "char"[] NOT NULL,
    role "char"[] NOT NULL,
    password_hash "char"[] NOT NULL,
    CONSTRAINT "Users_pkey" PRIMARY KEY (user_id)
    )

-- Table: public.Preferences

-- DROP TABLE IF EXISTS public."Preferences";

CREATE TABLE IF NOT EXISTS public."Preferences"
(
    registration_number uuid NOT NULL,
    teacher_id integer NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    "timestamp" timestamp without time zone,
    CONSTRAINT "Preferences_pkey" PRIMARY KEY (registration_number),
    CONSTRAINT "Fk_preferences_teacher" FOREIGN KEY (teacher_id)
    REFERENCES public."Users" (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

