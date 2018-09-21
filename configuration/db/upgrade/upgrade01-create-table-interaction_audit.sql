CREATE TABLE interaction_audit
(
  id           BIGINT  NOT NULL,

  request      VARCHAR NOT NULL,
  response     VARCHAR NOT NULL,
  created_date TIMESTAMP DEFAULT NOW(),
  CONSTRAINT interaction_audit_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);
ALTER TABLE interaction_audit
  OWNER TO gis_gmp_charge_user;
