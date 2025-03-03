# DPI Configuration in Kogutowicz

This document explains how to use the DPI (dots per inch) configuration feature in Kogutowicz.

## Overview

The DPI configuration feature allows you to specify width values in points (pt) instead of pixels (px) and to configure the DPI value used for the conversion between points and pixels.

Points (pt) are a relative unit of measurement commonly used in typography, where 1 pt = 1/72 inch. Using points instead of pixels makes the width values independent of the screen resolution, resulting in more consistent rendering across different devices and output formats.

## Configuration

### Setting the DPI Value

You can set the DPI value in your properties file using the `map.dpi` property:

```properties
# DPI configuration (default is 72.0)
map.dpi=96.0
```

The default DPI value is 72.0, which is the standard for many systems. Common DPI values include:

- 72 DPI: Standard for many systems
- 96 DPI: Common for Windows displays
- 300 DPI: Common for print output

### Using Point Values in Styles

You can specify width values in points by adding the "pt" suffix to the width value in your CSV style file:

```csv
"layer","zindex","type","startZoom","stopZoom","filter","color","width","pattern"
"roads",,"line",12,12,"highway = trunk","0x477147","2.25pt",
```

When a width value is specified with the "pt" suffix, it will be converted to pixels based on the configured DPI value.

## Examples

### Properties File

```properties
map=ImageMap
map.zoom=12
map.datasource=OsmFile
map.datasource.osmFile=sources/tartu-center.osm
map.mapStyle=CsvMapStyle
map.mapStyle.source=styles/osmstyle-pt.csv
map.renderer=PngRenderer
map.renderer.outputFile=output/tartu-center-pt.png
map.size=1024
map.west=26.70912
map.north=58.38593
map.east=26.72697
map.south=58.37618
# DPI configuration (default is 72.0)
map.dpi=96.0
```

### CSV Style File

```csv
"layer","zindex","type","startZoom","stopZoom","filter","color","width","pattern"
"roads",,"line",12,12,"highway = trunk","0x477147","2.25pt",
"roads",,"line",12,12,"highway = primary","0x8d4346","2.25pt",
"roads",,"line",12,12,"highway = secondary","0xa37b48","1.875pt",
```

## Conversion Formula

The conversion between points and pixels is done using the following formula:

```
pixels = points * (dpi / 72.0)
```

For example, with a DPI of 96:

```
2.25pt = 2.25 * (96 / 72.0) = 3px
```

## Benefits

Using points instead of pixels for width values provides the following benefits:

1. **Consistency**: Width values are consistent across different output formats and devices.
2. **Scalability**: Width values scale proportionally when the DPI changes.
3. **Readability**: Point values are more intuitive for designers familiar with typography.