CREATE TABLE accident (
    id          serial primary key,
    name        varchar,
    address     text,
    car_number  varchar,
    description text,
    status      text,
    photo       boolean
);