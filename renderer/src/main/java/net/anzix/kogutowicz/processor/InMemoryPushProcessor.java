/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;





/**
 *
 * @author elek
 */
public class InMemoryPushProcessor {

//    private Map<Long, net.anzix.kogutowicz.element.Node> nodes = new HashMap();
//
//    private Map<Long, net.anzix.kogutowicz.element.Way> ways = new HashMap();
//
//    private Map<Long, net.anzix.kogutowicz.element.Relation> relations = new HashMap();
//
//    private Cartographer cartographer;
//
//    private Renderer renderer;
//
//    private SimpleSelector selector;
//
//    private final DrawSpace drawer;
//
//    private int startZoomLevel;
//
//    private int stopZoomLevel;
//
//    private File outputDirectory;
//
//    private File intputFile;
//
//    private Java2DRenderer j2drenderer;
//
//    public InMemoryPushProcessor(Cartographer cartographer, DrawSpace drawer) {
//        this.drawer = drawer;
//        renderer = new BufferedRenderer(j2drenderer = new Java2DRenderer(), cartographer.getLayers());
//        this.cartographer = cartographer;
//        selector = new SimpleSelector(cartographer);
//
//    }
//
//    public void load() {
//        //XmlReader reader = new XmlReader(new File("/home/elek/Documents/nagykovacsi.osm"), true, CompressionMethod.None);
//        XmlReader reader = new XmlReader(intputFile, true, CompressionMethod.None);
//        //FastXmlReader reader = new FastXmlReader(new File("/home/elek/Documents/nagykovacsi.osm"), true, CompressionMethod.None);
//        reader.setSink(new Sink() {
//
//            @Override
//            public void process(EntityContainer arg0) {
//                Entity e = arg0.getEntity();
//                if (e instanceof Relation) {
//                    Relation r = (Relation) e;
//                    net.anzix.kogutowicz.element.Relation relation = new net.anzix.kogutowicz.element.Relation();
//                    copyTags(relation, r);
//                    for (RelationMember member : r.getMembers()) {
//                        if (member.getMemberType().equals(EntityType.Way)) {
//                            net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Way> rm =
//                                    new net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Way>(member.getMemberRole(), ways.get(member.getMemberId()));
//                            relation.addWay(rm);
//                        } else if (member.getMemberType().equals(EntityType.Node)) {
//                            net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Node> rm =
//                                    new net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Node>(member.getMemberRole(), nodes.get(member.getMemberId()));
//                            relation.addNode(rm);
//                        } else {
//                            throw new IllegalArgumentException();
//                        }
//                    }
//
//                    relations.put(r.getId(), relation);
//                } else if (e instanceof Way) {
//                    Way w = (Way) e;
//                    net.anzix.kogutowicz.element.Way way = new net.anzix.kogutowicz.element.Way();
//                    copyTags(way, e);
//
//                    for (WayNode wn : w.getWayNodes()) {
//                        way.addNode(nodes.get(wn.getNodeId()));
//                    }
//                    ways.put(w.getId(), way);
//                } else if (e instanceof Node) {
//                    Node n = (Node) e;
//                    net.anzix.kogutowicz.element.Node node = new net.anzix.kogutowicz.element.Node();
//                    node.setLongitude(n.getLongitude());
//                    node.setLatitude(n.getLatitude());
//                    copyTags(node, e);
//                    nodes.put(n.getId(), node);
//                }
//
//            }
//
//            @Override
//            public void complete() {
//            }
//
//            @Override
//            public void release() {
//            }
//        });
//        reader.run();
//    }
//
//    public void render(Box box, int zoom, File outputFile) {
//        drawer.setTopLeft(box.getTopLeft());
//        drawer.setZoom(zoom);
//        j2drenderer.setOutputFile(outputFile);
//        j2drenderer.setSize(box, zoom);
//        render(box);
//    }
//
//    public void render(Box box) {
//        renderer.initSpace(0, 0, 0, 0, 0);
//        for (net.anzix.kogutowicz.element.Node node : nodes.values()) {
//            if (node.isIntersect(box)) {
//                renderElement(node);
//            }
//        }
//        for (net.anzix.kogutowicz.element.Way way : ways.values()) {
//            if (way.isIntersect(box)) {
//                renderElement(way);
//            }
//        }
//        for (net.anzix.kogutowicz.element.Relation relation : relations.values()) {
//            if (relation.isIntersect(box)) {
//                renderElement(relation);
//            }
//        }
//        renderer.release();
//    }
//
//    public void renderElement(net.anzix.kogutowicz.element.Element ownElement) {
//        try {
//            List<SelectedFigure> figures = selector.getItem(ownElement);
//            for (SelectedFigure figure : figures) {
//                for (GeometryElement ge : figure.getFigure().drawElements(new Mercator(),ownElement)) {
//                    renderer.renderGeometry(figure.getLayer(), ge);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error on rendering " + ownElement.getClass() + " " + ownElement);
//        }
//    }
//
//    public void copyTags(net.anzix.kogutowicz.element.Element ownElement, Entity osmElement) {
//        for (Tag tag : osmElement.getTags()) {
//            ownElement.addTag(tag.getKey(), tag.getValue());
//        }
//
//    }
//
//    public Cartographer getCartographer() {
//        return cartographer;
//    }
//
//    public void setCartographer(Cartographer cartographer) {
//        this.cartographer = cartographer;
//    }
//
//    public File getOutputDirectory() {
//        return outputDirectory;
//    }
//
//    public void setOutputDirectory(File outputDirectory) {
//        this.outputDirectory = outputDirectory;
//    }
//
//    public int getStartZoomLevel() {
//        return startZoomLevel;
//    }
//
//    public void setStartZoomLevel(int startZoomLevel) {
//        this.startZoomLevel = startZoomLevel;
//    }
//
//    public int getStopZoomLevel() {
//        return stopZoomLevel;
//    }
//
//    public void setStopZoomLevel(int stopZoomLevel) {
//        this.stopZoomLevel = stopZoomLevel;
//    }
//
//    public File getIntputFile() {
//        return intputFile;
//    }
//
//    public void setIntputFile(File intputFile) {
//        this.intputFile = intputFile;
//    }
//
//    public void renderTiles(net.anzix.kogutowicz.element.Node topLeft, net.anzix.kogutowicz.element.Node bottomRight, int startZoom, int stopZoom) {
//        for (int zoom = startZoom; zoom <= stopZoom; zoom++) {
//            drawer.setZoom(zoom);
//            Tile tileFrom = new Tile(topLeft.getLatitude(), topLeft.getLongitude(), zoom);
//            Tile tileTo = new Tile(bottomRight.getLatitude(), bottomRight.getLongitude(), zoom);
//            System.out.println("Rendering tiles from " + tileFrom + "to " + tileTo + " at zoom " + zoom);
//            for (int x = tileFrom.getXtile(); x <= tileTo.getXtile(); x++) {
//                for (int y = tileFrom.getYtile(); y <= tileTo.getYtile(); y++) {
//                    Tile curr = new Tile(x, y, zoom);
//                    Tile next = new Tile(x + 1, y + 1, zoom);
//                    drawer.setTopLeft(curr.getTopLeftNode());
//                    File outputFile = new File(outputDirectory, curr.getZoom() + "/" + curr.getXtile() + "/" + curr.getYtile() + ".png");
//                    if (!outputFile.getParentFile().exists()) {
//                        outputFile.getParentFile().mkdirs();
//                    }
//                    j2drenderer.setOutputFile(outputFile);
//                    render(new Box(curr.getTopLeftNode(), next.getTopLeftNode()));
//                }
//            }
//        }
//
//    }
}
