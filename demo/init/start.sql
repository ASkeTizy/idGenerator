
create schema if not exists "LIBRARY";
CREATE TABLE IF NOT EXISTS "LIBRARY".sequencedata (
    id bigInt,
    role VARCHAR(16) PRIMARY KEY
);
insert into "LIBRARY".sequencedata values
(0,'book'),
(0,'author'),
(0,'user');