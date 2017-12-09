
--BASE STATEMENT CONS1

CREATE INDEX unof
ON CHECKOUT (ID, USUARIO_CLIENTE);


CREATE INDEX unos
ON PRODUCTO_CHECKOUT(CHECKOUT_ID);


CREATE INDEX unosq
ON PRODUCTO_CHECKOUT (RESTAURANTE_NOMBRE);

CREATE INDEX TEMP
ON PRODUCTO_CHECKOUT (RESTAURANTE_NOMBRE,CHECKOUT_ID);

CREATE INDEX TIEMPO ON
CHECKOUT(tiempor);

create index HASH_INDEX_TIEMPOR on checkout( to_char(TO_DATE(tiempor), 'DAY'));

DROP INDEX unos;
DROP INDEX unosq;
DROP INDEX unof;
DROP INDEX TEMP;
SELECT DISTINCT US.* 
FROM (SELECT DISTINCT CHECKOUT.USUARIO_CLIENTE
    FROM PRODUCTO_CHECKOUT PK INNER JOIN CHECKOUT ON PK.CHECKOUT_ID = CHECKOUT.ID
    WHERE RESTAURANTE_NOMBRE='Telepizza' AND CHECKOUT.TIEMPOR BETWEEN '01/11/2017' AND '20/11/2017')T1
INNER JOIN USUARIO US ON T1.USUARIO_CLIENTE=US.USUARIO;



--BASE STATEMENT CONS2

SELECT USU.*
FROM USUARIO USU INNER JOIN(
    SELECT *
    FROM PRODUCTO_CHECKOUT PK INNER JOIN CHECKOUT CK ON PK.CHECKOUT_ID=CK.ID 
    WHERE PK.PRODUCTO_NOMBRE='Arroz Blanco' and CK.TIEMPOR BETWEEN '19/11/2017' AND '20/11/2017' AND
    cK.ID NOT IN
    (SELECT PK.CHECKOUT_ID
                FROM PRODUCTO_CHECKOUT PK INNER JOIN CHECKOUT CK ON PK.CHECKOUT_ID=CK.ID 
                WHERE RESTAURANTE_NOMBRE='Tostao'AND CK.TIEMPOR BETWEEN '19/11/2017' AND '20/11/2017')
)CONS ON USU.USUARIO=CONS.USUARIO_CLIENTE;

--BASE STATEMENT CONS3 
select tt.DIASEM,tg.PRODUCTO_NOMBRE,tt.CNT
 from (select diasem, max(cnt)CNT 
        from((select diasem, t2.producto_nombre, count(*) CNT
            FROM (SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
                FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
                (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
                from PRODUCTO_CHECKOUT ppcc where ppcc.restaurante_nombre = 'Tostao') t2 ON t1.id = t2.CHECKOUT_ID 
                group by diasem, t2.PRODUCTO_NOMBRE)) 
        group by diasem)tt 
JOIN (select diasem, t2.producto_nombre, count(*) CNT
     FROM(SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
        FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
        (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
        from PRODUCTO_CHECKOUT ppcc where ppcc.restaurante_nombre = 'Tostao') t2 ON t1.id = t2.CHECKOUT_ID 
        group by diasem, t2.PRODUCTO_NOMBRE)tg
on tt.CNT=tg.CNT AND tt.diasem=tg.diasem;

--SEGUNDO
select tt.DIASEM,tg.PRODUCTO_NOMBRE,tt.CNT
 from (select diasem, min(cnt)CNT 
        from((select diasem, t2.producto_nombre, count(*) CNT
            FROM (SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
                FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
                (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
                from PRODUCTO_CHECKOUT ppcc where ppcc.restaurante_nombre = 'El Corral') t2 ON t1.id = t2.CHECKOUT_ID 
                group by diasem, t2.PRODUCTO_NOMBRE)) 
        group by diasem)tt 
JOIN (select diasem, t2.producto_nombre, count(*) CNT
     FROM(SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
        FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
        (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
        from PRODUCTO_CHECKOUT ppcc where ppcc.restaurante_nombre = 'El Corral') t2 ON t1.id = t2.CHECKOUT_ID 
        group by diasem, t2.PRODUCTO_NOMBRE)tg
on tt.CNT=tg.CNT AND tt.diasem=tg.diasem;



--TERCERO
select tt.DIASEM,tg.RESTAURANTE_NOMBRE,tt.CNT
 from (select diasem, MAX(cnt)CNT 
        from((select diasem, t2.RESTAURANTE_NOMBRE, count(*) CNT
            FROM (SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
                FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
                (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
                from PRODUCTO_CHECKOUT ppcc ) t2 ON t1.id = t2.CHECKOUT_ID 
                group by diasem, t2.RESTAURANTE_NOMBRE)) 
        group by diasem)tt 
JOIN (select diasem, t2.RESTAURANTE_NOMBRE, count(*) CNT
     FROM(SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
        FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
        (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
        from PRODUCTO_CHECKOUT ppcc) t2 ON t1.id = t2.CHECKOUT_ID 
        group by diasem, t2.RESTAURANTE_NOMBRE)tg
on tt.CNT=tg.CNT AND tt.diasem=tg.diasem;


--CUATRO
select tt.DIASEM,tg.RESTAURANTE_NOMBRE,tt.CNT
 from (select diasem, MIN(cnt)CNT 
        from((select diasem, t2.RESTAURANTE_NOMBRE, count(*) CNT
            FROM (SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
                FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
                (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
                from PRODUCTO_CHECKOUT ppcc ) t2 ON t1.id = t2.CHECKOUT_ID 
                group by diasem, t2.RESTAURANTE_NOMBRE)) 
        group by diasem)tt 
JOIN (select diasem, t2.RESTAURANTE_NOMBRE, count(*) CNT
     FROM(SELECT c.id, to_char(to_date(c.TIEMPOR),'DAY') as diasem 
        FROM CHECKOUT c Join PRODUCTO_CHECKOUT pc on c.id = pc.CHECKOUT_ID) t1 JOIN
        (select ppcc.CHECKOUT_ID, ppcc.RESTAURANTE_NOMBRE, ppcc.PRODUCTO_NOMBRE 
        from PRODUCTO_CHECKOUT ppcc) t2 ON t1.id = t2.CHECKOUT_ID 
        group by diasem, t2.RESTAURANTE_NOMBRE)tg
on tt.CNT=tg.CNT AND tt.diasem=tg.diasem order by tg.RESTAURANTE_NOMBRE;

--BASE STATEMENT CONS4
SELECT *FROM((SELECT * FROM((SELECT * FROM CHECKOUT 
INNER JOIN PRODUCTO_CHECKOUT ON CHECKOUT.ID= PRODUCTO_CHECKOUT.CHECKOUT_ID)k INNER JOIN USUARIO ON k.USUARIO_CLIENTE=USUARIO.USUARIO)WHERE ROL = 'Cliente')j 
INNER JOIN PRODUCTO ON PRODUCTO.NOMBRE=j.PRODUCTO_NOMBRE) WHERE CATEGORIA ='Plato fuerte' ;
