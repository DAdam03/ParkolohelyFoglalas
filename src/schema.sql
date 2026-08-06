PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS cars (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    license TEXT NOT NULL,
	handicapped INTEGER,
	electric INTEGER
	
	--UNIQUE (license)
);


CREATE TABLE IF NOT EXISTS parking_spaces (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
	space_type INTEGER
);


CREATE TABLE IF NOT EXISTS reservations (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
	start_time TEXT,
	end_time TEXT,
	car_id INTEGER,
	parking_space_id INTEGER,
	
	FOREIGN KEY(car_id) 
        REFERENCES cars(id)
        ON DELETE CASCADE
	FOREIGN KEY(parking_space_id) 
        REFERENCES parking_spaces(id)
        ON DELETE CASCADE
);



INSERT OR IGNORE INTO cars(id, license, handicapped, electric)
VALUES
(0, 'ABC-123', 0, 0),
(1, 'DEF-456', 1, 0),
(2, 'GHI-789', 0, 1),
(3, 'JKL-012', 1, 1),
(4, 'MNO-345', 0, 0);



INSERT OR IGNORE INTO parking_spaces(id, space_type)
VALUES
(0, 0),
(1, 0),
(2, 0),
(3, 0),
(4, 0),
(5, 0),
(6, 0),
(7, 0),
(8, 0),
(9, 0),
(10, 1),
(11, 1),
(12, 1),
(13, 2),
(14, 2);



INSERT OR IGNORE INTO reservations(id, start_time, end_time, car_id, parking_space_id)
VALUES
(0, '2026-8-10 10:00:00', '2026-8-10 10:30:00', 0, 0),
(1, '2026-8-10 10:30:00', '2026-8-10 12:00:00', 1, 10),
(2, '2026-8-11 10:00:00', '2026-8-11 16:00:00', 1, 11),
(3, '2026-8-12 12:30:00', '2026-8-12 14:30:00', 2, 13),
(4, '2026-8-13 12:00:00', '2026-8-12 14:50:00', 2, 14),
(5, '2026-8-20 10:00:00', '2026-8-20 12:00:00', 2, 2),
(6, '2026-8-20 11:30:00', '2026-8-20 14:00:00', 3, 3),
(7, '2026-8-22 10:40:00', '2026-8-22 16:30:00', 3, 5),
(8, '2026-8-22 14:30:00', '2026-8-22 17:00:00', 4, 6),
(9, '2026-8-23 16:30:00', '2026-8-23 18:00:00', 4, 7);





