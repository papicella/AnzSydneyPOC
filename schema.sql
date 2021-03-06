drop table if exists anz.trades cascade;
drop table if exists anz.trade_metadata cascade;

create schema anz;

create table anz.trades
(trade_id text not null primary key,
 payload_digest text,
 payload bytea,
 created_timestamp text)
DISTRIBUTED BY (trade_id);

create table anz.trade_metadata
(trade_id text not null,
 key text,
 value text)
DISTRIBUTED BY (trade_id);

CREATE INDEX trade_id_trade_metadata_idx1 ON anz.trade_metadata  (trade_id);