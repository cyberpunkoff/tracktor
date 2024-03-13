--liquibase formatted sql

--changeset vasilii:1
CREATE TABLE chats
(
    chat_id bigint not null unique
);

--changeset vasilii:2
CREATE TABLE links
(
    id  bigint generated always as identity unique,
    url text not null unique,
    checked_at timestamp with time zone NOT NULL DEFAULT NOW(),
    updated_at timestamp with time zone NOT NULL DEFAULT NOW()
);

--changeset vasilii:3
CREATE TABLE chats_links
(
    chat_id bigint REFERENCES chats (chat_id) ON UPDATE CASCADE ON DELETE CASCADE,
    link_id bigint REFERENCES links (id) ON UPDATE CASCADE,
    CONSTRAINT chats_links_pk PRIMARY KEY (chat_id, link_id)
);
