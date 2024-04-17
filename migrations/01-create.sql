--liquibase formatted sql

--changeset vasilii:1
CREATE TABLE chat
(
    id bigint primary key
);

--changeset vasilii:2
CREATE TABLE link
(
    id         bigint generated always as identity primary key,
    url        text                     not null unique,
    checked_at timestamp with time zone NOT NULL DEFAULT NOW(),
    updated_at timestamp with time zone NOT NULL DEFAULT NOW()
);

--changeset vasilii:3
CREATE TABLE chat_link
(
    chat_id bigint REFERENCES chat (id) ON UPDATE CASCADE ON DELETE CASCADE,
    link_id bigint REFERENCES link (id) ON UPDATE CASCADE,
    CONSTRAINT pk_chat_link PRIMARY KEY (chat_id, link_id)
);
