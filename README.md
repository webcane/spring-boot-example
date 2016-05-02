# spring-boot example
some spring boot examples:
* prop-test
* boot-security
* actuator-default
* h2-default


## prop-test

## actuator-default example
1. приложение доступно по следующему url: [http://localhost:8092](http://localhost:8092)
2. доступные конечные точки актуатора:
 - `/autoconfig`
 - `/beans`
 - `/configprops`
 - `/env`
 - `/info`
 - `/metrics`
 - `/logfile`
 - `/trace`

## h2-default example
Spring boot по умолчанию настраивает h2 на работу из памяти
```
```

В примере используются настройки по умолчанию. Подгружаем только начальные данные из скрипта `data.sql`
```
```

Для простоты реализации пример доступен по REST:
```
GET localhost:8090/items
```
