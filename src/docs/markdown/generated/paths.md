## Paths
### printWelcome
```
GET /
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|model|model|false|object||
|BodyParameter|locale|locale|false|Locale||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* hello-controller

### Crea una entidad Ejemplo 
```
POST /ejemplos
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|e|e|true|Ejemplo||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Ejemplo|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json;charset=UTF-8

#### Produces

* */*

#### Tags

* ejemplo-controller

### Retorna un objeto GridWrapper con una coleccion de entidades Ejemplo 
```
GET /ejemplos
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|filters|Filtros sobre la entidad|false|string||
|QueryParameter|fetchs|Listado de instanciaciones requeridas de composiciones de la entidad|false|string||
|QueryParameter|page|Pagina solicitada|false|integer (int32)||
|QueryParameter|rows|Cantidad de registros por pagina|false|integer (int32)||
|QueryParameter|sidx|Campo por el que se desea ordenar los resultados|false|string||
|QueryParameter|sord|Modo por el que se desea ordenar los resultados (asc = Ascendente / desc = Descendente)|false|string||
|QueryParameter|searchField|Campo por el que se desea buscar|false|string||
|QueryParameter|searchOper|Operador de busqueda|false|string||
|QueryParameter|searchString|Valor por el que se desea filtrar la busqueda|false|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|GridWrapperOfEjemplo|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* application/json;charset=UTF-8

#### Tags

* ejemplo-controller

### Ejemplo de llamado con un multipart
```
POST /ejemplos/file
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|ejemplo|Entidad|true|string||
|FormDataParameter|file|Archivo|true|file||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* multipart/form-data

#### Produces

* */*

#### Tags

* ejemplo-controller

### Actualiza una entidad Ejemplo 
```
PUT /ejemplos/{id}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|e|e|true|Ejemplo||
|PathParameter|id|id|true|integer (int32)||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Ejemplo|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json;charset=UTF-8

#### Produces

* */*

#### Tags

* ejemplo-controller

### Elimina una entidad Ejemplo 
```
DELETE /ejemplos/{id}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|id|id|true|integer (int32)||
|QueryParameter|userId|userId|true|integer (int32)||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* ejemplo-controller

### Retorna por id una 
```
GET /ejemplos/{id}
```

#### Description

Retorna una unica entidad coincidente con el id pasado en la url. Admite el parametro fetch el listado de las composiciones a inicializar. 

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|id|Id de la entidad|true|integer (int32)||
|QueryParameter|fetchs|Listado de instanciaciones requeridas de composiciones de la entidad|false|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Ejemplo|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* application/json;charset=UTF-8

#### Tags

* ejemplo-controller

### error403
```
GET /errores/403
```

#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* error-controller

### error404
```
GET /errores/404
```

#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* error-controller

### setExtraInformationError
```
POST /exception/error
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|errorId|errorId|true|string||
|QueryParameter|message|message|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* base-exception-controller

### getError
```
GET /exception/error
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|exception|exception|false|string||
|QueryParameter|message|message|false|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* base-exception-controller

### hello
```
GET /{name}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|name|name|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* hello-controller
