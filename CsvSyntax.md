You can define the style of the elements using csv. One line is one drawing rule. The order of the fields are irrelevant, but you should use the same table header string as in above.

For a detailed example see samles/styles/osmstyle.csv

| **Field name in heder** | function |
|:------------------------|:---------|
| layer                   | The choose layer of the element. If empty the last defined layer is used. Fill only if you would like to define a new layer. |
| zindex                  | order of rendering (integer) |
| startZoom               | The first zoom level where the rule should be used. (OSM like zoom level, integer between 0 and 20) |
| stopZoom                | The last level where the rule should be used |
| filter                  | Textual filter to filter based on tag values. Basic operators and funcstion can be used (OR, NOT, AND, etc.) See osmstyle.csv for examples. |
| color (column 6.)       | first paramter of the rule (usually the color)|
| width (column 7.)       | second paramter of the rule (usually the width value)|
| pattern (column 8.)     | third  paramter of the rule  |

The parameter values can be defined with zoom depend values.
Eg. value `12:0xEEEEE,13-15:0xFFFFFF` means 0xEEEEEE on zoom 12 and 0xFFFFFF on zoom 13, 14 and 15. Undefined in any other zoom level.