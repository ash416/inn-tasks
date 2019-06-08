create table if not exists logs
(
  id            integer not null,
  log_date      date,
  log_level     varchar(20),
  message       varchar(100),
  log_exception varchar(10000)
);