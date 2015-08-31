Currently you can run the program only from command line:

```
java -jar kogutowicz.jar map.properties
```

You shoud add a parameter file location as a command line parameter. In the parameter file you can choose from the components to render a map

# Structure of the parameter file #

Technically you can build an object hierarchy base on the property file. First of all you can choose the main entry point, a class which implements the MapApplication interface.

```
map=ImageMap
```

You can also use the fully qualified Java class name.

```
map=net.anzix.kogutowicz.app.ImageMap
```

You can set parameters of the class in form _parentProperty.fieldName_, eg.

```
map.datasource=OsmFile
```

You can also set fields on datasource instance as

```
map.datasource.osmFile=nagykovacsi.osm
```

When you set a field with an instance you should type the fully qualified Java name or the short name. When you set a simple vale you can type only the string representation.

```
map.renderer=PngRenderer 
map.west=18.87
```

# Map applications #

Map application is the main entry point for map rendering. Currently you can choose only from two map applcation.

## ImageMap ##

(_Class: net.anzix.kogutowicz.app.ImageMap_)

The image map renders a single map file.

Parameters are:

| **field**| **description**|
|:---------|:---------------|
| datasource | the source of the map datas - an implementation of DataSource interface |
| renderer | the output format (png, pdf, etc.) - an implementation of Renderer interface |
| mapStyle || the map style definition in any format  -  an implementation of MapStyle|
| west     | define the target area of the maps |
| east     | define the target area of the maps |
| north    | define the target area of the maps |
| south    | define the target area of the maps |
| zoom     |  the detail level of the map  |

## TileMap ##

The tile map renders a lot tile images which can be used from OpenLayers.

| **field**| **description**|
|:---------|:---------------|
| datasource | the source of the map datas - an implementation of DataSource interface |
| renderer | the output format (png, pdf, etc.) - an implementation of Renderer interface |
| mapStyle || the map style definition in any format - an implementation of MapStyle|
| west     | define the target area of the maps |
| east     | define the target area of the maps |
| north    | define the target area of the maps |
| south    | define the target area of the maps |
| zoom     | the detail level of the map  |

## Style ##

You can define the sytles of the map elements in various format.

### CsvMapStyle ###

Define the map style in a simple csv format (similar to Kosmos). See also CsvSyntax page.

| **field** | **description** |
|:----------|:----------------|
| source    | the location of csv style |

### !Java style ###

You can also use any Java interface which implements net.anzix.kogutowicz.style.MapStyle.

## Ouput formats ##

### PngRenderer ###

Render map to png format

| **field** | **description** |
|:----------|:----------------|
| outputFile | the location of the outputFile |

### SvgRenderer ###

| **field** | **description** |
|:----------|:----------------|
| outputFile | the location of the outputFile |

### PdfRenderer ###

| **field** | **description** |
|:----------|:----------------|
| outputFile | the location of the outputFile |