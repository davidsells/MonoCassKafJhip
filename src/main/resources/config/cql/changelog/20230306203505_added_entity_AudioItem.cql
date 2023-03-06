
CREATE TABLE IF NOT EXISTS audioItem (
    id uuid,
    slug text,
    transaction text,
    itemDescription text,
    categoryName text,
    price text,
    location text,
    dateofadd text,
    link text,
    image text,
    PRIMARY KEY(id)
);
