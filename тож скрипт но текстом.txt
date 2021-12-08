create table user_entity
(
	ID bigserial not null unique,
	name varchar(25) not null unique,
	fullname varchar(100) not null,
	address varchar(100) not null,
	password varchar(50) not null,
	role varchar(10) not null,
	constraint pk_user_entity_ID primary key (ID) 
);
create table purse_entity
(
	userId bigint not null,
	balance numeric,
	CONSTRAINT userId_fk FOREIGN KEY (userId)
	REFERENCES user_entity(id) ON DELETE CASCADE ON UPDATE cascade
);
create table product_entity
(
	ID bigserial not null,
	name varchar(25) not null unique,
	description varchar(100) not null,
	cost numeric, CHECK(cost > 0),
	month smallint not null,
	constraint pk_product_entity_ID primary key (ID) 
);
create table user_subs(

	userId bigint not null,
	productId bigInt not null,
	expired date not null,
	CONSTRAINT userId_fk FOREIGN KEY (userId)
	REFERENCES user_entity(id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT productId_fk FOREIGN KEY (productId)
	REFERENCES product_entity(id) ON DELETE CASCADE ON UPDATE cascade,
	UNIQUE(userId,productId)
);
create table user_cart(

	userId bigint not null,
	productId bigInt not null,
	CONSTRAINT userId_fk FOREIGN KEY (userId)
	REFERENCES user_entity(id) ON DELETE CASCADE ON UPDATE cascade,
	CONSTRAINT productId_fk FOREIGN KEY (productId)
	REFERENCES product_entity(id) ON DELETE CASCADE ON UPDATE cascade,
	UNIQUE(userId,productId)
);