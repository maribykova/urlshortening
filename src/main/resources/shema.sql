
drop table if exists URL;
create table URL
(
    Id SERIAL PRIMARY KEY,
    url  text,
    created_date timestamp,
    short_url   text
)