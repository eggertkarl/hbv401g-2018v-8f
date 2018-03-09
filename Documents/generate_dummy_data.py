columns_const = ('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                 'U', 'V', 'W', 'X', 'Y', 'Z')


def get_values(plane, rows, columns, isfirstclass):
    total = ""
    txt = "('{t}', {r}, '{c}', {f}),"
    for r in rows:
        for c in columns:
            total += "\n" + txt.format(t=plane, r=r, c=c, f=isfirstclass)
    return total


def get_values_total(plane, n_rows, n_columns, n_columns_firstclass, split):
    total = get_values(plane, range(1, split), columns_const[0:n_columns_firstclass], 1)
    total += get_values(plane, range(split, n_rows + 1), columns_const[0:n_columns], 0)
    return total

name1 = 'Alpha'
name2 = 'Beta'
name3 = 'Gamma'

sql_query = "INSERT INTO AirplaneTypes (AirplaneType) VALUES ('{t1}'), ('{t2}'), ('{t3}');\n".format(t1=name1, t2=name2, t3=name3)
sql_query += 'INSERT INTO FlightSeats (AirplaneType, Row, Column, IsFirstClass)\nVALUES'
sql_query += get_values_total(name1, 32, 6, 4, 7)
sql_query += get_values_total(name2, 28, 6, 6, 7)
sql_query += get_values_total(name3, 20, 4, 4, 4)
sql_query = sql_query[:-1] + ";"
print(sql_query)

