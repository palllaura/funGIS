INSERT INTO location (id, coordinates, description)
VALUES (1001, ST_SetSRID(ST_Point(24.7536, 59.4370), 4326),
        'Chanterelle cluster in Tallinn Old Town'),
       (1002, ST_SetSRID(ST_Point(26.7222, 58.3780), 4326),
        'Single porcini in Tartu forest edge'),
       (1003, ST_SetSRID(ST_Point(23.5371, 58.9332), 4326),
        'Morels spotted near Pärnu beach'),
       (1004, ST_SetSRID(ST_Point(25.5976, 58.2496), 4326),
        'Three slippery jacks by Võru trail'),
       (1005, ST_SetSRID(ST_Point(27.0202, 59.3766), 4326),
        'Big portobello south of Narva'),
       (1006, ST_SetSRID(ST_Point(24.4844, 59.3023), 4326),
        'Small cluster of oyster mushrooms'),
       (1007, ST_SetSRID(ST_Point(25.1453, 58.7951), 4326),
        'Fly agaric near Viljandi lake'),
       (1008, ST_SetSRID(ST_Point(24.2537, 58.3922), 4326),
        'Boletus near Haapsalu pine forest'),
       (1009, ST_SetSRID(ST_Point(26.0424, 58.9376), 4326),
        'Tiny chanterelles in Paide meadow'),
       (1010, ST_SetSRID(ST_Point(23.5611, 59.0198), 4326),
        'Giant mushroom near Kärdla')
ON CONFLICT (id) DO NOTHING;

