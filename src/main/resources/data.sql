INSERT INTO location (id, coordinates, description)
VALUES (1001, ST_SetSRID(ST_Point(24.1864013671875, 58.89045768672168), 4326),
        'palju seeni (erinevad, tundmatud'),
       (1002, ST_SetSRID(ST_Point(22.557334899902347, 58.86344002718625), 4326),
        'Ükskord leidsin puravikke'),
       (1003, ST_SetSRID(ST_Point(21.88656806945801, 58.287027142291514), 4326),
        'Kuuseriisikad'),
       (1004, ST_SetSRID(ST_Point(27.065227031707767, 57.73395450036673), 4326),
        'Mingid tundmatud seened'),
       (1005, ST_SetSRID(ST_Point(27.315101623535156, 59.040731232252355), 4326),
        'riisikad vist'),
       (1006, ST_SetSRID(ST_Point(24.073791503906254, 59.10478272378236), 4326),
        'Kännuseened (palju)'),
       (1007, ST_SetSRID(ST_Point(23.88839721679688, 59.341068461392304), 4326),
        'Pilvikud (2023 juuli)'),
       (1008, ST_SetSRID(ST_Point(25.571665763854984, 58.88921595820663), 4326),
        'Majaseen Paide Ordulinnuse küljes'),
       (1009, ST_SetSRID(ST_Point(22.313232421875004, 58.39019698411526), 4326),
        'kukeseened!!!!'),
       (1010, ST_SetSRID(ST_Point(24.51103985309601, 58.392876395373584), 4326),
        'Rääma pargis pingi all')
ON CONFLICT (id) DO NOTHING;

