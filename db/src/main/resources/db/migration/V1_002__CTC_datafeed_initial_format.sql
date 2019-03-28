CREATE TABLE hmrc_ctc_household (
    id uuid not null primary key,
    household_identifier varchar(50),
    file_import_number integer,
    created_timestamp TIMESTAMP DEFAULT NOW()
);

CREATE TABLE hmrc_ctc_adult (
    id uuid not null primary key,
    hmrc_ctc_household_id uuid not null,
    nino varchar(9),
    award_date date,
    surname varchar(35),
    first_forename varchar(35),
    second_forename varchar(35),
    initials varchar(35),
    title varchar(35),
    other_title varchar(35),
    additional_address_information varchar(35),
    address_line_1 varchar(35),
    address_line_2 varchar(35),
    address_line_3 varchar(35),
    address_line_4 varchar(35),
    address_line_5 varchar(35),
    address_postcode varchar(8),
    returned_letter_service boolean
);

ALTER TABLE hmrc_ctc_adult ADD FOREIGN KEY(hmrc_ctc_household_id) REFERENCES hmrc_ctc_household (id) ON DELETE CASCADE;
CREATE INDEX hmrc_ctc_adult_nino_idx ON hmrc_ctc_adult (nino);

CREATE TABLE hmrc_ctc_child (
    id uuid not null primary key,
    hmrc_ctc_household_id uuid not null,
    first_forename varchar(35),
    second_forename varchar(35),
    surname varchar(35),
    date_of_birth date,
    responsibility_start_date date,
    entitlement_start_date date
);

ALTER TABLE hmrc_ctc_child ADD FOREIGN KEY(hmrc_ctc_household_id) REFERENCES hmrc_ctc_household (id) ON DELETE CASCADE;
