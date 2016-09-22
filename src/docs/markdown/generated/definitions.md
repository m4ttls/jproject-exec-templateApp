## Definitions
### Bar

Bar entity

|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|foo||false|Foo||
|id|Bar's primary key|false|integer (int32)||
|date|Date|true|string (date-time)||
|detail|Detail|true|string||
|active|Marca de entidad activa|false|boolean||
|vigenciaDesde|Fecha de vigencia desde|false|string (date-time)||
|vigenciaHasta|Fecha de vigencia hasta|false|string (date-time)||
|deleted|Marca de borrado|false|string||
|version|Version de la entidad para control de concurrencia|false|integer (int32)||


### Foo

Foo entity

|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|id|Foo's primary key|false|integer (int32)||
|code|Code|true|string||
|description|Foo's description|true|string||
|bars|Foo's type|true|Collection«Bar»||
|type|Foo's type|true|enum (EXAMPLE_ONE, EXAMPLE_TWO)||
|active|Marca de entidad activa|false|boolean||
|vigenciaDesde|Fecha de vigencia desde|false|string (date-time)||
|vigenciaHasta|Fecha de vigencia hasta|false|string (date-time)||
|deleted|Marca de borrado|false|string||
|version|Version de la entidad para control de concurrencia|false|integer (int32)||


### ModelAndView
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|empty||false|boolean||
|model||false|object||
|modelMap||false|object||
|reference||false|boolean||
|status||false|enum (100, 101, 102, 103, 200, 201, 202, 203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 302, 303, 304, 305, 307, 308, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 413, 414, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 426, 428, 429, 431, 451, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511)||
|view||false|View||
|viewName||false|string||


### View
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|contentType||false|string||


