## Paths
### save
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

### getCollection
```
GET /ejemplos
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|filters|filters|false|string||
|QueryParameter|fetchs|fetchs|false|string||
|QueryParameter|page|page|false|integer (int32)||
|QueryParameter|rows|rows|false|integer (int32)||
|QueryParameter|sidx|sidx|false|string||
|QueryParameter|sord|sord|false|string||
|BodyParameter|e|e|false|Ejemplo||


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

* application/json;charset=UTF-8

#### Tags

* ejemplo-controller

### getGreeting
```
POST /ejemplos/file
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|ejemplo|Entity in json format|true|string||
|BodyParameter|file|file|true|string||


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

### update
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

### Resource to get a ${nombreObjeto} 
```
GET /ejemplos/{id}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|id|id|true|integer (int32)||
|BodyParameter|e|e|false|Ejemplo||


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

### delete
```
DELETE /ejemplos/{id}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|id|id|true|integer (int32)||
|QueryParameter|userId|userId|true|integer (int32)||
|BodyParameter|e|e|false|Ejemplo||


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

### printWelcome
```
GET /hello
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|model|model|false|object||


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

### hello
```
GET /hello/{name}
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

