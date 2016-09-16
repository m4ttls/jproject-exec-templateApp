## Definitions
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


### value

description

|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|id|Clave primaria de la entidad Ejemplo|false|integer (int32)||
|codigo|Codigo|true|string||
|descripcion|Descripcion de la entidad|true|string||
|tipo|Propiedad correspondiente a un Enum|true|enum (EXAMPLE_ONE, EXAMPLE_TWO)||
|active|Marca de entidad activa|false|boolean||
|vigenciaDesde|Fecha de vigencia desde|false|string (date-time)||
|vigenciaHasta|Fecha de vigencia hasta|false|string (date-time)||
|deleted|Marca de borrado|false|string||
|version|Version de la entidad para control de concurrencia|false|integer (int32)||


