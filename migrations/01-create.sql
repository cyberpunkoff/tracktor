--liquibase formatted sql

--changeset vasilii:1
CREATE TABLE chats
(
    id      bigint generated always as identity unique,
    chat_id bigint not null
);

--changeset vasilii:2
CREATE TABLE links
(
    id  bigint generated always as identity unique,
    url text not null,
    updated_at timestamp with time zone
);

--changeset vasilii:3
CREATE TABLE chats_links
(
    chat_id bigint REFERENCES chats (id) ON UPDATE CASCADE ON DELETE CASCADE,
    link_id bigint REFERENCES links (id) ON UPDATE CASCADE,
    CONSTRAINT chats_links_pk PRIMARY KEY (chat_id, link_id)
);
