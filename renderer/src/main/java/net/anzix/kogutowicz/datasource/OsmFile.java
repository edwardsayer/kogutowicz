/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.core.xml.v0_6.XmlReader;

/**
 *
 * @author elek
 */
public class OsmFile implements DataSource {

    private Map<Element.Id, net.anzix.kogutowicz.element.Element> elements = new HashMap();

    private Double top;

    private Double left;

    private Double bottom;

    private Double right;

    private net.anzix.kogutowicz.element.Node br;

    @NotNull
    private File osmFile;

    private Logger logger = Logger.getLogger(OsmFile.class.getCanonicalName());

    private Set<Element.Id> returned = new HashSet();

    private boolean initialized = false;

    private TileDivision division;

    private Map<TileCoord, Collection<Element>> index = new HashMap();

    public OsmFile(File osmFile) {
        this.osmFile = osmFile;
    }

    public OsmFile() {
    }

    public void setOsmFile(File osmFile) {
        this.osmFile = osmFile;
    }

    @Override
    public void init(TileDivision div) {
        this.division = div;
        if (!initialized) {
            logger.log(Level.FINE, "init osmFile dataSource " + osmFile);
            XmlReader reader = new XmlReader(osmFile, true, CompressionMethod.None);
            //FastXmlReader reader = new FastXmlReader(new File("/home/elek/Documents/nagykovacsi.osm"), true, CompressionMethod.None);
            reader.setSink(new Sink() {

                @Override
                public void process(EntityContainer arg0) {
                    Entity e = arg0.getEntity();
                    if (e instanceof Relation) {
//                    Relation r = (Relation) e;
//
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
//                    elements.put(new Id(net.anzix.kogutowicz.element.Relation.class, r.getId()), relation);
                    } else if (e instanceof Way) {
                        Way w = (Way) e;
                        net.anzix.kogutowicz.element.Way way;
                        if (w.isClosed()) {
                            way = new net.anzix.kogutowicz.element.Area();
                        } else {
                            way = new net.anzix.kogutowicz.element.Way();
                        }
                        way.setId(new Element.Id(net.anzix.kogutowicz.element.Way.class, w.getId()));
                        copyTags(way, e);

                        for (WayNode wn : w.getWayNodes()) {
                            way.addNode((net.anzix.kogutowicz.element.Node) elements.get(new Element.Id(net.anzix.kogutowicz.element.Node.class, wn.getNodeId())));
                        }
                        elements.put(way.getId(), way);
                        indexWay(way);
                    } else if (e instanceof Node) {
                        Node n = (Node) e;
                        net.anzix.kogutowicz.element.Node node = new net.anzix.kogutowicz.element.Node();
                        node.setId(new Element.Id(net.anzix.kogutowicz.element.Node.class, n.getId()));
                        node.setLongitude(n.getLongitude());
                        node.setLatitude(n.getLatitude());
                        copyTags(node, e);
                        elements.put(node.getId(), node);
                        if (top == null) {
                            top = node.getLatitude();
                            bottom = node.getLatitude();
                            right = node.getLongitude();
                            left = node.getLongitude();
                        }
                        if (node.getLongitude() < left) {
                            left = node.getLongitude();
                        }
                        if (node.getLongitude() > right) {
                            right = node.getLongitude();
                        }
                        if (node.getLatitude() > top) {
                            top = node.getLatitude();
                        }
                        if (node.getLatitude() < bottom) {
                            bottom = node.getLatitude();
                        }
                        indexNode(node);

                    }

                }

                @Override
                public void complete() {
                    logger.log(Level.INFO, "File " + osmFile.getAbsolutePath() + " is loaded.");
                    logger.log(Level.INFO, left + " " + top + " " + right + " " + bottom);
                    initialized = true;
                }

                @Override
                public void release() {
                }
            });
            reader.run();
        }
    }

    private void putIndex(TileCoord coord, Element e) {
        Collection<Element> elem = index.get(coord);
        if (elem == null) {
            elem = new ArrayList<Element>();
            index.put(coord, elem);
        }
        elem.add(e);
    }

    private void indexNode(net.anzix.kogutowicz.element.Node node) {
        TileCoord coord = division.getTileCoord(node);
        putIndex(coord, node);
    }

    private void indexWay(net.anzix.kogutowicz.element.Way w) {
        TileCoord min = null;
        TileCoord max = null;
        for (net.anzix.kogutowicz.element.Node n : w.getNodes()) {
            TileCoord curr = division.getTileCoord(n);
            if (min == null) {
                min = new TileCoord(curr);
                max = new TileCoord(curr);
            } else {
                if (min.getX() > curr.getX()) {
                    min.setX(curr.getX());
                }
                if (min.getY() > curr.getY()) {
                    min.setY(curr.getY());
                }
                if (max.getX() < curr.getX()) {
                    max.setX(curr.getX());
                }
                if (max.getY() < curr.getY()) {
                    max.setY(curr.getY());
                }
            }
        }
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                putIndex(new TileCoord(x, y), w);
            }
        }

    }

    public void copyTags(net.anzix.kogutowicz.element.Element ownElement, Entity osmElement) {
        for (Tag tag : osmElement.getTags()) {
            ownElement.addTag(tag.getKey(), tag.getValue());
        }

    }

    @Override
    public Collection<Element> getElements(TileCoord c) {
        return index.get(c) == null ? new ArrayList<Element>() : index.get(c);
    }

    @Override
    public void reset() {
        returned.clear();
    }
}
