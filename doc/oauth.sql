-- sql: https://github.com/spring-attic/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql
-- 注释: https://andaily.com/spring-oauth-server/db_table_description.html
-- 字段调整: VARCHAR(256) PRIMARY KEY -> VARCHAR(128) PRIMARY KEY
-- LONGVARBINARY -> BLOB
-- TIMESTAMP -> TIMESTAMP DEFAULT CURRENT_TIMESTAMP
create table oauth_client_details
(
    client_id               VARCHAR(128) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

create table oauth_client_token
(
    authentication_id VARCHAR(128) PRIMARY KEY,
    token_id          VARCHAR(256),
    token             BLOB,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

create table oauth_access_token
(
    authentication_id VARCHAR(128) PRIMARY KEY,
    token_id          VARCHAR(256),
    token             BLOB,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

create table oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--
-- -- customized oauth_client_details table
-- create table ClientDetails
-- (
--     appId                  VARCHAR(128) PRIMARY KEY,
--     resourceIds            VARCHAR(256),
--     appSecret              VARCHAR(256),
--     scope                  VARCHAR(256),
--     grantTypes             VARCHAR(256),
--     redirectUrl            VARCHAR(256),
--     authorities            VARCHAR(256),
--     access_token_validity  INTEGER,
--     refresh_token_validity INTEGER,
--     additionalInformation  VARCHAR(4096),
--     autoApproveScopes      VARCHAR(256)
-- );