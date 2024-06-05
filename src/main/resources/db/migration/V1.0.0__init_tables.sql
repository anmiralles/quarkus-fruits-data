CREATE TABLE fruit
(
    id           SERIAL,
    name         varchar      NOT NULL,
    itemid       integer      NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX return_ind_01 ON return (id);