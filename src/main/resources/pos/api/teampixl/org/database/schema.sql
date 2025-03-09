CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT,
    role TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_active INT NOT NULL,
    additional_info TEXT
);

CREATE TABLE IF NOT EXISTS settings (
    user_id TEXT PRIMARY KEY,
    theme TEXT,
    resolution TEXT,
    currency TEXT,
    timezone TEXT,
    language TEXT,
    access_level TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS menu_items (
    id TEXT PRIMARY KEY,
    item_name TEXT NOT NULL,
    price REAL NOT NULL,
    item_type TEXT NOT NULL,
    active_item INTEGER NOT NULL,
    dietary_requirement TEXT,
    description TEXT NOT NULL,
    notes TEXT,
    amount_ordered INTEGER NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id TEXT PRIMARY KEY,
    order_number INTEGER NOT NULL,
    user_id TEXT NOT NULL,
    order_status TEXT NOT NULL,
    is_completed INTEGER NOT NULL DEFAULT 0,
    order_type TEXT NOT NULL,
    table_number INTEGER,
    customers INTEGER,
    total REAL NOT NULL,
    special_requests TEXT,
    payment_method TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    order_id TEXT NOT NULL,
    menu_item_id TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (order_id, menu_item_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

CREATE TABLE IF NOT EXISTS ingredients (
    id TEXT PRIMARY KEY,
    item_name TEXT NOT NULL,
    notes TEXT
);

CREATE TABLE IF NOT EXISTS stock (
    id TEXT PRIMARY KEY,
    ingredient_id TEXT NOT NULL,
    stock_status TEXT NOT NULL,
    on_order INTEGER NOT NULL,
    created_at TEXT NOT NULL,
    last_updated TEXT NOT NULL,
    unit_type TEXT NOT NULL,
    numeral REAL NOT NULL,
    desired_quantity REAL DEFAULT 0,
    price_per_unit REAL DEFAULT 0.00,
    low_stock_threshold REAL DEFAULT 0.00,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);

CREATE TABLE IF NOT EXISTS menu_item_ingredients (
    menu_item_id TEXT NOT NULL,
    ingredient_id TEXT NOT NULL,
    numeral REAL NOT NULL,
    PRIMARY KEY (menu_item_id, ingredient_id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);

CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_order_number ON orders(order_number);
CREATE INDEX IF NOT EXISTS idx_menu_items_item_name ON menu_items(item_name);
CREATE INDEX IF NOT EXISTS idx_order_items_menu_item_id ON order_items(menu_item_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);