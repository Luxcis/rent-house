CREATE TABLE IF NOT EXISTS t_user
(
    id          BIGINT,
    name        VARCHAR(20),
    username    VARCHAR(20),
    password    VARCHAR(128),
    unionid     VARCHAR(128),
    openid      VARCHAR(128),
    active      BOOLEAN,
    create_time DATETIME,
    create_by   BIGINT,
    update_time DATETIME,
    update_by   BIGINT,
    is_removed  BOOLEAN,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_role
(
    id          BIGINT,
    code        VARCHAR(20),
    name        VARCHAR(128),
    create_time DATETIME,
    create_by   BIGINT,
    update_time DATETIME,
    update_by   BIGINT,
    is_removed  BOOLEAN,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_user_role_ref
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id)
);


CREATE TABLE IF NOT EXISTS t_config
(
    id          BIGINT,
    name        VARCHAR(20),
    code        VARCHAR(20),
    `value`     VARCHAR(128),
    create_time DATETIME,
    create_by   BIGINT,
    update_time DATETIME,
    update_by   BIGINT,
    is_removed  BOOLEAN,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_history
(
    id          BIGINT,
    bean_name   VARCHAR(128),
    table_name  VARCHAR(20),
    business_id BIGINT,
    old_value   VARCHAR(1024),
    new_value   VARCHAR(1024),
    create_time DATETIME,
    create_by   BIGINT,
    update_time DATETIME,
    update_by   BIGINT,
    is_removed  BOOLEAN,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_log
(
    id          BIGINT,
    type        VARCHAR(10),
    is_success  BOOLEAN,
    method      VARCHAR(10),
    api         VARCHAR(256),
    business_id BIGINT,
    parameters  TEXT,
    result      TEXT,
    exception   TEXT,
    user_id     BIGINT,
    ip          VARCHAR(15),
    log_time    DATETIME,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_room
(
    id          BIGINT,
    name        VARCHAR(50),
    rent        INTEGER,
    status      TINYINT,
    create_time DATETIME,
    create_by   BIGINT,
    update_time DATETIME,
    update_by   BIGINT,
    is_removed  BOOLEAN,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS t_bill
(
    id               BIGINT,
    room_id          BIGINT,
    rent             INTEGER,
    water            REAL,
    water_usage      REAL,
    water_price      REAL,
    water_fee        REAL,
    electric         REAL,
    electric_usage   REAL,
    electric_price   REAL,
    electric_fee     REAL,
    management_price INTEGER,
    garbage_price    INTEGER,
    ext_price        INTEGER,
    ext_remark       VARCHAR(1024),
    sum_price        REAL,
    is_paid          BOOLEAN,
    status           TINYINT,
    create_time      DATETIME,
    create_by        BIGINT,
    update_time      DATETIME,
    update_by        BIGINT,
    is_removed       BOOLEAN,
    PRIMARY KEY (id)
);
