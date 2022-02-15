create table if not exists items (
    id serial primary key,
    description text,
    date date,
    done boolean
);