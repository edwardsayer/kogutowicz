package net.anzix.kogutowicz.style;

import net.anzix.kogutowicz.*;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.Color;

/**
 * Sample style definition with Java API.
 * @author elek
 */
public class TuhuStyleFactory implements MapStyle {

    @Override
    public Cartographer applyStyle(Cartographer map) {
        Layer areas = map.createLayer("areas");

        areas.addFigure(new PolygonFigure(0xead8bd)).addFilter(
                new TagFilter("landuse", "farm"));

        areas.addFigure(new PolygonFigure(0xb6fdb6)).addFilter(
                new TagFilter("leisure", "park"));

        areas.addFigure(new PolygonFigure(0xdddddd)).addFilter(
                new TagFilter("landuse", "residential"));

        areas.addFigure(new PolygonFigure(0x8dc56c)).addFilter(
                new TagFilter("landuse", "forest"));

        areas.addFigure(new PolygonFigure(new Color(223, 209, 214))).addFilter(
                new TagFilter("landuse", "industrial"));

        areas.addFigure(new PolygonFigure(0xbdd0d0)).addFilter(
                new OrFilter(
                new TagFilter("landuse", "reservoir", "water"),
                new TagFilter("natural", "lake", "water")));

        areas.addFigure(new PolygonFigure(0xf2efe9)).addFilter(
                new TagFilter("natural", "land"));


        Layer streets = map.createLayer("streets");


        streets.addFigure(
                new LineFigure(0xbbbbbb, 1).startZoom(10).endZoom(12).addFilter(
                new TagFilter("highway", "tertiary", "residential", "unclassified", "road")));

        streets.addFigure(new CombinedFigure(
                new LineFigure(0x999999, "13:6,14:7.5,15-16:11,17-18:16").startZoom(13),
                new LineFigure(0xffffb3, "13:4.5,14:6,15-16:9.4,17-18:13").startZoom(13)).addFilter(
                new TagFilter("highway", "tertiary")).setZindex(20));

        streets.addFigure(new CombinedFigure(
                new LineFigure(0xa37b48, "9-10:1,11:2,12:2.5,13-14:8.5,15-16:11.5,17-18:16").startZoom(9),
                new LineFigure(0xfed7a5, "12-13:2,13-14:8,15-16:11,17-18:15.5").startZoom(12)).addFilter(
                new TagFilter("highway", "secondary")).setZindex(30));


        streets.addFigure(new CombinedFigure(
                new LineFigure(0x999999, "13:3,14-15:4.5,16:11,17-18:16").startZoom(13),
                new LineFigure(0xffffff, "13:2,14-15:3,16:9.4,17-18:13").startZoom(13)).addFilter(
                new TagFilter("highway", "residential", "minor", "unclassified")));


        streets.addFigure(new LineFigure(0x996600, 1.2f, new float[]{3, 4}).addFilter(
                new TagFilter("highway", "track")).startZoom(13));

        streets.addFigure(new LineFigure(0xB76126, 1f, 3f).addFilter(
                new TagFilter("highway", "footway")).startZoom(14));

        streets.addFigure(new LineFigure(0x666666, 2f).startZoom(13).addFilter(
                new TagFilter("railway", "narrow_gauge")));

        streets.addFigure(new LineFigure(0xcccccc, 1f).startZoom(8).startZoom(9).addFilter(
                new TagFilter("railway", "narrow_gauge", "tram")));



        return map;
    }
}
